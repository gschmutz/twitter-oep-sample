package com.trivadis.oep.twitter.event;


public class Location {
	private final GeoLocation southwest, northeast;

	public Location(GeoLocation southwest, GeoLocation northeast) {
		super();
		this.southwest = southwest;
		this.northeast = northeast;
	}

	public GeoLocation getSouthwest() {
		return southwest;
	}

	public GeoLocation getNortheast() {
		return northeast;
	}

	@Override
	public String toString() {
		return "Location [southwest=" + southwest + ", northeast=" + northeast
				+ "]";
	}
	

}
