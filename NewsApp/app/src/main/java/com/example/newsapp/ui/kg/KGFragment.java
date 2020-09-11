package com.example.newsapp.ui.kg;
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
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapp.Activity.ShowEntityActivity;
import com.example.newsapp.Activity.ShowNewsActivity;
import com.example.newsapp.Adapter.EntityAdapter;
import com.example.newsapp.Adapter.NewsAdapter;
import com.example.newsapp.Data.CovidNews;
import com.example.newsapp.Data.CovidNewsWithText;
import com.example.newsapp.Data.EntityManager;
import com.example.newsapp.Data.HistoryManager;
import com.example.newsapp.Data.NewsManager;
import com.example.newsapp.Data.SearchHistory;
import com.example.newsapp.Data.VirusEntity;
import com.example.newsapp.R;
import com.example.newsapp.Thread.GetContentThread;
import com.example.newsapp.Thread.SearchEntityThread;
import com.example.newsapp.Thread.SearchThread;
import com.example.newsapp.utils.StaticVar;

import java.util.*;

public class KGFragment extends Fragment {
    private View view;
    private EditText edittext;
    private Button search_btn;
    private EntityManager emanager;
    private ListView kg_listview_entity;
    private ArrayList<VirusEntity> ve_list;
    private EntityAdapter eadapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        view = inflater.inflate(R.layout.fragment_kg, container, false);
        init_search();
        init_listview();
        return view;
    }

    private void init_search(){

        ve_list = new ArrayList<VirusEntity>();

        kg_listview_entity = view.findViewById(R.id.kg_listview_entity);
        eadapter = new EntityAdapter(getContext(), R.layout.one_entity, ve_list);
        kg_listview_entity.setAdapter(eadapter);
        kg_listview_entity.setVisibility(View.GONE);

        emanager = new EntityManager();
        edittext = view.findViewById(R.id.kg_search_content);
        search_btn = view.findViewById(R.id.kg_search_btn);
        edittext.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                kg_listview_entity.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String searchContent = edittext.getText().toString().trim();
                if (searchContent.length() != 0){
                    search(searchContent);
                }else {
                    Toast.makeText(getContext(), "请输入内容", Toast.LENGTH_SHORT).show();
                }

                kg_listview_entity.setVisibility(View.VISIBLE);
            }
        });

    }
    private void init_listview(){
        kg_listview_entity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                Intent intent = new Intent(getContext(), ShowEntityActivity.class);
                intent.putExtra("label", ve_list.get(i - kg_listview_entity.getHeaderViewsCount()).getLable());
                intent.putExtra("img", ve_list.get(i - kg_listview_entity.getHeaderViewsCount()).getImg());
                startActivity(intent);
            }
        });
    }

    private void search(String content){
        ve_list.clear();
        SearchEntityThread searchthread = new SearchEntityThread(content,emanager,ve_list);
        Thread thread = new Thread(searchthread);
        thread.start();
        try{
            thread.join();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }



}
