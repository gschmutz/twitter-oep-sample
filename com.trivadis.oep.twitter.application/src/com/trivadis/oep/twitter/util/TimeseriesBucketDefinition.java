package com.trivadis.oep.twitter.util;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import org.joda.time.DateTime;
import org.joda.time.Hours;
import org.joda.time.Interval;

public class TimeseriesBucketDefinition {
	private String bucketDateFormatPattern;
	private String dataPointKeyDateFormatPattern;
	private Integer bucketLengthInMinutes;
	private Integer cacheTTL = 30;
	
	public TimeseriesBucketDefinition() {
	}

	public TimeseriesBucketDefinition(String bucketDateFormatPattern, String dataPointKeyDateFormatPattern, Integer bucketLengthInMinutes) {
		this.bucketDateFormatPattern = bucketDateFormatPattern;
		this.dataPointKeyDateFormatPattern = dataPointKeyDateFormatPattern;
		this.bucketLengthInMinutes = bucketLengthInMinutes;
	}
	
	public String getBucketDateFormatPattern() {
		return bucketDateFormatPattern;
	}

	public void setBucketDateFormatPattern(String bucketDateFormatPattern) {
		this.bucketDateFormatPattern = bucketDateFormatPattern;
	}

	public String getDataPointKeyDateFormatPattern() {
		return dataPointKeyDateFormatPattern;
	}

	public void setDataPointKeyDateFormatPattern(String dataPointKeyDateFormatPattern) {
		this.dataPointKeyDateFormatPattern = dataPointKeyDateFormatPattern;
	}

	public Integer getBucketLengthInMinutes() {
		return bucketLengthInMinutes;
	}

	public void setBucketLengthInMinutes(Integer bucketLengthInMinutes) {
		this.bucketLengthInMinutes = bucketLengthInMinutes;
	}
	
	public DateFormat getBucketDateFormat() {
		SimpleDateFormat bdf = new SimpleDateFormat(this.bucketDateFormatPattern);
	  	bdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		return bdf;
	}

	public DateFormat getDatePointKeyDateFormat() {
		SimpleDateFormat dpkdf = new SimpleDateFormat(this.dataPointKeyDateFormatPattern);
	  	dpkdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		return dpkdf;
	}
	
	public Integer getCacheTTL() {
		return cacheTTL;
	}
	
	public Interval bucket(DateTime begin, DateTime end, GranularityEnum forGranularity) {
		//System.out.println(begin.hourOfDay().roundFloorCopy());
		//System.out.println(end.hourOfDay().roundFloorCopy().plusHours(1));
		//System.out.println(begin.dayOfMonth().roundFloorCopy());
		//System.out.println(end.dayOfMonth().roundFloorCopy().plusMonths(1));
		Interval i = null;
		if (forGranularity.equals(GranularityEnum.MINUTE)) {
			i =	new Interval(begin.hourOfDay().roundFloorCopy(), end.hourOfDay().roundFloorCopy().plusHours(1));
		} else if (forGranularity.equals(GranularityEnum.HOUR)) {
			i =	new Interval(begin.dayOfMonth().roundFloorCopy(), end.dayOfMonth().roundFloorCopy().plusDays(1));
		} else if (forGranularity.equals(GranularityEnum.DAY)) {
			i =	new Interval(begin.monthOfYear().roundFloorCopy(), end.monthOfYear().roundFloorCopy().plusMonths(1));
		} else if (forGranularity.equals(GranularityEnum.MONTH)) {
			i =	new Interval(begin.yearOfCentury().roundFloorCopy(), end.yearOfCentury().roundFloorCopy().plusYears(1));
		}
		return i;
	}
	
	public DateTime bucketBoundaryEnd(DateTime forDate) {
		Interval i = new Interval(forDate.hourOfDay().roundFloorCopy().toInstant(), Hours.ONE);
		return i.getEnd();
	}
	
	public DateTime bucketBoundaryStart(DateTime forDate) {
		return forDate.hourOfDay().roundFloorCopy();
	}
}
