package com.example.newsapp.Data;

import org.litepal.crud.LitePalSupport;

/*
新闻浏览记录类，用于存储单个新闻的浏览记录
 */
public class NewsHistory extends LitePalSupport
{
    private String newsID;
    public String title = "1";
    public String date2="2";
    public String seg_text="3";
    public String source="4";
    public NewsHistory(String newsID,String title,String date2,String seg_text,String source)
    {
        super();
        this.newsID=newsID;
        this.title=title;
        this.date2=date2;
        this.seg_text=seg_text;
        this.source=source;
    }
    public String getNewsID()
    {
        return this.newsID;
    }
}

