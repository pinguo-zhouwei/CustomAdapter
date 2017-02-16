package com.zhouwei.rvadapterlib;

import android.content.Context;

/**
 * Created by zhouwei on 17/2/16.
 */

public class Utils {
    /**
     *  dp 转换 px
     * @param context
     * @param dip
     * @return
     */
    public static int dpToPx(Context context, float dip) {
        final float SCALE = context.getResources().getDisplayMetrics().density;
        float valueDips = dip;
        int valuePixels = (int) (valueDips * SCALE + 0.5f);
        return valuePixels;
    }
}
