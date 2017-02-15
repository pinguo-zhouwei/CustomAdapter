package com.zhouwei.customadapter.ui;

import android.view.LayoutInflater;
import android.view.View;

import com.zhouwei.customadapter.DataMocker;
import com.zhouwei.customadapter.R;
import com.zhouwei.customadapter.cell.BannerCell;
import com.zhouwei.customadapter.cell.ImageCell;
import com.zhouwei.customadapter.cell.TextCell;
import com.zhouwei.customadapter.model.Entry;
import com.zhouwei.rvadapterlib.base.Cell;
import com.zhouwei.rvadapterlib.fragment.AbsBaseFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.zhouwei.customadapter.DataMocker.mockData;
import static com.zhouwei.customadapter.DataMocker.mockMoreData;

/**
 * Created by zhouwei on 17/2/3.
 */

public class HomePageFragment extends AbsBaseFragment<Entry> {
    @Override
    public void onRecyclerViewInitialized() {
       //初始化View和数据加载
       //设置刷新进度条颜色
       setColorSchemeResources(R.color.colorAccent);
       loadData();
    }

    @Override
    public void onPullRefresh() {
        //下拉刷新回调
        mRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
               setRefreshing(false);
            }
        },2000);
    }

    @Override
    public void onLoadMore() {
        //上拉加载回调
       loadMore();
    }


    private void loadMore(){
        mRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                hideLoadMore();
                mBaseAdapter.addAll(getCells(mockMoreData()));
            }
        },10000);
    }

    protected List<Cell> getCells(List<Entry> entries){
        //根据实体生成Cell
        List<Cell> cells = new ArrayList<>();
        cells.add(new BannerCell(Arrays.asList(DataMocker.images)));
        for (int i=0;i<entries.size();i++){
            Entry entry = entries.get(i);
            if(entry.type == Entry.TYPE_IMAGE){
                cells.add(new ImageCell(entry));
            }else{
                cells.add(new TextCell(entry));
            }
        }
        return cells;
    }


    @Override
    public View addToolbar() {
        View toolbar = LayoutInflater.from(getContext()).inflate(R.layout.title_bar_layout,null);
        return toolbar;
    }

    /**
     * 模拟从服务器取数据
     */
    private void loadData(){
        View loadingView = LayoutInflater.from(getContext()).inflate(R.layout.manu_loading_layout,null);
        mBaseAdapter.showLoading(loadingView);
        mRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mBaseAdapter.hideLoading();
                mBaseAdapter.addAll(getCells(mockData()));
            }
        },2000);
    }
}
