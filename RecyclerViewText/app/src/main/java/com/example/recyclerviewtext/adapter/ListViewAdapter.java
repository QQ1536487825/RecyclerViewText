package com.example.recyclerviewtext.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recyclerviewtext.R;
import com.example.recyclerviewtext.bean.ItemBean;

import java.util.List;

public class ListViewAdapter extends BaseAdapter {
    //普通条目类型
    public static final int TYPE_NORMAL = 0;
    //加载更多
    public static final int TYPE_LODADER_MORE = 1;
    public OnRefreshListener mUpPullRefreshListener;

    public ListViewAdapter(List<ItemBean> mData) {
        super(mData);

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = getSubView(parent, viewType);
        if (viewType == TYPE_NORMAL) {
            return new InnerHolder(view);
        } else {
//            LoaderMoreHolder loaderMoreHolder = new LoaderMoreHolder(view);
//            loaderMoreHolder.update(LoaderMoreHolder.LOADER_START_LOADING);
            return new LoaderMoreHolder(view);

        }


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_NORMAL && holder instanceof InnerHolder) {
            ((InnerHolder) holder).setData(mData.get(position), position);
        } else if (getItemViewType(position) == TYPE_LODADER_MORE && holder instanceof LoaderMoreHolder) {
            ((LoaderMoreHolder) holder).update(LoaderMoreHolder.LOADER_START_LOADING);

        }
        //绑定数据
    }

    @Override
    protected View getSubView(ViewGroup parent, int viewType) {
        View view;
        //根据类型来创建view
        if (viewType == TYPE_NORMAL) {
            //
            view = View.inflate(parent.getContext(), R.layout.item_list_view, null);
        } else {
            //这个是加载更多的
            view = View.inflate(parent.getContext(), R.layout.item_list_loader_more, null);

        }
        return view;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            //最后一个返回加载更多
            return TYPE_LODADER_MORE;
        }
        return TYPE_NORMAL;
    }

    //设置刷新监听的接口
    public void setOnRefreshListener(OnRefreshListener listener) {
        this.mUpPullRefreshListener = listener;
    }

    //定义接口
    public interface OnRefreshListener {
        void onUpdatePullRefresh(LoaderMoreHolder loaderMoreHolder);
    }

    public class LoaderMoreHolder extends RecyclerView.ViewHolder {
        public static final int LOADER_START_LOADING = 0;
        public static final int LOADER_START_RELOAD = 1;
        public static final int LOADER_START_NORMAL = 2;


        private LinearLayout loading;
        private TextView reload;


        public LoaderMoreHolder(@NonNull View itemView) {
            super(itemView);
            loading = itemView.findViewById(R.id.loading);
            reload = itemView.findViewById(R.id.reload);
            reload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //这里要设置去触发加载数据
                    //触发加载数据
                    update(LOADER_START_LOADING);
                }
            });
        }

        public void update(int state) {
            //重置控件状态
            loading.setVisibility(View.GONE);
            reload.setVisibility(View.GONE);
            switch (state) {
                case LOADER_START_LOADING:
                    loading.setVisibility(View.VISIBLE);
                    //触发加载数据
                    startLoaderMore();
                    break;
                case LOADER_START_RELOAD:
                    reload.setVisibility(View.VISIBLE);

                    break;
                case LOADER_START_NORMAL:
                    loading.setVisibility(View.GONE);
                    reload.setVisibility(View.GONE);
                    break;
            }

        }
        
        public void startLoaderMore() {
            if (mUpPullRefreshListener != null) {
                mUpPullRefreshListener.onUpdatePullRefresh(this);
            }
        }
    }
}
