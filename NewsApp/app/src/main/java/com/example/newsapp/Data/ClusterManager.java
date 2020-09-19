package com.example.newsapp.Data;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import com.example.newsapp.Activity.ShowExpertActivity;
/*
聚类管理类
 */
public class ClusterManager
{
    private ArrayList<EventCluster> clusters;
    public ClusterManager()
    {
        this.clusters=new ArrayList<>();
        for(int i=0;i<5;i++)
        {
            Integer t=i;
            String filePath="enents_cluster_"+t.toString()+".jsonl";
            this.clusters.add(new EventCluster(i,filePath));
        }
    }
    public ArrayList<EventCluster> getClusters()
    {
        return this.clusters;
    }
}
