package com.example.newsapp.Data;

import org.json.JSONArray;
import org.json.JSONObject;

/*
搜索实体类
 */
public class VirusEntity
{
    private double hot;
    private String lable="";
    private String url="";
    private String description="";
    private JSONObject properties;
    private JSONArray relations;
    private String img="";
    VirusEntity(double hot,String lable,String url,String description,JSONObject properties,JSONArray relations,String img)
    {
        this.hot=hot;
        this.lable=lable;
        this.url=url;
        this.description=description;
        this.properties=properties;
        this.relations=relations;
        this.img=img;
    }
    public double getHot()
    {
        return this.hot;
    }
    public String getLable()
    {
        return this.lable;
    }
    public String getUrl()
    {
        return this.url;
    }
    public String getDescription()
    {
        return this.description;
    }
    public JSONObject getProperties()
    {
        return this.properties;
    }
    public JSONArray getRelations()
    {
        return this.relations;
    }
    public String getImg()
    {
        return this.img;
    }
}
