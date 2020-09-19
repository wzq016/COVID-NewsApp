package com.example.newsapp.Activity;



import com.cheng.channel.ChannelView;
import com.cheng.channel.Channel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;

import com.cheng.channel.adapter.ChannelListenerAdapter;
import com.example.newsapp.MainActivity;
import com.example.newsapp.utils.StaticVar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.multidex.MultiDex;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.newsapp.Data.*;

import com.example.newsapp.R;

import java.util.ArrayList;
import java.util.*;

public class ShowChannelActivity extends AppCompatActivity {
    private ChannelView channelView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choosetag);

        channelView = findViewById(R.id.tag_select);
        init();
    }

    private void init() {
        Intent intent = getIntent();
        String[] myChannel = {};
        String[] recommendChannel1 = {"全部", "news", "paper","传播","细胞","疫苗","冠状病毒", "结构"};
        String[] recommendChannel2 = {};


        List<Channel> myChannelList = new ArrayList<>();
        List<Channel> recommendChannelList1 = new ArrayList<>();
        List<Channel> recommendChannelList2 = new ArrayList<>();

        for (int i = 0; i < myChannel.length; i++) {
            String aMyChannel = myChannel[i];
            Channel channel;
            if (i > 2 && i < 6) {
                //可设置频道归属板块（channelBelong），当前设置此频道归属于第二板块，当删除该频道时该频道将回到第二板块
                channel = new Channel(aMyChannel, 2, i);
            } else if (i > 7 && i < 10) {
                //可设置频道归属板块（channelBelong），当前设置此频道归属于第三板块，当删除该频道时该频道将回到第三板块中
                channel = new Channel(aMyChannel, 3, i);
            } else {
                channel = new Channel(aMyChannel, (Object) i);
            }
            myChannelList.add(channel);
        }
        if(intent==null) {
            for (String aMyChannel : recommendChannel1) {
                Channel channel = new Channel(aMyChannel);
                recommendChannelList1.add(channel);
            }

            for (String aMyChannel : recommendChannel2) {
                Channel channel = new Channel(aMyChannel);
                recommendChannelList2.add(channel);
            }
        } else{
            String categs = intent.getStringExtra("categs");

            for (String aMyChannel : recommendChannel1) {
                Channel channel = new Channel(aMyChannel);
                if(categs.contains(aMyChannel)) {
                    recommendChannelList1.add(channel);
                } else {
                    recommendChannelList2.add(channel);
                }
            }

            for (String aMyChannel : recommendChannel2) {
                Channel channel = new Channel(aMyChannel);
                if(categs.contains(aMyChannel)) {
                    recommendChannelList1.add(channel);
                } else {
                    recommendChannelList2.add(channel);
                }
            }
        }


        channelView.setChannelFixedCount(1);
        channelView.setInsertRecommendPosition(6);
        channelView.addPlate("已选择类别", recommendChannelList1);
        channelView.addPlate("其他", recommendChannelList2);
        channelView.inflateData();
        Intent intent__ = new Intent(this, MainActivity.class);
        channelView.setOnChannelListener(new ChannelListenerAdapter() {
            @Override
            public void channelItemClick(int position, Channel channel) {

            }

            @Override
            public void channelEditStateItemClick(int position, Channel channel) {

            }

            @Override
            public void channelEditFinish(List<Channel> channelList) {
                String tab_strings = "";
                List<Channel> channel = channelView.getMyChannel();

                for(Channel channelitem:channel)
                {
                    tab_strings = tab_strings + channelitem.getChannelName() + " ";
                }
                StaticVar.tab_strings = tab_strings;

                startActivity(intent__);
                finish();
            }

            @Override
            public void channelEditStart() {

            }
        });
    }
}
