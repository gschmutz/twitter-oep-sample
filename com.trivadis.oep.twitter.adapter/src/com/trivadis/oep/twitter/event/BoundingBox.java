package com.trivadis.oep.twitter.event;

import java.util.ArrayList;
import java.util.List;

public class BoundingBox {
	
	private List<List<GeoLocation>> boundingBox;
	
	public BoundingBox() {
		boundingBox = new ArrayList<List<GeoLocation>>();
	}
	
	public void add(List<GeoLocation> geoLocations) {
		boundingBox.add(geoLocations);
	}

	public List<List<GeoLocation>> getBoundingBox() {
		return boundingBox;
	}

	@Override
	public String toString() {
		return "BoundingBox [boundingBox=" + boundingBox + "]";
	}
	
	
}
