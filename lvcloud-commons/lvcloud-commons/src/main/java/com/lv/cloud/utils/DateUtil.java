package com.lv.cloud.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateUtil {
	public static String[] MONTHS = new String[] { "一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月" };

	private static int[] DOMonth = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
	private static int[] lDOMonth = { 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
    private static final Map<String, String> weekDays = new HashMap<String, String>();
    static {
        weekDays.put("monday", "星期一");
        weekDays.put("tuesday", "星期二");
        weekDays.put("wednesday", "星期三");
        weekDays.put("thursday", "星期四");
        weekDays.put("friday", "星期五");
        weekDays.put("saturday", "星期六");
        weekDays.put("sunday", "星期日");
    }

	/**
	 * 取当前小时，24小时制
	 * @param date
	 * @return
	 */
	public static int getHour(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		Object hour = cal.get(Calendar.HOUR_OF_DAY);
		if(hour != null) {
			return Integer.valueOf(hour.toString());
		}
		
		return 0;
	}
	/**
	 * 取日期是一个月中的几号.
	 *
	 * @param date
	 * @return
	 */
	public static int getDay(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		Object day = cal.get(Calendar.DATE);
		if (day != null) {
			return Integer.valueOf(day.toString());
		} else {
			return 0;
		}
	}

	/**
	 * 判断两个时间的大小.
	 *
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static boolean isCompareTime(Date startTime, Date endTime) {
		if (endTime.getTime() > startTime.getTime()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 取日期是月份.
	 *
	 * @param date
	 * @return
	 */
	public static int getMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, 1);
		Object day = cal.get(Calendar.MONTH);
		if (day != null) {
			return Integer.valueOf(day.toString());
		} else {
			return 0;
		}
	}

    /**
     * 取日期是月份.
     * 范围（1-12）
     * @param date
     * @return
     */
    public static int getRealMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        Object day = cal.get(Calendar.MONTH);
        if (day != null) {
            return Integer.valueOf(day.toString())+1;
        } else {
            return 0;
        }
    }

	public static Date getDateAfterMonths(Date date, int duration) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, duration);
		return cal.getTime();
	}

	public static String formatSimpleDate(Date date){
		return getFormatDate(date, SIMPLE_DATE_FORMAT);
	}

    public static String formatHHMMSSDate(Date date){
        return getFormatDate(date, HHMMSS_DATE_FORMAT);
    }

    public static String formatHHMMDate(Date date){
        return getFormatDate(date, HHMM_DATE_FORMAT);
    }

	/**
	 * 当前日期小时相加或相减所得日期（+,-）操作,输入一个日期得到天数加减后的日期。
	 *
	 * @param date
	 * @param hours
	 * @return
	 */
	public static Date DsDay_HourOfDay(Date date, int hours) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.HOUR_OF_DAY, hours);

		return cal.getTime();
	}

	public static int getDayOfWeek(String date) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf=  new SimpleDateFormat("yyyy-MM-dd");
		try {
			cal.setTime(sdf.parse(date));
		} catch (ParseException e) {
			LOG.error(e);
		}
		 return cal.get(Calendar.DAY_OF_WEEK)-1;
	}

	/**
	 * 清理所有天后面的日期时间
	 *
	 * @param date
	 * @return
	 */
	public static Date clearDateAfterDay(Date date) {
		return changeDayEnd(date, 0, 0, 0);
	}

	public static Date toSimpleDate(String sdate){
		return toDate(sdate, SIMPLE_DATE_FORMAT);
	}

	public static final String SIMPLE_DATE_FORMAT="yyyy-MM-dd";
	
	public static final String HHMM_DATE_FORMAT="yyyy-MM-dd HH:mm";
	
	public static final String HHMMSS_DATE_FORMAT="yyyy-MM-dd HH:mm:ss";
	
	public static String SimpleFormatDateToString(Date date){
		SimpleDateFormat format1 = new SimpleDateFormat(SIMPLE_DATE_FORMAT);
		return format1.format(date);
	}

	/**
	 * 0,1,2,3,4,5,6
	 * @param date
	 * @return
	 */
	public static int getWeek(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_WEEK) - 1;
	}

	/**
	 * 两个时间的分钟数
	 *
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static int getMinute(Date beginDate, Date endDate) {
		return (int) ((endDate.getTime() - beginDate.getTime()) / (60 * 1000));
	}

	/**
	 * 两个时间的小时数
	 *
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static int getHour(Date beginDate, Date endDate) {
		return (int) ((endDate.getTime() - beginDate.getTime()) / (3600L * 1000));
	}

	/**
	 * 修改时分秒
	 *
	 * @param date
	 * @param hour
	 * @param minute
	 * @param second
	 * @return
	 */
	public static Date changeDayEnd(Date date, int hour, int minute, int second) {
		if (date == null) {
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, hour);
		c.set(Calendar.MINUTE, minute);
		c.set(Calendar.SECOND, second);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}

	/**
	 * startDate 减 endDate 的差值
	 *
	 * @return long 返回相差的秒数
	 */
	public static long diffSec(Date startDate, Date endDate) {
		return diffMillis(startDate, endDate) / 1000;
	}

	/**
	 * startDate 减 endDate 的差值
	 *
	 * @return long 返回相差的分钟数
	 */
	public static long diffMinute(Date startDate, Date endDate) {
		return diffMillis(startDate, endDate) / (60 * 1000);
	}

	/**
	 * startDate 减 endDate 的差值
	 *
	 * @return long 返回相差的小时数
	 */
	public static long diffHour(Date startDate, Date endDate) {
		return diffMillis(startDate, endDate) / (60 * 60 * 1000);
	}

	/**
	 * startDate 减 endDate 的差值
	 *
	 * @return long 返回相差的天数
	 */
	public static long diffDay(Date startDate, Date endDate) {
		return diffMillis(startDate, endDate) / (24 * 60 * 60 * 1000);
	}

	private static long diffMillis(Date startDate, Date endDate) {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(startDate);

		Calendar c2 = Calendar.getInstance();
		c2.setTime(endDate);

		return c2.getTimeInMillis() - c1.getTimeInMillis();
	}
	
	public static Date getNextMonth(Date date,int addMonth) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DATE, 1);
		int month=cal.get(Calendar.MONTH);
		cal.set(Calendar.MONTH, month + addMonth);
		return cal.getTime();
	}

	public static Date addDays(Date date, int amount)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, amount);
		return cal.getTime();
	}

    /**
     * 功能：判断日期是否和当前date对象在同一天。
     * @param d1 日期1
     * @param d2 日期2
     * @return boolean 如果在返回true，否则返回false。
     */
    public static boolean isTheSameDay(Date d1,Date d2) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(d1);
        c2.setTime(d2);
        return (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR))
                && (c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH))
                && (c1.get(Calendar.DAY_OF_MONTH) == c2.get(Calendar.DAY_OF_MONTH));
    }

    public static boolean isTheSameNullableDay(Date d1,Date d2) {
        if (d1 == d2) {
            return true;
        }
        if ((d1 == null && d2 != null) || (d2 == null && d1 != null)) {
            return false;
        }
        return isTheSameDay(d1, d2);
    }

    /**
     * 
     * @param startTime
     * @param endTime
     * @return
     */
    public static String dateDiff(Date startTime, Date endTime) {
    	String str = "";
    	try {
	    	long nd = 1000*24*60*60;//一天的毫秒数
	    	long nh = 1000*60*60;//一小时的毫秒数
	    	long nm = 1000*60;//一分钟的毫秒数
	    	long ns = 1000;//一秒钟的毫秒数
	    	long diff;
	    	//获得两个时间的毫秒时间差异    	
			Long end =  endTime.getTime();
		    Long start = startTime.getTime();
		    diff = end-start;
		    long day = diff/nd;//计算差多少天
		    long hour = diff%nd/nh;//计算差多少小时
			long min = diff%nd%nh/nm;//计算差多少分钟
			long sec = diff%nd%nh%nm/ns;//计算差多少秒//输出结果
//			str="时间相差："+day+"天"+hour+"小时"+min+"分钟"+sec+"秒";
//			System.out.println("时间相差："+day+"天"+hour+"小时"+min+"分钟"+sec+"秒。");
			str = day+"天"+hour+"时"+min+"分";
		} catch (Exception e) {
			e.printStackTrace();
		} 	  
    	return str;
    }

    /**
     * 
     * @param specifiedDay
     * @param stayDay
     * @return
     */
    public static  String getSpecifiedDayAfter(String specifiedDay,int stayDay){ 
    	Calendar c = Calendar.getInstance(); 
    	Date date=null; 
    	try { 
    	date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay); 
    	} catch (ParseException e) {
			LOG.error(e);
    	} 
    	c.setTime(date); 
    	int day=c.get(Calendar.DATE); 
    	c.set(Calendar.DATE,day+stayDay); 
    	String dayAfter=new SimpleDateFormat("yyyy-MM-dd").format(c.getTime()); 
    	return dayAfter; 
    }
	
    /**
     * 
     * @param delayWeek 为推迟的周数，0本周，-1向前推迟一周，1下周，依次类推
     * @param whichDay 想周几，这里就传几,如：Calendar.MONDAY
     * @return
     */
    public static String getDateOfWeek(int delayWeek, int whichDay){
    	Calendar cal = Calendar.getInstance();
    	cal.add(Calendar.DATE, delayWeek*7);
    	cal.set(Calendar.DAY_OF_WEEK, whichDay);
    	return new SimpleDateFormat("yyyyMMdd").format(cal.getTime());
    }
    
    public static boolean isAfterSaturdayNoon(){
    	Calendar c = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c2.set(Calendar.HOUR_OF_DAY, 12);
        c2.set(Calendar.DAY_OF_WEEK, 6);
        c2.set(Calendar.MINUTE, 0);
        c2.set(Calendar.MILLISECOND, 0);
        return c.after(c2);
    }

    /**
     * 得到X年X月X日
     * @return
     */
    public static String getChineseDay(Date date){
        String simpleDate = DateUtil.formatSimpleDate(date);
        String[] ymd = simpleDate.split("-");
        return ymd[0]+"年"+ymd[1]+"月"+ymd[2]+"日";
    }

	public static final String PATTERN_yyyy_MM_dd = "yyyy-MM-dd";
	public static final String PATTERN_yyyyMMdd = "yyyyMMdd";
	public static final String PATTERN_MMdd = "MM-dd";
	public static final String PATTERN_yyMMdd = "yy/MM/dd";
	public static final String PATTERN_yyyyMMddHHmmss = "yyyyMMddHHmmss";
	public static final String PATTERN_yyyyMMddHHmmssSSS = "yyyyMMddHHmmssSSS";
	public static final String PATTERN_yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";
	public static final String PATTERN_yyyy_MM_dd_HH_mm = "yyyy-MM-dd HH:mm";
	public static final String PATTERN_HH_mm_ss = "HH:mm:ss";
	public static final String PATTERN_HHmmss = "HHmmss";
	public static final String PATTERN_yyyyMMdd_HHmmss = "yyyy/MM/dd HH:mm:ss";
	public static final String PATTERN_yyyyMMdd2 = "yyyy/MM/dd";
	public static final String PATTERN_yyyy_MM_dd_HH_mm_ss_SSS = "yyyy-MM-dd HH:mm:ss SSS";

	static Log LOG = LogFactory.getLog(DateUtil.class);

	/**
	 * 取日期是哪一年
	 * 
	 * @param date
	 * @return
	 */
	public static int getYear(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		Object year = cal.get(Calendar.YEAR);
		if (year != null) {
			return Integer.valueOf(year.toString());
		} else {
			return 0;
		}
	}

	public static int getDaysOfmonth(int month) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, month);
		if ((cal.get(Calendar.YEAR) % 4) == 0) {
			if ((cal.get(Calendar.YEAR) % 100) == 0
					&& (cal.get(Calendar.YEAR) % 400) != 0)
				return DOMonth[cal.get(Calendar.MONTH)];
			return lDOMonth[cal.get(Calendar.MONTH)];
		} else
			return DOMonth[cal.get(Calendar.MONTH)];
	}

	public static Calendar getClearCalendar() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal;
	}

	public static Date getDateAfterDays(Date date, int duration) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, duration);
		return cal.getTime();
	}

	public static Date getDateAfter0000Date(Date date, int duration) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, duration);
		cal.set(Calendar.HOUR_OF_DAY, 00);
		cal.set(Calendar.MINUTE, 00);
		cal.set(Calendar.SECOND, 00);
		return cal.getTime();
	}

	public static Date getDateAfter2359Date(Date date, int duration) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, duration);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
		return cal.getTime();
	}

	public static Date getDateBeforeHours(Date date, int duration) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.HOUR_OF_DAY, -duration);
		return cal.getTime();
	}

	public static Date getDateAfterMinutes(long duration) {
		long curr = System.currentTimeMillis();
		curr = curr + duration * 60 * 1000;
		return new Date(curr);
	}

	public static String formatDate(Date date, String format) {
		return getFormatDate(date, format);
	}

	/**
	 * 格式时间
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String getFormatDate(Date date, String format) {
		if (date != null) {
			SimpleDateFormat f = new SimpleDateFormat(format);
			return f.format(date);
		} else {
			return null;
		}
	}

	public static String getZHDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		switch (calendar.get(Calendar.DAY_OF_WEEK)) {
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
		case Calendar.SUNDAY:
			return "星期日";
		default:
			return "";
		}
	}
	public static String getZhouDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		switch (calendar.get(Calendar.DAY_OF_WEEK)) {
		case Calendar.MONDAY:
			return "周一";
		case Calendar.TUESDAY:
			return "周二";
		case Calendar.WEDNESDAY:
			return "周三";
		case Calendar.THURSDAY:
			return "周四";
		case Calendar.FRIDAY:
			return "周五";
		case Calendar.SATURDAY:
			return "周六";
		case Calendar.SUNDAY:
			return "周日";
		default:
			return "";
		}
	}

	/**
	 * 当前日期小时相加或相减所得日期（+,-）操作,输入一个日期得到天数加减后的日期。
	 * 
	 * @param date
	 * @param hours
	 * @return
	 */
	public static Date DsDay_Hour(Date date, Integer hours) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.HOUR, hours);

		return cal.getTime();
	}

	/**
	 * 把参数时间精确到天
	 * 
	 * @param date
	 * @return
	 */

	public static Date accurateToDay(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return clearDateAfterDay(cal);
	}

	public static int getDayOfWeek(Calendar cal) {// 得到每月1号是星期几
		cal.set(Calendar.DATE, 1);
		return cal.get(Calendar.DAY_OF_WEEK);
	}

	public static Date getTheMiddle(Date date, int plus) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(cal.DATE, 1);
		cal.add(cal.MONTH, plus);
		return cal.getTime();
	}

	public static Map<String, Object> getBeginAndEndDateByDate(Date date) {

		Calendar calClearDate = Calendar.getInstance();
		calClearDate.setTime(date);
		calClearDate.set(calClearDate.DATE, 1);
		date = calClearDate.getTime();
		Map<String, Object> map = new HashMap<String, Object>();
		// SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		int dayOfWeek = getDayOfWeek(cal);
		cal.set(Calendar.DATE, -(dayOfWeek - 2));
		map.put("beginDate", cal.getTime());
		cal.add(Calendar.DATE, 21);
		map.put("currPageDate", cal.getTime());
		cal.add(Calendar.DATE, 20);
		map.put("endDate", cal.getTime());
		return map;
	}

	/**
	 * 根据格式获取日期字符串.
	 * 
	 * @param format
	 * @param aDate
	 * @return
	 */
	public static String getDateTime(String format, Date aDate) {
		SimpleDateFormat df = new SimpleDateFormat(format);
		String returnValue = df.format(aDate);
		return returnValue;
	}

	/**
	 * 当前日期分钟相加或相减所得日期（+,-）操作,输入一个日期得到分钟加减后的日期。
	 * 
	 * @param date
	 * @param minute
	 * @return
	 */
	public static Date DsDay_Minute(Date date, int minute) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int minutes = calendar.get(Calendar.MINUTE);
		calendar.set(Calendar.MINUTE, minutes + minute);
		return calendar.getTime();
	}
	
	/**
	 * 当前日期秒钟相加或相减所得日期（+,-）操作,输入一个日期得到分钟加减后的日期。
	 * 
	 * @param date
	 * @param second
	 * @return
	 */
	public static Date DsDay_Second(Date date, int second) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int seconds = calendar.get(Calendar.SECOND);
		calendar.set(Calendar.SECOND, seconds + second);
		return calendar.getTime();
	}

	/**
	 * 清理所有天后面的日期时间
	 * 
	 * @param c
	 * @return
	 */
	public static Date clearDateAfterDay(Calendar c) {
		c.set(Calendar.MILLISECOND, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.HOUR_OF_DAY, 0);
		return c.getTime();
	}

	/**
	 * String转Date
	 * 
	 * @param sdate
	 *            日期字符串
	 * @param fmString
	 *            指定日期格式
	 * @return
	 */
	public static Date toDate(String sdate, String fmString) {
		DateFormat df = new SimpleDateFormat(fmString);
		try {
			return df.parse(sdate);
		} catch (ParseException e) {
			throw new RuntimeException("日期格式不正确 ");
		}
	}

	/**
	 * 根据出生日期得到年龄
	 */
	public static int getAge(Date birthDay) {
		Calendar cal = Calendar.getInstance();

		if (cal.before(birthDay)) {
			return 0;
		}

		int yearNow = cal.get(Calendar.YEAR);
		int monthNow = cal.get(Calendar.MONTH) + 1;// 注意此处，如果不加1的话计算结果是错误的
		int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
		cal.setTime(birthDay);

		int yearBirth = cal.get(Calendar.YEAR);
		int monthBirth = cal.get(Calendar.MONTH) + 1;
		int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

		int age = yearNow - yearBirth;
		if (monthNow < monthBirth
				|| (monthNow == monthBirth && dayOfMonthNow < dayOfMonthBirth)) {
			age--;
		}
		return age;
	}

	// 计算两个日期之间有多少天
	public static int getDaysBetween(Date startDate, Date endDate) {
		Calendar d1 = Calendar.getInstance();
		d1.setTime(startDate);
		Calendar d2 = Calendar.getInstance();
		d2.setTime(endDate);
		if (d1.after(d2)) { // swap dates so that d1 is start and d2 is end
			Calendar swap = d1;
			d1 = d2;
			d2 = swap;
		}
		int days = d2.get(Calendar.DAY_OF_YEAR) - d1.get(Calendar.DAY_OF_YEAR);
		int y2 = d2.get(Calendar.YEAR);
		if (d1.get(Calendar.YEAR) != y2) {
			d1 = (Calendar) d1.clone();
			do {
				days += d1.getActualMaximum(Calendar.DAY_OF_YEAR);// 得到当年的实际天数
				d1.add(Calendar.YEAR, 1);
			} while (d1.get(Calendar.YEAR) != y2);
		}
		return days;
	}

	/**
	 * 当前日期相加或相减所得日期（+,-）操作
	 * 
	 * @param date
	 * @param day
	 * @return Date
	 */
	public static Date dsDay_Date(Date date, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int days = calendar.get(Calendar.DATE);
		calendar.set(Calendar.DATE, days + day);
		Date cc = calendar.getTime();
		return cc;
	}

	/**
	 * 取年.
	 * 
	 * @param date
	 * @return
	 */
	public static String getFormatYear(Date date) {
		String str = null;
		if (date != null) {
			str = DateFormatUtils.format(date, "yyyy");
		}
		return str;
	}

	public static List<Date> getDateList(Date beginDate, Date endDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(beginDate);
		List<Date> dateList = new ArrayList<Date>();

		dateList.add(beginDate);

		while (true) {
			if (clean(beginDate).equals(endDate)) {
				break;
			}
			calendar.add(Calendar.DATE, 1);
			Date currentDate = calendar.getTime();
			dateList.add(currentDate);
			if (currentDate.after(endDate)
					|| clean(currentDate).equals(clean(endDate))) {
				break;
			}
		}
		return dateList;
	}

	/**
	 * 得到前一天晚上6点钟.
	 * 
	 * @return
	 * @throws ParseException
	 */
	public static Date getYestoday18Hour() throws ParseException {
		Date d = new Date();
		SimpleDateFormat simpleOldDate = new SimpleDateFormat(
				"yyyy-MM-dd 00:00:00");
		Calendar ca = Calendar.getInstance();
		ca.setTime(simpleOldDate.parse(simpleOldDate.format(d)));
		ca.add(Calendar.DATE, -1);
		ca.add(Calendar.HOUR, 18);
		return ca.getTime();
	}

	private static Date clean(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	public static Date getAfterDay(Date date) {
		SimpleDateFormat simpleOldDate = new SimpleDateFormat(
				"yyyy-MM-dd 00:00:00");
		Calendar ca = Calendar.getInstance();
		try {
			ca.setTime(simpleOldDate.parse(simpleOldDate.format(date)));
		} catch (ParseException e) {
			ca.setTime(getDayStart(new Date()));
		}
		ca.add(Calendar.DATE, 1);
		return ca.getTime();
	}

	public static Date getBeforeDay(Date date) {
		SimpleDateFormat simpleOldDate = new SimpleDateFormat(
				"yyyy-MM-dd 00:00:00");
		Calendar ca = Calendar.getInstance();
		try {
			ca.setTime(simpleOldDate.parse(simpleOldDate.format(date)));
		} catch (ParseException e) {
			ca.setTime(getDayStart(new Date()));
		}
		ca.add(Calendar.DATE, -1);
		return ca.getTime();
	}

	/**
	 * 返回 该日期的开始处
	 * 
	 * @param date
	 * @return
	 */
	public static Date getDayStart(Date date) {
		if (date == null)
			return null;
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}

	public static Date getDayEnd(Date date) {
		if (date == null)
			return null;
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		c.set(Calendar.MILLISECOND, 999);
		return c.getTime();
	}

	/**
	 * 得到当前时间的23点.
	 * 
	 * @param date
	 * @return
	 */
	public static Date getDayEndBeforeOneH(Date date) {
		if (date == null)
			return null;
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 00);
		c.set(Calendar.SECOND, 00);
		c.set(Calendar.MILLISECOND, 000);
		return c.getTime();
	}

	/**
	 * date1是否早于date2
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean inAdvance(Date date1, Date date2) {
		if (date1 == null || date2 == null) {
			return false;
		}
		return date1.getTime() < date2.getTime();
	}

	/**
	 * 将日期和时分的两个时间合并到一起
	 * 
	 * @param date
	 * @param time
	 * @return
	 */
	public static Date mergeDateTime(Date date, Date time) {
		Calendar dateCalendar = Calendar.getInstance();
		dateCalendar.setTime(date);
		Calendar timeCalendar = Calendar.getInstance();
		timeCalendar.setTime(time);
		dateCalendar.set(Calendar.HOUR, timeCalendar.get(Calendar.HOUR));
		dateCalendar.set(Calendar.MINUTE, timeCalendar.get(Calendar.MINUTE));
		dateCalendar.set(Calendar.SECOND, timeCalendar.get(Calendar.SECOND));
		return dateCalendar.getTime();
	}

	public static Date getDateByStr(String dateStr, String formate) {
		SimpleDateFormat sdf = new SimpleDateFormat(formate);
		Date date = null;
		try {
			date = sdf.parse(dateStr);
		} catch (Exception e) {
			LOG.error(e);
		}
		return date;
	}

	/**
	 * 字符串转换到时间格式
	 * 
	 * @param dateStr
	 *            需要转换的字符串
	 * @param formatStr
	 *            需要格式的目标字符串 举例 yyyy-MM-dd
	 * @return Date 返回转换后的时间
	 * @throws ParseException
	 *             转换异常
	 */
	public static Date stringToDate(String dateStr, String formatStr) {
		DateFormat sdf = new SimpleDateFormat(formatStr);
		Date date = null;
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			LOG.error(e);
		}
		return date;
	}

	/**
	 * 比较2个日期 前一个日期至少比后一个日期大一天以上
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean compareDateLessOneDayMore(Date date1, Date date2) {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(date1);
		Calendar c2 = Calendar.getInstance();
		c2.setTime(date2);
		if (c1.get(Calendar.YEAR) > c2.get(Calendar.YEAR)) {
			return true;
		} else if (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)
				&& c1.get(Calendar.MONTH) > c2.get(Calendar.MONTH)) {
			return true;
		} else if (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)
				&& c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH)
				&& c1.get(Calendar.DATE) > c2.get(Calendar.DATE)) {
			return true;
		}
		return false;
	}

	/**
	 * 将日期加年.
	 * 
	 * @param date
	 * @param years
	 * @return
	 */
	public static Date mergeDateTimeAddYear(Date date, Integer years) {
		Calendar dateCalendar = Calendar.getInstance();
		dateCalendar.setTime(date);
		dateCalendar.set(dateCalendar.YEAR, dateCalendar.get(Calendar.YEAR)
				+ years);
		return dateCalendar.getTime();
	}

	/**
	 * 将日期加月.
	 * 
	 * @param date
	 * @param Months
	 * @return
	 */
	public static Date mergeDateTimeAddMonth(Date date, Integer Months) {
		Calendar dateCalendar = Calendar.getInstance();
		dateCalendar.setTime(date);
		dateCalendar.set(dateCalendar.MONTH, dateCalendar.get(Calendar.MONTH)
				+ Months);
		return dateCalendar.getTime();
	}

	public static Long getMinBetween(Date startDate, Date endDate) {
		Calendar d1 = Calendar.getInstance();
		d1.setTime(startDate);
		Calendar d2 = Calendar.getInstance();
		d2.setTime(endDate);
		Long min = 0L;
		if (d1.getTimeInMillis() > d2.getTimeInMillis()) {
			min = (d1.getTimeInMillis() - d2.getTimeInMillis()) / (1000 * 60);
		} else {
			min = (d2.getTimeInMillis() - d1.getTimeInMillis()) / (1000 * 60);
		}
		return min;
	}

	/**
	 * 相差秒数
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static Long getMillisBetween(Date startDate, Date endDate) {
		Calendar d1 = Calendar.getInstance();
		d1.setTime(startDate);
		Calendar d2 = Calendar.getInstance();
		d2.setTime(endDate);
		Long min = 0L;
		if (d1.getTimeInMillis() > d2.getTimeInMillis()) {
			min = (d1.getTimeInMillis() - d2.getTimeInMillis())/1000;
		} else {
			min = (d2.getTimeInMillis() - d1.getTimeInMillis())/1000;
		}
		return min;
	}

	/**
	 * 获取今天的日期，去掉时、分、秒
	 */
	public static Date getTodayYMDDate() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, 0);// 24小时制
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}
	/**
	 * 得到今天几点
	 * @return
	 */
	public static int getTodayHourDate(){
		Calendar cal = Calendar.getInstance();
 	    int hour=cal.get(Calendar.HOUR_OF_DAY);
 	    return hour;
	}

	/**
	 * 获取今天的日期yyyy-MM-dd格式
	 * 
	 * @return
	 */
	public static Date getTodayDate() {
		return stringToDate(formatDate(new Date(), "yyyy-MM-dd"), "yyyy-MM-dd");
	}

	/**
	 * 将分钟转成小时数,有小误差
	 */
	public static float convertToHours(Long minutes) {
		if (minutes == null) {
			return 0f;
		}
		String fStr = (new Float(minutes + "") / 60) + "";
		String res = fStr.substring(0, fStr.lastIndexOf(".") + 2);
		return Float.parseFloat(res);
	}
	
	public static void main(String[] args) {
		System.out.println(minutesToDate(1l));
	}

	/**
	 * 将小时数转为分钟,有小误差
	 */
	public static long convertToMinutes(Float hours) {
		if (hours == null) {
			return 0l;
		}
		long h = hours.intValue() * 60;
		Float f = (hours - new Float(hours.intValue())) * 60;
		String fStr = f.toString();
		return h + Long.parseLong(fStr.substring(0, fStr.indexOf(".")));
	}

	/**
	 * 年月日时分秒的日期转只有年月日的日期
	 */
	public static Date toYMDDate(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 0);// 24小时制
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}

	/**
	 * 本月第一天
	 */
	public static Date getFirstdayOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return cal.getTime();
	}

	/**
	 * 本月最后一天
	 */
	public static Date getLastdayOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int value = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		cal.set(Calendar.DAY_OF_MONTH, value);
		date = cal.getTime();
		return date;
	}

	/**
	 * date 减去当前日期 . 剩余0天0时0分
	 * 
	 * @return str
	 */
	public static String getRemainTimeByCurrentDate(Date date) {
		String str = "剩余0天0时0分";
		if (null != date) {
			Date d = new Date();
			long seconds = (date.getTime() - d.getTime()) / 1000;
			if (seconds > 0) { // 秒
				long day = seconds / (3600 * 24); // 天数
				long house = (seconds % (3600 * 24)) / 3600; // 小时
				long min = (seconds % (3600)) / 60;// 分
				return "剩余" + day + "天" + house + "时" + min + "分";
			}
		}
		return str;
	}

	/**
	 * 得到一个时间的具体日期 例如：两个月后的前一个星期 month = 3 ; day = -7
	 * 
	 * @param Month
	 * @param day
	 * @return DATE
	 * @author zhongshuangxi
	 */
	public static Date getTimesByTimes(int Month, int day) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.MONTH, Month);
		cal.add(Calendar.DATE, day);
		return cal.getTime();
	}

	/**
	 * 返回当前时间 毫秒
	 * 
	 * @param date
	 * @return
	 */
	public static long getDateTime(String date) {
		if (StringUtils.isEmpty(date)) {
			return 0l;
		}
		Date d = toDate(date, "yyyy-MM-dd");
		return d.getTime();
	}
	
	/**
	 * 比较2个日期 前一个日期至少比后一个日期大一天以上
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static String compareDate(Date date1,Date date2){
		if(date1==null || date2==null){
			return null;
		}
		if(date1.getTime()-date2.getTime()<0){
			return null;
		}
		long seconds = (date1.getTime()-date2.getTime()) / 1000;
		Long day = seconds / (3600 * 24); // 天数
		Long hour = (seconds % (3600 * 24)) / 3600; // 小时
		Long min = (seconds % (3600)) / 60;// 分
		String str="";
		if(day>0){
			str = str + day + "天";
		}
		if(hour>0){
			str = str + hour + "小时";
		}
		if(min>0){
			str = str +min + "分";
		}
		return str;	
	}
	
	/**
	 * 在特定的日期上，加入特定的时间点，返回新的日期
	 * @param date 特定日期
	 * @param time 给入的时间点
	 * 				  time 的格式举例：09:00
	 * @return
	 */
	public static Date defineDate(Date date, String time) throws Exception{
		try {
			String []strs = time.split(":");
			int hour =Integer.parseInt(strs[0]);
			int minute = Integer.parseInt(strs[1]);
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.set(Calendar.HOUR_OF_DAY, hour);
			c.set(Calendar.MINUTE, minute);
			c.set(Calendar.SECOND, 0);
			c.set(Calendar.MILLISECOND, 0);
			return c.getTime();
		} catch (Exception e) {
			throw new Exception("给入的时间点参数格式错误！");
		}
	}

	public static List<Date> converFromString(String addition) {
		String[] ds = addition.split(",");
		List<Date> dateList = new ArrayList<Date>();
		for (String d : ds) {
			d = StringUtils.trimToNull(d);
			if (d == null) {
				continue;
			}
			try {
				dateList.add(new SimpleDateFormat(PATTERN_yyyyMMdd).parse(d));
			} catch (ParseException e) {
				LOG.error(e);
			}
		}
		Collections.sort(dateList);
		return dateList;
	}

	public static String convertDate(Date specDate) {
		return new SimpleDateFormat(PATTERN_yyyyMMdd).format(specDate);
	}

	public static Date converDateFromStr(String str) throws ParseException {
		return new SimpleDateFormat(PATTERN_yyyyMMdd).parse(str);
	}
	
	public static Date converDateFromStr2(String str) throws ParseException {
		return new SimpleDateFormat(PATTERN_yyyyMMdd2).parse(str);
	}
	
	public static Date converDateFromStr3(String str) throws ParseException {
		if(StringUtil.isEmptyString(str)){
			return new Date();
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = format.parse(str);
		return date;
	}
	public static Date converDateFromStr4(String str) throws ParseException {
		if(StringUtil.isEmptyString(str)){
			return null;
		}
		return new SimpleDateFormat(PATTERN_yyyy_MM_dd).parse(str);
	}
	

	public static String convertDate3(Date specDate)  {
		String str = "";
		try{
			str = new SimpleDateFormat(PATTERN_yyyy_MM_dd).format(specDate);
		}catch(Exception e){
			LOG.error(e);
		}
		return str;
	}
	public static Date getBeforeDayBySecond(int second) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.SECOND, calendar.get(Calendar.SECOND) - second);
		return calendar.getTime();
	}
	
	public static List<String> getLimitDateByWeek(String beginDate,String endDate,String weeks){
		List<String> dates = new ArrayList<String>();
		Date begin = DateUtil.getDateByStr(beginDate, "yyyy-MM-dd");
		Date end = DateUtil.getDateByStr(endDate, "yyyy-MM-dd");
		while(!begin.after(end)){
			Calendar c = Calendar.getInstance();
			c.setTime(begin);
			int day = c.get(Calendar.DAY_OF_WEEK)-1;
			if(weeks.contains(""+day)){
				dates.add(DateUtil.formatDate(begin, "yyyy-MM-dd"));
			}
			begin = DateUtil.dsDay_Date(begin, 1);
		}
		return dates;
	}
	
	/**
	 * 计算两个日期的天数
	 * @param smdate
	 * @param bdate
	 * @return
	 * @throws ParseException
	 */
	 public static int daysBetween(String smdate,String bdate){  
		 long between_days=0;
		 try{
			 SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
		        Calendar cal = Calendar.getInstance();    
		        cal.setTime(sdf.parse(smdate));    
		        long time1 = cal.getTimeInMillis();                 
		        cal.setTime(sdf.parse(bdate));    
		        long time2 = cal.getTimeInMillis();         
		        between_days=(time2-time1)/(1000*3600*24); 
		 }catch(Exception ex){
			 LOG.error(ex);
		 }
	       return Integer.parseInt(String.valueOf(between_days));     
	    } 

	/**
	 * 当前日期按月份相加之后所得日期
	 * @param date
	 * @param month
	 * @return
	 */
	 public static Date getDate_Month(Date date, int month) { 
		 Calendar calendar = Calendar.getInstance(); 
		 calendar.setTime(date); 
		 int monthTest = calendar.get(Calendar.MONTH); 
		 calendar.set(Calendar.MONTH, monthTest+month); 
		 calendar.set(Calendar.HOUR_OF_DAY, 0); 
		 calendar.set(Calendar.MINUTE, 0); 
		 calendar.set(Calendar.SECOND, 0); 
		 calendar.set(Calendar.MILLISECOND, 0); 
		 Date cc = calendar.getTime(); 
		 return cc; 
	 }

	 	public static String minutesToDate(Long minutes) {
	 		if(minutes == null) {
	 			return "";
	 		}
	 		double time = (double)minutes;
	 		int day = 0;
	 		int hour = 0;
	 		int minute = 0;
	 		if (time > 0) {
	 			day = (int)Math.ceil(time / 1440);
	 			if (time % 1440 == 0) {
	 				hour = 0;
	 				minute = 0;
	 			} else {
	 				hour = (int)(1440 - time % 1440) / 60;
	 				minute = (int)(1440 - time % 1440) % 60;
	 			}
	 		} else if (time < 0) {
	 			time = -time;
	 			hour = (int)time / 60;
	 			minute = (int)time % 60;
	 		}
	 		String hourStr = "" + hour, minuteStr = "" + minute;
	 		if (hour < 10)
	 			hourStr = "0" + hour;
	 		if (minute < 10)
	 			minuteStr = "0" + minute;
	 		return day + "天" + hourStr + "点" +minuteStr + "分";
	 	}


	public static String getLogTime(Date date) {
		if (date != null) {
			SimpleDateFormat logFormat = new SimpleDateFormat("MM月dd日 HH时mm分ss秒");
			return logFormat.format(date);
		}
		return null;
	}

	// 计算两个日期之间有多少分钟
	public static long getMinutesBetween(Date startDate, Date endDate) {
		return (startDate.getTime() - endDate.getTime())/1000/60;
	}
	//把日期转为字符串  
    public static String converDateToString(Date date,String resultType)  
    {  
    	if (date==null) {
			return null;
		}
        DateFormat df = new SimpleDateFormat(resultType);  
          
        return df.format(date);  
    } 

	/**
	 * 返回当前时间 分
	 * 例如：1天1小时1分钟==>1501分
	 * @param day
	 * @param hour
	 * @param minute
	 * @return
	 */
	public static Long getLongTimeFromDayHourMinute(Integer day, Integer hour, Integer minute) {
		try {
			if (day == null || hour == null || minute == null) {
				return 0L;
			}
			Integer time = day*1440 + hour*60 + minute;
			return time.longValue();
		} catch (Exception e) {
			LOG.error(e);
		}
		return 0L;
	}

    public static String getChsWeekDay(String engWeekDay) {
        if (StringUtils.isEmpty(engWeekDay)) {
            return "";
        }
        String lowerCase = engWeekDay.toLowerCase();
        return weekDays.get(lowerCase);
    }

	/**
	 * 字符串转换成Date
     */
	public static Date parse(String str, String formateStr) {
		Date date = null;
		try {
			if(StringUtil.isEmptyString(formateStr)){
				formateStr = PATTERN_yyyy_MM_dd_HH_mm_ss_SSS;
			}
			date = new SimpleDateFormat(formateStr).parse(str);
		}catch (Exception e){
			LOG.error(e);
		}
		return date;
	}

	   /**
     * 
    * @Title: DcDay_minOfDay 
    * @Description: 传入的时间，加上给定的分钟数 
    * @param @param date
    * @param @param minstes
    * @param @return    设定文件 
    * @return Date    返回类型 
    * @throws
     */
    public static Date dateAddMinutes(Date date, int minstes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, minstes);
        
        return cal.getTime();
    }
	
}
