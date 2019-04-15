package com.jzx.personal.utils;

import android.support.annotation.NonNull;

public class RegUtils {
    public static boolean isCellphoneSimple(@NonNull String phone){
        return phone.matches("^1[0-9]{10}$");
    }

    public static boolean isChnName(@NonNull String name){
        return name.matches("^[\u4e00-\u9fa5]{2,20}$");
    }
}
