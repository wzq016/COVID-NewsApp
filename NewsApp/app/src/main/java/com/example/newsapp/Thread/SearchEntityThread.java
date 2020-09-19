package com.example.newsapp.Thread;




import com.example.newsapp.Data.EntityManager;
import com.example.newsapp.Data.VirusEntity;

import java.util.ArrayList;
import java.util.Collections;
public class SearchEntityThread implements Runnable {
    private String content;
    private EntityManager manager;
    public ArrayList<VirusEntity> ve_list;
    public SearchEntityThread(String content, EntityManager manager, ArrayList<VirusEntity> ve_list){
        this.content = content;
        this.manager = manager;
        this.ve_list = ve_list;
    }

    @Override
    public void run(){
        ve_list.addAll(manager.searchByQuery(content));
    }

}

