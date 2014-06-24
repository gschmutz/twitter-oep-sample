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
import com.datastax.driver.core.querybuilder.Insert;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.trivadis.oep.cassandra.data.CassandraDataObject;
import com.trivadis.oep.cassandra.data.CassandraDataObjectProvider;
import com.trivadis.oep.cassandra.util.CassandraUtil;

public class CassandraInsertAdapter implements Adapter, RunnableBean, StreamSink {
	static final Log logger = LogFactory.getLog("CassandraAdapter");

    private static final int SLEEP_MILLIS = 300;
	private Cluster cluster;
	private Session session;
	
	private String[] cassandraHosts;
	private int cassandraPort;
	private String keyspaceName;
	
	private CassandraDataObjectProvider cassandraDataProvider;
	
    public CassandraInsertAdapter() {
    	super();
    }
    
	public void setCassandraDataProvider(CassandraDataObjectProvider cassandraDataProvider) {
		this.cassandraDataProvider = cassandraDataProvider;
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

	@Override
	public void run() {
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
		
		CassandraDataObject dataObject = null;

		if (cassandraDataProvider.supports(event)) {
			dataObject = cassandraDataProvider.getDataObject(event);
		} else {
			throw new RuntimeException("Object " + event.getClass().toString() + " has no CassandraDataObjectProvider");
		}
			
		String[] columnNames = dataObject.getColumns().keySet().toArray(new String[dataObject.getColumns().keySet().size()]);
		Object[] columnValues = CassandraUtil.createBindMarkers(columnNames.length);
		Insert insertIntoTable = QueryBuilder.insertInto(dataObject.getColumnFamilyName()).values(columnNames, columnValues);	
		statement = session.prepare(insertIntoTable);			

		bindVariables.clear();
		for (Object obj : dataObject.getColumns().values()) {
			bindVariables.add(obj);
		}
		execute(statement, bindVariables);			
		
	}

	private ResultSet execute(PreparedStatement statement, List<Object> bindVariables) {
		logger.debug(statement.getQueryString() + " -> " + bindVariables.toString());
		BoundStatement boundStatement = new BoundStatement(statement);
		ResultSet result = session.execute(boundStatement.bind(bindVariables.toArray()));
		
		return result;
	}
}
