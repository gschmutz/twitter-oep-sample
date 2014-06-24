package com.trivadis.oep.twitter.data;

import java.util.ArrayList;
import java.util.List;

import com.trivadis.oep.cassandra.data.CassandraDataObject;
import com.trivadis.oep.cassandra.data.CassandraDataObjectProvider;
import com.trivadis.oep.twitter.event.HashtagEntity;
import com.trivadis.oep.twitter.event.TweetEvent;

public class TweetCassandraDataObjectProvider implements CassandraDataObjectProvider {
	private final static String COLUMN_FAMILY_NAME = "tweet";

	@Override
	public boolean supports(Object object) {
		return (object instanceof TweetEvent);
	}

	@Override
	public CassandraDataObject getDataObject(Object object) {
		CassandraDataObject dataObject = new CassandraDataObject(COLUMN_FAMILY_NAME);
		
		if (object instanceof TweetEvent) {
			TweetEvent event = (TweetEvent) object;
			dataObject.addColumn("tweet_id", event.getId());
			dataObject.addColumn("created_at", event.getCreatedAt());
			dataObject.addColumn("message", event.getText());
			dataObject.addColumn("hashtags", extractHashtags(event.getHashtagsEntities()));
			if (event.getGeoLocation() != null) {
				dataObject.addColumn("latitude", event.getGeoLocation().getLatitude());
				dataObject.addColumn("longitude", event.getGeoLocation().getLongitude());
			}
		}
		
		return dataObject;
	}

	private List<String> extractHashtags(List<HashtagEntity> hashtags) {
		List<String> results = new ArrayList<String>();
		for (HashtagEntity hashtag : hashtags) {
			results.add(hashtag.getText());
		}
		return results;
	}
	
}
