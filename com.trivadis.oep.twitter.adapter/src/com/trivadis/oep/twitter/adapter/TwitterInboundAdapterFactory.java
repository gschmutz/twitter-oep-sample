package com.trivadis.oep.twitter.adapter;

import com.bea.wlevs.ede.api.Adapter;
import com.bea.wlevs.ede.api.AdapterFactory;

public class TwitterInboundAdapterFactory implements AdapterFactory {

	@Override
	public Adapter create() throws IllegalArgumentException {
		return new TwitterInboundAdapter();
	}

}
