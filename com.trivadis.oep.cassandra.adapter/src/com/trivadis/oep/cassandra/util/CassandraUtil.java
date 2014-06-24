package com.trivadis.oep.cassandra.util;

import java.util.ArrayList;
import java.util.List;

import com.datastax.driver.core.querybuilder.BindMarker;
import com.datastax.driver.core.querybuilder.QueryBuilder;

public class CassandraUtil {
	/**
	 * Create an array of bind markers
	 * @param numberOf	the number of bind markers to create
	 * @return
	 */
	public static BindMarker[] createBindMarkers(long numberOf) {
		List<BindMarker> list = new ArrayList<BindMarker>(); 
		for (int i = 0; i < numberOf; i++) {
			list.add(QueryBuilder.bindMarker()); 
		}
		return list.toArray(new BindMarker[0]);
	}

	public static BindMarker[] createBindMarkers(List<String> names) {
		List<BindMarker> list = new ArrayList<BindMarker>(); 
		for (String name : names) {
			list.add(QueryBuilder.bindMarker(name)); 
		}
		return list.toArray(new BindMarker[0]);
	}
}
