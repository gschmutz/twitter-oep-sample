package com.trivadis.oep.twitter.event;

public class HashtagEntity {
	private String text;

	public HashtagEntity(String text) {
		super();
		this.text = text;
	}

	public String getText() {
		return text;
	}

	@Override
	public String toString() {
		return "HashtagEntity [text=" + text + "]";
	}

}
