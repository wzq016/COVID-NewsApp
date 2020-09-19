package com.example.newsapp.Thread;

import com.example.newsapp.Data.CovidNews;
import com.example.newsapp.Data.CovidNewsWithText;


public class GetContentThread implements Runnable {
    private CovidNews one_new;
    private CovidNewsWithText one_new_text;
    public String get_content(){
        return one_new_text.getContent();
    }

    public GetContentThread(CovidNews one_new){
        this.one_new = one_new;
    }

    @Override
    public void run(){
        one_new_text =new CovidNewsWithText(one_new);
    }

}
