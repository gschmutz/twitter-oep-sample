package com.trivadis.oep.twitter.event;

public class MediaEntity extends UrlEntity {
	private long id;
	private String mediaURL;
	private String mediaURLHttps;
	private String type;
	
	public MediaEntity(String displayURL, String URL, String expandedURL,
			String text, long id, String mediaURL, String mediaURLHttps, String type) {
		super(displayURL, URL, expandedURL, text);
		this.id = id;
		this.mediaURL = mediaURL;
		this.mediaURLHttps = mediaURLHttps;
		this.type = type;
	}

	public long getId() {
		return id;
	}

	public String getMediaURL() {
		return mediaURL;
	}

	public String getMediaURLHttps() {
		return mediaURLHttps;
	}

	public String getType() {
		return type;
	}

	@Override
	public String toString() {
		return "MediaEntity [id=" + id + ", mediaURL=" + mediaURL
				+ ", mediaURLHttps=" + mediaURLHttps + ", type=" + type
				+ ", getDisplayURL()=" + getDisplayURL() + ", getURL()="
				+ getURL() + ", getExpandedURL()=" + getExpandedURL()
				+ ", getText()=" + getText() + ", toString()="
				+ super.toString() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + "]";
	}

	
	
}
