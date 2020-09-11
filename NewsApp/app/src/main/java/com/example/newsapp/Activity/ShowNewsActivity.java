package com.example.newsapp.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.newsapp.Data.HistoryManager;
import com.example.newsapp.R;

public class ShowNewsActivity extends Activity{
    private TextView news_title;
    private TextView news_date;
    private TextView news_source;
    private TextView news_body;
    private ImageView back2list;

    private String title;
    private String date;
    private String source;
    private String body;
    private ImageView share;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shownews);
        news_title = findViewById(R.id.news_title);
        news_date = findViewById(R.id.news_date);
        news_body= findViewById(R.id.news_body);
        news_source = findViewById(R.id.news_source);
        back2list = findViewById(R.id.back2list_view);
        share = findViewById(R.id.share_news);
        init_Listener();
        my_show();
    }

    private void init_Listener(){
        back2list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent textIntent = new Intent(Intent.ACTION_SEND);
                textIntent.setType("text/plain");
                textIntent.putExtra(Intent.EXTRA_SUBJECT, title.substring(0,40) +"...");
                textIntent.putExtra(Intent.EXTRA_TEXT,body.substring(0,100)+"...");
                startActivity(Intent.createChooser(textIntent, "分享"));
            }
        });
    }

    private void my_show(){
        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        date = intent.getStringExtra("date");
        source = intent.getStringExtra("source");
        body = intent.getStringExtra("body");
        news_title.setText(title);
        news_date.setText(date);
        news_source.setText(source);
        news_body.setText(body);
    }


}
