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
            NewsHistory history=new NewsHistory(newsID,news.getTitle(),news.getDate(),news.getSeg_text(),news.getSource(),news.getContent());
            history.save(); //保存历史记录
        }
    }
    /*
    返回所有的浏览记录
     */
    public ArrayList<CovidNews> getNewsHistory()
    {
        ArrayList<CovidNews> result=new ArrayList<>();
        List<NewsHistory> readHistories = LitePal.findAll(NewsHistory.class);
        for(NewsHistory news:readHistories)
            result.add(new CovidNews(news));
        return result;
    }

    /*
    删除单条浏览记录
     */
    public void deleteNewsHistory(String id)
    {
        LitePal.deleteAll(NewsHistory.class, "newsID = ?",id);
        LitePal.deleteAll(CovidNewsWithText.class, "id = ?",id);
    }
    /*
    删除所有浏览记录
     */
    public void deleteAllNewsHistory()
    {
        LitePal.deleteAll(NewsHistory.class);
        LitePal.deleteAll(CovidNewsWithText.class);
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

}
