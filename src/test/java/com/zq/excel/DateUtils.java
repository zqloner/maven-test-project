package com.zq.excel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
    public static final SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat format0 = new SimpleDateFormat("yyyy-MM-dd HH");
    public static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /**
     * 描述：获取一小时前日期
     * @参数：@param date　指定日期
     * @参数：@return
     * @返回值：String
     */
    public static String get1HourAgo(Date date){

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR,calendar.get(Calendar.HOUR) - 1);// 让日期加1
        //System.out.println(calendar.get(Calendar.DATE));// 加1之后的日期Top
        //System.out.println(format0.format(calendar.getTime()));

        return format0.format(calendar.getTime());
    }
    /**
     * 描述：获取一小时前日期
     * @参数：@param date　指定日期
     * @参数：@return
     * @返回值：Long
     */
    public static Long get1HourAgo2(Date date){

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR,calendar.get(Calendar.HOUR) - 1);// 让日期加1
        System.out.println(calendar.get(Calendar.DATE));// 加1之后的日期Top
        System.out.println(format.format(calendar.getTime()));

        return calendar.getTimeInMillis();
    }
    /**
     * jdk1.8 localDateTime 格式化
     * @param localDateTime
     * @param patten
     * @return
     */
    public static String localDateTimeToString(LocalDateTime localDateTime, String patten) {
        return localDateTime.format(DateTimeFormatter.ofPattern(patten));
    }
    public static void main(String[] args) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR,calendar.get(Calendar.HOUR) - 1);// 让日期加1
        System.out.println(calendar.get(Calendar.DATE));// 加1之后的日期Top
        System.out.println(format.format(calendar.getTime()));
    }
    /**
     * 描述：获取1天前日期
     * @参数：@param date　指定日期
     * @参数：@return
     * @返回值：String
     */
    public static String get1DayAgo(Date date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, - 1);
        Date d = c.getTime();
        String day = format.format(d);
        return day;
    }
    /**
	 * 描述：获取7天前日期
     * @参数：@param date　指定日期 
     * @参数：@return 
     * @返回值：String 
     */  
    public static String get7DayAgo(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, - 7);
        Date d = c.getTime();
        String day = format.format(d);
        return day;  
    }  
    /** 
	 * 描述：获取一个月前日期
     * @参数：@param date　指定日期 
     * @参数：@return 
     * @返回值：String 
     */  
    public static String getOneMonthAgo(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, -1);
        Date d = c.getTime();
        String day = format.format(d);
        return day;  
    }  
    /** 
   	 * 描述：获取一年前日期
    * @参数：@param date　指定日期 
    * @参数：@return 
    * @返回值：String 
    */  
   public static String getOneYearAgo(Date date){
       Calendar c = Calendar.getInstance();
       c.setTime(date);
       c.add(Calendar.YEAR, -1);
       Date d = c.getTime();
       String day = format.format(d);
       return day;  
   }  
   
   public static int getNowYear() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.YEAR);
   }

    /**
     * 日期型数据转换为整形数据
     * @param d
     * @return
     */
    public static int dateToInt(Date d)
    {
        Calendar cl = Calendar.getInstance();
        cl.setTime(d);
        int year=cl.get(Calendar.YEAR);
        int month=cl.get(Calendar.MONTH)+1;
        int day=cl.get(Calendar.DAY_OF_MONTH);
        return ((year<<16)|(month<<8)|day);
    }

    /**
     * 整形数据转换为日期型数据
     * @param d
     * @return
     */
    public static Date intToDate(int d)
    {
        Calendar cl = Calendar.getInstance();
        cl.set(Calendar.YEAR,((d>>16)&0xffff));
        cl.set(Calendar.MONTH,((d>>8)&0xff)-1);
        cl.set(Calendar.DAY_OF_MONTH,(d&0xff));
        return cl.getTime();
    }
    /**
     * 整形数据转换为日期型数据
     * @param d
     * @return
     */
    public static Date stringToDate(String d)throws ParseException {
        return format.parse(d);
    }

    /**
     * 日期型数据转换为整形数据
     * @param d
     * @return
     */
    public static String dateToString(Date d){
        return df.format(d);
    }

    /**
     * 整形数据增加一天
     * @param d
     * @return
     */
    public static int addDayToInt(int d,int day)
    {
        Calendar cl = Calendar.getInstance();
        cl.set(Calendar.YEAR,((d>>16)&0xffff));
        cl.set(Calendar.MONTH,((d>>8)&0xff)-1);
        cl.set(Calendar.DAY_OF_MONTH,(d&0xff));
        cl.add(Calendar.DAY_OF_MONTH, 1);
        return dateToInt(cl.getTime());
    }
    /**
     * 根据年 月 获取对应的月份 天数
     * 王杰
     * */
    public static int getDaysByYearMonth(int year, int month) {

        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }
    /*
     * 将时间转换为时间戳
     */
    public static Long dateToStamp(String s) throws ParseException{
        String res = "";
        Date date = format.parse(s);
        long ts = date.getTime();
       // res = String.valueOf(ts);
        return ts;
    }
    /*
     * 将时间戳转换为时间
     */
    public static String stampToDate(Long lt){
        String res = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(lt));
        return res;
    }

    public static  int getAge(String strDate) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date birthDay = sdf.parse(strDate);
        Calendar cal = Calendar.getInstance();
        if (cal.before(birthDay)) { //出生日期晚于当前时间，无法计算
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }
        int yearNow = cal.get(Calendar.YEAR);  //当前年份
        int monthNow = cal.get(Calendar.MONTH);  //当前月份
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH); //当前日期
        cal.setTime(birthDay);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
        int age = yearNow - yearBirth;   //计算整岁数
        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) age--;//当前日期在生日之前，年龄减一
            }else{
                age--;//当前月份在生日之前，年龄减一
            }
        } return age;
    }
}
