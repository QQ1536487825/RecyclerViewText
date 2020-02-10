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

public class GridViewAdapter extends BaseAdapter {

    public GridViewAdapter(List<ItemBean> mData) {
        super(mData);
    }

    @Override
    protected View getSubView(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_grid_view, null);
        return view;
    }

}
