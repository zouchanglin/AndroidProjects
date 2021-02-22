package cn.tim.textviewtest;


import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;

public class SizeUtils {
    public SizeUtils() {
    }

    public static void forceGetViewSize(final View view, final SizeUtils.onGetSizeListener listener) {
        view.post(new Runnable() {
            public void run() {
                if (listener != null) {
                    listener.onGetSize(view);
                }

            }
        });
    }

    /** @deprecated */
    @Deprecated
    public static int Sp2Px(Context context, float sp) {
        if (context == null) {
            return 0;
        } else {
            float scale = context.getResources().getDisplayMetrics().scaledDensity;
            return (int)(sp * scale);
        }
    }

    /** @deprecated */
    @Deprecated
    public static int Px2Dp(Context context, float px) {
        if (context == null) {
            return 0;
        } else {
            float scale = context.getResources().getDisplayMetrics().density;
            return (int)(px / scale + 0.5F);
        }
    }

    /** @deprecated */
    @Deprecated
    public static int Dp2Px(Context context, float dp) {
        if (context == null) {
            return 0;
        } else {
            float scale = context.getResources().getDisplayMetrics().density;
            return (int)(dp * scale + 0.5F);
        }
    }

    public static int dp2px(float dpValue) {
        float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int)(dpValue * scale + 0.5F);
    }

    public static int px2dp(float pxValue) {
        float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int)(pxValue / scale + 0.5F);
    }

    public static int sp2px(float spValue) {
        float fontScale = Resources.getSystem().getDisplayMetrics().scaledDensity;
        return (int)(spValue * fontScale + 0.5F);
    }

    public static int px2sp(float pxValue) {
        float fontScale = Resources.getSystem().getDisplayMetrics().scaledDensity;
        return (int)(pxValue / fontScale + 0.5F);
    }

    public static int[] measureView(View view) {
        LayoutParams lp = view.getLayoutParams();
        if (lp == null) {
            lp = new LayoutParams(-1, -2);
        }

        int widthSpec = ViewGroup.getChildMeasureSpec(0, 0, lp.width);
        int lpHeight = lp.height;
        int heightSpec;
        if (lpHeight > 0) {
            heightSpec = MeasureSpec.makeMeasureSpec(lpHeight, 1073741824);
        } else {
            heightSpec = MeasureSpec.makeMeasureSpec(0, 0);
        }

        view.measure(widthSpec, heightSpec);
        return new int[]{view.getMeasuredWidth(), view.getMeasuredHeight()};
    }

    public static int getMeasuredWidth(View view) {
        return measureView(view)[0];
    }

    public static int getMeasuredHeight(View view) {
        return measureView(view)[1];
    }

    public interface onGetSizeListener {
        void onGetSize(View var1);
    }
}
