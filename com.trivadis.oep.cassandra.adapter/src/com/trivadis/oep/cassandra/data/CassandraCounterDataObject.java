package com.trivadis.oep.cassandra.data;

import java.util.HashMap;
import java.util.Map;

public class CassandraCounterDataObject extends CassandraDataObject {

	// the counter columns to use
	private Map<String, Object> counterColumns = new HashMap<String, Object>();

	public CassandraCounterDataObject(String columnFamilyName) {
		super(columnFamilyName);
	}

	public Map<String, Object> getCounterColumns() {
		return counterColumns;
	}
	
	public void addCounterColumn(String columnName, Object value) {
		this.counterColumns.put(columnName, value);
	}


}
