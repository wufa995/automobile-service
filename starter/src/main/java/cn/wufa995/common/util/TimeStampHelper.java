/**
 * All rights Reserved, Designed By Suixingpay.
 *
 * @author: 程志祥<cheng_zx @ suixingpay.com>
 * @date: 2018/1/19
 * @Copyright: ©2017 Suixingpay. All rights reserved.
 * 注意：本内容仅限于随行付支付有限公司内部传阅，禁止外泄以及用于其他的商业用途。
 */
package cn.wufa995.common.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @description:
 * @author: 程志祥<cheng_zx @ suixingpay.com>
 * @date: 2018/1/19 下午8:15
 * @version: V1.0
 */

public class TimeStampHelper {

    public static String nextTimeStamp() {
        return String.valueOf((int) (System.currentTimeMillis() / 1000));
    }

    /**
     * 将日期转换为短格式 月+日
     *
     * @param date 日期对象
     * @return 转换后的字符串
     */
    public static String dateToMMDD(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("MMdd");
        return df.format(date);
    }

    /**
     * 将日期转换为日期字符串
     *
     * @param date 日期对象
     * @return 转换后的字符串
     */
    public static String dateToString(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(date);
    }

    /**
     * 将日期转换为日期字符串
     *
     * @param date 日期对象
     * @return 转换后的字符串
     */
    public static String dateToString2(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        return df.format(date);
    }


    /**
     * 将时间转换为时间字符串
     *
     * @param date 日期对象
     * @return 转换后的字符串
     */
    public static String dateTimeToString(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(date);
    }

    /**
     * 当前时间的字符串格式
     *
     * @return 转换后的字符串
     */
    public static String currentDateTimeString() {
        return dateTimeToString(Calendar.getInstance().getTime());
    }

    /**
     * 计算工作日时长,排除节假日
     **/
    public static Double completeDays(Date begin, Date end, List<String> holidays) {
        return completeDays(begin, end, null, holidays);
    }

    /**
     * 计算工作日时长,排除节假日
     **/
    public static Double completeDays(Date begin, Date end, Date real, List<String> holidays) {
        // 如果具备投产时间不为空则按具备投产时间进行计算
        if (real != null) {
            end = real;
        }
        boolean isReversal = false;
        if (end.before(begin)) {
            //当遇到创建日期晚于投产日期的 将日期调转后 算负天数
            Date temp = begin;
            begin = end;
            end = temp;
            isReversal = true;
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        String beginDateStr = df.format(begin);
        String endDateStr = df.format(end);
        // 如果日期为同一天的则算一天
        if (beginDateStr.equals(endDateStr)) {
            return 1D;
        }
        String currentDateStr = "";
        Calendar currentDate = Calendar.getInstance();
        currentDate.setTime(begin);
        Double days = 0D;
        // 循环获取该时间段内的所有日期
        while (!endDateStr.equals(currentDateStr)) {
            currentDateStr = df.format(currentDate.getTime());
            currentDate.add(Calendar.DAY_OF_YEAR, 1);
            if (holidays.contains(currentDateStr)) {
                continue;
            }
            days++;
        }
        //如果是创建日期晚于投产日期的情况
        if (isReversal) {
            return -days;
        }
        return days;
    }

    /**
     * 获取日期区间内所有的日期
     **/
    public static List<String> createDateList(Date begin, Date end) {
        return createDateList(begin, end, "yyyy-MM-dd");
    }

    /**
     * 获取日期区间内所有的日期
     **/
    public static List<String> createDateList(Date begin, Date end, String pattern) {
        List<String> dateList = new ArrayList<>();
        if (end.before(begin)) {
            //当遇到创建日期晚于结束的 将日期调转
            Date temp = begin;
            begin = end;
            end = temp;
        }
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        String beginDateStr = df.format(begin);
        String endDateStr = df.format(end);
        // 添加第一天
        dateList.add(beginDateStr);
        // 如果日期为同一天的则算一天
        if (beginDateStr.equals(endDateStr)) {
            return dateList;
        }
        String currentDateStr = "";
        Calendar currentDate = Calendar.getInstance();
        currentDate.setTime(begin);
        // 循环获取该时间段内的剩余的所有日期
        while (!endDateStr.equals(currentDateStr)) {
            currentDate.add(Calendar.DAY_OF_YEAR, 1);
            currentDateStr = df.format(currentDate.getTime());
            dateList.add(currentDateStr);
        }
        return dateList;
    }

    /**
     * 计算日期相差天数
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDaysByMillisecond(Date date1, Date date2) {
        long time1 = date1.getTime();
        long time2 = date2.getTime();
        long diff;
        if (time1 < time2) {
            diff = time2 - time1;
        } else {
            diff = time1 - time2;
        }
        int days = (int) (diff / (1000 * 3600 * 24));
        return days;
    }


    /**
     * 计算日期：是否为同一天
     *
     * @param date1
     * @param date2
     * @return
     */
    public static Boolean compareIsOneDay(Long date1, Long date2) {
        if (date1 != null && date2 != null) {
            Date d1 = new Date(date1);
            Date d2 = new Date(date2);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            return sdf.format(date1).equals(sdf.format(date2));
        }
        else if (date1 == null && date2 == null){
            return true;
        }
        return false;
    }

    /**
     * 获取：星期几
     * @param time
     * @return
     */
    public static Integer getDay(Long time) {
        if (time == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(time));
        return calendar.get(Calendar.DAY_OF_WEEK);
    }




}
