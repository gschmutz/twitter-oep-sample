package com.trivadis.oep.cassandra.data;


public interface CassandraDataObjectProvider {
	
	public boolean supports(Object object);
	public CassandraDataObject getDataObject(Object object);

}
