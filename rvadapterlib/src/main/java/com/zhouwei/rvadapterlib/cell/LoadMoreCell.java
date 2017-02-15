package com.zhouwei.rvadapterlib.cell;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhouwei.rvadapterlib.R;
import com.zhouwei.rvadapterlib.base.RVBaseCell;
import com.zhouwei.rvadapterlib.base.RVBaseViewHolder;
import com.zhouwei.rvadapterlib.base.RVSimpleAdapter;

/**
 * Created by zhouwei on 17/1/23.
 */

public class LoadMoreCell extends RVBaseCell<Object> {
    public LoadMoreCell(Object o) {
        super(o);
    }

    @Override
    public int getItemType() {
        return RVSimpleAdapter.LOAD_MORE_TYPE;
    }

    @Override
    public RVBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_load_more_layout,null);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(params);
        return new RVBaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RVBaseViewHolder holder, int position) {

    }
}
