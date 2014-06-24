package com.trivadis.oep.twitter.event;

import java.util.List;

public class Place {
	private String url;
	private String streetAddress;
	private String placeType;
	private String name;
	private String fullName;
	private String country;
	private String countryCode;
	private String boundingBoxType;
	
	private BoundingBox boundingBox;

	public Place(String url, String streetAddress, String placeType,
			String name, String fullName, String country, String countryCode,
			String boundingBoxType, BoundingBox boundingBox) {
		super();
		this.url = url;
		this.streetAddress = streetAddress;
		this.placeType = placeType;
		this.name = name;
		this.fullName = fullName;
		this.country = country;
		this.countryCode = countryCode;
		this.boundingBoxType = boundingBoxType;
		this.boundingBox = boundingBox;
	}

	public String getUrl() {
		return url;
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public String getPlaceType() {
		return placeType;
	}

	public String getName() {
		return name;
	}

	public String getFullName() {
		return fullName;
	}

	public String getCountry() {
		return country;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public String getBoundingBoxType() {
		return boundingBoxType;
	}

	public BoundingBox getBoundingBox() {
		return boundingBox;
	}

	@Override
	public String toString() {
		return "Place [url=" + url + ", streetAddress=" + streetAddress
				+ ", placeType=" + placeType + ", name=" + name + ", fullName="
				+ fullName + ", country=" + country + ", countryCode="
				+ countryCode + ", boundingBoxType=" + boundingBoxType
				+ ", boundingBox=" + boundingBox + "]";
	}
	
	
}
