package com.trivadis.oep.twitter.adapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;

import com.bea.wlevs.ede.api.Adapter;
import com.bea.wlevs.ede.api.RunnableBean;
import com.bea.wlevs.ede.api.StreamSender;
import com.bea.wlevs.ede.api.StreamSource;
import com.trivadis.oep.twitter.event.Location;
import com.trivadis.oep.twitter.event.TweetEvent;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.StatsReporter.StatsTracker;
import com.twitter.hbc.core.endpoint.DefaultStreamingEndpoint;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.endpoint.StatusesSampleEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.BasicClient;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;
import com.twitter.hbc.twitter4j.v3.Twitter4jStatusClient;
import com.twitter.hbc.twitter4j.v3.handler.StatusStreamHandler;
import com.twitter.hbc.twitter4j.v3.message.DisconnectMessage;
import com.twitter.hbc.twitter4j.v3.message.StallWarningMessage;

public class TwitterInboundAdapter implements Adapter, RunnableBean, StreamSource {
	static final Log logger = LogFactory.getLog("TwitterAdapter");

    private static final int SLEEP_MILLIS = 300;
    
	private BlockingQueue<String> queue;
	private BasicClient client;
	private StatsTracker tracker;
	private int numberOfProcessingThreads = 5; 
	private int blockingQueueSize = 10000;

    private StreamSender eventSender;
    private boolean suspended;
    
    private String consumerKey;
    private String consumerSecret;
    private String token;
    private String secret;
    
    // a unique id of the sensor
    private String sensorId;
    
    // filter for given terms
    private String[] filterTerms;
    // filter for given languages
    private String[] filterLanguages;
    // filter for given userids authoring the tweets
    private Long[] filterUserIds;
    // filter for given location
    private List<Location> filterLocations;

    public TwitterInboundAdapter() {
    	super();
    }
    
    private boolean hasFilters() {
    	//return true;
    	return ((filterTerms != null && filterTerms.length > 0) ||
    			(filterLocations != null && filterLocations.size() > 0) ||
    			(filterUserIds != null && filterUserIds.length > 0) ||
    			(filterLanguages != null && filterLanguages.length > 0));
    }

    protected DefaultStreamingEndpoint createSampleEndpoint() {
    	return new StatusesSampleEndpoint();
    }
    
	protected DefaultStreamingEndpoint createEndpoint() {

		StatusesFilterEndpoint endpoint = new StatusesFilterEndpoint();
		
		// set the term filter if terms are specified
		if (filterTerms != null && filterTerms.length > 0) {
			System.out.println("track the terms" + Arrays.asList(filterTerms));
			endpoint.trackTerms(Arrays.asList(filterTerms));
		}
		
		// set the location filter if locations are specified
		if (filterLocations != null && filterLocations.size() > 0) {
			System.out.println("track the locations" + filterLocations);
			endpoint.locations(TwitterHelper.convert(filterLocations));
		}
		
		// set the user filter if users are specified
		if (filterUserIds != null && filterUserIds.length > 0) {
			System.out.println("track the userids" + Arrays.asList(filterUserIds));
			endpoint.followings(Arrays.asList(filterUserIds));
		}
		
		// set the languages filter if languages are specified
		if (filterLanguages != null && filterLanguages.length > 0) {
			System.out.println("track the languages" + Arrays.asList(filterLanguages));
			endpoint.languages(Arrays.asList(filterLanguages));
		}
		
		return endpoint;
	}
    
	@Override
	public void run() {
		suspended = false;

		// Create an appropriately sized blocking queue
		queue = new LinkedBlockingQueue<String>(blockingQueueSize);

		DefaultStreamingEndpoint endpoint = null;
		if (hasFilters()) {
			endpoint = createEndpoint();
		} else {
			endpoint = createSampleEndpoint();
		}

		endpoint.stallWarnings(true);

		Authentication auth = new OAuth1(consumerKey, consumerSecret, token, secret);

		// Create a new BasicClient. By default gzip is enabled.
		client = new ClientBuilder().name("sample")
				.hosts(Constants.STREAM_HOST).endpoint(endpoint)
				.authentication(auth)
				.processor(new StringDelimitedProcessor(queue)).build();
		System.out.println("client created ...");
		tracker = client.getStatsTracker();

		ExecutorService service = Executors.newFixedThreadPool(numberOfProcessingThreads);

		List<StatusListener> listeners = new ArrayList<StatusListener>();
		listeners.add(listener);
		Twitter4jStatusClient t4jClient = new Twitter4jStatusClient(client, queue, listeners, service);

		// Establish a connection
		t4jClient.connect();
		System.out.println("connection established ...");

		for (int threads = 0; threads < this.numberOfProcessingThreads; threads++) {
			// This must be called once per processing thread
			t4jClient.process();
			System.out.println("thread " + threads + " started ...");

		}		
       
		try {
			Thread.sleep(60000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void suspend() throws Exception {
		suspended = true;
		System.out.println("suspend called");
		System.out.println("getNumConnectionFailures: " + tracker.getNumConnectionFailures());
		client.stop();
	}

	@Override
	public void setEventSender(StreamSender sender) {
		eventSender = sender;
	}

    private synchronized boolean isSuspended() {
        return suspended;
    }

	public void setConsumerKey(String consumerKey) {
		this.consumerKey = consumerKey;
	}

	public void setConsumerSecret(String consumerSecret) {
		this.consumerSecret = consumerSecret;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}
	
	public void setNumberOfProcessingThreads(int numberOfProcessingThreads) {
		this.numberOfProcessingThreads = numberOfProcessingThreads;
	}
	
	public void setBlockingQueueSize(int blockingQueueSize) {
		this.blockingQueueSize = blockingQueueSize;
	}

	public void setSensorId(String sensorId) {
		this.sensorId = sensorId;
	}

	public void setFilterTerms(String[] filterTerms) {
		this.filterTerms = filterTerms;
	}

	public void setFilterLanguages(String[] filterLanguages) {
		this.filterLanguages = filterLanguages;
	}

	public void setFilterUserIds(Long[] filterUserIds) {
		this.filterUserIds = filterUserIds;
	}

	public void setFilterLocations(List<Location> filterLocations) {
		this.filterLocations = filterLocations;
	}


	private StatusListener listener = new StatusStreamHandler() {
		
		@Override
		public void onException(Exception ex) {
			logger.error(ex.getMessage());
			
		}
		
		@Override
		public void onTrackLimitationNotice(int trackLimit) {
			logger.error("trackLimit: " + trackLimit);
			
		}
		
		@Override
		public void onStatus(Status status) {
			logger.debug("onStatus: " + status.getId() + ":" + status.getText());
			try {
				TweetEvent event = TwitterStatusUpdateConverter.convert(UUID.fromString(sensorId), status);
				eventSender.sendInsertEvent(event);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		
		@Override
		public void onStallWarning(StallWarning stallWarn) {
			logger.warn(stallWarn.getMessage());
		}
		
		@Override
		public void onScrubGeo(long arg0, long arg1) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onDeletionNotice(StatusDeletionNotice statusDeletion) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onUnknownMessageType(String unknownMessageType) {
			logger.warn(unknownMessageType);
			
		}
		
		@Override
		public void onStallWarningMessage(StallWarningMessage stallWarningMessage) {
			logger.warn(stallWarningMessage.getMessage());
			
		}
		
		@Override
		public void onDisconnectMessage(DisconnectMessage disconnectMessage) {
			logger.error(disconnectMessage.getDisconnectReason());
			
		}
	};
}
