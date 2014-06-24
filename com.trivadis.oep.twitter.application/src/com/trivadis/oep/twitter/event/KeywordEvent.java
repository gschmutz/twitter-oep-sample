package com.trivadis.oep.twitter.event;

import java.util.UUID;

import com.trivadis.oep.twitter.util.GranularityDate;

public class KeywordEvent {
	private UUID sensorId;
	private String keyword;
	private GranularityDate createdAt;
	
	public KeywordEvent(UUID sensorId, String keyword, GranularityDate createdAt) {
		super();
		this.sensorId = sensorId;
		this.keyword = keyword;
		this.createdAt = createdAt;
	}
	
	public UUID getSensorId() {
		return sensorId;
	}
	public void setSensorId(UUID sensorId) {
		this.sensorId = sensorId;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public GranularityDate getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(GranularityDate createdAt) {
		this.createdAt = createdAt;
	}

}
