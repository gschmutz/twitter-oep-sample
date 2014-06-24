package com.trivadis.oep.cassandra.data;

import java.util.HashMap;
import java.util.Map;

public class CassandraDataObject {
	private String columnFamilyName;
	private Map<String, Object> columns = new HashMap<String, Object>();

	public CassandraDataObject(String columnFamilyName) {
		this.columnFamilyName = columnFamilyName;
	}
	
	public String getColumnFamilyName() {
		return columnFamilyName;
	}

	public Map<String, Object> getColumns() {
		return columns;
	}

	public void addColumn(String columnName, Object value) {
		this.columns.put(columnName, value);
	}
	
	public void removeColumn(String columnName) {
		this.columns.remove(columnName);
	}

	public Object getColumnValue(String columnName) {
		return this.columns.get(columnName);
	}
	
	
}
