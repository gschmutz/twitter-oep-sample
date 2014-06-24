package com.trivadis.oep.cassandra.adapter;

import com.bea.wlevs.ede.api.Adapter;
import com.bea.wlevs.ede.api.AdapterFactory;

public class CassandraCounterAdapterFactory implements AdapterFactory {

	@Override
	public Adapter create() throws IllegalArgumentException {
		return new CassandraCounterAdapter();
	}

}
