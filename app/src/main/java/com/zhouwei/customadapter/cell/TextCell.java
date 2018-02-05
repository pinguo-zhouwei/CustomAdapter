package com.zhouwei.customadapter.cell;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.zhouwei.customadapter.R;
import com.zhouwei.customadapter.model.Entry;
import com.zhouwei.rvadapterlib.base.RVBaseCell;
import com.zhouwei.rvadapterlib.base.RVBaseViewHolder;

/**
 * Created by zhouwei on 17/1/19.
 */

public class TextCell extends RVBaseCell<Entry> {
    public static final int TYPE = 0;
    public TextCell(Entry entry) {
        super(entry);
    }

    @Override
    public int getItemType() {
        return TYPE;
    }

    @Override
    public RVBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RVBaseViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.text_cell_layout,null));
    }

    @Override
    public void onBindViewHolder(RVBaseViewHolder holder, int position) {
        Log.e("zhouwei","TextCell onBindViewHolder");
       holder.setText(R.id.text_content,mData.content);
    }

    @Override
    public void releaseResource() {
        super.releaseResource();
        Log.e("zhouwei","TextCell releaseResource");
    }
}
