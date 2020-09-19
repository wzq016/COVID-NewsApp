package com.example.newsapp.Data;
import java.util.ArrayList;

/*
某地区的疫情数据
 */
public class RegionData
{
    private String country="";
    private String province="";
    private String county="";
    private String begin="";
    private ArrayList<Integer> confirmed=new ArrayList<Integer>();
    private ArrayList<Integer> suspected=new ArrayList<Integer>();
    private ArrayList<Integer> cured=new ArrayList<Integer>();
    private ArrayList<Integer> dead=new ArrayList<Integer>();
    RegionData(String country,String province,String county,String begin,ArrayList<Integer> confirmed,
               ArrayList<Integer> suspected,ArrayList<Integer> cured,ArrayList<Integer> dead)
    {
        this.country=country;
        this.province=province;
        this.county=county;
        this.begin=begin;
        this.confirmed=confirmed;
        this.cured=cured;
        this.suspected=suspected;
        this.dead=dead;
    }
    public String getCountry()
    {
        return this.country;
    }
    public String getProvince()
    {
        return this.province;
    }
    public String getCounty()
    {
        return this.county;
    }
    public String getBegin()
    {
        return this.begin;
    }
    public ArrayList<Integer> getConfirmed()
    {
        return this.confirmed;
    }
    public ArrayList<Integer> getSuspected()
    {
        return this.suspected;
    }
    public ArrayList<Integer> getCured()
    {
        return  this.cured;
    }
    public ArrayList<Integer> getDead()
    {
        return this.dead;
    }
}
