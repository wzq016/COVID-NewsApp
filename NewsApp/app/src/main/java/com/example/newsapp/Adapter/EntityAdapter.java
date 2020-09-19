package com.example.newsapp.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.example.newsapp.Data.VirusEntity;
import com.example.newsapp.R;
import org.w3c.dom.Text;

import java.util.List;

public class EntityAdapter extends ArrayAdapter<VirusEntity> {
    private int textViewResourceId;
    public View view;


    public EntityAdapter(Context context, int textViewResourceId, List<VirusEntity> objects) {
        super(context, textViewResourceId, objects);
        this.textViewResourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        VirusEntity ve = getItem(position);

        if(convertView==null){
            view = LayoutInflater.from(getContext()).inflate(textViewResourceId, parent, false);
        } else{
            view = convertView;
        }

        TextView entity_name = view.findViewById(R.id.entity_name);


        entity_name.setText(ve.getLable());

        return view;
    }

}
