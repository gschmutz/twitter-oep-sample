package com.trivadis.oep.twitter.adapter;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import com.bea.wlevs.ede.api.Adapter;
import com.bea.wlevs.ede.api.EventRejectedException;
import com.bea.wlevs.ede.api.RunnableBean;
import com.bea.wlevs.ede.api.StreamSink;

public class TwitterOutboundAdapter implements RunnableBean, Adapter, StreamSink {
	private Twitter twitter;
	private TwitterMessageFormatter twitterMessageFormatter;
	
	private String consumerKey;
	private String consumerSecret;
	private String accessToken;
	private String accessTokenSecret;
	
	@Override
	public void run() {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
			.setOAuthConsumerKey(consumerKey)
			.setOAuthConsumerSecret(consumerSecret)
			.setOAuthAccessToken(accessToken)
			.setOAuthAccessTokenSecret(accessTokenSecret);
		TwitterFactory tf = new TwitterFactory(cb.build());
		twitter = tf.getInstance();
	}

	@Override
	public void suspend() throws Exception {
	}
	
	@Override
	public void onInsertEvent(Object event) throws EventRejectedException {
		String message = twitterMessageFormatter.formatTweet(event);
		if (message != null) {
			try {
				twitter.updateStatus(message);
			} catch (TwitterException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public void setTwitterMessageFormatter(
			TwitterMessageFormatter twitterMessageFormatter) {
		this.twitterMessageFormatter = twitterMessageFormatter;
	}

	public void setConsumerKey(String consumerKey) {
		this.consumerKey = consumerKey;
	}

	public void setConsumerSecret(String consumerSecret) {
		this.consumerSecret = consumerSecret;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public void setAccessTokenSecret(String accessTokenSecret) {
		this.accessTokenSecret = accessTokenSecret;
	}

}
