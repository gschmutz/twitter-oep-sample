package com.trivadis.oep.twitter.event;

public enum CategoryEnum {
	HASHTAG, WORD, MENTION, LANGUAGE;
	
	@Override public String toString() {
		String s = super.toString();
		return s.toLowerCase();
	}
}
