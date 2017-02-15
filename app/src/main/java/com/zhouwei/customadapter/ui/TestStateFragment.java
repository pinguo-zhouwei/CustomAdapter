package com.zhouwei.customadapter.ui;

import android.view.LayoutInflater;
import android.view.View;

import com.zhouwei.customadapter.DisplayUtils;
import com.zhouwei.customadapter.R;
import com.zhouwei.customadapter.cell.HeaderCell;
import com.zhouwei.rvadapterlib.base.Cell;
import com.zhouwei.rvadapterlib.fragment.AbsBaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhouwei on 17/2/15.
 */

public class TestStateFragment extends AbsBaseFragment<Object> {
    @Override
    public void onRecyclerViewInitialized() {
       loadData();
    }

    @Override
    public void onPullRefresh() {
      mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onLoadMore() {

    }

    private void loadData(){
        mBaseAdapter.addAll(getCells(null));

        showLoading();
        //showEmpty();
        //showError();
    }

    private void showEmpty(){
        int height = DisplayUtils.getScreenHeight(getContext()) - DisplayUtils.dpToPx(getContext(),300);
       mBaseAdapter.showEmptyKeepCount(1,height);
       mBaseAdapter.showLoading();
    }

    private void showError(){
        int height = DisplayUtils.getScreenHeight(getContext()) - DisplayUtils.dpToPx(getContext(),300);
        mBaseAdapter.showErrorKeepCount(1,height);
    }

    private void showLoading(){
        int height = DisplayUtils.getScreenHeight(getContext()) - DisplayUtils.dpToPx(getContext(),300);
        View loadingView = LayoutInflater.from(getContext()).inflate(R.layout.manu_loading_layout,null);
        mBaseAdapter.showLoadingKeepCount(1,height,loadingView);
    }

    @Override
    protected List<Cell> getCells(List<Object> list) {
        List<Cell> cells = new ArrayList<>();
        cells.add(new HeaderCell(null));
        return cells;
    }
}
