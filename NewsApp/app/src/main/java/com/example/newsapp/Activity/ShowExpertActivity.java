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
    private TextView expert_name_zh;
    private TextView expert_name;

    private TextView expert_passed;
    private TextView expert_edu;
    private TextView expert_affiliation;
    private TextView expert_position;
    private TextView expert_bio;
    private TextView expert_work;

    private String name_zh;
    private String name;
    private String img;


    private String passed;
    private String edu;
    private String affiliation;
    private String position;
    private String bio;
    private String work;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showexpert);
        expert_back2list = findViewById(R.id.expert_back2list_view);
        expert_img = findViewById(R.id.expert_picture);
        expert_name_zh = findViewById(R.id.expert_name_zh);
        expert_name = findViewById(R.id.expert_name);

        expert_passed = findViewById(R.id.expert_passed);
        expert_edu= findViewById(R.id.expert_edu);
        expert_affiliation= findViewById(R.id.expert_affiliation);
        expert_position= findViewById(R.id.expert_position);
        expert_bio= findViewById(R.id.expert_bio);
        expert_work= findViewById(R.id.expert_work);


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
        name_zh = intent.getStringExtra("name_zh");
        name = intent.getStringExtra("name");
        img = intent.getStringExtra("img");

        passed = intent.getStringExtra("passed");
        edu = intent.getStringExtra("edu");
        affiliation = intent.getStringExtra("affiliation");
        position = intent.getStringExtra("position");
        bio = intent.getStringExtra("bio");
        work = intent.getStringExtra("work");

        expert_passed.setText(passed);
        expert_edu.setText(edu);
        expert_affiliation.setText(affiliation);
        expert_bio.setText(bio);
        expert_position.setText(position);
        expert_work.setText(work);


        expert_name_zh.setText(name_zh);
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

