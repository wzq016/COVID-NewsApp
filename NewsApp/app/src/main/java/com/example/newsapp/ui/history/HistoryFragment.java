package com.example.newsapp.ui.history;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.newsapp.Activity.ShowNewsActivity;
import com.example.newsapp.Adapter.NewsAdapter;
import com.example.newsapp.Data.CovidNews;
import com.example.newsapp.Data.CovidNewsWithText;
import com.example.newsapp.Data.HistoryManager;
import com.example.newsapp.Data.NewsManager;
import com.example.newsapp.R;
import com.example.newsapp.Thread.GetContentThread;

import java.util.ArrayList;

public class HistoryFragment extends Fragment {
    private View view;
    private ListView listView;
    private ArrayList<CovidNews> newslist;
    private HistoryManager historyManager;
    private NewsAdapter newsAdapter;
    private NewsManager newsManager;
    private Button btn_del_history;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_history,container,false);
        listView = view.findViewById(R.id.history_listview);
        newsManager = new NewsManager();
        btn_del_history = view.findViewById(R.id.btn_del_hitstory);

        init_listview();

        return view;
    }

    private void init_listview(){
        newslist = new ArrayList<CovidNews>();
        historyManager = new HistoryManager();
        newslist.addAll(historyManager.getNewsHistory());

        newsAdapter = new NewsAdapter(getContext(), R.layout.one_news, newslist);
        listView.setAdapter(newsAdapter);
        listView.setVisibility(View.VISIBLE);

        btn_del_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                historyManager.deleteAllNewsHistory();
                newslist.clear();
                newsAdapter.notifyDataSetChanged();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getContext(), ShowNewsActivity.class);
                intent.putExtra("title", newslist.get(i - listView.getHeaderViewsCount()).getTitle());
                intent.putExtra("date", newslist.get(i - listView.getHeaderViewsCount()).getDate());

                intent.putExtra("source", newslist.get(i - listView.getHeaderViewsCount()).getSource());

                CovidNewsWithText temp = newsManager.getOfflineNews(newslist.get(i - listView.getHeaderViewsCount()));


                intent.putExtra("body", temp.getContent());

                startActivity(intent);
            }

        });
    }


}
