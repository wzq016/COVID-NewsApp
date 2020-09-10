package com.example.newsapp.ui.homepage;
import android.content.Intent;
import android.media.Image;
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
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.newsapp.Activity.ShowChannelActivity;
import com.example.newsapp.Activity.ShowNewsActivity;
import com.example.newsapp.Adapter.NewsAdapter;
import com.example.newsapp.Data.CovidNews;
import com.example.newsapp.Data.CovidNewsWithText;
import com.example.newsapp.Data.NewsManager;
import com.example.newsapp.R;
import com.example.newsapp.Thread.GetContentThread;
import com.example.newsapp.Thread.SearchThread;
import com.example.newsapp.utils.StaticVar;
import com.google.android.material.tabs.TabLayout;

import java.lang.reflect.Array;
import java.util.*;

public class HomepageFragment extends Fragment{
    private View view;
    private TabLayout tab_layout;
    private ImageView btn_showchannel;
    private ArrayList<String> categ_list;
    private ViewPager viewpager;
    private ArrayList<Fragment> fragments = new ArrayList<>();
//    private FmPagerAdapter pagerAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_homepage,container,false);
        init_tab();
        return view;
    }

    private void init_tab(){
        categ_list = new ArrayList<>();
        init_channel();
        tab_layout = view.findViewById(R.id.tab_layout);
        viewpager = view.findViewById(R.id.viewpager);

        for(String str:categ_list){
//            fragments.add(new TabFragment());
            TabLayout.Tab tab = tab_layout.newTab();
            tab.setText(str);
            tab_layout.addTab(tab);
        }

        tab_layout.setupWithViewPager(viewpager,false);
//        pagerAdapter = new FmPagerAdapter(fragments,getSupportFragmentManager());
//        viewPager.setAdapter(pagerAdapter);

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
        }else{
            String[] tab_strings = StaticVar.tab_strings.split(" ");
            for(String str:tab_strings){
                categ_list.add(str);
            }
        }
    }

}
