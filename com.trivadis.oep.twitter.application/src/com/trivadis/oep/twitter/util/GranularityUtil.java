package com.trivadis.oep.twitter.util;

import java.text.ParseException;
import java.util.Date;

import org.joda.time.DateTime;

public class GranularityUtil {
	public String formatBucket(String forGranularity, DateTime timestamp) throws ParseException {
		return TimeseriesBucketUtil.formatBucketId(GranularityEnum.valueOf(forGranularity), timestamp);
	}
	
	public Date formatTimestamp(String forGranularity, DateTime timestamp) throws ParseException {
		return (TimeseriesBucketUtil.formatTimestamp(GranularityEnum.valueOf(forGranularity), timestamp));
	}

}
