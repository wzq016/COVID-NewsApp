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
        for(EventCluster cluster:this.clusters)
        {
            System.out.println(cluster.getKeyword()+'\n');
            for(CovidNews event:cluster.getEvents())
            {
                System.out.println(event.getId());
                System.out.println(event.getTitle());
            }
            System.out.println("*********************");
        }
    }
    public ArrayList<EventCluster> getClusters()
    {
        return this.clusters;
    }
}
