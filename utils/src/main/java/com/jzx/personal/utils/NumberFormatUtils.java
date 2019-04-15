package com.jzx.personal.utils;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class NumberFormatUtils {
    /**
     * 格式化金额数据，保留两位小数
     * @param amount 待格式化金额
     *  3.5801 -> 3.58
     *  3.5861 -> 3.59
     *  3.05 -> 3.05
     *  3.10 -> 3.10
     * @return 格式化后的值
     */
    public static String formatAmount(double amount){
        DecimalFormat format = new DecimalFormat("0.00");
        return format.format(amount);
    }

    /**
     * 人性化输出金额
     * @param amount 金额，精确到3位小数，不进行四舍五入
     * 10000.050 -> 1万
     * 13050.050 -> 1.305万
     * 123.015 -> 123.015
     * 1.2356 ->  1.235
     * @return 格式化后的值
     */
    public static String formatAmountHumanize(double amount){
        DecimalFormat format = new DecimalFormat("0.000");
        format.setRoundingMode(RoundingMode.FLOOR);
        if(amount > 10000){
            return handleZero(format.format(amount / 10000)) + "万";
        }
        return handleZero(format.format(amount));
    }


    private static String handleZero(String inputs){
        int index = inputs.indexOf('.');
        //去除末尾0
        if(index != -1 && inputs.length() >= index+1){
            String suffix = inputs.substring(index+1);
            String temp = "";
            for (int i= suffix.length() -1 ;i>=0; i--){
                if(suffix.charAt(i) == '0'){
                    temp = suffix.substring(0,i);
                }else{
                    temp = suffix.substring(0,i+1);
                    break;
                }
            }
            String result;
            if(temp.length() > 0){
                result = inputs.substring(0,index+1) + temp;
            }else{
                result = inputs.substring(0,index);
            }
            return result;
        }
        return inputs;
    }

}
