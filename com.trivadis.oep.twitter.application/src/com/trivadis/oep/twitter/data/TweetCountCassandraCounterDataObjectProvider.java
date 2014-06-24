package com.trivadis.oep.twitter.data;

import com.trivadis.oep.cassandra.data.CassandraCounterDataObject;
import com.trivadis.oep.cassandra.data.CassandraDataObject;
import com.trivadis.oep.cassandra.data.CassandraDataObjectProvider;
import com.trivadis.oep.twitter.event.TwitterCountEvent;

public class TweetCountCassandraCounterDataObjectProvider implements CassandraDataObjectProvider {
	private final static String COLUMN_FAMILY_NAME = "tweet_count";

	@Override
	public boolean supports(Object object) {
		return (object instanceof TwitterCountEvent);
	}

	@Override
	public CassandraDataObject getDataObject(Object object) {
		CassandraCounterDataObject dataObject = new CassandraCounterDataObject(COLUMN_FAMILY_NAME);
		
		if (object instanceof TwitterCountEvent) {
			TwitterCountEvent event = (TwitterCountEvent) object;
			dataObject.addColumn("sensor_id", event.getSensorId());
			dataObject.addColumn("bucket_id", event.getBucketId());
			dataObject.addColumn("category", event.getCategory());
			dataObject.addColumn("time_id", event.getTimeId());
			dataObject.addColumn("keyword", event.getKeyword());
			dataObject.addCounterColumn("count", event.getCounter());
		}
		
		return dataObject;
	}

}
