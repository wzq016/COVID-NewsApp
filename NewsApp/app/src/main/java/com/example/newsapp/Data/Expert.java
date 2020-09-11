package com.example.newsapp.Data;

import org.json.JSONException;
import org.json.JSONObject;

/*
新冠知疫学者类
 */
public class Expert
{
    private String avatar="";
    private String id="";
    private String name="";
    private String nameZh="";
    private boolean isPassedAway;
    private double activity;
    private int citations;
    private double diversity;
    private int gindex;
    private int hindex;
    private double sociability;
    private double newStar;
    private double risingStar;
    private  int pubs;
    private JSONObject profile;
    private String affiliation=""; //从属机构
    private String biography="";
    private String education="";
    private String position="";
    private String work=""; //工作经历
    Expert(String avatar, String id,String name,String nameZh,boolean isPassedAway,double activity,int citations,
           double diversity,int gindex,int hindex,double sociability,double newStar,double risingStar,int pubs,JSONObject profile)
    {
        this.avatar=avatar;
        this.id=id;
        this.name=name;
        this.nameZh=nameZh;
        this.isPassedAway=isPassedAway;
        this.activity=activity;
        this.citations=citations;
        this.diversity=diversity;
        this.gindex=gindex;
        this.hindex=hindex;
        this.sociability=sociability;
        this.newStar=newStar;
        this.risingStar=risingStar;
        this.pubs=pubs;
        this.profile=profile;
        try
        {
            this.affiliation=profile.getString("affiliation");
            this.biography=profile.getString("bio");
            this.education=profile.getString("edu");
            this.position=profile.getString("position");
            this.work=profile.getString("work");
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

    }
    public String  getAffiliation()
    {
        return this.affiliation;
    }
    public String getBiography()
    {
        return this.biography;
    }
    public String getEducation()
    {
        return this.education;
    }
    public String getPosition()
    {
        return this.position;
    }
    public String getWork()
    {
        return this.work;
    }
    public String getAvatar()
    {
        return this.avatar;
    }
    public String getId()
    {
        return this.id;
    }
    public boolean getIsPassedAway()
    {
        return this.isPassedAway;
    }
    public String getName()
    {
        return this.name;
    }
    public String getNameZh()
    {
        return this.nameZh;
    }
    public double getActivity()
    {
        return this.activity;
    }
    public int getCitations()
    {
        return this.citations;
    }
    public int getGindex()
    {
        return this.gindex;
    }
    public int getHindex()
    {
        return this.hindex;
    }
    public int getPubs()
    {
        return  this.pubs;
    }
    public JSONObject getProfile()
    {
        return this.profile;
    }
    public double getDiversity()
    {
        return this.diversity;
    }
    public  double getSociability()
    {
        return this.sociability;
    }
    public double getNewStar()
    {
        return this.newStar;
    }
    public double getRisingStar()
    {
        return this.risingStar;
    }
}
