package com.example.newsapp.Data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/*
新冠疫情新闻类
 */
public class CovidNews
{
    private String id = "";
    private String type = "";
    private String title = "";
    private String category = "";
    private String time = "";
    private String lang = "";
    private String url ="";
    private String source="";
    private String date="";
    private String segText="";
    private double tfidfScore=0.0;

    public CovidNews(String id,String type,String title,String category,String time,String lang,String source,String date,String segText)
    {
        this.id=id;
        this.type=type;
        this.category=category;
        this.lang=lang;
        this.time=time;
        this.title=title;
        this.url="https://covid-dashboard.aminer.cn/api/event/"+this.id;
        this.source=source;
        this.date=date;
        this.segText=segText;
        try
        {
            SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
            Date s=sdf.parse(this.date);
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.US);
            this.date=sdf.format(s).toString();
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
    }
    public CovidNews(CovidNewsWithText news)
    {
        this.id=news.getId();
        this.type=news.getType();
        this.title=news.getTitle();
        this.category=news.getCategory();
        this.time=news.getTime();
        this.lang=news.getLang();
        this.url=news.getUrl();
    }
    /*
    override排序接口
     */
    public int compareTo(CovidNews news)
    {
        try
        {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
            Date d1=sdf.parse(this.date);
            Date d2=sdf.parse(news.date);
            if(d1.after(d2))
                return 1;
            else
                return -1;
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return 1;
    }
    public String getSegText()
    {
        return this.segText;
    }
    public double getTfidfScore()
    {
        return this.tfidfScore;
    }
    public void setTfidfScore(double score)
    {
        this.tfidfScore=score;
    }
    public String getDate()
    {
        return this.date;
    }
    public String getSource()
    {
        return this.source;
    }
    public String getId()
    {
        return this.id;
    }
    public String getType()
    {
        return this.type;
    }
    public String getTitle()
    {
        return this.title;
    }
    public String getCategory()
    {
        return this.category;
    }
    public String getTime()
    {
        return this.time;
    }
    public String getLang()
    {
        return this.lang;
    }
    public String getUrl()
    {
        return this.url;
    }
}

