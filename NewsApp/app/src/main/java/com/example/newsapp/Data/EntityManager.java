package com.example.newsapp.Data;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/*
实体管理类，用来进行实体相关的操作
 */
public class EntityManager
{
    /*
    用于解析json格式的数据，生成对应的实体对象
    Verified
    */
    public VirusEntity analyseEntity(JSONObject eneity)
    {
        try
        {
            double hot=eneity.getDouble("hot");
            String label=eneity.getString("label");
            String url=eneity.getString("url");
            JSONObject abstractInfo=eneity.getJSONObject("abstractInfo");
            String description=abstractInfo.getString("enwiki")+"\n"+abstractInfo.getString("baidu")
                    +"\n"+ abstractInfo.getString("zhwiki");
            JSONObject covid=abstractInfo.getJSONObject("COVID");
            JSONObject properties=covid.getJSONObject("properties");
            JSONArray relations=covid.getJSONArray("relations");
            String img=eneity.getString("img");
            VirusEntity virus=new VirusEntity(hot,label,url,description,properties,relations,img);
            return virus;
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return null;
    }
    /*
    根据输入的query直接调用接口，返回查询的实体结果
    Verified
     */
    public ArrayList<VirusEntity> searchByQuery(String query)
    {
        ArrayList<VirusEntity> result=new ArrayList<>();
        OkHttpClient client = new OkHttpClient();
        String url="https://innovaapi.aminer.cn/covid/api/v1/pneumonia/entityquery?entity="+query;
        Request request = new Request.Builder().url(url).build();
        Response response = null;
        try
        {
            response = client.newCall(request).execute();
            String responseData = response.body().string();
            JSONObject searchResult=new JSONObject(responseData);
            JSONArray data=searchResult.getJSONArray("data");
            for (int i=0;i<data.length();i++)
            {
                JSONObject tmp=data.getJSONObject(i);
                result.add(analyseEntity(tmp));
            }

        }
        catch (IOException | JSONException e)
        {
            e.printStackTrace();
        }
        return result;
    }
}
