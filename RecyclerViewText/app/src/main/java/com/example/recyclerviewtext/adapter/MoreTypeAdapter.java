package com.example.recyclerviewtext.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recyclerviewtext.R;
import com.example.recyclerviewtext.bean.MoreTypeBean;

import java.util.List;

public class MoreTypeAdapter extends RecyclerView.Adapter {
    //定义三种常量，因为有三个类型
    private static final int TYPE_FULL_IMAGE = 0;
    private static final int TYPE_RIGHT_IMAGE = 1;
    private static final int TYPE_THREE_IMAGE = 2;
    public List<MoreTypeBean> mData;

    public MoreTypeAdapter(List<MoreTypeBean> data) {
        this.mData = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        //这里面去创建ViewHolder
        if (viewType == TYPE_FULL_IMAGE) {
            view = null;
            view= View.inflate(parent.getContext(), R.layout.item_type_full_image,null);
            return new FullImageHolder(view);
        }else if (viewType == TYPE_RIGHT_IMAGE){
            view = null;
            view= View.inflate(parent.getContext(), R.layout.item_right_image,null);

            return new RightImageHolder(view);

        }else {
            view = null;
            view= View.inflate(parent.getContext(), R.layout.item_type_three,null);

            return new ThreeImageHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        if (mData != null) {
            return mData.size();
        }
        return 0;
    }
    //要复写一个方法，这个方法根据条件返回条目类型

    @Override
    public int getItemViewType(int position) {
        MoreTypeBean moreTypeBean = mData.get(position);
        if (moreTypeBean.type == 0) {
            return TYPE_FULL_IMAGE;
        } else if (moreTypeBean.type == 1) {
            return TYPE_RIGHT_IMAGE;
        } else {
            return TYPE_THREE_IMAGE;
        }
    }

    private class FullImageHolder extends RecyclerView.ViewHolder {
        public FullImageHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    private class ThreeImageHolder extends RecyclerView.ViewHolder {
        public ThreeImageHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    private class RightImageHolder extends RecyclerView.ViewHolder {
        public RightImageHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
