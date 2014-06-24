package com.trivadis.oep.twitter.util;


import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.springframework.util.Assert;

public class TimeseriesBucketUtil {
	static TimeseriesBucketUtil timeseriesBucketUtil = new TimeseriesBucketUtil();
	
	// time-dimension format map (first date for the bucket-id, second date for
	// the time-id)
	private Map<GranularityEnum, TimeseriesBucketDefinition> bucketDefinitionMap = null;

	public TimeseriesBucketUtil(Map<GranularityEnum, TimeseriesBucketDefinition> bucketDefinitionMap) {
		this.bucketDefinitionMap = bucketDefinitionMap;
	}
	
	public TimeseriesBucketUtil() {
		this.bucketDefinitionMap = new HashMap<GranularityEnum, TimeseriesBucketDefinition>();
		this.bucketDefinitionMap.put(GranularityEnum.MINUTE, new TimeseriesBucketDefinition("yyyy/MM/dd/HH", "yyyy/MM/dd/HH/mm", 60));
		this.bucketDefinitionMap.put(GranularityEnum.HOUR, new TimeseriesBucketDefinition("yyyy/MM/dd", "yyyy/MM/dd/HH", 1440));
		this.bucketDefinitionMap.put(GranularityEnum.DAY, new TimeseriesBucketDefinition("yyyy/MM", "yyyy/MM/dd", 43200));
		this.bucketDefinitionMap.put(GranularityEnum.MONTH, new TimeseriesBucketDefinition("yyyy", "yyyy/MM", 518400));
	}
	
	public int numberOfBuckets() {
		return bucketDefinitionMap.size();
	}
	
	/**
	 * Returns the formated bucketId and timestamp for a given granularity and timestamp
	 * @param forGranularity	the granularity to use
	 * @param timestamp			the timestamp
	 * @return	a pair of bucketId and timestamp
	 * @throws ParseException
	 */
	public Pair<String,Date> formatBucketIdAndTimestamp(GranularityEnum forGranularity, DateTime timestamp) throws ParseException {
		return bucketIdAndTimestampMap(timestamp).get(forGranularity);
	}

	public static String formatBucketId(GranularityEnum forGranularity, DateTime timestamp) throws ParseException {
		return timeseriesBucketUtil.formatBucketIdAndTimestamp(forGranularity, timestamp).left();
	}
	
	public static String formatBucketId(DateTime timestamp) throws ParseException {
		return timeseriesBucketUtil.formatBucketIdAndTimestamp(GranularityEnum.MINUTE, timestamp).left();
	}

	public static Date formatTimestamp(GranularityEnum forGranularity, DateTime timestamp) throws ParseException {
		return timeseriesBucketUtil.formatBucketIdAndTimestamp(forGranularity, timestamp).right();
	}

	/**
	 * Returns a pair of formated bucketId and timestamp for the given granularity
	 * @param timestamp		the timestamp 
	 * @return	the pair
	 * @throws ParseException
	 */
    public Pair<String,String> bucketIdAndTimestamp(GranularityEnum forGranularity, DateTime timestamp) {
	        TimeseriesBucketDefinition bucket = bucketDefinitionMap.get(forGranularity);
	        StringBuffer bucketId = new StringBuffer()
	        	.append(forGranularity.name()).append("-")
		    	.append(bucket.getBucketDateFormat().format(timestamp.toDate()));

  	        return new Pair<String, String>(bucketId.toString(), bucket.getDatePointKeyDateFormat().format(timestamp.toDate()));
	}

	/**
	 * Returns a map by granularity with a pair of formated bucketId and timestamp to be used for the given granularity
	 * @param timestamp		the timestamp 
	 * @return	the map
	 * @throws ParseException
	 */
	public Map<GranularityEnum, Pair<String,Date>> bucketIdAndTimestampMap(DateTime timestamp)
			throws ParseException {
		Map<GranularityEnum, Pair<String, Date>> result = new HashMap<GranularityEnum, Pair<String, Date>>();

		if (timestamp != null) {
			for (GranularityEnum timeDimensionKey : bucketDefinitionMap.keySet()) {
				TimeseriesBucketDefinition bucket = bucketDefinitionMap.get(timeDimensionKey);
				// left value of the pair holds the date format part for the bucket
				StringBuffer bucketId = new StringBuffer()
						.append(timeDimensionKey.name()).append("-")
						.append(bucket.getBucketDateFormat().format(timestamp.toDate()));
				Date timeId = null;
				timeId = bucket.getDatePointKeyDateFormat().parse(
						bucket.getDatePointKeyDateFormat().format(timestamp.toDate()));
				result.put(timeDimensionKey, new Pair<String,Date>(bucketId.toString(), timeId));
			}
		}
		return result;
	}
	
	public List<String> listOfBucketIds(GranularityEnum forGranularity, DateTime from, DateTime to, boolean ascending) {
		Assert.isTrue(bucketDefinitionMap.containsKey(forGranularity), "the granularity " + forGranularity + " does not exist in the bucket definition map");
		TimeseriesBucketDefinition bucket = bucketDefinitionMap.get(forGranularity);
		
		List<String> bucketIds = new ArrayList<String>();
		DateTime fromDT = from;
		Interval i = bucket.bucket(from, to, forGranularity);
		while(i.contains(fromDT)) {
			StringBuffer bucketId = new StringBuffer().append(forGranularity.name()).append("-").append(bucket.getBucketDateFormat().format(fromDT.toDate()));
			if (ascending) { 
				bucketIds.add(bucketId.toString());
			} else {
				bucketIds.add(0, bucketId.toString());
			}

			fromDT = fromDT.plusMinutes(bucket.getBucketLengthInMinutes());
		}
		
		System.out.println("Buckets size: " + bucketIds.size());
		
		return bucketIds;
	}


}
