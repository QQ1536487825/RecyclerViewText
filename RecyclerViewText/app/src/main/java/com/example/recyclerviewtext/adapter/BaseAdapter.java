package com.example.recyclerviewtext.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recyclerviewtext.R;
import com.example.recyclerviewtext.bean.ItemBean;

import java.util.List;

public abstract class BaseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public final List<ItemBean> mData;
    private OnItemClickListener mOnItemClickListener;

    public BaseAdapter(List<ItemBean> mData) {
        this.mData = mData;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = getSubView(parent, viewType);
        return new InnerHolder(view);
    }

    protected abstract View getSubView(ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        //绑定数据
        ((InnerHolder)holder).setData(mData.get(position),position);
    }

    @Override
    public int getItemCount() {
        //返回条目个数
        if (mData != null) {
            return mData.size();
        }
        return 0;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        //设置一个监听，就是设置一个回调的接口
        this.mOnItemClickListener = listener;

    }

    /*
     * 编写回调的步骤
     * 1.创建这个接口
     * 2.定义接口内部的方法
     * 3.提供设置接口实现
     * 4.接口方法的调用*/

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public class InnerHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private ImageView icon;
        private int mPosition;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            icon = itemView.findViewById(R.id.icon);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(mPosition);
                    }
                }
            });
        }

        public void setData(ItemBean itemBean, int position) {
            this.mPosition = position;
            title.setText(itemBean.title);
            icon.setImageResource(itemBean.icon);
        }
    }
}
