package com.zhouwei.rvadapterlib.base;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.zhouwei.rvadapterlib.Utils;
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
    private boolean mIsShowError = false;
    private boolean mIsShowLoading = false;
    private boolean mIsShowEmpty = false;

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

    /**
     * 判断是否是瀑布流布局
     * @param holder
     * @return
     */
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
      mIsShowLoading = true;
      add(mLoadingCell);
    }

    /**
     * 列表Loading 状态显示的View，默认全屏高度
     * @param loadingView
     * @see {@link #showEmpty(View, int)}
     */
    public void showLoading(View loadingView){
        showLoading(loadingView,0);
    }

    /**
     * 指定列表Loading 状态显示的View，并指定View显示高度
     * @param loadingView
     * @param viewHeight
     */
    public void showLoading(View loadingView,int viewHeight){
        if(loadingView == null){
            showLoading();
            return;
        }
        clear();
        mIsShowLoading = true;
        mLoadingCell.setView(loadingView);
        mLoadingCell.setHeight(viewHeight);
        add(mLoadingCell);
    }
    /**
     * 列表LoadingView 状态显示的View
     * <p>列表显示LoadingView并保留keepCount个Item</p>
     * @param keepCount 保留的条目数量
     */
    public void showLoadingKeepCount(int keepCount){
        showLoadingKeepCount(keepCount,0);
    }

    /**
     * 列表Loading状态显示的View，保留keepCountg个Item，并指定高度
     * @param keepCount 保留item的个数
     * @param height View显示的高度
     */
    public void showLoadingKeepCount(int keepCount,int height){
        showLoadingKeepCount(keepCount,height,null);
    }

    /**
     * 列表Loading状态显示的View，保留keepCountg个Item，并指定高度，指定显示的View
     * @param keepCount 保留item的个数
     * @param height View显示的高度
     * @param loadingView 显示的View
     */
    public void showLoadingKeepCount(int keepCount,int height,View loadingView){
        if(keepCount < 0 || keepCount>mData.size()){
            return;
        }
        remove(keepCount,mData.size() - keepCount);
        checkNotContainSpecailCell();
        mIsShowLoading = true;
        if(loadingView!=null){
            mLoadingCell.setView(loadingView);
        }
        mLoadingCell.setHeight(height);
        add(mLoadingCell);
    }


    /**
     * hide Loading view
     */
    public void hideLoading(){
        if(mData.contains(mLoadingCell)){
            mData.remove(mLoadingCell);
            mIsShowLoading = false;
        }
    }

    /**
     * 显示错误提示View
     * <p>当网络请求发生错误，需要在界面给出错误提示时，调用{@link #showError}</p>
     * @see #showErrorKeepCount(int)
     */
    public void showError(){
       clear();
        mIsShowError = true;
       add(mErrorCell);
    }

    /**
     * 显示错误提示View
     * <p>当网络请求发生错误，需要在界面给出错误提示时，调用{@link #showErrorKeepCount(int)},并保留keepCount 条Item</p>
     * @param keepCount 保留Item数量
     */
    public void showErrorKeepCount(int keepCount){
        showErrorKeepCount(keepCount,0);
    }

    /**
     *  显示错误提示View，并指定保留的item数和View显示的高
     * @param keepCount 保留的item数
     * @param height view显示的高
     */
    public void showErrorKeepCount(int keepCount,int height){
        showErrorKeepCount(keepCount,height,null);
    }

    /**
     *  显示错误提示View，并指定保留的item数和View显示的高
     * @param keepCount 保留的item数
     * @param height view显示的高
     * @param errorView 指定显示的View，null 则显示默认View
     */
    public void showErrorKeepCount(int keepCount,int height,View errorView){
        if(keepCount < 0 || keepCount>mData.size()){
            return;
        }
        remove(keepCount,mData.size() - keepCount);
        checkNotContainSpecailCell();
        mIsShowError = true;
        if(errorView!=null){
            mErrorCell.setView(errorView);
        }
        mErrorCell.setHeight(height);
        add(mErrorCell);
    }

    /**
     * 指定列表发生错误时显示的View，默认为全屏高度
     * @param errorView
     * @see {@link #showError(View, int)}
     */
    public void showError(View errorView){
        showError(errorView,0);
    }

    /**
     * 指定列表发生错误时显示的View，并指定View高度
     * @param errorView
     * @param viewHeight
     */
    public void showError(View errorView,int viewHeight){
      if(errorView == null){
          showError();
          return;
      }
      clear();
      mIsShowError = true;
      mErrorCell.setHeight(viewHeight);
      mErrorCell.setView(errorView);
      add(mErrorCell);
    }

    /**
     * 隐藏错误提示
     */
    public void hideErorr(){
       if(mData.contains(mErrorCell)){
           remove(mErrorCell);
           mIsShowError = false;
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
       checkNotContainSpecailCell();
       add(mLoadMoreCell);
       mIsShowLoadMore = true;
    }

    /**
     * 指定显示的LoadMore View
     * @param loadMoreView
     */
    public void showLoadMore(View loadMoreView){
        showLoadMore(loadMoreView,0);
    }

    /**
     * 指定显示的LoadMoreView,并指定显示的高度
     * @param loadMoreView
     * @param height
     */
    public void showLoadMore(View loadMoreView,int height){
       if(loadMoreView == null){
           return;
       }
       checkNotContainSpecailCell();
       //设置默认高度
       if(height == 0){
           int defaultHeight = Utils.dpToPx(loadMoreView.getContext(),LoadMoreCell.mDefaultHeight);
           mLoadMoreCell.setHeight(defaultHeight);
       }else{
           mLoadMoreCell.setHeight(height);
       }
       mLoadMoreCell.setView(loadMoreView);
       mIsShowLoadMore = true;
       add(mLoadMoreCell);
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
     * 显示空状态View，并保留keepCount个Item
     * @param keepCount
     */
    public void showEmptyKeepCount(int keepCount){
        showEmptyKeepCount(keepCount,0);
    }

    /**
     * 显示空状态View，保留keepCount个Item,并指定View显示的高
     * @param keepCount 保留的Item个数
     * @param height View 显示的高度
     *
     * @see {@link #showEmptyKeepCount(int)}
     * @see {@link #showEmptyKeepCount(int, int, View)}
     */
    public void showEmptyKeepCount(int keepCount,int height){
        showEmptyKeepCount(keepCount,height,null);
    }

    /**
     * 显示空状态View，保留keepCount个Item,并指定View和View显示的高
     * @param keepCount 保留的Item个数
     * @param height 显示的View的高
     * @param view 显示的View，null 则显示默认View
     */
    public void showEmptyKeepCount(int keepCount,int height,View view){
        if(keepCount < 0 || keepCount>mData.size()){
            return;
        }
        remove(keepCount,mData.size() - keepCount);
        checkNotContainSpecailCell();
        mIsShowEmpty = true;
        if(view !=null){
            mEmptyCell.setView(view);
        }
        mEmptyCell.setHeight(height);
        add(mEmptyCell);
    }

    /**
     * 显示空view
     * <p>当页面没有数据的时候，调用{@link #showEmpty()}显示空View，给用户提示</p>
     */
    public void showEmpty(){
      clear();
      mIsShowEmpty = true;
      add(mEmptyCell);
    }

    /**
     * 显示指定的空状态View，并指定View显示的高度
     * @param emptyView  页面为空状态显示的View
     * @param viewHeight view显示的高
     */
    public void showEmpty(View emptyView,int viewHeight){
        if(emptyView == null){
            showEmpty();
            return;
        }
        clear();
        mIsShowEmpty = true;
        mEmptyCell.setView(emptyView);
        mEmptyCell.setHeight(viewHeight);
        add(mEmptyCell);
    }

    /**
     * 显示指定的空状态View,默认显示屏幕高度
     * @param emptyView
     * @see {@link #showEmpty(View, int)}
     */
    public void showEmpty(View emptyView){
        showEmpty(emptyView,0);
    }

    /**
     * 隐藏空View
     */
    public void hideEmpty(){
      if(mData.contains(mEmptyCell)){
          remove(mEmptyCell);
          mIsShowEmpty = false;
      }
    }

    /**
     * 检查列表是否已经包含了这4种Cell
     */
    private void checkNotContainSpecailCell(){
        if(mData.contains(mEmptyCell)){
            mData.remove(mEmptyCell);
        }
        if(mData.contains(mErrorCell)){
            mData.remove(mErrorCell);
        }
        if(mData.contains(mLoadingCell)){
            mData.remove(mLoadingCell);
        }
        if(mData.contains(mLoadMoreCell)){
            mData.remove(mLoadMoreCell);
        }
    }

    @Override
    public void clear() {
        mIsShowError = false;
        mIsShowLoading = false;
        mIsShowEmpty = false;
        mIsShowLoadMore = false;
        super.clear();
    }

    public boolean isShowEmpty() {
        return mIsShowEmpty;
    }

    public boolean isShowLoading() {
        return mIsShowLoading;
    }

    public boolean isShowError() {
        return mIsShowError;
    }
}
