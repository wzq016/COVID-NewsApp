package com.example.newsapp.Data;

import org.litepal.crud.LitePalSupport;

/*
新闻浏览记录类，用于存储单个新闻的浏览记录
 */
public class NewsHistory extends LitePalSupport
{
    private String newsID;
    NewsHistory(String newsID)
    {
        super();
        this.newsID=newsID;
    }
    public String getNewsID()
    {
        return this.newsID;
    }
}

