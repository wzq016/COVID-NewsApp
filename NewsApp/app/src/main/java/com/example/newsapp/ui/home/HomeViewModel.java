//package com.example.newsapp.ui.home;
//
//import androidx.lifecycle.LiveData;
//import androidx.lifecycle.MutableLiveData;
//import androidx.lifecycle.ViewModel;
//
//import com.example.newsapp.Activity.
//
//public class HomeViewModel extends ViewModel {
//
//    private MutableLiveData<String> mText;
//
//    public HomeViewModel() {
//        mText = new MutableLiveData<>();
//        mText.setValue("This is home fragment");
//    }
//
//    public LiveData<String> getText() {
//        return mText;
//    }
//}


package com.example.newsapp.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.newsapp.Data.EntityManager;
import com.example.newsapp.Data.VirusEntity;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private EntityManager wzq;
    public HomeViewModel() {
        wzq=new EntityManager();

        mText = new MutableLiveData<>();
        mText.setValue("init val");
    }

    public void Query(){
        ArrayList<VirusEntity> law=wzq.searchByQuery("病毒");
        String out="";
        for (VirusEntity virus:law)
        {
            out+=virus.getLable()+' '+virus.getDescription()+'\n';
            break;
        }
        mText.setValue(out);
    }


    public LiveData<String> getText() {
        return mText;
    }
}