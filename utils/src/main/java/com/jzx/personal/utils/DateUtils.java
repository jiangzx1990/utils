package com.jzx.personal.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtils {
    //region 日期
    /**
     * 格式化日期
     * @param millisecond 时间毫秒值
     * @return "yyyy-MM-dd"
     */
    public static String formatDate(long millisecond){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINESE);
        return dateFormat.format(millisecond);
    }

    /**
     * 格式化日期
     * @param millisecond 时间毫秒值
     * @return "yyyy年MM月dd日"
     */
    public static String formatDateChn(long millisecond){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日", Locale.CHINESE);
        return dateFormat.format(millisecond);
    }
    //endregion

    //region 日期时间
    /**
     * 格式化日期时间
     * @param millisecond 时间毫秒值
     * @return "yyyy-MM-dd HH:mm:ss"
     */
    public static String fromatDateTime(long millisecond){
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.CHINESE);
        return dateTimeFormat.format(millisecond);
    }

    /**
     * 格式化日期时间
     * @param millisecond 时间毫秒值
     * @return "yyyy年MM月dd日 HH时mm分ss秒"
     */
    public static String fromatDateTimeChn(long millisecond){
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒",Locale.CHINESE);
        return dateTimeFormat.format(millisecond);
    }
    //endregion

    //region 时间
    /**
     * 格式化时间
     * @param millisecond 时间毫秒数
     * @return "HH:mm:ss"
     */
    public static String formatTime(long millisecond){
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss",Locale.CHINESE);
        return timeFormat.format(millisecond);
    }

    /**
     * 格式化时间
     * @param millisecond 时间毫秒数
     * @return "HH时mm分ss秒"
     */
    public static String formatTimeChn(long millisecond){
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH时mm分ss秒",Locale.CHINESE);
        return timeFormat.format(millisecond);
    }
    //endregion

    private static String getWeekDay(Calendar date){
        switch (date.get(Calendar.DAY_OF_WEEK)){
            case Calendar.SUNDAY:
                return "星期日";
            case Calendar.MONDAY:
                return "星期一";
            case Calendar.TUESDAY:
                return "星期二";
            case Calendar.WEDNESDAY:
                return "星期三";
            case Calendar.THURSDAY:
                return "星期四";
            case Calendar.FRIDAY:
                return "星期五";
            case Calendar.SATURDAY:
                return "星期六";
            default:
                return "";
        }
    }

    /**
     * 人性化格式化时间
     * @param millisecond 时间毫秒值
     * @return "今天 HH:mm、昨天 HH:mm、前天 HH:mm、周一 HH:mm... yyyy年MM月dd日 HH:mm"
     */
    public static String formatTimeHumanize(long millisecond){
        Calendar date = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"),Locale.CHINESE);
        date.setTimeInMillis(millisecond);

        Calendar now = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"),Locale.CHINESE);
        now.setTimeInMillis(System.currentTimeMillis());

        now.set(Calendar.HOUR_OF_DAY,23);
        now.set(Calendar.MINUTE,59);
        now.set(Calendar.SECOND,59);
        long space = now.getTimeInMillis() - date.getTimeInMillis();
        long spaceAbs = Math.abs(space);
        long day = 1000*60*60*24;
        //相差7天内
        String result;
        if(spaceAbs <= day*7) {
            SimpleDateFormat format = new SimpleDateFormat("HH:mm",Locale.CHINESE);
            if (spaceAbs <= day) {
                //相差一天
                result = format.format(date.getTimeInMillis());
            }else if(spaceAbs <= day * 2){
                if(space > 0){
                    //昨天
                    result = "昨天 " + format.format(date.getTimeInMillis());
                }else{
                    //明天
                    result = "明天 " + format.format(date.getTimeInMillis());
                }
            }else if (spaceAbs <= day * 3){
                if(space > 0){
                    // 前天
                    result = "前天 " + format.format(date.getTimeInMillis());
                }else{
                    //后天
                    result = "后天 " + format.format(date.getTimeInMillis());
                }
            }else{
                result = getWeekDay(date) + " " + format.format(date.getTimeInMillis());
            }
            return result;
        }else{
            return new SimpleDateFormat("yyyy年MM月dd日 HH:mm",Locale.CHINESE).format(date);
        }
    }
}
