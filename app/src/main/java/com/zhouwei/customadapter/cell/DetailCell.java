package com.zhouwei.customadapter.cell;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.zhouwei.customadapter.model.DetailEntry;
import com.zhouwei.customadapter.R;
import com.zhouwei.rvadapterlib.base.RVBaseCell;
import com.zhouwei.rvadapterlib.base.RVBaseViewHolder;

import java.util.Random;

/**
 * Created by zhouwei on 17/2/4.
 */

public class DetailCell extends RVBaseCell<DetailEntry> {
    public DetailCell(DetailEntry detailEntry) {
        super(detailEntry);
    }

    @Override
    public int getItemType() {
        return 0;
    }

    @Override
    public RVBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RVBaseViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_item_layout,null));
    }

    @Override
    public void onBindViewHolder(RVBaseViewHolder holder, int position) {
        Picasso.with(holder.getItemView().getContext()).load(mData.imageUrl).into(holder.getImageView(R.id.grid_image));
        holder.setText(R.id.grid_title,mData.title);
    }

    private int  generateHeight(){
       return new Random().nextInt(100);
    }
}
