package com.example.newsapp.Data;

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


    public CovidNews(String id,String type,String title,String category,String time,String lang,String source)
    {
        this.id=id;
        this.type=type;
        this.category=category;
        this.lang=lang;
        this.time=time;
        this.title=title;
        this.url="https://covid-dashboard.aminer.cn/api/event/"+this.id;
        this.source=source;
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

