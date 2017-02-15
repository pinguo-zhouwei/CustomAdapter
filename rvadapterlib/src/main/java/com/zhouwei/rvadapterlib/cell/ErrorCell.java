package com.zhouwei.rvadapterlib.cell;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.zhouwei.rvadapterlib.R;
import com.zhouwei.rvadapterlib.base.RVBaseViewHolder;
import com.zhouwei.rvadapterlib.base.RVSimpleAdapter;

/**
 * Created by zhouwei on 17/1/23.
 */

public class ErrorCell extends RVAbsStateCell {
    public ErrorCell(Object o) {
        super(o);
    }

    @Override
    public int getItemType() {
        return RVSimpleAdapter.ERROR_TYPE;
    }

    @Override
    public void onBindViewHolder(RVBaseViewHolder holder, int position) {

    }

    @Override
    protected View getDefaultView(Context context) {
        return LayoutInflater.from(context).inflate(R.layout.rv_error_layout,null);
    }
}
