package com.trivadis.oep.twitter.converter;

import java.util.ArrayList;
import java.util.List;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;

import com.bea.wlevs.adapters.jms.api.MessageConverterException;
import com.bea.wlevs.adapters.jms.api.OutboundMessageConverter;
import com.trivadis.oep.twitter.event.TwitterCountEvent;
import com.trivadis.oep.twitter.util.DateUtil;


public class TwitterCountEventToMapMessageConverter implements OutboundMessageConverter {

	@Override
	public List<Message> convert(Session session, Object inputEvent)
			throws MessageConverterException, JMSException {
		
		TwitterCountEvent event = (TwitterCountEvent) inputEvent;
        MapMessage message = session.createMapMessage();
        message.setStringProperty("granularity", event.getBucketId().substring(0, event.getBucketId().indexOf("-")) );
        message.setString("granularity", event.getBucketId().substring(0, event.getBucketId().indexOf("-")) );
        message.setString("sensorId", event.getSensorId().toString());
        message.setString("category", event.getCategory());
        message.setString("keyword", event.getKeyword());
        message.setString("bucketId", event.getBucketId());
        message.setString("timeId", DateUtil.getDateAsString(event.getTimeId()));
        System.out.println(DateUtil.getDateAsString(event.getTimeId()));
        message.setLong("counter", event.getCounter());
        	
        List<Message> messages = new ArrayList<Message>();
        messages.add(message);
        return messages;
	}

}
