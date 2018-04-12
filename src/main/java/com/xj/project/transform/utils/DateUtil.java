package com.xj.project.transform.utils;
import com.alibaba.druid.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期工具类
 *
 * @author xiangjing
 * @date 2018/4/2
 * @company 天极云智
 */
public class DateUtil {

    public static final String default_format="yyyy-MM-dd HH:mm:ss";

   private static SimpleDateFormat getSimpleDateFormat(String dateFormat){
       if(null == dateFormat || dateFormat.length() == 0){
           return new SimpleDateFormat(default_format);
       }else{
           return new SimpleDateFormat(dateFormat);
       }
   }
    public static String format(Date date,String dateFormat){
        return getSimpleDateFormat(dateFormat).format(date);
    }
    public static String format(Date date){
        return getSimpleDateFormat(null).format(date);
    }

    public static Date parse(String date,String dateFormat) throws ParseException {
        return getSimpleDateFormat(dateFormat).parse(date);
    }
    public static Date parse(String date) throws ParseException {
        return getSimpleDateFormat(null).parse(date);
    }

    public static void main(String[] args) throws ParseException {
        System.out.println(format(parse("2018-03-13 11:22:51.0")));
    }

}
