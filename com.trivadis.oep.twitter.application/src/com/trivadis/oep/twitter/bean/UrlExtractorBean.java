package com.trivadis.oep.twitter.bean;

import com.bea.wlevs.ede.api.EventRejectedException;
import com.bea.wlevs.ede.api.StreamSender;
import com.bea.wlevs.ede.api.StreamSink;
import com.bea.wlevs.ede.api.StreamSource;
import com.trivadis.oep.twitter.event.HashtagEntity;
import com.trivadis.oep.twitter.event.KeywordEvent;
import com.trivadis.oep.twitter.event.TweetEvent;
import com.trivadis.oep.twitter.event.UrlEntity;
import com.trivadis.oep.twitter.util.GranularityDate;

public class UrlExtractorBean implements StreamSink, StreamSource {

	StreamSender sender;

	@Override
	public void setEventSender(StreamSender sender) {
		this.sender = sender;
	}

	@Override
	public void onInsertEvent(Object event) throws EventRejectedException {
		if (event instanceof TweetEvent) {
            TweetEvent tweetEvent = (TweetEvent) event;
            
            for (UrlEntity url : tweetEvent.getUrlEntities()) {

            	sender.sendInsertEvent(new KeywordEvent(tweetEvent.getSensorId()
            											, url.getExpandedURL()
            											, new GranularityDate(tweetEvent.getCreatedAt())));
            }
            
        }

	}

}
