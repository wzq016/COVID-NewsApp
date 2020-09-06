package com.example.newsapp.Data;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.crud.LitePalSupport;
import com.example.newsapp.Data.CovidNews;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;
/*
查询后返回的新冠疫情新闻类，含正文等
 */
public class CovidNewsWithText extends LitePalSupport
{
    private String id = "";
    private String type = "";
    private String title = "";
    private String category = "";
    private String time = "";
    private String lang = "";
    private String url ="";
    private String content="";
    private String date="";
    private String source="";
    private String seg_text="";
    private JSONArray relatedEvents;
    public CovidNewsWithText(CovidNews news)
    {
        super();
        this.id = news.getId();
        this.category = news.getCategory();
        this.type = news.getType();
        this.time = news.getTime();
        this.title = news.getTitle();
        this.lang = news.getLang();
        this.url = news.getUrl();
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(this.url).build();
        Response response = null;
        try
        {
            response = client.newCall(request).execute();
            String responseData = response.body().string();
            JSONObject NewsText=new JSONObject(responseData);
            JSONObject data=NewsText.getJSONObject("data");
            this.content=data.getString("content");
            this.date=data.getString("date");
            if (data.has("source"))
            {
                this.source=data.getString("source");
            }
            this.seg_text=data.getString("seg_text");
            this.relatedEvents=data.getJSONArray("related_events");
        }
        catch (IOException | JSONException e)
        {
            e.printStackTrace();
        }
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
    public String getContent()
    {
        return this.content;
    }
    public String getDate()
    {
        return this.date;
    }
    public String getSource()
    {
        return this.source;
    }
    public String getSeg_text()
    {
        return this.seg_text;
    }
    public JSONArray getRelatedEvents()
    {
        return this.relatedEvents;
    }
}
