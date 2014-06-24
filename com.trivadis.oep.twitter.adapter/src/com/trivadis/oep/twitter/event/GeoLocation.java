package com.trivadis.oep.twitter.event;

public class GeoLocation {
	private Double latitude;
	private Double longitude;
	
	public GeoLocation(Double latitude, Double longitude) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	@Override
	public String toString() {
		return "GeoLocation [latitude=" + latitude + ", longitude=" + longitude
				+ "]";
	}
	

}
