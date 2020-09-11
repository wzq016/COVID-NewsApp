package com.example.newsapp.ui.xuezhe;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.newsapp.Activity.ShowEntityActivity;
import com.example.newsapp.Activity.ShowExpertActivity;
import com.example.newsapp.Adapter.EntityAdapter;
import com.example.newsapp.Adapter.XuezheAdapter;
import com.example.newsapp.Data.Expert;
import com.example.newsapp.Data.ExpertManager;
import com.example.newsapp.R;

import java.util.ArrayList;

public class XuezheFragment extends Fragment {
    private View view;
    private ListView listView;
    private ArrayList<Expert> ppl_list;
    private XuezheAdapter xzadapter;
    private ExpertManager emanager;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        view = inflater.inflate(R.layout.fragment_xuezhe, container, false);
        listView = view.findViewById(R.id.listview_xuezhe);
        ppl_list = new ArrayList<>();
        init_ppl();
        xzadapter = new XuezheAdapter(getContext(), R.layout.one_xuezhe, ppl_list);
        listView.setAdapter(xzadapter);
        listView.setVisibility(View.VISIBLE);
        init_listView();
        return view;
    }

    private void init_listView(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                Intent intent = new Intent(getContext(), ShowExpertActivity.class);
                intent.putExtra("name", ppl_list.get(i - listView.getHeaderViewsCount()).getName());
                intent.putExtra("name_zh", ppl_list.get(i - listView.getHeaderViewsCount()).getNameZh());
                intent.putExtra("img", ppl_list.get(i - listView.getHeaderViewsCount()).getAvatar());
                intent.putExtra("bio", ppl_list.get(i - listView.getHeaderViewsCount()).getBiography());
                intent.putExtra("work", ppl_list.get(i - listView.getHeaderViewsCount()).getWork());
                intent.putExtra("affiliation", ppl_list.get(i - listView.getHeaderViewsCount()).getAffiliation());
                intent.putExtra("position", ppl_list.get(i - listView.getHeaderViewsCount()).getPosition());
                if(ppl_list.get(i - listView.getHeaderViewsCount()).getIsPassedAway())
                    intent.putExtra("passed", "状态：离世");
                else
                    intent.putExtra("passed", "状态：在世");

                startActivity(intent);
            }
        });
    }

    private void init_ppl(){
        emanager = new ExpertManager();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                emanager.getExpertsOnline();
            }
        });
        thread.start();
        try{
            thread.join();
        }catch(Exception e){
            e.printStackTrace();
        }
        ppl_list.addAll(emanager.getExperts());
    }

}
