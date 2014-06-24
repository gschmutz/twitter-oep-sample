package com.trivadis.oep.twitter.event;

import java.util.Date;

public class User {
	private long id;
	private String description;
	private int favoritesCount;
	private String screenName;
	private Date createdAt;
	private int followersCount;
	private int friendsCount;
	private boolean isGeoEnabled;
	private int listedCount;
	private int statusesCount;
	private String lang;
	private String location;
	private String name;
	private boolean isProtected;
	private String timeZone;
	private String URL;
	private int utcOffset;
	public User(long id, String description, int favoritesCount,
			String screenName, Date createdAt, int followersCount,
			int friendsCount, boolean isGeoEnabled, int listedCount,
			int statusesCount, String lang, String location, String name,
			boolean isProtected, String timeZone, String uRL, int utcOffset) {
		super();
		this.id = id;
		this.description = description;
		this.favoritesCount = favoritesCount;
		this.screenName = screenName;
		this.createdAt = createdAt;
		this.followersCount = followersCount;
		this.friendsCount = friendsCount;
		this.isGeoEnabled = isGeoEnabled;
		this.listedCount = listedCount;
		this.statusesCount = statusesCount;
		this.lang = lang;
		this.location = location;
		this.name = name;
		this.isProtected = isProtected;
		this.timeZone = timeZone;
		URL = uRL;
		this.utcOffset = utcOffset;
	}
	public long getId() {
		return id;
	}
	public String getDescription() {
		return description;
	}
	public int getFavoritesCount() {
		return favoritesCount;
	}
	public String getScreenName() {
		return screenName;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public int getFollowersCount() {
		return followersCount;
	}
	public int getFriendsCount() {
		return friendsCount;
	}
	public boolean isGeoEnabled() {
		return isGeoEnabled;
	}
	public int getListedCount() {
		return listedCount;
	}
	public int getStatusesCount() {
		return statusesCount;
	}
	public String getLang() {
		return lang;
	}
	public String getLocation() {
		return location;
	}
	public String getName() {
		return name;
	}
	public boolean isProtected() {
		return isProtected;
	}
	public String getTimeZone() {
		return timeZone;
	}
	public String getURL() {
		return URL;
	}
	public int getUtcOffset() {
		return utcOffset;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", description=" + description
				+ ", favoriteCount=" + favoritesCount + ", screenName="
				+ screenName + ", createdAt=" + createdAt + ", followersCount="
				+ followersCount + ", friendsCount=" + friendsCount
				+ ", isGeoEnabled=" + isGeoEnabled + ", listedCount="
				+ listedCount + ", statusesCount=" + statusesCount + ", lang="
				+ lang + ", location=" + location + ", name=" + name
				+ ", isProtected=" + isProtected + ", tiemZone=" + timeZone
				+ ", URL=" + URL + ", utcOffset=" + utcOffset + "]";
	}
	
	
}
