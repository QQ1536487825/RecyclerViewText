package com.example.recyclerviewtext.adapter;

import android.view.View;
import android.view.ViewGroup;

import com.example.recyclerviewtext.R;
import com.example.recyclerviewtext.bean.ItemBean;

import java.util.List;

public class StaggerAdapter extends BaseAdapter {

    public StaggerAdapter(List<ItemBean> mData) {
        super(mData);
    }

    @Override
    protected View getSubView(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_stagger, null);
        return view;
    }
}
