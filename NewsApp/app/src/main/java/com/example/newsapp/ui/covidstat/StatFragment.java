package com.example.newsapp.ui.covidstat;

import android.graphics.Region;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.newsapp.Data.RegionData;
import com.example.newsapp.Data.RegionDataManager;
import com.example.newsapp.R;

import java.util.ArrayList;
import java.util.List;

public class StatFragment extends Fragment {
    private View view;
    private RegionDataManager rdmanager;
    private MultiGroupHistogramView stat_view;
    private Button cn;
    private Button world;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        view = inflater.inflate(R.layout.fragment_stat, container, false);
        stat_view = view.findViewById(R.id.stat_view);
        cn = view.findViewById(R.id.btn_china);
        world = view.findViewById(R.id.btn_world);
        rdmanager = new RegionDataManager();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                rdmanager.getRegionData();
            }
        });
        thread.start();
        try{
            thread.join();
        } catch(Exception e){
            e.printStackTrace();
        }
        stat_view.setVisibility(View.GONE);
        init_listener();
        return view;
    }
    private void init_listener(){
        cn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                init_china(true);
            }
        });
        world.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                init_china(false);
            }
        });
    }
    private void init_china(boolean is_cn){
        ArrayList<RegionData> datas;
        if(is_cn)
            datas = rdmanager.getProvinceData();
        else
            datas = rdmanager.getCountryData();
        int nr_province = datas.size();

        List<MultiGroupHistogramGroupData> groupDataList = new ArrayList<>();
        for (int i = 0; i < nr_province; i++) {
            RegionData data = datas.get(i);
            List<MultiGroupHistogramChildData> childDataList = new ArrayList<>();
            MultiGroupHistogramGroupData groupData = new MultiGroupHistogramGroupData();
            if(is_cn)
                groupData.setGroupName(data.getProvince());
            else
                groupData.setGroupName(data.getCountry());

            MultiGroupHistogramChildData childData1 = new MultiGroupHistogramChildData();
            childData1.setSuffix("");
            childData1.setValue(data.getConfirmed().get(data.getConfirmed().size()-1));
            childDataList.add(childData1);

            MultiGroupHistogramChildData childData2 = new MultiGroupHistogramChildData();
            childData2.setSuffix("");
            childData2.setValue(data.getCured().get(data.getCured().size()-1));
            childDataList.add(childData2);

            MultiGroupHistogramChildData childData3 = new MultiGroupHistogramChildData();
            childData3.setSuffix("");
            childData3.setValue(data.getDead().get(data.getDead().size()-1));
            childDataList.add(childData3);


            groupData.setChildDataList(childDataList);
            groupDataList.add(groupData);
        }
        stat_view.setDataList(groupDataList);
        stat_view.postInvalidate();
        stat_view.setVisibility(View.VISIBLE);
        int[] color1 = new int[]{getResources().getColor(R.color.color_orange), getResources().getColor(R.color.colorPrimary)};
////
        int[] color2 = new int[]{getResources().getColor(R.color.color_supper_tip_normal), getResources().getColor(R.color.bg_supper_selected)};
////        // 设置直方图颜色
        int[] color3 = new int[]{getResources().getColor(R.color.color_lunch_normal), getResources().getColor(R.color.bg_lunch_normal)};
        stat_view.setHistogramColor(color1, color2, color3);
    }
}
