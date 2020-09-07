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
管理知疫学者数据相关操作的类
 */
public class ExpertManager
{
    private ArrayList<Expert> experts;
    ExpertManager()
    {
        this.experts=new ArrayList<>();
    }
    public Expert analyseExperrt(JSONObject jsonExpert)
    {
        try
        {
            String avatar=jsonExpert.getString("avatar");
            String id=jsonExpert.getString("id");
            String name=jsonExpert.getString("name");
            String nameZh=jsonExpert.getString("name_zh");
            boolean isPassedAway=jsonExpert.getBoolean("is_passedaway");
            JSONObject indices=jsonExpert.getJSONObject("indices");
            double activity=indices.getDouble("activity");
            int citations=indices.getInt("citations");
            double diversity=indices.getDouble("diversity");
            int gindex=indices.getInt("gindex");
            int hindex=indices.getInt("hindex");
            double sociability=indices.getInt("sociability");
            double newStar=indices.getDouble("newStar");
            double risingStar=indices.getDouble("risingStar");
            int pubs=indices.getInt("pubs");
            JSONObject profile=jsonExpert.getJSONObject("profile");
            Expert re=new Expert(avatar,id,name,nameZh,isPassedAway,activity,citations,
                    diversity,gindex,hindex,sociability,newStar,risingStar,pubs,profile);
            return re;
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return null;
    }
    /*
    从网页获取学者的数据
     */
    public void getExperts()
    {
        OkHttpClient client = new OkHttpClient();
        String url="https://innovaapi.aminer.cn/predictor/api/v1/valhalla/highlight/get_ncov_expers_list?v=2";
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
                this.experts.add(analyseExperrt(tmp));
            }
        }
        catch (IOException | JSONException e)
        {
            e.printStackTrace();
        }
    }
}
