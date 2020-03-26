package org.sang.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @CLassName DateTimeUtil
 * @Description TODO
 * @Author ll
 * @Date 2018/11/9 15:42
 **/
public class DateTimeUtil {


    public static String FORMATTIME_DATE_1 = "yyyy-MM-dd";

    public static String FORMATTIME_DATE_2 = "yyyy/MM/dd";

    public static String FORMATTIME_DATE_3 = "yyyy-MM-dd HH:mm:ss";

    public static String FORMATTIME_DATE_4 = "yyyyMMdd";


    /*
     * @author ll
     * @Description 时间戳按格式转时间
     * @date 2018/11/9 15:45
     * @param [timeMillon, format]
     * @return java.lang.String
     */
    public static String timeMillinToDateStr(long timeMillon , String format){
        SimpleDateFormat sdf=new SimpleDateFormat(format);
        String sd = sdf.format(new Date(timeMillon));
        return sd;
    }

    public static String timeMillinToDateStr(String format){
        return timeMillinToDateStr(System.currentTimeMillis() , format);
    }

    /*
     * @author ll
     * @Description 时间转时间戳
     * @date 2018/11/13 11:38
     * @param [s, format]
     * @return long
     */
    public static long dateToStamp(String s , String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = simpleDateFormat.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long ts = date.getTime();
        return ts;
    }

    public static long dateToStamp(String s) {
        return dateToStamp(s , FORMATTIME_DATE_3);
    }

}
