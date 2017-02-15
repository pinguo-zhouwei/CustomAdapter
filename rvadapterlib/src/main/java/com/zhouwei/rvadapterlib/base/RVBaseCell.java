package com.zhouwei.rvadapterlib.base;

/**
 * Created by zhouwei on 17/1/19.
 */

public  abstract class RVBaseCell<T> implements Cell {

    public RVBaseCell(T t){
        mData = t;
    }
    public T mData;

    @Override
    public void releaseResource() {
        // do nothing
        // 如果有需要回收的资源，子类自己实现
    }
}
