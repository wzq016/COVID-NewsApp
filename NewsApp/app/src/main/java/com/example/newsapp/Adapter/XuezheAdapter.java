package com.example.newsapp.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.newsapp.Data.CovidNews;
import com.example.newsapp.Data.Expert;
import com.example.newsapp.ui.search.SearchFragment;
import com.example.newsapp.R;
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class XuezheAdapter extends ArrayAdapter<Expert> {
    private int textViewResourceId;
    public View view;

    public XuezheAdapter(Context context, int textViewResourceId, List<Expert> objects) {
        super(context, textViewResourceId, objects);
        this.textViewResourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Expert ppl = getItem(position);

        if(convertView==null){
            view = LayoutInflater.from(getContext()).inflate(textViewResourceId, parent, false);
        } else{
            view = convertView;
        }

        TextView name_xuezhe = view.findViewById(R.id.name_xuezhe);
        name_xuezhe.setText(ppl.getName());
        TextView name_xuezhe_zh = view.findViewById(R.id.name_xuezhe_zh);
        name_xuezhe_zh.setText(ppl.getNameZh());


        return view;
    }

}