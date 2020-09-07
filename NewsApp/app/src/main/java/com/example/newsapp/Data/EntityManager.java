package com.example.newsapp.Data;

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
     */
    public VirusEntity analyseEntity(JSONObject eneity)
    {
        try
        {
            double hot=eneity.getDouble("hot");
            String lable=eneity.getString("lable");
            String url=eneity.getString("url");
            JSONObject abstractInfo=eneity.getJSONObject("abstractInfo");
            String description=abstractInfo.getString("enwiki")+"\n"+abstractInfo.getString("baidu")
                    +"\n"+ abstractInfo.getString("zhwiki");
            JSONObject covid=abstractInfo.getJSONObject("COVID");
            JSONObject properties=covid.getJSONObject("properties");
            JSONArray relations=covid.getJSONArray("relations");
            String img=eneity.getString("img");
            VirusEntity virus=new VirusEntity(hot,lable,url,description,properties,relations,img);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

    }
    public VirusEntity searchByQuery(String query)
    {
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
            Iterator iter = data.keys();
            while(iter.hasNext())
            {
                String key = (String) iter.next();
                JSONObject value = data.getJSONObject(key);
                String[] regions=key.split("\\|");
                String country=regions[0];
                String province="";
                String conuty="";
                if (regions.length>1)
                {
                    province=regions[1];
                }
                if (regions.length>2)
                {
                    conuty=regions[2];
                }
                String begin=value.getString("begin");
                JSONArray numberList=value.getJSONArray("data");
                ArrayList<ArrayList<Integer>> dataList=getNumData(numberList);//等待实现
                ArrayList<Integer> confirmed=new ArrayList<Integer>();
                ArrayList<Integer> suspected=new ArrayList<Integer>();
                ArrayList<Integer> cured=new ArrayList<Integer>();
                ArrayList<Integer> dead=new ArrayList<Integer>();
                for (ArrayList<Integer> tmpList:dataList)
                {
                    confirmed.add(tmpList.indexOf(0));
                    suspected.add(tmpList.indexOf(1));
                    cured.add(tmpList.indexOf(2));
                    dead.add(tmpList.indexOf(3));
                }
            }

        }
        catch (IOException | JSONException e)
        {
            e.printStackTrace();
        }
    }
}
