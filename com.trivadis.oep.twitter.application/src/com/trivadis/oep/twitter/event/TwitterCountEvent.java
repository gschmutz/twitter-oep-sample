package com.trivadis.oep.twitter.event;

import java.util.Date;
import java.util.UUID;

import com.trivadis.oep.cassandra.data.CassandraCounterDataObject;

public class TwitterCountEvent  {
	private UUID sensorId;
	private String category;
	private String keyword;
	private String bucketId;
	private Date timeId;
	private Long counter;
	
	public TwitterCountEvent() {
	}
	
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Long getCounter() {
		return counter;
	}

	public void setCounter(Long counter) {
		this.counter = counter;
	}

	public UUID getSensorId() {
		return sensorId;
	}

	public void setSensorId(UUID sensorId) {
		this.sensorId = sensorId;
	}

	public String getBucketId() {
		return bucketId;
	}

	public void setBucketId(String bucketId) {
		this.bucketId = bucketId;
	}

	public Date getTimeId() {
		return timeId;
	}

	public void setTimeId(Date timeId) {
		this.timeId = timeId;
	}

	@Override
	public String toString() {
		return "TwitterCountEvent [sensorId=" + sensorId + ", keyword="
				+ keyword + ", bucketId=" + bucketId + ", timeId=" + timeId
				+ ", counter=" + counter + "]";
	}

}
