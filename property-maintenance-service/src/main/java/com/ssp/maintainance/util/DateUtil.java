package com.ssp.maintainance.util;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	public static Date getCurrentDate(){
		Calendar cal = Calendar.getInstance();
		return cal.getTime();
	}
	
	public static Timestamp getCurrentDateTime(){
		Calendar cal = Calendar.getInstance();
		return new Timestamp(cal.getTimeInMillis());
	}
	
	public static Date addMonthsOffSet(Date currentDate, int offSetMonth){
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentDate);
		cal.add(Calendar.MONTH, offSetMonth);
		return cal.getTime();
	}
}
