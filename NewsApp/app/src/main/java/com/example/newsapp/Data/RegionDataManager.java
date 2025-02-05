package com.example.newsapp.Data;
import com.example.newsapp.Data.RegionData;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

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
    private ArrayList<RegionData> provinceData=new ArrayList<>();
    private ArrayList<RegionData> countryData=new ArrayList<>();
    public RegionDataManager()
    {

    }
    /*
    从服务器获取所有地区的疫情数据情况并存入AllRegionData中
    然后选择出中国各省的数据和全球各国的数据
    verified
     */
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
                String numbers=value.getString("data");
                ArrayList<ArrayList<Integer>> dataList=new Gson().fromJson(numbers,new TypeToken<ArrayList<ArrayList<Integer>>>(){}.getType());
                ArrayList<Integer> confirmed=new ArrayList<Integer>();
                ArrayList<Integer> suspected=new ArrayList<Integer>();
                ArrayList<Integer> cured=new ArrayList<Integer>();
                ArrayList<Integer> dead=new ArrayList<Integer>();
                for (ArrayList<Integer> tmpList:dataList)
                {
                    confirmed.add(tmpList.get(0));
                    suspected.add(tmpList.get(1));
                    cured.add(tmpList.get(2));
                    dead.add(tmpList.get(3));
                }
                RegionData regionData=new RegionData(country,province,conuty,begin,confirmed,suspected,cured,dead);
                if(regionData.getProvince().equals("")&&regionData.getCounty().equals(""))
                {
                    countryData.add(regionData);
                }
                if(regionData.getCountry().equals("China")&&regionData.getCounty().equals("")&&!regionData.getProvince().equals(""))
                {
                    provinceData.add(regionData);
                }
                AllRegionData.add(regionData);
            }
        }
        catch (IOException | JSONException e)
        {
            e.printStackTrace();
        }
    }
    /*
    获取全部地区的疫情数据
     */
    public ArrayList<RegionData> getAllRegionData()
    {
        return this.AllRegionData;
    }
    /*
    获取中国各省的疫情数据
     */
    public ArrayList<RegionData> getProvinceData()
    {
        return this.provinceData;
    }
    /*
    获取全球各国的疫情数据
     */
    public ArrayList<RegionData> getCountryData()
    {
        return this.countryData;
    }
}
