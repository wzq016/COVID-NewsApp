package com.example.newsapp.Data;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import com.example.newsapp.Activity.ShowExpertActivity;
/*
用于描述event聚类的类
 */
public class EventCluster
{
    private int tag=0;
    private String keyword="";
    private ArrayList<CovidNews> events;
    private String filePath="";


    public EventCluster(int tag, String filePath)
    {

        this.tag=tag;
        this.filePath=filePath;
        this.events=new ArrayList<>();
        /*
        File file = new File(filePath);
        System.out.println(filePath);
        try{
            int cnt=1;
            System.out.println("aaaa");
            InputStream is = getResources().getAssets().open(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String s = null;
            while((s = br.readLine())!=null)
            {
                System.out.println(s);
                if(cnt==1)
                {
                    this.keyword=s;
                    cnt++;
                    continue;
                }
                JSONObject searchResult=new JSONObject(s);
                this.events.add(analyseNews(searchResult));
            }
            br.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        */

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
            String date="";
            String segText="";
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
            if(news.has("date"))
                date=news.getString("date");
            if(news.has("seg_text"))
                segText=news.getString("seg_text");
            CovidNews covidNews=new CovidNews(id,type,title,category,time,lang,source,date,segText);
            return covidNews;
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return null;
    }
    public int getTag()
    {
        return this.tag;
    }
    public String getKeyword()
    {
        return this.keyword;
    }
    public ArrayList<CovidNews> getEvents()
    {
        return this.events;
    }
}
