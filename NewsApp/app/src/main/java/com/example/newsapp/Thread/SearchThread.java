package com.example.newsapp.Thread;

import com.example.newsapp.Data.CovidNews;
import com.example.newsapp.Data.NewsManager;

import java.util.ArrayList;
import java.util.Collections;
public class SearchThread implements Runnable {
    private String content;
    private NewsManager manager;
    public ArrayList<CovidNews> newslist;
    public SearchThread(String content, NewsManager manager, ArrayList<CovidNews> newslist){
        this.content = content;
        this.manager = manager;
        this.newslist = newslist;
    }

    @Override
    public void run(){
        newslist.addAll(manager.searchByQuery(content));
    }

}
