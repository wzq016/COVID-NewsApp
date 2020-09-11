package com.example.newsapp.ui.homepage;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.newsapp.Activity.ShowChannelActivity;
import com.example.newsapp.Activity.ShowNewsActivity;
import com.example.newsapp.Adapter.NewsAdapter;
import com.example.newsapp.Data.ClusterManager;
import com.example.newsapp.Data.CovidNews;
import com.example.newsapp.Data.CovidNewsWithText;
import com.example.newsapp.Data.EventCluster;
import com.example.newsapp.Data.HistoryManager;
import com.example.newsapp.Data.NewsManager;
import com.example.newsapp.Data.SearchHistory;
import com.example.newsapp.R;
import com.example.newsapp.Thread.GetContentThread;
import com.example.newsapp.Thread.SearchThread;
import com.example.newsapp.utils.StaticVar;
import com.google.android.material.tabs.TabLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.lang.reflect.Array;
import java.util.*;

public class HomepageFragment extends Fragment{
    private View view;
    private TabLayout tab_layout;
    private ImageView btn_showchannel;
    private ArrayList<String> categ_list;
    private ViewPager viewpager;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private ListView listview_news;
    private ArrayList<CovidNews> newslist;
    private NewsManager newsmanager;
    private NewsAdapter newsadapter;
    private ArrayList<CovidNews> all_news;
    private ArrayList<CovidNews> news_news;
    private ArrayList<CovidNews> paper_news;
    private ArrayList<CovidNews> cl1_news;
    private ArrayList<CovidNews> cl2_news;
    private ArrayList<CovidNews> cl3_news;
    private ArrayList<CovidNews> cl4_news;
    private ArrayList<CovidNews> cl5_news;
    private String keyword1;
    private String keyword2;
    private String keyword3;
    private String keyword4;
    private String keyword5;
    private String current_tab;
    private List<CovidNews> current_news;
    private HistoryManager historyManager;
    private int add_once = 14;
//    private FmPagerAdapter pagerAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_homepage,container,false);
        historyManager = new HistoryManager();
        init_tab();
        init_listview();
        init_pull();
        return view;
    }

    private void init_tab(){
        categ_list = new ArrayList<>();

        cl1_news = new ArrayList<>();
        cl2_news = new ArrayList<>();
        cl3_news = new ArrayList<>();
        cl4_news = new ArrayList<>();
        cl5_news = new ArrayList<>();

        ClusterManager clusterManager = new ClusterManager();
        ArrayList<EventCluster> clusters = clusterManager.getClusters();

        cl1_news.addAll(clusters.get(0).getEvents());
        cl2_news.addAll(clusters.get(1).getEvents());
        cl3_news.addAll(clusters.get(2).getEvents());
        cl4_news.addAll(clusters.get(3).getEvents());
        cl5_news.addAll(clusters.get(4).getEvents());

        keyword1 = clusters.get(0).getKeyword();
        keyword2 = clusters.get(1).getKeyword();
        keyword3 = clusters.get(2).getKeyword();
        keyword4 = clusters.get(3).getKeyword();
        keyword5 = clusters.get(4).getKeyword();
//        System.out.println(keyword1);
//        System.out.println(keyword2);
//        System.out.println(keyword3);
//        System.out.println(keyword4);
//        System.out.println(keyword5);

        init_channel();
        tab_layout = view.findViewById(R.id.tab_layout);
        viewpager = view.findViewById(R.id.viewpager);

        for(String str:categ_list){
            TabLayout.Tab tab = tab_layout.newTab();
            tab.setText(str);
            tab_layout.addTab(tab);
        }


        tab_layout.setupWithViewPager(viewpager,false);


        btn_showchannel = view.findViewById(R.id.btn_showchannel);
        btn_showchannel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ShowChannelActivity.class);
                String now_categ = "";
                for(String str:categ_list)
                    now_categ = now_categ+str+" ";
                intent.putExtra("categs",now_categ);
                startActivity(intent);
//                getActivity().finish();
            }
        });



    }

    private void init_channel(){
        if(StaticVar.tab_strings==null) {
            categ_list.add("全部");
            categ_list.add("news");
            categ_list.add("paper");
            categ_list.add(keyword1);
            categ_list.add(keyword2);
            categ_list.add(keyword3);
            categ_list.add(keyword4);
            categ_list.add(keyword5);
        }else{
            String[] tab_strings = StaticVar.tab_strings.split(" ");
            for(String str:tab_strings){
                categ_list.add(str);
            }
        }
    }

    private void init_listview(){
        listview_news = view.findViewById(R.id.news_listview_smart);
        newslist = new ArrayList<CovidNews>();
        newsmanager = new NewsManager();

        all_news = new ArrayList<>();
        news_news = new ArrayList<>();
        paper_news = new ArrayList<>();
//        cl1_news = new ArrayList<>();
//        cl2_news = new ArrayList<>();
//        cl3_news = new ArrayList<>();
//        cl4_news = new ArrayList<>();
//        cl5_news = new ArrayList<>();

        all_news.addAll(getNews());
        news_news.addAll(newsClassify("news"));
        paper_news.addAll(newsClassify("paper"));

//        ClusterManager clusterManager = new ClusterManager();
//        ArrayList<EventCluster> clusters = clusterManager.getClusters();
//
//        cl1_news.addAll(clusters.get(0).getEvents());
//        cl2_news.addAll(clusters.get(1).getEvents());
//        cl3_news.addAll(clusters.get(2).getEvents());
//        cl4_news.addAll(clusters.get(3).getEvents());
//        cl5_news.addAll(clusters.get(4).getEvents());
//
//        keyword1 = clusters.get(0).getKeyword();
//        keyword2 = clusters.get(0).getKeyword();
//        keyword3 = clusters.get(0).getKeyword();
//        keyword4 = clusters.get(0).getKeyword();
//        keyword5 = clusters.get(0).getKeyword();

        tab_layout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                String type = (String) tab.getText();

                if(type.equals("全部")){
                    newslist = all_news;
                } else if(type.equals("news")){
                    newslist = news_news;
                } else if(type.equals("paper")){
                    newslist = paper_news;
                } else if(type.equals(keyword1)){
                    newslist = cl1_news;
                } else if(type.equals(keyword2)){
                    newslist = cl2_news;
                } else if(type.equals(keyword3)){
                    newslist = cl3_news;
                } else if(type.equals(keyword4)){
                    newslist = cl4_news;
                } else if(type.equals(keyword5)){
                    newslist = cl5_news;
                }  else {
                    assert false;
                }

                current_news = newslist.subList(0,add_once);
                current_tab = type;
                newsadapter = new NewsAdapter(getContext(), R.layout.one_news, newslist);
                listview_news.setAdapter(newsadapter);
                view.postInvalidate();

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        current_tab = "全部";
        current_news = all_news.subList(0,add_once);
        newslist = all_news;

        newsadapter = new NewsAdapter(getContext(), R.layout.one_news, current_news);
        listview_news.setAdapter(newsadapter);

        listview_news.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                Intent intent = new Intent(getContext(), ShowNewsActivity.class);
                intent.putExtra("title", current_news.get(i - listview_news.getHeaderViewsCount()).getTitle());
                intent.putExtra("date", current_news.get(i - listview_news.getHeaderViewsCount()).getDate());

                intent.putExtra("source", current_news.get(i - listview_news.getHeaderViewsCount()).getSource());

                current_news.get(i - listview_news.getHeaderViewsCount()).isTarsh = true;

                GetContentThread content_thread = new GetContentThread(current_news.get(i - listview_news.getHeaderViewsCount()));
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
                        historyManager.addToNewsHistory(new CovidNewsWithText(current_news.get(i - listview_news.getHeaderViewsCount())));
                        System.out.println("Add to history!");
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

    private ArrayList<CovidNews> getNews(){
        ArrayList<CovidNews> a = new ArrayList<>();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                a.addAll(newsmanager.getNews());
            }
        });
        thread.start();
        try{
            thread.join();
        } catch(Exception e){
            e.printStackTrace();
        }
        return a;
    }

    private ArrayList<CovidNews> newsClassify(String type){
        ArrayList<CovidNews> a = new ArrayList<>();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                a.addAll(newsmanager.newsClassify(type));
            }
        });
        thread.start();
        try{
            thread.join();
        } catch(Exception e){
            e.printStackTrace();
        }
        return a;
    }


    private void init_pull(){
        RefreshLayout refreshLayout = (RefreshLayout)view.findViewById(R.id.refreshLayout);

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                if(!current_tab.equals(keyword1) &&
                        !current_tab.equals(keyword2) &&
                        !current_tab.equals(keyword3) &&
                        !current_tab.equals(keyword4) &&
                        !current_tab.equals(keyword5)){
                    newslist.clear();
                    if(current_tab.equals("全部")){
                        newslist.addAll(getNews());
                    } else if(current_tab.equals("news")){
                        newslist.addAll(newsClassify("news"));
                    } else if(current_tab.equals("paper")){
                        newslist.addAll(newsClassify("paper"));
                    } else{
                        assert false;
                    }
                    current_news = newslist.subList(0,add_once);
                    newsadapter = new NewsAdapter(getContext(), R.layout.one_news, current_news);
                    listview_news.setAdapter(newsadapter);
                    view.postInvalidate();
                }
                refreshlayout.finishRefresh(true);//传入false表示刷新失败
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                int max_size = current_news.size()+add_once;
                if(max_size>newslist.size())
                    max_size = newslist.size();
                newsadapter.addAll(newslist.subList(current_news.size(),max_size));
                newsadapter.notifyDataSetChanged();

                refreshlayout.finishLoadMore(true);

            }
        });
    }

}
