package com.trivadis.oep.twitter.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.joda.time.DateTime;

public class DateUtil {
	public static final String DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss";

	private static ThreadLocal<DateFormat> df = new ThreadLocal<DateFormat>() {

		@Override
		public DateFormat get() {
			return super.get();
		}

		@Override
		protected DateFormat initialValue() {
			DateFormat df = new SimpleDateFormat(DateUtil.DATE_PATTERN);
			df.setTimeZone(TimeZone.getTimeZone("UTC"));
			return df;
		}

		@Override
		public void remove() {
			super.remove();
		}

		@Override
		public void set(DateFormat value) {
			super.set(value);
		}

	};

	public static String getDateAsString(Date date) {
		if (date == null) {
			return null;
		}
		return df.get().format(date);
	}

	public static DateTime toDate(String startDate) {
		if (startDate != null && startDate.length() > 0) {
			try {
				return new DateTime(df.get().parse(startDate));
			} catch (NumberFormatException nfe) {
				return null;
			} catch (ParseException e) {
				throw new RuntimeException(e);
			} catch (ArrayIndexOutOfBoundsException e) {
				throw new RuntimeException(
						"Invalid startDate in DateUtil.toDate(): " + startDate,
						e.getCause());
			}
		} else {
			return null;
		}
	}

	public static Date currentDateOnMidnight() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

}
