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

public class EmptyCell extends RVBaseCell<Object> {
    private View mEmptyView = null;
    public EmptyCell(Object o) {
        super(o);
    }


    public void setEmptyView(View emptyView) {
        mEmptyView = emptyView;
    }

    @Override
    public void releaseResource() {
        mEmptyView =null;
    }

    @Override
    public int getItemType() {
        return RVSimpleAdapter.EMPTY_TYPE;
    }

    @Override
    public RVBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.empty_layout,null);
        if(mEmptyView!=null){
            LinearLayout container = (LinearLayout) view.findViewById(R.id.empty_root);
            container.removeAllViews();
            container.addView(mEmptyView);
        }
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        view.setLayoutParams(params);
        return new RVBaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RVBaseViewHolder holder, int position) {

    }
}
