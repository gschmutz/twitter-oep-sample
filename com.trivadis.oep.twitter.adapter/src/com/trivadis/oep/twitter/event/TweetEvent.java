package com.trivadis.oep.twitter.event;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class TweetEvent implements Serializable {

	private UUID sensorId;
	private Long id;
	private Date createdAt;
	private long currentUserRetweetId;
	private String inReplyToScreenName;
	private long inReplyToStatusId;
	private long inReplyToUserId;
	private long retweetCount;
	private String source;
	private String text;
	private int favoriteCount;
	private boolean isFavorited;
	private boolean isPossiblySensitive;
	private boolean isRetweet;
	private boolean isRetweetByMe;
	private boolean isTruncated;
	private GeoLocation geoLocation;
	
	private List<HashtagEntity> hashtagsEntities;
	private List<MediaEntity> mediaEntities;
	private List<UrlEntity> urlEntities;
	private List<UserMentionEntity> userMentionEntities;
	private List<SymbolEntity> symbolEntities;
	private Place place;
	private User user;
	public TweetEvent(UUID sensorId, Long id, Date createdAt, long currentUserRetweetId,
			String inReplyToScreenName, long inReplyToStatusId,
			long inReplyToUserId, long retweetCount,
			String source, String text, int favoriteCount, boolean isFavorited,
			boolean isPossiblySensitive, boolean isRetweet,
			boolean isRetweetByMe, boolean isTruncated,
			GeoLocation geoLocation, List<HashtagEntity> hashtagsEntities,
			List<MediaEntity> mediaEntities, List<UrlEntity> urlEntities,
			List<UserMentionEntity> userMentionEntities,
			List<SymbolEntity> symbolEntities, Place place, User user) {
		super();
		this.sensorId = sensorId;
		this.id = id;
		this.createdAt = createdAt;
		this.currentUserRetweetId = currentUserRetweetId;
		this.inReplyToScreenName = inReplyToScreenName;
		this.inReplyToStatusId = inReplyToStatusId;
		this.inReplyToUserId = inReplyToUserId;
		this.retweetCount = retweetCount;
		this.source = source;
		this.text = text;
		this.favoriteCount = favoriteCount;
		this.isFavorited = isFavorited;
		this.isPossiblySensitive = isPossiblySensitive;
		this.isRetweet = isRetweet;
		this.isRetweetByMe = isRetweetByMe;
		this.isTruncated = isTruncated;
		this.geoLocation = geoLocation;
		this.hashtagsEntities = hashtagsEntities;
		this.mediaEntities = mediaEntities;
		this.urlEntities = urlEntities;
		this.userMentionEntities = userMentionEntities;
		this.symbolEntities = symbolEntities;
		this.place = place;
		this.user = user;
	}
	public UUID getSensorId() {
		return sensorId;
	}
	public Long getId() {
		return id;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public long getCurrentUserRetweetId() {
		return currentUserRetweetId;
	}
	public String getInReplyToScreenName() {
		return inReplyToScreenName;
	}
	public long getInReplyToStatusId() {
		return inReplyToStatusId;
	}
	public long getInReplyToUserId() {
		return inReplyToUserId;
	}
	public long getRetweetCount() {
		return retweetCount;
	}
	public String getSource() {
		return source;
	}
	public String getText() {
		return text;
	}
	public int getFavoriteCount() {
		return favoriteCount;
	}
	public boolean isFavorited() {
		return isFavorited;
	}
	public boolean isPossiblySensitive() {
		return isPossiblySensitive;
	}
	public boolean isRetweet() {
		return isRetweet;
	}
	public boolean isRetweetByMe() {
		return isRetweetByMe;
	}
	public boolean isTruncated() {
		return isTruncated;
	}
	public GeoLocation getGeoLocation() {
		return geoLocation;
	}
	public List<HashtagEntity> getHashtagsEntities() {
		return hashtagsEntities;
	}
	public List<MediaEntity> getMediaEntities() {
		return mediaEntities;
	}
	public List<UrlEntity> getUrlEntities() {
		return urlEntities;
	}
	public List<UserMentionEntity> getUserMentionEntities() {
		return userMentionEntities;
	}
	public List<SymbolEntity> getSymbolEntities() {
		return symbolEntities;
	}
	public Place getPlace() {
		return place;
	}
	public User getUser() {
		return user;
	}
	
	@Override
	public String toString() {
		return "TweetEvent [sensorId=" + sensorId + ", id=" + id
				+ ", createdAt=" + createdAt + ", currentUserRetweetId="
				+ currentUserRetweetId + ", inReplyToScreenName="
				+ inReplyToScreenName + ", inReplyToStatusId="
				+ inReplyToStatusId + ", inReplyToUserId=" + inReplyToUserId
				+ ", retweetCount=" + retweetCount + ", source=" + source
				+ ", text=" + text + ", favoriteCount=" + favoriteCount
				+ ", isFavorited=" + isFavorited + ", isPossiblySensitive="
				+ isPossiblySensitive + ", isRetweet=" + isRetweet
				+ ", isRetweetByMe=" + isRetweetByMe + ", isTruncated="
				+ isTruncated + ", geoLocation=" + geoLocation
				+ ", hashtagsEntities=" + hashtagsEntities + ", mediaEntities="
				+ mediaEntities + ", urlEntities=" + urlEntities
				+ ", userMentionEntities=" + userMentionEntities
				+ ", symbolEntities=" + symbolEntities + ", place=" + place
				+ ", user=" + user + "]";
	}



}
