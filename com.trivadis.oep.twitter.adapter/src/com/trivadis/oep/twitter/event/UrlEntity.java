package com.trivadis.oep.twitter.event;

public class UrlEntity {
	private String displayURL;
	private String URL;
	private String expandedURL;
	private String text;
	
	public UrlEntity(String displayURL, String URL, String expandedURL, String text) {
		super();
		this.displayURL = displayURL;
		this.URL = URL;
		this.expandedURL = expandedURL;
		this.text = text;
	}
	
	public String getDisplayURL() {
		return displayURL;
	}
	public String getURL() {
		return URL;
	}
	public String getExpandedURL() {
		return expandedURL;
	}
	
	public String getText() {
		return text;
	}

	@Override
	public String toString() {
		return "UrlEntity [displayURL=" + displayURL + ", URL=" + URL
				+ ", expandedURL=" + expandedURL + ", text=" + text + "]";
	}

	
}
