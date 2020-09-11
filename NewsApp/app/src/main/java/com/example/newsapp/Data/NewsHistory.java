package com.example.newsapp.Data;

import org.litepal.crud.LitePalSupport;

/*
新闻浏览记录类，用于存储单个新闻的浏览记录
 */
public class NewsHistory extends LitePalSupport
{
    private String newsID;
    private String title = "1";
    private String date2="2";
    private String seg_text="3";
    private String source="4";
    private String content="";
    public NewsHistory(String newsID,String title,String date2,String seg_text,String source,String cotent)
    {
        super();
        this.newsID=newsID;
        this.title=title;
        this.date2=date2;
        this.seg_text=seg_text;
        this.source=source;
        this.content=cotent;
    }
    public String getTitle()
    {
        return this.title;
    }
    public String getDate2()
    {
        return  this.date2;
    }
    public String getSeg_text()
    {
        return this.seg_text;
    }
    public String getSource()
    {
        return this.source;
    }
    public String getContent()
    {
        return this.content;
    }
    public String getNewsID()
    {
        return this.newsID;
    }
}

