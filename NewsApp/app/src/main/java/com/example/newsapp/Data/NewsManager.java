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
    public void addToNewsHistory(CovidNewsWithText news)
    {
        String newsID=news.getId();
        List<NewsHistory> readHistories = LitePal.where("newsID = ?",newsID).find(NewsHistory.class);
        if (readHistories.size()==0)
        {
            NewsHistory history=new NewsHistory(newsID);
            history.save(); //保存历史记录
            news.save(); //离线保存新闻
        }
        else
        {
            NewsHistory history=new NewsHistory(newsID);
            history.updateAll("newsID= ? ",newsID);
        }
    }
    /*
    添加一条query到本地搜索记录
     */
    public void addToSearchHistory(SearchHistory searchHistory)
    {
        searchHistory.save();
    }
    /*
    返回所有的搜索记录
     */
    public ArrayList<String> getSearchHistory()
    {
        List<SearchHistory> searchHistories = LitePal.where().find(SearchHistory.class);
        ArrayList<String> result=new ArrayList<>();
        for (SearchHistory history:searchHistories)
        {
            result.add(history.getQuery());
        }
        return result;
    }
    /*
    展示历史记录
     */
    public ArrayList<CovidNews> showHistory()
    {
        ArrayList<NewsHistory> readHistories =new ArrayList<>();
        List<NewsHistory> tmpList = LitePal.where().find(NewsHistory.class);
        if(tmpList.size() > 0)
        {
            readHistories.addAll(tmpList);
        }
        ArrayList<CovidNews> news = new ArrayList<>();
        for (NewsHistory history : readHistories)
        {
            CovidNewsWithText covidNews = LitePal.where("newsID = ?", history.getNewsID()).findFirst(CovidNewsWithText.class);
            news.add(new CovidNews((covidNews)));
        }
        return news;
    }
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
            splitList.add(list.get(0));
        }
        /*
        计算每个keyword的idf
         */
        HashMap<String,Double> idf=new HashMap<String,Double>();
        for (String keyword:splitList)
        {
            int count=0;
            for (CovidNews news:newsList)
            {
                if(news.getSegText().contains(keyword))
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
            String[] seg=news.getSegText().split(" ");
            List<String> segWords= Arrays.asList(seg);
            for (String keyword:splitList)
            {
                double tf=((double)Collections.frequency(segWords,keyword)/(double)segWords.size());
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
        String url="https://covid-dashboard.aminer.cn/api/events/list?size=1000";
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
        System.out.println(result.size());
        return result;
    }
    /*
    分类搜索函数，按照类别显示新闻
    verified
     */
    public ArrayList<CovidNews> newsClassify(String type,ArrayList<CovidNews> newsToBeClassified)
    {
        ArrayList<CovidNews> newsSelected=new ArrayList<>();
        for (CovidNews news:newsToBeClassified)
        {
            if(news.getType().equals(type))
            {
                newsSelected.add(news);
            }
        }
        return newsSelected;
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
    排序函数，对新闻List按照PageRank算法排序
     */
    public void sortByPagerank(ArrayList<CovidNews> newsList)
    {

    }
}
