package com.example.newsapp.ui.history;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.newsapp.Adapter.NewsAdapter;
import com.example.newsapp.Data.CovidNews;
import com.example.newsapp.Data.HistoryManager;
import com.example.newsapp.Data.NewsManager;
import com.example.newsapp.R;

import java.util.ArrayList;

public class HistoryFragment extends Fragment {
    private View view;
    private ListView listView;
    private ArrayList<CovidNews> newslist;
    private HistoryManager historyManager;
    private NewsAdapter newsAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_history,container,false);
        listView = view.findViewById(R.id.history_listview);
        init_listview();

        return view;
    }

    private void init_listview(){
        newslist = new ArrayList<CovidNews>();
        historyManager = new HistoryManager();
        newslist.addAll(historyManager.getNewsHistory());
        newsAdapter = new NewsAdapter(getContext(), R.layout.one_news, newslist);
        listView.setAdapter(newsAdapter);
    }


}
