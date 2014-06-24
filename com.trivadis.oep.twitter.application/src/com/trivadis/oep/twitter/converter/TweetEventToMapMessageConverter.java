package com.trivadis.oep.twitter.converter;

import java.util.ArrayList;
import java.util.List;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;

import com.bea.wlevs.adapters.jms.api.MessageConverterException;
import com.bea.wlevs.adapters.jms.api.OutboundMessageConverter;
import com.trivadis.oep.twitter.event.TweetEvent;
import com.trivadis.oep.twitter.event.TwitterCountEvent;
import com.trivadis.oep.twitter.util.DateUtil;


public class TweetEventToMapMessageConverter implements OutboundMessageConverter {

	@Override
	public List<Message> convert(Session session, Object inputEvent)
			throws MessageConverterException, JMSException {
		
		TweetEvent event = (TweetEvent) inputEvent;
        MapMessage message = session.createMapMessage();
        message.setString("sensorId", event.getSensorId().toString());
        message.setLong("id", event.getId());
        message.setString("text", event.getText());
        message.setString("createdAt", DateUtil.getDateAsString(event.getCreatedAt()));
        if (event.getGeoLocation() != null) {
        	message.setDouble("latitude", event.getGeoLocation().getLatitude());
        	message.setDouble("longitude", event.getGeoLocation().getLongitude());
        }
        if (event.getUser() != null) {
        	message.setLong("userid", event.getUser().getId());
        	message.setString("userScreenName", event.getUser().getScreenName());
        	message.setString("userURL", event.getUser().getURL());
        }
        	
        List<Message> messages = new ArrayList<Message>();
        messages.add(message);
        return messages;
	}

}
