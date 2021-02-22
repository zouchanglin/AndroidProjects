package cn.tim.video_demo;

import android.content.Context;

public class DensityUtil {
    //DensityUtil.java
    public static float getHeightInPx(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }
    public static float getWidthInPx(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static float dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dipValue * scale + 0.5f); //+0.5是为了向上取整
    }

    public static int px2dip(Context context, float pxValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(pxValue / scale + 0.5f); //+0.5是为了向上取整
    }
}
