package com.example.recyclerviewtext;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.recyclerviewtext.adapter.BaseAdapter;
import com.example.recyclerviewtext.adapter.GridViewAdapter;
import com.example.recyclerviewtext.adapter.ListViewAdapter;
import com.example.recyclerviewtext.adapter.StaggerAdapter;
import com.example.recyclerviewtext.bean.Datas;
import com.example.recyclerviewtext.bean.ItemBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "";
    private RecyclerView mList;
    private List<ItemBean> mData;
    private BaseAdapter adapter;
    private SwipeRefreshLayout mRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //找到控件
        mList = findViewById(R.id.recycler_view);
        mRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        //准备数据
        /*准备数据，一般来说，我们在现实中开发，数据都是从网络获取的
         * ，这里只是模拟数据，现实中也要模拟数据，后台没准备好的时候，*/
        initData();
        //初始化事件
        initListener();
        //设置默认显示为ListView的效果
        showList(true, false);
        //处理下拉刷新
        handlerDownPullUpdate();
    }

    private void initListener() {
        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(MainActivity.this, "点击" + position, Toast.LENGTH_SHORT).show();
            }
        });
        //这里面去处理上拉加载更多
        if (adapter instanceof ListViewAdapter) {
            ((ListViewAdapter) adapter).setOnRefreshListener(new ListViewAdapter.OnRefreshListener() {
                @Override
                public void onUpdatePullRefresh(final ListViewAdapter.LoaderMoreHolder loaderMoreHolder) {

                    //更新UI
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            //随机数
                            Random random = new Random();
                            if (random.nextInt() % 2 == 0) {
                                //这里面去加载更多的数据，同样在子线程中，完成，这里仅作演示
                                ItemBean data = new ItemBean();
                                data.title = "我是新添加的数据";
                                data.icon = R.mipmap.pic1;
                                mData.add(data);
                                //这里做两件事，一件是刷新停止，另一件事是更新列表
                                adapter.notifyDataSetChanged();
                                ;
                                loaderMoreHolder.update(ListViewAdapter.LoaderMoreHolder.LOADER_START_NORMAL);

                            } else {
                                loaderMoreHolder.update(ListViewAdapter.LoaderMoreHolder.LOADER_START_RELOAD);
                            }

                        }
                    }, 3000);
                }
            });
        }
    }


    //这个方法用于初始化模拟数据
    private void initData() {
        //数据一般是集合Bean类
        //创建数据集合
        mData = new ArrayList<>();

        //创建模拟数据
        for (int i = 0; i < Datas.icons.length; i++) {
            //创建对象
            ItemBean data = new ItemBean();
            data.icon = Datas.icons[i];
            data.title = "我是第" + i + "个条目";
            //添加到集合里面
            mData.add(data);
            //设置默认的显示样式为ListView的效果
            showList(true, false);

        }


    }

    private void handlerDownPullUpdate() {
        //设置颜色
        mRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimaryDark);


        //首先要可用
        mRefreshLayout.setEnabled(true);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //在这里之心刷新数据的操作
                /*
                 * 当我们在顶部，下拉的时候，这个方法就会触发
                 * 但是这个方法是MainThread主线程，不可以执行耗时操作
                 * 一般来说，我们去请求一个数据在开一个线程去获取
                 * 这里演示就直接去获取*/
                //添加数据
                ItemBean data = new ItemBean();
                data.title = "我是新添加的数据";
                data.icon = R.mipmap.pic1;
                mData.add(0, data);
                //更新UI
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //这里做两件事，一件是刷新停止，另一件事是更新列表
                        adapter.notifyDataSetChanged();
                        ;
                        mRefreshLayout.setRefreshing(false);

                    }
                }, 3000);
            }
        });

    }

    /*用来加载菜单条目*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            //ListView部分
            case R.id.list_view_vertical_stander:
                Log.d(TAG, "onOptionsItemSelected: list_view_vertical_stander");
                showList(true, false);
                break;
            case R.id.list_view_vertical_reverse:
                showList(true, true);
                break;
            case R.id.list_view_horizontal_stander:
                showList(false, false);
                break;
            case R.id.list_view_horizontal_reverse:
                showList(false, true);
                break;
            //gridView部分
            case R.id.grid_view_vertical_stander:
                showGrid(true, false);
                break;
            case R.id.grid_view_vertical_reverse:
                showGrid(true, true);

                break;
            case R.id.grid_view_horizontal_stander:
                showGrid(false, false);

                break;
            case R.id.grid_view_horizontal_reverse:
                showGrid(false, true);

                break;
            //瀑布流部分
            case R.id.stagger_view_vertical_stander:
                showStagger(true, false);
                break;
            case R.id.stagger_view_vertical_reverse:
                showStagger(true, true);

                break;
            case R.id.stagger_view_horizontal_stander:
                showStagger(false, false);

                break;
            case R.id.stagger_view_horizontal_reverse:
                showStagger(false, true);
                break;
            case R.id.more_type:
                //跳转到新的Activity中去实现这个功能
                Intent intent = new Intent(this, MoreTypeActivity.class);
                startActivity(intent);

                break;


        }
        return super.onOptionsItemSelected(item);
    }

    /*这个方法用于实现瀑布流的效果*/
    private void showStagger(boolean isVertical, boolean isReverse) {
        //准备布局管理器
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, isVertical ? StaggeredGridLayoutManager.VERTICAL : StaggeredGridLayoutManager.HORIZONTAL);
        //设置布局管理器的方向
        layoutManager.setReverseLayout(isReverse);
        //设置布局管理器到RecyclerView里面
        mList.setLayoutManager(layoutManager);
        //创建适配器
        adapter = new StaggerAdapter(mData);
        //设置适配器
        mList.setAdapter(adapter);
        initListener();
    }

    /*
     * 这个方法用于实现和GridView一样的效果*/
    private void showGrid(boolean isVertical, boolean isReverse) {
        //创建布局管理器
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        //设置水平还是垂直
        layoutManager.setOrientation(isVertical ? GridLayoutManager.VERTICAL : GridLayoutManager.HORIZONTAL);
        //设置标准正向还是反向
        layoutManager.setReverseLayout(isReverse);

        //设置布局管理器
        mList.setLayoutManager(layoutManager);
        //创建适配器
        adapter = new GridViewAdapter(mData);
        //设置适配器
        mList.setAdapter(adapter);
        initListener();

    }

    /*这个方法用于显示ListView一样的效果*/
    private void showList(boolean isVertical, boolean isReverse) {
        //RecyclerView需要设置样式，就是设置布局管理器,要不然不显示数据
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        //设置布局管理器来控制

        //设置水平还是垂直
        linearLayoutManager.setOrientation(isVertical ? LinearLayoutManager.VERTICAL : LinearLayoutManager.HORIZONTAL);
        //设置标准正向还是反向
        linearLayoutManager.setReverseLayout(isReverse);


        mList.setLayoutManager(linearLayoutManager);
        //创建适配器
        adapter = new ListViewAdapter(mData);
        //设置到RecyclerView里面
        mList.setAdapter(adapter);
        initListener();

    }
}
