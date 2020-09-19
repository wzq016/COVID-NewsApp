package com.example.newsapp.Activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.newsapp.R;

import java.io.IOException;
import java.net.URL;

public class ShowEntityActivity extends Activity{
    private TextView entity_label;
    private ImageView entity_img;
    private ImageView kg_back2list;

    private TextView entity_des;
    private TextView entity_property;
    private TextView entity_relation;

    private String label;
    private String img;

    private String des;
    private String property;
    private String relation;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showentity);
        kg_back2list = findViewById(R.id.kg_back2list_view);
        entity_img = findViewById(R.id.entity_picture);
        entity_label = findViewById(R.id.entity_label);

        entity_des = findViewById(R.id.entity_des);
        entity_property = findViewById(R.id.entity_property);
        entity_relation = findViewById(R.id.entity_relation);

        init_Listener();
        my_show();
    }

    private void init_Listener(){
        kg_back2list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void my_show(){
        Intent intent = getIntent();
        label = intent.getStringExtra("label");
        img = intent.getStringExtra("img");

        des = intent.getStringExtra("des");
        property = intent.getStringExtra("property");
        relation = intent.getStringExtra("relation");


        entity_label.setText(label);
        entity_des.setText(des);
        entity_property.setText(property);
        entity_relation.setText(relation);

        new Thread(new Runnable(){
            @Override
            public void run(){
                Drawable drawable = loadImageFromNetwork(img);
                entity_img.post(new Runnable(){
                    @Override
                    public void run(){
                        if(drawable!=null)
                            entity_img.setImageDrawable(drawable);
                    }
                });
            }


            private Drawable loadImageFromNetwork(String urladdr) {
                Drawable drawable = null;
                try{
                    drawable = Drawable.createFromStream(new URL(urladdr).openStream(), "entityimage.jpg");
                }catch(IOException e){
                    Log.d("test",e.getMessage());
                }
                return drawable;
            }

        }).start();

    }

}
