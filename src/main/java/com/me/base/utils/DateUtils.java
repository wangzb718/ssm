package com.me.base.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtils extends org.apache.commons.lang.time.DateUtils {
	
	public static Date format(Date date,String format) {
		SimpleDateFormat f = new SimpleDateFormat(format);
		String dateStr = f.format(date);
		try {
			return f.parse(dateStr);
		} catch (ParseException e) {
			return null;
		}
	}
	
	public static Date format(String dateString,String formatString) {
		SimpleDateFormat f = new SimpleDateFormat(formatString);
		try {
			return f.parse(dateString);
		} catch (ParseException e) {
			return null;
		}
	}
	
	public static String formatToString(Date date,String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}
	
	//得到两个日期相差的天数 可以跨年计算
	public static int getDaysBetween (Date beginDate, Date endDate){  
		if(endDate.getTime()>=beginDate.getTime()){
			Calendar d1 = Calendar.getInstance(); 
			d1.setTime(beginDate);  
			Calendar d2 = Calendar.getInstance();  
			d2.setTime(endDate);  
			  int days = d2.get(Calendar.DAY_OF_YEAR) - d1.get(Calendar.DAY_OF_YEAR);  
			  int y2 = d2.get(Calendar.YEAR);  
			  if (d1.get(Calendar.YEAR) != y2) {  
				  d1 = (Calendar) d1.clone();  
				  do {  
				  days += d1.getActualMaximum(Calendar.DAY_OF_YEAR);//得到当年的实际天数  
				  d1.add(Calendar.YEAR, 1);  
				  } while (d1.get(Calendar.YEAR) != y2);  
			    
			  }  
			  return days;  
		}else{
			 return -1;
		}	
    }
	
	/**
	 * 计算两个时间差的时分秒数
	 * @param date
	 * @return
	 */
	public static String bewttenHHmmss(long l){
		if(l<0) l = -1*l;
		long day = l/(24*60*60*1000);
		long hour = l/(60*60*1000)-day*24;
		long min = l/(60*1000)-day*24*60-hour*60;
		long s = l/1000-day*24*60*60-hour*60*60-min*60;
		return hour+":"+min+":"+s;
	}
	
	/**
	 * 将字符串转换为日期（不包括时间）
	 * @param dateString "yyyy-MM-dd"格式的日期字符串
	 * @return 日期
	 */
	public static Date convertToDate(String dateString) {
		try {
			return new SimpleDateFormat("yyyy-MM-dd", java.util.Locale.CHINA).parse(dateString);
		} catch (ParseException e) {
			return null;
		}
	}
	
	//根据身份证计算年龄
	public static Integer calculationAge(String idCardNumber){
			if(idCardNumber == null) return null;
		try {
			String yy1 = idCardNumber.substring(6,10);          //出生的年份
	        String mm1 = idCardNumber.substring(10,12);       //出生的月份
	        String dd1 = idCardNumber.substring(12,14);         //出生的日期
	        String birthday = yy1.concat("-").concat(mm1).concat("-").concat(dd1);
	        return getDaysBetween(convertToDate(birthday), new Date())/365;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;    
    }
	
	//得到date时间相差diffday天的时间
	public static Date getPriDay(Date fDate,Integer diffday){
		Date lDate = null;
		try {
			GregorianCalendar c1 = new GregorianCalendar();
			c1.setTime(fDate );
			c1.add( Calendar.DATE, diffday );
			lDate = c1.getTime();
		} catch( Exception e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return lDate;
	}
}
