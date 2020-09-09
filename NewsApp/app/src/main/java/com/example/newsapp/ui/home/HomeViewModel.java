package com.example.newsapp.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.newsapp.Data.EntityManager;
import com.example.newsapp.Data.VirusEntity;

import java.util.ArrayList;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private ArrayList<VirusEntity> law;
    public HomeViewModel() {
        EntityManager wzq=new EntityManager();
        law=wzq.searchByQuery("病毒");
        mText = new MutableLiveData<>();
        mText.setValue(toString());
    }

    public String toString(){
        String out="";
        for (VirusEntity virus:this.law)
        {
            out+=virus.getLable()+' '+virus.getDescription()+'\n';
        }
        return out;
    }


    public LiveData<String> getText() {
        return mText;
    }
}