package com.trivadis.oep.twitter.bean;

import com.bea.wlevs.ede.api.EventRejectedException;
import com.bea.wlevs.ede.api.EventTypeRepository;
import com.bea.wlevs.ede.api.StreamSender;
import com.bea.wlevs.ede.api.StreamSink;
import com.bea.wlevs.ede.api.StreamSource;
import com.bea.wlevs.util.Service;
import com.trivadis.oep.twitter.event.HashtagEntity;
import com.trivadis.oep.twitter.event.KeywordEvent;
import com.trivadis.oep.twitter.event.TweetEvent;
import com.trivadis.oep.twitter.util.GranularityDate;

public class AuthorExtractorBean implements StreamSink, StreamSource {

	StreamSender sender;
	EventTypeRepository eventTypeRepository;
	
	@Service
	public void setEventTypeRepository(EventTypeRepository etr) {
		this.eventTypeRepository = etr;
	}
	
	@Override
	public void setEventSender(StreamSender sender) {
		this.sender = sender;
	}

	@Override
	public void onInsertEvent(Object event) throws EventRejectedException {
		if (event instanceof TweetEvent) {
            TweetEvent tweetEvent = (TweetEvent) event;
            
            sender.sendInsertEvent(new KeywordEvent(tweetEvent.getSensorId()
            						, tweetEvent.getUser().getScreenName()
            						, new GranularityDate(tweetEvent.getCreatedAt())));
            
        }

	}

}
