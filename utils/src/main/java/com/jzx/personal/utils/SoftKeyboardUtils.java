package com.jzx.personal.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class SoftKeyboardUtils {
    /**
     * 隐藏软键盘
     * @param focused 当前获得焦点的view
     */
    public static void hideSoftKeyboard(View focused){
        if(null!=focused){
            focused.setFocusable(true);
            focused.setFocusableInTouchMode(true);
            focused.requestFocus();
            InputMethodManager inputMethodManager=
                    (InputMethodManager) focused.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(focused.getWindowToken(),0);
        }
    }

    /**
     * 显示软键盘
     * @param focused 当前获得焦点的view
     */
    public static void showSoftKeyboard(View focused){
        if(null!=focused){
            focused.setFocusable(true);
            focused.setFocusableInTouchMode(true);
            focused.requestFocus();
            InputMethodManager inputMethodManager=
                    (InputMethodManager) focused.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.showSoftInput(focused,InputMethodManager.SHOW_FORCED);
        }
    }
}
