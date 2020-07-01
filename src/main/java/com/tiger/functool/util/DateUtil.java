package com.tiger.functool.util;

/**
 * Created by Administrator on 2018/11/8.
 */

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil {

    public static Date getLocalDate(){
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        return calendar.getTime();
    }

    public static Date  getDate(int day){
        Calendar calendar = new java.util.GregorianCalendar();
        calendar.add(Calendar.DATE, day);
        return  calendar.getTime();
    }

    public static String getBeforeTimeByUnitAndNum(String format,Integer unit ,Integer num){
        Calendar calendar = Calendar.getInstance();
        //unit Calendar.Year
        calendar.add(unit,num);
        Date date = calendar.getTime();
        //format "yyyy-MM-dd HH:mm:ss"
        return  DateUtil.getNowDateTime(format,date);
    }

    public static long getTimeApart(Date startDate,Date endDate){
        return DateUtil.getTimeTap(DateUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss", startDate)
                ,DateUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss", endDate));
    }

    public static int compareToDate(Date date,Date compareDate){
        Calendar var1 = Calendar.getInstance(Locale.CHINA);
        var1.setTime(date);
        Calendar var2 = Calendar.getInstance(Locale.CHINA);
        var2.setTime(compareDate);
        return var1.compareTo(var2);
    }

    public static String getYear(String age) {
        if (age != null) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy");
            Calendar c = Calendar.getInstance();
            c.setTime(new Date());
            c.add(Calendar.YEAR, Integer.valueOf("-" + age));
            Date y = c.getTime();
            return format.format(y);
        }
        return null;

    }

    /**
     * 时间戳转换成日期格式字符串
     *
     * @param seconds   精确到秒的字符串
     * @param formatStr
     * @return
     */
    public static String timeStamp2Date(String seconds, String format) {
        if (seconds == null || seconds.isEmpty() || seconds.equals("null")) {
            return "";
        }
        if (format == null || format.isEmpty()) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.valueOf(seconds + "000")));
    }

    /**
     * 日期格式字符串转换成时间戳
     *
     * @param date   字符串日期
     * @param format 如：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String date2TimeStamp(String date_str) {
        try {
            return date2TimeStamp(date_str, "yyyy-MM-dd HH:mm:ss");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    public static int getTimeTap(String startDate,String endDate){
        if(ValidationUtils.isNotBlank(startDate)&& ValidationUtils.isNotBlank(endDate)){
            return  Math.abs(Integer.valueOf(date2TimeStamp(endDate))-Integer.valueOf(date2TimeStamp(startDate)));
        }
        return Integer.MAX_VALUE;
    }
    public static String getNowDateTime(){
        return getNowDateTime("yyyy-MM-dd HH:mm:ss",null);
    }
    public static String getNowDateTime(String type,Date time){
        SimpleDateFormat sdf = new SimpleDateFormat(type);
        if(time==null){
            return sdf.format( new Date());
        }else{
            return sdf.format(time);
        }
    }

    public static String date2TimeStamp(long dateStr, String format) {
        return date2TimeStamp(String.valueOf(dateStr),format);
    }
    public static String date2TimeStamp(String dateStr, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            long timestamp =  sdf.parse(dateStr).getTime();
            return String.valueOf(timestamp/1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 取得当前时间戳（精确到秒）
     *
     * @return
     */
    public static String timeStamp() {
        long time = System.currentTimeMillis();
        String t = String.valueOf(time / 1000);
        return t;
    }

    public static Date getNextDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, 3);
        date = calendar.getTime();
        return date;
    }

    public static void main(String[] args) {
//        String timeStamp = timeStamp();
//        System.out.println("timeStamp=" + timeStamp); //运行输出:timeStamp=1470278082
//        System.out.println(System.currentTimeMillis());//运行输出:1470278082980
//        //该方法的作用是返回当前的计算机时间，时间的表达格式为当前计算机时间和GMT时间(格林威治时间)1970年1月1号0时0分0秒所差的毫秒数
//
//        String date = timeStamp2Date(timeStamp, "yyyy-MM-dd HH:mm:ss");
//        System.out.println("date=" + date);//运行输出:date=2016-08-04 10:34:42
//
//        String timeStamp2 = date2TimeStamp(date, "yyyy-MM-dd HH:mm:ss");
//        System.out.println(timeStamp2);  //运行输出:1470278082


       Date data =  getDate(-10);
       System.out.println(data);
    }
}
