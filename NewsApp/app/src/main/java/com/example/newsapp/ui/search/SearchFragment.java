package com.example.newsapp.ui.search;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapp.Activity.ShowNewsActivity;
import com.example.newsapp.Adapter.NewsAdapter;
import com.example.newsapp.Data.CovidNews;
import com.example.newsapp.Data.CovidNewsWithText;
import com.example.newsapp.Data.HistoryManager;
import com.example.newsapp.Data.NewsManager;
import com.example.newsapp.Data.SearchHistory;
import com.example.newsapp.R;
import com.example.newsapp.Thread.GetContentThread;
import com.example.newsapp.Thread.SearchThread;
import com.example.newsapp.utils.StaticVar;

import java.util.*;


public class SearchFragment extends Fragment{
    private View view;
    private Button search_btn;
    private EditText search_content;
    private ArrayList<CovidNews> newslist;
    private ListView listview_news;
    private NewsManager newsmanager;
    private HistoryManager history_manager;
    private RelativeLayout history_layout;
    private NewsAdapter newsadapter;
    private EditText edittext;
    private ArrayList<History> history_list;
    private RecyclerView recyclerView;
    private TextView del_all;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        view = inflater.inflate(R.layout.fragment_search, container, false);
        init_news();
        init_search();
        init_listview();
        return view;
    }

    private void init_news(){
        history_manager = new HistoryManager();
        listview_news = view.findViewById(R.id.listview_news);
        newslist = new ArrayList<CovidNews>();
        newsmanager = new NewsManager();
        newsadapter = new NewsAdapter(getContext(), R.layout.one_news, newslist,this);
        listview_news.setAdapter(newsadapter);
        listview_news.setVisibility(View.GONE);

        history_layout = view.findViewById(R.id.history);
        history_layout.setVisibility(View.VISIBLE);


        history_list = new ArrayList<History>();
        ArrayList<String> historys = history_manager.getSearchHistory();
        for(String content:historys)
            history_list.add(new History(content,R.drawable.delete));



        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
        HistoryAdapter adapter = new HistoryAdapter(history_list);
        recyclerView.setAdapter(adapter);
        recyclerView.setVisibility(View.GONE);
    }

    private void init_search(){
        edittext = view.findViewById(R.id.search_content);
        edittext.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                history_layout.setVisibility(View.VISIBLE);
                listview_news.setVisibility(View.GONE);
                HistoryAdapter adapter = new HistoryAdapter(history_list);
                recyclerView.setAdapter(adapter);
                recyclerView.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        del_all = view.findViewById(R.id.history_delete);
        del_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                history_manager.deleteAllSearchHistory();
                history_list.clear();
                HistoryAdapter adapter = new HistoryAdapter(history_list);
                recyclerView.setAdapter(adapter);
                recyclerView.setVisibility(View.VISIBLE);
            }
        });

        search_btn = view.findViewById(R.id.search_btn);
        search_content = view.findViewById(R.id.search_content);
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchContent = search_content.getText().toString().trim();
                if (searchContent.length() != 0){
                    search(searchContent);
                    newsadapter = new NewsAdapter(getContext(), R.layout.one_news, newslist);
                    listview_news.setAdapter(newsadapter);
                    listview_news.setVisibility(View.VISIBLE);
                    history_layout.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.GONE);
                }else {
                    Toast.makeText(getContext(), "请输入内容", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void init_listview(){
        listview_news.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                Intent intent = new Intent(getContext(), ShowNewsActivity.class);
                intent.putExtra("title", newslist.get(i - listview_news.getHeaderViewsCount()).getTitle());
                intent.putExtra("date", newslist.get(i - listview_news.getHeaderViewsCount()).getDate());

                intent.putExtra("source", newslist.get(i - listview_news.getHeaderViewsCount()).getSource());

                newslist.get(i - listview_news.getHeaderViewsCount()).isTarsh = true;

                GetContentThread content_thread = new GetContentThread(newslist.get(i - listview_news.getHeaderViewsCount()));
                Thread thread = new Thread(content_thread);
                thread.start();

                try{
                    thread.join();
                }catch (Exception e){
                    e.printStackTrace();
                }


                intent.putExtra("body", content_thread.get_content());
//                intent.putExtra("source_activity","search");
                Thread thread2 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        history_manager.addToNewsHistory(new CovidNewsWithText(newslist.get(i - listview_news.getHeaderViewsCount())));
                    }
                });
                thread2.start();

                try{
                    thread2.join();
                }catch (Exception e){
                    e.printStackTrace();
                }
                newsadapter.notifyDataSetChanged();
                startActivity(intent);
            }
        });
    }

    private void search(String content){
        history_manager.addToSearchHistory(content);
        history_list.add(new History(content,R.drawable.delete));
//        for(String str:newsmanager.getSearchHistory())
//            System.out.println(str);
        newslist.clear();
        SearchThread searchthread = new SearchThread(content,newsmanager,newslist);
        Thread thread = new Thread(searchthread);
        thread.start();
        try{
            thread.join();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
