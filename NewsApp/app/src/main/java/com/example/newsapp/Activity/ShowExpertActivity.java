package com.example.newsapp.Activity;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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

public class ShowExpertActivity extends Activity {
    private ImageView expert_back2list;
    private ImageView expert_img;
    private TextView expert_name;

    private String name;
    private String img;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showexpert);
        expert_back2list = findViewById(R.id.expert_back2list_view);
        expert_img = findViewById(R.id.expert_picture);
        expert_name = findViewById(R.id.expert_name);
        init_Listener();
        my_show();
    }
    private void init_Listener(){
        expert_back2list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void my_show(){
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        img = intent.getStringExtra("img");
        expert_name.setText(name);
        new Thread(new Runnable(){
            @Override
            public void run(){
                Drawable drawable = loadImageFromNetwork(img);
                expert_img.post(new Runnable(){
                    @Override
                    public void run(){
                        if(drawable!=null)
                            expert_img.setImageDrawable(drawable);
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

