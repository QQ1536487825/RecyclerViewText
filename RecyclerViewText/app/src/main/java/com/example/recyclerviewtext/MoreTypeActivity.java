package com.example.recyclerviewtext;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ListView;

import com.example.recyclerviewtext.adapter.MoreTypeAdapter;
import com.example.recyclerviewtext.bean.Datas;
import com.example.recyclerviewtext.bean.MoreTypeBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MoreTypeActivity extends AppCompatActivity {

    private RecyclerView mRecyclrview;
    private List<MoreTypeBean> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_type);

        mRecyclrview = findViewById(R.id.more_type_list);
        mData = new ArrayList<>();
        //初始化数据
        initDatas();

        //创建和设置布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclrview.setLayoutManager(layoutManager);

        //创建适配器
        MoreTypeAdapter adapter = new MoreTypeAdapter(mData);

        //设置适配器
        mRecyclrview.setAdapter(adapter);

    }

    private void initDatas() {
        Random random = new Random();
        //准备数据
        for (int i = 0; i < Datas.icons.length; i++) {
            MoreTypeBean data = new MoreTypeBean();
            data.pic = Datas.icons[i];

            data.type = random.nextInt(3);
            mData.add(data);

        }
    }
}
