package com.trivadis.oep.twitter.adapter;

import java.util.ArrayList;
import java.util.List;


import com.trivadis.oep.twitter.event.BoundingBox;
import com.trivadis.oep.twitter.event.GeoLocation;
import com.trivadis.oep.twitter.event.HashtagEntity;
import com.trivadis.oep.twitter.event.MediaEntity;
import com.trivadis.oep.twitter.event.Place;
import com.trivadis.oep.twitter.event.SymbolEntity;
import com.trivadis.oep.twitter.event.UrlEntity;
import com.trivadis.oep.twitter.event.User;
import com.trivadis.oep.twitter.event.UserMentionEntity;
import com.twitter.hbc.core.endpoint.Location;
import com.twitter.hbc.core.endpoint.Location.Coordinate;

public class TwitterHelper {
	public static List<HashtagEntity> convert(twitter4j.HashtagEntity[] hashtagEntities) {
		List<HashtagEntity> hashtags = new ArrayList<HashtagEntity>();
		for (int i=0; i<hashtagEntities.length; i++) {
			hashtags.add(new HashtagEntity(hashtagEntities[i].getText()));
		}
		return hashtags;
	}

	public static List<UrlEntity> convert(twitter4j.URLEntity[] urlEntities) {
		List<UrlEntity> urls = new ArrayList<UrlEntity>();
		for (int i=0; i<urlEntities.length; i++) {
			urls.add(new UrlEntity(urlEntities[i].getDisplayURL()
									, urlEntities[i].getURL()
									, urlEntities[i].getExpandedURL()
									, urlEntities[i].getText()));
		}
		return urls;
	}
	
	public static List<MediaEntity> convert(twitter4j.MediaEntity[] mediaEntities) {
		List<MediaEntity> medias = new ArrayList<MediaEntity>();
		for (int i=0; i<mediaEntities.length; i++) {
			medias.add(new MediaEntity(mediaEntities[i].getDisplayURL()
									, mediaEntities[i].getURL()
									, mediaEntities[i].getExpandedURL()
									, mediaEntities[i].getText()
									, mediaEntities[i].getId()
									, mediaEntities[i].getMediaURL()
									, mediaEntities[i].getMediaURLHttps()
									, mediaEntities[i].getType()));
		}
		return medias;
	}
	
	public static List<UserMentionEntity> convert(twitter4j.UserMentionEntity[] userMentionEntities) {
		List<UserMentionEntity> userMentions = new ArrayList<UserMentionEntity>();
		
		for (int i=0; i<userMentionEntities.length; i++) {
			userMentions.add(new UserMentionEntity(userMentionEntities[i].getId()
												, userMentionEntities[i].getName()
												, userMentionEntities[i].getScreenName()));
		}
		return userMentions;
	}
	
	public static List<SymbolEntity> convert(twitter4j.SymbolEntity[] symbolEntities) {
		List<SymbolEntity> symbols = new ArrayList<SymbolEntity>();
		
		for (int i=0; i<symbolEntities.length; i++) {
			symbols.add(new SymbolEntity(symbolEntities[i].getText()));
		}
		return symbols;
	}

	public static Place convert(twitter4j.Place twitterPlace) {
		Place place = null;
		if (twitterPlace != null) {
			place = new Place(twitterPlace.getURL()
									, twitterPlace.getStreetAddress()
									, twitterPlace.getPlaceType()
									, twitterPlace.getName()
									, twitterPlace.getFullName()
									, twitterPlace.getCountry()
									, twitterPlace.getCountryCode()
									, twitterPlace.getBoundingBoxType()
									, TwitterHelper.convert(twitterPlace.getBoundingBoxCoordinates()));
		}
		return place;
	}
	
	public static BoundingBox convert(twitter4j.GeoLocation[][] twitterGeoLocations) {
		// TODO implement this method
		BoundingBox boundingBox = new BoundingBox();

		if (twitterGeoLocations != null) {
			for (int i=0; i<twitterGeoLocations.length; i++) {
				List<GeoLocation> geoLocations = new ArrayList<GeoLocation>(); 
				for (int j=0; j<twitterGeoLocations[i].length; j++) {
					GeoLocation geoLocation = convert(twitterGeoLocations[i][j]);
					geoLocations.add(geoLocation);
				}
				boundingBox.add(geoLocations);
			}
		}
		return boundingBox;
	}
	
	public static GeoLocation convert(twitter4j.GeoLocation twitterGeoLocation) {
		GeoLocation geoLocation = null;
		
		if (twitterGeoLocation != null) {
			geoLocation = new GeoLocation(twitterGeoLocation.getLatitude(), twitterGeoLocation.getLongitude());
		}
		return geoLocation;
	}
	
	public static User convert(twitter4j.User twitterUser) {
		User user = null;
		if (twitterUser != null) {
			user = new User(twitterUser.getId()
								, twitterUser.getDescription()
								, twitterUser.getFavouritesCount()
								, twitterUser.getScreenName()
								, twitterUser.getCreatedAt()
								, twitterUser.getFollowersCount()
								, twitterUser.getFriendsCount()
								, twitterUser.isGeoEnabled()
								, twitterUser.getListedCount()
								, twitterUser.getStatusesCount()
								, twitterUser.getLang()
								, twitterUser.getLocation()
								, twitterUser.getName()
								, twitterUser.isProtected()
								, twitterUser.getTimeZone()
								, twitterUser.getURL()
								, twitterUser.getUtcOffset());
		}
		return user;
	}
	
	public static Location convert(com.trivadis.oep.twitter.event.Location location) {
		Location twitterLocation = null;
		
		if (location != null) {
			twitterLocation = new Location(new Coordinate(location.getSouthwest().getLongitude(), location.getSouthwest().getLatitude())
										, new Coordinate(location.getNortheast().getLongitude(), location.getNortheast().getLatitude()));
		}
		return twitterLocation;
	}

	public static List<Location> convert(List<com.trivadis.oep.twitter.event.Location> locations) {
		List<Location> twitterLocations = new ArrayList<Location>();
		for (com.trivadis.oep.twitter.event.Location location : locations) {
			twitterLocations.add(TwitterHelper.convert(location));
		}
		return twitterLocations;
	}
}
