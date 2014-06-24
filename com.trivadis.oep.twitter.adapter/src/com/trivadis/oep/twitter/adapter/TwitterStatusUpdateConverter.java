package com.trivadis.oep.twitter.adapter;

import java.util.UUID;

import twitter4j.Status;

import com.trivadis.oep.twitter.event.TweetEvent;

public class TwitterStatusUpdateConverter {

	
	public static TweetEvent convert(UUID sensorId, Status status) {
		TweetEvent tweet = new TweetEvent(sensorId
										, status.getId()
										, status.getCreatedAt()
										, status.getCurrentUserRetweetId()
										, status.getInReplyToScreenName()
										, status.getInReplyToStatusId()
										, status.getInReplyToUserId()
										, status.getRetweetCount()
										, status.getSource()
										, status.getText()
										, status.getFavoriteCount()
										, status.isFavorited()
										, status.isPossiblySensitive()
										, status.isRetweet()
										, status.isRetweetedByMe()
										, status.isTruncated()
										, TwitterHelper.convert(status.getGeoLocation())
										, TwitterHelper.convert(status.getHashtagEntities())
										, TwitterHelper.convert(status.getMediaEntities())
										, TwitterHelper.convert(status.getURLEntities())
										, TwitterHelper.convert(status.getUserMentionEntities())
										, TwitterHelper.convert(status.getSymbolEntities())
										, TwitterHelper.convert(status.getPlace())
										, TwitterHelper.convert(status.getUser()));
		return tweet;
	}
}
