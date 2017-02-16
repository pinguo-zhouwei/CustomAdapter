package com.zhouwei.customadapter.ui;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;

import com.zhouwei.customadapter.R;
import com.zhouwei.customadapter.cell.DetailCell;
import com.zhouwei.customadapter.model.DetailEntry;
import com.zhouwei.rvadapterlib.base.Cell;
import com.zhouwei.rvadapterlib.fragment.AbsBaseFragment;

import java.util.ArrayList;
import java.util.List;

import static com.zhouwei.customadapter.DataMocker.mockStaggerData;

/**
 * Created by zhouwei on 17/2/4.
 */

public class DetailFragment extends AbsBaseFragment<DetailEntry> {
    @Override
    public void onRecyclerViewInitialized() {
         mBaseAdapter.setData(getCells(mockStaggerData()));
    }

    @Override
    public void onPullRefresh() {

    }

    @Override
    public void onLoadMore() {

    }

    @Override
    protected RecyclerView.LayoutManager initLayoutManger() {
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        return layoutManager;
    }

    @Override
    protected List<Cell> getCells(List<DetailEntry> list) {
        List<Cell> cells = new ArrayList<>();
        for (int i=0;i<list.size();i++){
            cells.add(new DetailCell(list.get(i)));
        }
        return cells;
    }

    @Override
    protected View customLoadMoreView() {
        View loadMoreView = LayoutInflater.from(getContext()).inflate(R.layout.custeom_load_more_layout,null);
        return loadMoreView;
    }
}
