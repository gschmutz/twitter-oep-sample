package com.trivadis.oep.twitter.util;

import java.util.Date;
import java.text.ParseException;

import org.joda.time.DateTime;

public class GranularityDate {
	
	private DateTime dateTime;
	
	
	public GranularityDate(DateTime dateTime) {
		super();
		this.dateTime = dateTime;
	}

	public GranularityDate(Date date) {
		super();
		this.dateTime = new DateTime(date);
	}

	public String formatBucket(String forGranularity) throws ParseException {
		return TimeseriesBucketUtil.formatBucketId(GranularityEnum.valueOf(forGranularity), dateTime);
	}
	
	public Date formatTimestamp(String forGranularity) throws ParseException {
		return (TimeseriesBucketUtil.formatTimestamp(GranularityEnum.valueOf(forGranularity), dateTime));
	}

	public DateTime getDateTime() {
		return dateTime;
	}
}
