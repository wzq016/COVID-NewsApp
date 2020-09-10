package com.example.newsapp.Data;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

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
    新闻关键词搜索函数，输入为字符串query，返回的搜索结果为排序后的List
     */
    public ArrayList<CovidNews> searchByQuery(String query)
    {
        return null;//还没写
    }

}
