package com.lianle.utils;

import com.lianle.entity.IndexConfig;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Description: <br>
 *
 * @author <a href=mailto:lianle1@jd.com>lianle1</a>
 * @date 2016/3/16 11:38
 */
public class DateUtils {

    private static final Map<Integer, String> monthMap;

    static {
        monthMap = new HashMap<Integer, String>();
        monthMap.put(1,"Jan");
        monthMap.put(2,"Feb");
        monthMap.put(3,"Mar");
        monthMap.put(4,"Apr");
        monthMap.put(5,"May");
        monthMap.put(6,"Jun");
        monthMap.put(7,"Jul");
        monthMap.put(8,"Aug");
        monthMap.put(9,"Sep");
        monthMap.put(10,"Oct");
        monthMap.put(11,"Nov");
        monthMap.put(12,"Dec");
    }

    /**
     * 得到时间的天数字串
     * @param date
     * @return
     */
    public static String getDay(Date date) {
        Calendar cal=Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_MONTH) + "";
    }

    /**
     * 得到时间的月份
     * @param date
     * @return
     */
    public static int getMonth(Date date) {
        Calendar cal=Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MONTH) + 1;
    }

    /**
     * 根据月份得到英文缩写
     * @param month
     * @return
     */
    public static String getEnglishMonth(int month) {
        return monthMap.get(month);
    }

    public static void main(String[] args) {
        System.out.println(DateUtils.getDay(new Date()));

        Calendar now = Calendar.getInstance();
        System.out.println("年: " + now.get(Calendar.YEAR));
        System.out.println("月: " + (now.get(Calendar.MONTH) + 1) + "");
        System.out.println("日: " + now.get(Calendar.DAY_OF_MONTH));
        System.out.println("时: " + now.get(Calendar.HOUR_OF_DAY));
        System.out.println("分: " + now.get(Calendar.MINUTE));
        System.out.println("秒: " + now.get(Calendar.SECOND));
        System.out.println("当前时间毫秒数：" + now.getTimeInMillis());
        System.out.println(now.getTime());

        Date d = new Date();
        System.out.println(d);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateNowStr = sdf.format(d);
        System.out.println("格式化后的日期：" + dateNowStr);

        String str = "2012-1-13 17:26:33";  //要跟上面sdf定义的格式一样
        Date today = null;
        try {
            today = sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println("字符串转成日期：" + today);

        System.out.println("**************************");
        System.out.println(getDay(d));
        System.out.println(getMonth(d));
        System.out.println("month is[" + getEnglishMonth(getMonth(d))+ "]");
    }
}
