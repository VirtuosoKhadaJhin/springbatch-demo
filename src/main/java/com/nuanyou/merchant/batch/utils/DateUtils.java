package com.nuanyou.merchant.batch.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtils {

    private static final Logger log = LoggerFactory.getLogger(DateUtils.class.getSimpleName());

    public static final String YMDHHMMSSPattern = "yyyy-MM-dd HH:mm:ss";

    public static final SimpleDateFormat YMD = new SimpleDateFormat("yyyy-MM-dd");

    public static final SimpleDateFormat YMDHHMMSS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String getDefaultFormat() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        return formatDefault(new Date(), sdf);
    }

    public static String format(Date date) {
        return formatDefault(date, YMDHHMMSS);
    }

    public static String formatDay(Date date) {
        return formatDefault(date, YMD);
    }


    public static String formatDefault(Date date, DateFormat format) {
        if (date == null)
            return null;
        return format.format(date);
    }


    /**
     * 获取当前时间
     */
    public static Date getCurDate() {
        Calendar c = Calendar.getInstance();
        return c.getTime();
    }

    public static Date parseWholeDateInt(String dateString) {
        try {
            return new SimpleDateFormat("yyyyMMdd").parse(dateString);
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date parseWholeDate(String dateString) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 格式化日期
     */
    public static String formatDate(String patternStr, Date date) {
        try {
            if (date == null) {
                date = getCurDate();
            }
            SimpleDateFormat sdf = new SimpleDateFormat(patternStr);
            return sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static Date parse(String patternStr, String dateString) {
        try {
            return new SimpleDateFormat(patternStr).parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Date parse(String dateString) {
        try {
            if (dateString.length() <= 6) {
                return new SimpleDateFormat("yyyyMM").parse(dateString);
            } else if (dateString.indexOf("-") < 0) {
                return new SimpleDateFormat("yyyyMMdd").parse(dateString);
            } else if (dateString.length() == 7 && dateString.indexOf("-") > 0) {
                return new SimpleDateFormat("yyyy-MM").parse(dateString);

            } else {
                return new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 时间戳转时间字符串
     * create by kevin
     * 2016-08-24 17:07
     *
     * */
    public static String timeStampToString(Long timeStamp,String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String date = sdf.format(new Date(timeStamp*1000L));
       return date;
    }

    /**
     * 字符串通过时区转为date
     *
     * @param dateString
     * @param TimeZoneString
     * @return
     */
    public static Date parseByTimeZone(String dateString, String TimeZoneString) {
        DateFormat sdf = new SimpleDateFormat(YMDHHMMSSPattern);
        sdf.setTimeZone(TimeZone.getTimeZone(TimeZoneString));
        return parse(dateString, sdf);
    }

    /**
     * date通过时区转为字符串
     *
     * @param date
     * @param TimeZoneString
     * @return
     */
    public static String formatByTimeZone(Date date, String TimeZoneString) {
        DateFormat sdf = new SimpleDateFormat(YMDHHMMSSPattern);
        sdf.setTimeZone(TimeZone.getTimeZone(TimeZoneString));
        return format(date, sdf);
    }

    public static String format(Date date, DateFormat format) {
        if (date == null)
            return null;
        return format.format(date);
    }

    public static Date parse(String str, DateFormat format) {
        try {
            return format.parse(str);
        } catch (ParseException e) {
            log.warn("日期格式错误" + str, e);
            return null;
        }
    }

    /**	 * Date类型转换为10位时间戳	 * @param time	 * @return	 */
    public static Integer DateToTimestamp(Date time){
        Timestamp ts = new Timestamp(time.getTime());
        return (int) ((ts.getTime())/1000);
    }

    public static Date getDateBeforeNowDay(Date expireTime){
        Calendar theCa = Calendar.getInstance();
        theCa.setTime(expireTime);
        theCa.add(theCa.DATE, -7);
        return theCa.getTime();
    }

    public static Date extendExpireTime(Date expireTime){
        Calendar theCa = Calendar.getInstance();
        theCa.setTime(expireTime);
        theCa.add(theCa.DATE, 30);
        return theCa.getTime();
    }

}
