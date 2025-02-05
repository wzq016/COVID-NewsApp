package com.example.newsapp.Data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.LitePal;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.lang.Math;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import com.example.newsapp.ui.search.History;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.huaban.analysis.jieba.*;
/*
新闻排序用的比较器类,用于时间排序
verified
 */
class newsComparator implements Comparator {
    public int compare(Object o1, Object o2) {
        CovidNews news1 = (CovidNews) o1;
        CovidNews news2 = (CovidNews) o2;
        try
        {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
            Date d1=sdf.parse(news1.getDate());
            Date d2=sdf.parse(news2.getDate());
            if(d1.after(d2))
                return -1;
            else
                return 1;
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return 1;
    }
}
/*
新闻排序用的比较器类,用于按照tfidf分数排序
 */
class newsTfIdfComparator implements Comparator {
    public int compare(Object o1, Object o2) {
        CovidNews news1 = (CovidNews) o1;
        CovidNews news2 = (CovidNews) o2;
        double score1=news1.getTfidfScore();
        double score2=news2.getTfidfScore();
        if(score1<score2)
            return 1;
        else
            return -1;
    }
}
/*
用来管理新闻数据的类
 */
public class NewsManager
{
    private int searchNum; //新闻关键词搜索时搜索的范围
    public NewsManager()
    {
        this.searchNum=1000;
    }
    /*
    添加单个新闻到历史记录并离线保存
     */

    /*
    解析新闻的函数，将json格式的新闻解析为CovidNews类
    verified
     */
    public CovidNews analyseNews(JSONObject news)
    {
        try
        {
            String id="";
            String type="";
            String category="";
            String title="";
            String source="";
            String time="";
            String lang="";
            String date="";
            String segText="";
            if(news.has("_id"))
                id=news.getString("_id");
            if(news.has("type"))
                type=news.getString("type");
            if(news.has("category"))
                category=news.getString("category");
            if(news.has("title"))
                title=news.getString("title");
            if(news.has("source"))
                source=news.getString("source");
            if(news.has("time"))
                time=news.getString("time");
            if(news.has("lang"))
                lang=news.getString("lang");
            if(news.has("date"))
                date=news.getString("date");
            if(news.has("seg_text"))
                segText=news.getString("seg_text");
            CovidNews covidNews=new CovidNews(id,type,title,category,time,lang,source,date,segText);
            return covidNews;
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return null;
    }
    /*
    计算tfidf分数
     */
    public void setScore(String query,ArrayList<CovidNews> newsList)
    {
        /*
        先对query进行分词
         */
        JiebaSegmenter segmenter = new JiebaSegmenter();
        String jiebaOutput=segmenter.process(query, JiebaSegmenter.SegMode.SEARCH).toString();
        ArrayList<ArrayList<String>> rawSplitList=new Gson().fromJson(jiebaOutput,new TypeToken<ArrayList<ArrayList<String>>>(){}.getType());
        ArrayList<String> splitList=new ArrayList<>();
        for (ArrayList<String> list:rawSplitList)
        {
            if(list.get(0)!=null)
                splitList.add(list.get(0).toLowerCase());
        }

        /*
        计算每个keyword的idf
         */
        HashMap<String,Double> idf=new HashMap<>();
        for (String keyword:splitList)
        {
            int count=0;
            for (CovidNews news:newsList)
            {
                if(news.getSegText().toLowerCase().contains(keyword))
                    count++;
            }
            double idfScore=Math.log((double)(1+newsList.size())/(double)(count+1));
            idf.put(keyword,idfScore);
        }
        /*
        计算每条新闻的tf-idf分数
         */
        for (CovidNews news:newsList)
        {
            double tfidfScore=0.0;
            String[] seg=news.getSegText().toLowerCase().split(" ");
            List<String> segWords= Arrays.asList(seg);
            for (String keyword:splitList)
            {
                double tf=((double)Collections.frequency(segWords,keyword.toLowerCase())/(double)segWords.size());
                tfidfScore+=tf*idf.get(keyword);
            }
            news.setTfidfScore(tfidfScore);
        }
    }
    /*
    新闻关键词搜索函数，输入为字符串query，返回的搜索结果为排序后的List
     */
    public ArrayList<CovidNews> searchByQuery(String query)
    {
        /*
        返回前20条新闻
         */
        ArrayList<CovidNews> result=new ArrayList();
        String url="https://covid-dashboard.aminer.cn/api/events/list?size=500";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Response response = null;
        try
        {
            response = client.newCall(request).execute();
            String responseData = response.body().string();
            JSONObject searchResult=new JSONObject(responseData);
            JSONArray data=searchResult.getJSONArray("data");
            for (int i=0;i<data.length();i++)
            {
                JSONObject tmp=data.getJSONObject(i);
                result.add(analyseNews(tmp));
            }
        }
        catch (IOException | JSONException e)
        {
            e.printStackTrace();
        }
        setScore(query,result);
        newsTfIdfComparator cmp=new newsTfIdfComparator();
        Collections.sort(result,cmp);
        HistoryManager m=new HistoryManager();
        m.tagNewsList(result);
        return result;
    }
    public ArrayList<CovidNews> getNews()
    {
        ArrayList<CovidNews> result=new ArrayList();
        String url="https://covid-dashboard.aminer.cn/api/events/list?size=300";
        OkHttpClient client = new OkHttpClient();
        try
        {
            Request request = new Request.Builder().url(url).build();
            Response response = null;
            response = client.newCall(request).execute();
            String responseData = response.body().string();
            JSONObject searchResult=new JSONObject(responseData);
            JSONArray data=searchResult.getJSONArray("data");
            for (int i=0;i<data.length();i++)
            {
                JSONObject tmp=data.getJSONObject(i);
                result.add(analyseNews(tmp));
            }
        }
        catch (IOException | JSONException e)
        {
            e.printStackTrace();
        }
        sortByDate(result);
        HistoryManager m=new HistoryManager();
        m.tagNewsList(result);
        return result;
    }
    /*
    分类搜索函数，按照类别显示新闻
    verified
     */
    public ArrayList<CovidNews> newsClassify(String type)
    {
        ArrayList<CovidNews> result=new ArrayList();
        String url1="https://covid-dashboard.aminer.cn/api/events/list?type=paper&size=150";
        String url2="https://covid-dashboard.aminer.cn/api/events/list?type=news&size=150";
        String url="";
        if (type.equals("news"))
            url=url2;
        else
            url=url1;
        OkHttpClient client = new OkHttpClient();
        try
        {
            Request request = new Request.Builder().url(url).build();
            Response response = null;
            response = client.newCall(request).execute();
            String responseData = response.body().string();
            JSONObject searchResult=new JSONObject(responseData);
            JSONArray data=searchResult.getJSONArray("data");
            for (int i=0;i<data.length();i++)
            {
                JSONObject tmp=data.getJSONObject(i);
                result.add(analyseNews(tmp));
            }
        }
        catch (IOException | JSONException e)
        {
            e.printStackTrace();
        }
        sortByDate(result);
        HistoryManager m=new HistoryManager();
        m.tagNewsList(result);
        return result;
    }
    /*
     按照新闻id返回新闻正文对象
     verified
     */
    public CovidNewsWithText getNewsWithText(CovidNews news)
    {
        return new CovidNewsWithText(news);
    }
    /*
    排序函数，对新闻List按照时间排序，越新的新闻越靠前
    verified
     */
    public void sortByDate(ArrayList<CovidNews> newsList)
    {
        newsComparator cmp=new newsComparator();
        Collections.sort(newsList,cmp);
    }
    /*
    离线返回新闻信息，根据离线的NewsHistory返回新闻正文
     */
    public CovidNewsWithText getOfflineNews(CovidNews news)
    {
        NewsHistory his=LitePal.where("newsID = ?",news.getId()).findFirst(NewsHistory.class);
        return new CovidNewsWithText(his);
    }
}
