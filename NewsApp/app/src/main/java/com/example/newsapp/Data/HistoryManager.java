package com.example.newsapp.Data;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;
import org.w3c.dom.CDATASection;

import java.util.ArrayList;
import java.util.List;
/*
管理历史记录的类
 */
public class HistoryManager
{
    public HistoryManager()
    {

    }
    /*
    添加一条新闻到浏览记录，并离线新闻
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
    public void addToSearchHistory(String query)
    {
        SearchHistory searchHistory=new SearchHistory(query);
        List<SearchHistory> readHistories = LitePal.where("query = ?",query).find(SearchHistory.class);
        if (readHistories.size()==0)
        {
            searchHistory.save();
        }
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
    删除单条搜索记录
     */
    public void deleteSearchHistory(String query)
    {
        LitePal.deleteAll(SearchHistory.class, "query = ?",query);
    }
    /*
    删除所有搜索记录
     */
    public void deleteAllSearchHistory()
    {
        LitePal.deleteAll(SearchHistory.class);
    }
    /*
    展示历史记录
     */
    public ArrayList<CovidNews> getNewsHistory()
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
}
