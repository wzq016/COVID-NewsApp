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

    private String label;
    private String img;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showentity);
        kg_back2list = findViewById(R.id.kg_back2list_view);
        entity_img = findViewById(R.id.entity_picture);
        entity_label = findViewById(R.id.entity_label);

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
        entity_label.setText(label);
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
