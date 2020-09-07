package com.example.newsapp.Data;
import com.example.newsapp.Data.RegionData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/*
管理各地区疫情数据的类，负责从网页抓取各地疫情数据完成初始化，以及相关数据操作
 */
public class RegionDataManager
{
    private ArrayList<RegionData> AllRegionData=new ArrayList<RegionData>();
    RegionDataManager()
    {

    }
    public ArrayList<ArrayList<Integer>> getNumData(JSONArray numberList)
    {
        ArrayList<ArrayList<Integer>> result=new ArrayList<>();
        return result;
    }
    public void getRegionData()
    {
        OkHttpClient client = new OkHttpClient();
        String url="https://covid-dashboard.aminer.cn/api/dist/epidemic.json";
        Request request = new Request.Builder().url(url).build();
        Response response = null;
        try
        {
            response = client.newCall(request).execute();
            String responseData = response.body().string();
            JSONObject data=new JSONObject(responseData);
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
                AllRegionData.add(new RegionData(country,province,conuty,begin,confirmed,suspected,cured,dead));
            }

        }
        catch (IOException | JSONException e)
        {
            e.printStackTrace();
        }
    }
    public ArrayList<RegionData> getAllRegionData()
    {
        return this.AllRegionData;
    }
}
