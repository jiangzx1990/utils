package com.jzx.personal.utils;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;

public class DensityUtils {
    /**
     * 获取屏幕宽度
     * @return 屏幕宽度
     */
    public static int getScreenWidth(){
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        return metrics.widthPixels;
    }

    /**
     * 获取屏幕高度
     * @return 屏幕高度
     */
    public static int getScreenHeight(){
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        return metrics.heightPixels;
    }

    /**
     * dp转px
     * @param dpValue dp
     * @return px
     */
    public static int dp2px(float dpValue){
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dpValue,metrics));
    }

    /**
     * 获取手机状态栏高度
     * @return 手机状态栏高度
     */
    public static int getStatusBarHeight(){
        int result = 0;
        final Resources resources = Resources.getSystem();
        int res = resources.getIdentifier("status_bar_height",
                "dimen",
                "android");
        if(res != 0){
            result = resources.getDimensionPixelOffset(res);
        }
        return result;
    }
}
