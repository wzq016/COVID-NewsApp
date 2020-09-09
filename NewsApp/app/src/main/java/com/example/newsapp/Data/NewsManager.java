package com.example.newsapp.Data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.LitePal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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
            CovidNews covidNews=new CovidNews(id,type,title,category,time,lang,source);
            return covidNews;
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return null;
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
        String url="https://covid-dashboard.aminer.cn/api/events/list";
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

        return result;//还没写
    }
    /*
    分类搜索函数，按照类别显示新闻
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
     */
    public CovidNewsWithText getNewsWithText(CovidNews news)
    {
        return new CovidNewsWithText(news);
    }
}
