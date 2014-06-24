package com.trivadis.oep.twitter.event;

public class UserMentionEntity {
	private long id;
	private String name;
	private String screenName;
	public UserMentionEntity(long id, String name, String screenName) {
		super();
		this.id = id;
		this.name = name;
		this.screenName = screenName;
	}
	public long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getScreenName() {
		return screenName;
	}
	
	@Override
	public String toString() {
		return "UserMentionEntity [id=" + id + ", name=" + name
				+ ", screenName=" + screenName + "]";
	}
	
	
}
