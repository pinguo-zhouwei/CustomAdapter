package com.zhouwei.rvadapterlib.base;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.zhouwei.rvadapterlib.cell.EmptyCell;
import com.zhouwei.rvadapterlib.cell.ErrorCell;
import com.zhouwei.rvadapterlib.cell.LoadMoreCell;
import com.zhouwei.rvadapterlib.cell.LoadingCell;

/**
 * Created by zhouwei on 17/1/23.
 */

public class RVSimpleAdapter extends RVBaseAdapter{
    public static final int ERROR_TYPE = Integer.MAX_VALUE -1;
    public static final int EMPTY_TYPE = Integer.MAX_VALUE -2;
    public static final int LOADING_TYPE = Integer.MAX_VALUE -3;
    public static final int LOAD_MORE_TYPE = Integer.MAX_VALUE -4;

    private EmptyCell mEmptyCell;
    private ErrorCell mErrorCell;
    private LoadingCell mLoadingCell;
    private LoadMoreCell mLoadMoreCell;
    //LoadMore 是否已显示
    private boolean mIsShowLoadMore = false;

    public RVSimpleAdapter(){
        mEmptyCell = new EmptyCell(null);
        mErrorCell = new ErrorCell(null);
        mLoadingCell = new LoadingCell(null);
        mLoadMoreCell = new LoadMoreCell(null);
    }
    @Override
    protected void onViewHolderBound(RVBaseViewHolder holder, int position) {

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        //处理GridView 布局
        if(manager instanceof GridLayoutManager){
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) manager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int viewType = getItemViewType(position);
                    return (viewType == ERROR_TYPE|| viewType == EMPTY_TYPE || viewType == LOADING_TYPE
                    ||viewType == LOAD_MORE_TYPE) ? gridLayoutManager.getSpanCount():1;
                }
            });
        }

    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        // 处理StaggeredGridLayoutManager 显示这个Span
        int position = holder.getAdapterPosition();
        int viewType = getItemViewType(position);
        if(isStaggeredGridLayout(holder)){
            if(viewType == ERROR_TYPE|| viewType == EMPTY_TYPE || viewType == LOADING_TYPE
                    ||viewType == LOAD_MORE_TYPE){

                StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
                //设置显示整个span
                params.setFullSpan(true);
            }
        }

    }

    private boolean isStaggeredGridLayout(RecyclerView.ViewHolder holder) {
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        if (layoutParams != null && layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
            return true;
        }
        return false;
    }

    /**
     * 显示LoadingView
     * <p>请求数据时调用，数据请求完毕时调用{@link #hideLoading }</p>
     * @see #showLoadingKeepCount(int)
     */
    public void showLoading(){
      clear();
      add(mLoadingCell);
    }

    public void showLoading(View loadingView){
        if(loadingView == null){
            showLoading();
        }
        clear();
        mLoadingCell.setLoadingView(loadingView);
        add(mLoadingCell);
    }
    /**
     * 显示LoadingView
     * <p>列表显示LoadingView并保留keepCount个Item</p>
     * @param keepCount 保留的条目数量
     */
    public void showLoadingKeepCount(int keepCount){
        if(keepCount < 0 || keepCount>mData.size()){
            return;
        }
        remove(keepCount,mData.size() - keepCount);
        if(mData.contains(mLoadingCell)){
            mData.remove(mLoadingCell);
        }
        add(mLoadingCell);
    }
    /**
     * hide Loading view
     */
    public void hideLoading(){
        if(mData.contains(mLoadingCell)){
            mData.remove(mLoadingCell);
        }
    }

    /**
     * 显示错误提示
     * <p>当网络请求发生错误，需要在界面给出错误提示时，调用{@link #showError}</p>
     * @see #showErrorKeepCount(int)
     */
    public void showError(){
       clear();
       add(mErrorCell);
    }

    /**
     * 显示错误提示
     * <p>当网络请求发生错误，需要在界面给出错误提示时，调用{@link #showErrorKeepCount(int)},并保留keepCount 条Item</p>
     * @param keepCount 保留Item数量
     */
    public void showErrorKeepCount(int keepCount){
        if(keepCount < 0 || keepCount>mData.size()){
            return;
        }
        remove(keepCount,mData.size() - keepCount);
        if(mData.contains(mErrorCell)){
            mData.remove(mErrorCell);
        }
        add(mErrorCell);

    }

    /**
     * 隐藏错误提示
     */
    public void hideErorr(){
       if(mData.contains(mErrorCell)){
           remove(mErrorCell);
       }
    }

    /**
     * 显示LoadMoreView
     * <p>当列表滑动到底部时，调用{@link #showLoadMore()} 提示加载更多，加载完数据，调用{@link #hideLoadMore()}
     * 隐藏LoadMoreView,显示列表数据。</p>
     *
     */
    public void showLoadMore(){
       if(mData.contains(mLoadMoreCell)){
           return;
       }
       add(mLoadMoreCell);
       mIsShowLoadMore = true;
    }

    /**
     * 隐藏LoadMoreView
     * <p>调用{@link #showLoadMore()}之后，加载数据完成，调用{@link #hideLoadMore()}隐藏LoadMoreView</p>
     */
    public void hideLoadMore(){
       if(mData.contains(mLoadMoreCell)){
           remove(mLoadMoreCell);
           mIsShowLoadMore = false;
       }
    }

    /**
     * LoadMore View 是否已经显示
     * @return
     */
    public boolean isShowLoadMore() {
        return mIsShowLoadMore;
    }

    /**
     *
     * @param keepCount
     */
    public void showEmptyKeepCount(int keepCount){
        if(keepCount < 0 || keepCount>mData.size()){
            return;
        }
        remove(keepCount,mData.size() - keepCount);
        if(mData.contains(mEmptyCell)){
            mData.remove(mEmptyCell);
        }
        add(mEmptyCell);

    }

    /**
     * 显示空view
     * <p>当页面没有数据的时候，调用{@link #showEmpty()}显示空View，给用户提示</p>
     */
    public void showEmpty(){
      clear();
      add(mEmptyCell);
    }

    /**
     * 隐藏空View
     */
    public void hideEmpty(){
      if(mData.contains(mEmptyCell)){
          remove(mEmptyCell);
      }
    }
}
