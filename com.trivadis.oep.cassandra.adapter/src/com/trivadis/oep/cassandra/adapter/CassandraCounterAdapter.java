package com.trivadis.oep.cassandra.adapter;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bea.wlevs.ede.api.Adapter;
import com.bea.wlevs.ede.api.EventRejectedException;
import com.bea.wlevs.ede.api.RunnableBean;
import com.bea.wlevs.ede.api.StreamSink;
import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.policies.ConstantReconnectionPolicy;
import com.datastax.driver.core.policies.DowngradingConsistencyRetryPolicy;
import com.datastax.driver.core.policies.Policies;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Update;
import com.trivadis.oep.cassandra.data.CassandraCounterDataObject;
import com.trivadis.oep.cassandra.data.CassandraDataObjectProvider;

public class CassandraCounterAdapter implements Adapter, RunnableBean, StreamSink {
	static final Log logger = LogFactory.getLog("CassandraCounterAdapter");

    private static final int SLEEP_MILLIS = 300;
	private Cluster cluster;
	private Session session;
	
	private String[] cassandraHosts;
	private int cassandraPort;
	private String keyspaceName;

	private CassandraDataObjectProvider cassandraDataProvider;
	
    public CassandraCounterAdapter() {
    	super();
    }
    
	public String[] getCassandraHosts() {
		return cassandraHosts;
	}

	public void setCassandraHosts(String[] cassandraHosts) {
		this.cassandraHosts = cassandraHosts;
	}

	public int getCassandraPort() {
		return cassandraPort;
	}

	public void setCassandraPort(int cassandraPort) {
		this.cassandraPort = cassandraPort;
	}

	public String getKeyspaceName() {
		return keyspaceName;
	}

	public void setKeyspaceName(String keyspaceName) {
		this.keyspaceName = keyspaceName;
	}
	
	public void setCassandraDataProvider(
			CassandraDataObjectProvider cassandraDataProvider) {
		this.cassandraDataProvider = cassandraDataProvider;
	}

	@Override
	public void run() {
		System.out.println("Connecting to " + cassandraHosts + " on port " + cassandraPort);
	    cluster = Cluster.builder().addContactPoints(cassandraHosts)
					.withPort(cassandraPort)
					.withRetryPolicy(DowngradingConsistencyRetryPolicy.INSTANCE)
					.withReconnectionPolicy(new ConstantReconnectionPolicy(100L))
					.withLoadBalancingPolicy(Policies.defaultLoadBalancingPolicy())
					.build();

	    session = cluster.connect(keyspaceName);
	}

	@Override
	public void suspend() throws Exception {
		session.shutdown();
	}


	@Override
	public void onInsertEvent(Object event) throws EventRejectedException {
		PreparedStatement statement = null;
		List<Object> bindVariables = new ArrayList<Object>();

		CassandraCounterDataObject dataEvent = null;
		
		if (cassandraDataProvider.supports(event)) {
			dataEvent = (CassandraCounterDataObject)cassandraDataProvider.getDataObject(event);
		} else {
			throw new RuntimeException("Object " + event.getClass().toString() + " has no CassandraDataObjectProvider");
		}
		
		Update updateCounter = QueryBuilder.update(dataEvent.getColumnFamilyName());
		for (String conditionColumnName : dataEvent.getColumns().keySet()) {
			updateCounter.where().and(
					QueryBuilder.eq(conditionColumnName, QueryBuilder.bindMarker()));
		}
		for (String counterColumnName : dataEvent.getCounterColumns().keySet()) {
			updateCounter.with(QueryBuilder.incr(counterColumnName, QueryBuilder.bindMarker()));
			//update.using(QueryBuilder.ttl(GLOBAL_TTL));
		}
			
		statement = session.prepare(updateCounter);			
		bindVariables.clear();
		for (Object obj : dataEvent.getCounterColumns().values()) {
			bindVariables.add(obj);
		}
		for (Object obj : dataEvent.getColumns().values()) {
			bindVariables.add(obj);
		}
		//System.out.println("Excecuting CQL Statement: " + updateCounter.toString() + " {" + bindVariables.toString() + "}");
		execute(statement, bindVariables);			
	}

	private ResultSet execute(PreparedStatement statement, List<Object> bindVariables) {
		logger.debug(statement.getQueryString() + " -> " + bindVariables.toString());
		BoundStatement boundStatement = new BoundStatement(statement);
		ResultSet result = session.execute(boundStatement.bind(bindVariables.toArray()));
		
		return result;
	}
}
