package com.example.newsapp.ui.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.newsapp.Adapter.NewsAdapter;
import com.example.newsapp.Data.CovidNews;
import com.example.newsapp.Data.NewsManager;
import com.example.newsapp.R;
import java.util.*;


public class SearchFragment extends Fragment{
    private View view;
    private Button search_btn;
    private EditText search_content;
    private ArrayList<CovidNews> newslist;
    private ListView listview_news;
    private NewsManager newsmanager;
    private LinearLayout history_layout;
    private NewsAdapter newsadapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        view = inflater.inflate(R.layout.fragment_search, container, false);
        init_news();
        init();
        return view;
    }

    private void init_news(){
        listview_news = view.findViewById(R.id.listview_news);
        newslist = new ArrayList<CovidNews>();
        newsmanager = new NewsManager();
        newsadapter = new NewsAdapter(getContext(), R.layout.one_news, newslist,this);
        listview_news.setAdapter(newsadapter);
        listview_news.setVisibility(View.GONE);

        history_layout = view.findViewById(R.id.history);
        history_layout.setVisibility(View.VISIBLE);
    }

    private void init(){
        search_btn = view.findViewById(R.id.search_btn);
        search_content = view.findViewById(R.id.search_content);
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchContent = search_content.getText().toString().trim();
                System.out.println(searchContent);
                if (searchContent.length() != 0){
                    newslist = newsmanager.searchByQuery(searchContent);
                    listview_news.setVisibility(View.VISIBLE);
                    history_layout.setVisibility(View.GONE);
//                    linearLayout.setVisibility(View.GONE);
//                    mRecyclerView.setVisibility(View.GONE);

                    //
//                    mSearchAdapter.updata(mDatebase.queryData(""));
                }else {
                    Toast.makeText(getContext(), "请输入内容", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
