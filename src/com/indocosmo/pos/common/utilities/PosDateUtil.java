package com.indocosmo.pos.common.utilities;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.data.providers.shopdb.PosSystemParamProvider;

public final class PosDateUtil {

	public static final String DATE_SEPERATOR = "-";
	public static final String TIME_SEPERATOR = ":";

	public static final String TIME_FORMAT_24 = "HH" + TIME_SEPERATOR + "mm"
			+ TIME_SEPERATOR + "ss";
	public static final String TIME_FORMAT_12 = "hh" + TIME_SEPERATOR + "mm"
			+ TIME_SEPERATOR + "ss";
	public static final String SHORT_TIME_FORMAT_12 = "hh" + TIME_SEPERATOR
			+ "mm a";
	public static final String SHORT_TIME_FORMAT_24 = "hh" + TIME_SEPERATOR
			+ "mm";
	public static final String DATE_FORMAT_NOW_24 = "yyyy" + DATE_SEPERATOR
			+ "MM" + DATE_SEPERATOR + "dd HH" + TIME_SEPERATOR + "mm"
			+ TIME_SEPERATOR + "ss";
	public static final String DATE_FORMAT_NOW_12 = "yyyy" + DATE_SEPERATOR
			+ "MM" + DATE_SEPERATOR + "dd hh" + TIME_SEPERATOR + "mm"
			+ TIME_SEPERATOR + "ss";

	public static final String DATE_FORMAT_DDMMYY = "dd" + DATE_SEPERATOR
			+ "MM" + DATE_SEPERATOR + "yyyy";
	public static final String DATE_FORMAT_YYYYMMDD = "yyyy" + DATE_SEPERATOR
			+ "MM" + DATE_SEPERATOR + "dd";
	public static final String DATE_FORMAT_MMDDYYYY = "MM" + DATE_SEPERATOR
			+ "dd" + DATE_SEPERATOR + "yyyy";

	public static final String SHORT_DATE_TIME_FORMAT = "yyyy" + DATE_SEPERATOR
			+ "MM" + DATE_SEPERATOR + "dd HH" + TIME_SEPERATOR + "mm";

	/**
	 * Get current date time with Date()
	 * 
	 * @return
	 */
	public static String getDateTime() {
		return getDateTime(true);
	}

	/**
	 * @param format
	 * @return
	 */
	public static String getDateTime(String format) {
		DateFormat dateFormat = new SimpleDateFormat(format);
		Date dateTime = Calendar.getInstance().getTime();
		return dateFormat.format(dateTime);
	}

	/**
	 * @param is24H
	 * @return
	 */
	public static String getDateTime(boolean is24H) {

		DateFormat dateFormat = new SimpleDateFormat(
				((is24H) ? DATE_FORMAT_NOW_24 : DATE_FORMAT_NOW_12));
		Date dateTime = Calendar.getInstance().getTime();
		return dateFormat.format(dateTime);
	}

	/**
	 * Get current date Date()
	 * 
	 * @return
	 */
	public static String getDate() {
		return getDate(DATE_FORMAT_YYYYMMDD);
	}

	/**
	 * @param format
	 * @return
	 */
	public static String getDate(String format) {
		DateFormat dateFormat = new SimpleDateFormat(format);
		Date date = new Date();

		return dateFormat.format(date);
	}

	/**
	 * @return
	 */
	public static String getDateInJapaneaseFormat() {

		DateFormat df = DateFormat.getDateInstance(DateFormat.FULL, new Locale(
				"ja"));
		return df.format(new Date());
	}
 
	/**
	 * Get current time with Date()
	 * 
	 * @return
	 */
	public static String getTime() {

		return getTime(true);
	}

	/**
	 * @param is24H
	 * @return
	 */
	public static String getTime(boolean is24H) {
		DateFormat dateFormat = new SimpleDateFormat(((is24H) ? TIME_FORMAT_24
				: TIME_FORMAT_12));
		Date dateTime = Calendar.getInstance().getTime();

		return dateFormat.format(dateTime);
	}

	/**
	 * @return
	 */
	public static java.sql.Date getCurrentJavaSqlDate() {

		java.util.Date today = new java.util.Date();
		return new java.sql.Date(today.getTime());
	}

	/**
	 * @param date
	 * @param time
	 * @return
	 */
	public static long getTimeInMills(String date, String time) {

		if ((date == null || date.trim().equals(""))
				|| (time == null || time.trim().equals("")))
			return 0;
		final String dateTime = date + " " + time;
		return getTimeInMills(dateTime);
	}

	/**
	 * This functions returns the date in milli seconds
	 * 
	 * @param dateTime
	 * @return
	 */
	public static long getTimeInMills(String dateTime) {

		long timeInMil = 0;
		if (dateTime != null) {
			try {
				SimpleDateFormat dateFormat = new SimpleDateFormat(
						DATE_FORMAT_NOW_24);
				Date convertedTime = dateFormat.parse(dateTime);
				timeInMil = convertedTime.getTime();
			} catch (Exception e) {
				PosLog.write("PosDateUtil", "getTimeInMills", e);
			}
		}
		return timeInMil;
	}

	/**
	 * @param date
	 * @return
	 */
	public static boolean validateDate(String date) {

		boolean isValid = false;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				DATE_FORMAT_YYYYMMDD);
		simpleDateFormat.setLenient(false);
		try {
			System.out.println(date);
			simpleDateFormat.parse(date);
			System.out.println(date);
			isValid = true;
		} catch (ParseException e) {

		}
		return isValid;
	}

	/**
	 * @param time
	 * @return
	 */
	public static boolean validateTime(String time) {

		boolean isValid = false;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(TIME_FORMAT_24);
		simpleDateFormat.setLenient(false);
		try {
			System.out.println(time);
			simpleDateFormat.parse(time);
			System.out.println(time);
			isValid = true;
		} catch (ParseException e) {

		}
		return isValid;
	}

	/**
	 * @param date
	 * @return
	 */
	public static Date parse(String date) {

		return parse(DATE_FORMAT_YYYYMMDD, date);
	}

	/**
	 * @param format
	 * @param date
	 * @return
	 */
	public static Date parse(String format, String date) {

		Date pDate = null;
		try {
			SimpleDateFormat dateFormtter = new SimpleDateFormat(format);
			pDate = dateFormtter.parse(date);

		} catch (ParseException e) {
			PosLog.debug(e);
		}
		return pDate;

	}

	/**
	 * @param format
	 * @param date
	 * @return
	 */
	public static String format(String format, Date date) {

		String pDate = null;
		try {
			SimpleDateFormat dateFormtter = new SimpleDateFormat(format);
			pDate = dateFormtter.format(date);

		} catch (Exception e) {
		}
		return pDate;
	}

	/**
	 * @param date
	 * @return
	 */
	public static String format(String date) {

		return format(PosEnvSettings.getInstance().getDateTimeFormat(),
				PosDateUtil.parse(DATE_FORMAT_NOW_24, date));
	}

	 
	/**
	 * @return
	 */
	public static String getLocalDateTimeFormat() {

		String localFormat = null;
		try {

			    localFormat=PosSystemParamProvider.getInstance().getSystemParam().getDateFormat().getFormat() + " " + 
					PosSystemParamProvider.getInstance().getSystemParam().getTimeFormat().getFormat();

		} catch (Exception e) {

			PosLog.debug(e);
			localFormat=DATE_FORMAT_NOW_24;
		}

		return localFormat;
	}
	/**
	 * @return
	 */
	public static String getLocalTimeFormat() {

		String localFormat = null;
		try {

			    localFormat= 	PosSystemParamProvider.getInstance().getSystemParam().getTimeFormat().getFormat();

		} catch (Exception e) {

			PosLog.debug(e);
			localFormat=DATE_FORMAT_NOW_24;
		}

		return localFormat;
	}
	
	/**
	 * @return
	 */
	public static String getLocalDateFormat() {

		String localFormat = null;
		try {

			    localFormat=PosSystemParamProvider.getInstance().getSystemParam().getDateFormat().getFormat();

		} catch (Exception e) {

			PosLog.debug(e);
			localFormat=DATE_FORMAT_NOW_24;
		}

		return localFormat;
	}

	public static String formatLocalDateTime(String date) {

		return formatLocalDateTime(DATE_FORMAT_NOW_24, date);
	}

	/**
	 * @param sourceFormat
	 * @param date
	 * @return
	 */
	public static String formatLocalDateTime(String sourceFormat, String date) {
 
		String pDate = null;
		try {

			pDate = format(getLocalDateTimeFormat(), parse(sourceFormat, date));
		} catch (Exception e) {
			PosLog.debug(e);
		}
		return pDate;
	}

	
	
	public static String formatLocal(String date) {

		return formatLocal(DATE_FORMAT_YYYYMMDD, date);
	}

	/**
	 * @param sourceFormat
	 * @param date
	 * @return
	 */
	public static String formatLocal(String sourceFormat, String date) {
 
		String pDate = null;
		try {

			pDate = format(getLocalDateFormat(), parse(sourceFormat, date));
		} catch (Exception e) {
			PosLog.debug(e);
		}
		return pDate;
	}
	/**
	 * @param date
	 * @return
	 */
	public static String formatShortDateTime(String date) {

		return format(SHORT_DATE_TIME_FORMAT,
				PosDateUtil.parse(DATE_FORMAT_NOW_24, date));
	}

	/**
	 * @param format
	 * @param date
	 * @return
	 */
	public static String format(String format, String date) {

		return format(format, PosDateUtil.parse(DATE_FORMAT_NOW_24, date));
	}

	/**
	 * @param year
	 * @param month
	 * @param date
	 * @return
	 */
	public static Date build(String year, String month, String date) {

		return build(year, month, date, DATE_SEPERATOR);
	}

	/**
	 * @param year
	 * @param month
	 * @param date
	 * @param seperator
	 * @return
	 */
	public static Date build(String year, String month, String date,
			String seperator) {

		return parse(buildStringDate(year, month, date, seperator));
	}

	/**
	 * @param year
	 * @param month
	 * @param date
	 * @param seperator
	 * @return
	 */
	public static String buildStringDate(String year, String month,
			String date, String seperator) {

		String stringDate = null;
		try {
			SimpleDateFormat dateFormtter = new SimpleDateFormat(
					DATE_FORMAT_YYYYMMDD);
			stringDate = dateFormtter.format(dateFormtter.parse(year
					+ seperator + month + seperator + date));
		} catch (ParseException e) {
		}
		return stringDate;
	}

	/**
	 * @param date
	 * @param days
	 * @return
	 */
	public static String getNextDate(String date, int days) {

		// SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(sdf.parse(date));
			c.add(Calendar.DATE, days); // number of days to add
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String dt = sdf.format(c.getTime());
		return dt;
	}

	/**
	 * @param hour
	 * @param minute
	 * @param second
	 * @param seperator
	 * @return
	 */
	public static String buildStringTime(String hour, String minute,
			String second, String seperator) {

		String stringTime = hour + seperator + minute + seperator + second;
		return stringTime;
	}

	/**
	 * @return
	 */
	public static String getDayOfWeek() {

		String[] day_name = { "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" };
		final int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1;
		return day_name[day];

	}

	/**
	 * @param startTime
	 * @return
	 * @throws ParseException
	 */
	public static Date parseToDateTime(String startTime, String format)
			throws ParseException {

		SimpleDateFormat sf = new SimpleDateFormat(format);
		Date dt = sf.parse(startTime);

		return dt;
	}

	/**
	 * @param startTime
	 * @param endTime
	 * @param format
	 * @return
	 * @throws ParseException
	 */
	public static long getDiff(String startTime, String endTime, String format)
			throws ParseException {

		SimpleDateFormat sf = new SimpleDateFormat(format);

		Date dtFrom = sf.parse(startTime);
		Date dtto = sf.parse(endTime);

		return dtto.getTime() - dtFrom.getTime();
	}

	public static String getDayOfWeek(String date) {

		final java.util.Date newDate = PosDateUtil.parse(
				PosDateUtil.DATE_FORMAT_NOW_24, date);
		Calendar cal = Calendar.getInstance();
		cal.setTime(newDate);

		final int weekDay = cal.get(Calendar.DAY_OF_WEEK);

		String day = "";
		switch (weekDay) {
		case 1:
			day = "Sunday";
			break;
		case 2:
			day = "Monday";
			break;
		case 3:
			day = "Tuesday";
			break;
		case 4:
			day = "Wednesday";
			break;
		case 5:
			day = "Thursday";
			break;
		case 6:
			day = "Friday";
			break;
		case 7:
			day = "Saturday";
			break;
		}
		return day;
	}
}
