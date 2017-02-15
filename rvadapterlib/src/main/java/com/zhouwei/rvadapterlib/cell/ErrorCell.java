package com.zhouwei.rvadapterlib.cell;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.zhouwei.rvadapterlib.R;
import com.zhouwei.rvadapterlib.base.RVBaseCell;
import com.zhouwei.rvadapterlib.base.RVBaseViewHolder;
import com.zhouwei.rvadapterlib.base.RVSimpleAdapter;

/**
 * Created by zhouwei on 17/1/23.
 */

public class ErrorCell extends RVBaseCell<Object> {
    private View mErrorView;
    public ErrorCell(Object o) {
        super(o);
    }

    public void setErrorView(View errorView) {
        mErrorView = errorView;
    }

    @Override
    public void releaseResource() {
        mErrorView = null;
    }

    @Override
    public int getItemType() {
        return RVSimpleAdapter.ERROR_TYPE;
    }

    @Override
    public RVBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.error_layout,null);
        if(mErrorView!=null){
            LinearLayout container = (LinearLayout) view.findViewById(R.id.empty_root);
            container.removeAllViews();
            container.addView(mErrorView);
        }
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        view.setLayoutParams(params);
        return new RVBaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RVBaseViewHolder holder, int position) {

    }
}
