package com.example.newsapp.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.newsapp.Data.CovidNews;
import com.example.newsapp.ui.search.SearchFragment;
import com.example.newsapp.R;
import org.w3c.dom.Text;

import java.util.List;

public class NewsAdapter extends ArrayAdapter<CovidNews> {
    private int textViewResourceId;
    private SearchFragment search_parent;
    public View view;
    List<CovidNews> objects;

    public NewsAdapter(Context context, int textViewResourceId, List<CovidNews> objects, SearchFragment parent) {
        super(context, textViewResourceId, objects);
        this.textViewResourceId = textViewResourceId;
        search_parent = parent;
        this.objects = objects;
    }

    public NewsAdapter(Context context, int textViewResourceId, List<CovidNews> objects) {
        super(context, textViewResourceId, objects);
        this.textViewResourceId = textViewResourceId;
        this.objects = objects;
    }

    public void add(List<CovidNews> in_objects){
        int position = objects.size();
        objects.addAll(position, in_objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CovidNews news = getItem(position);

        if(convertView==null){
            view = LayoutInflater.from(getContext()).inflate(textViewResourceId, parent, false);
        } else{
            view = convertView;
        }

        TextView one_news_title = view.findViewById(R.id.one_news_title);
        one_news_title.setTextColor(Color.rgb(255, 255, 255));
        TextView one_news_source = view.findViewById(R.id.one_news_source);
        TextView one_news_date = view.findViewById(R.id.one_news_date);

        one_news_title.setText(news.getTitle());
        one_news_source.setText(news.getSource());
        one_news_date.setText(news.getDate());

        return view;
    }

}
