package com.trivadis.oep.twitter.event;

public class SymbolEntity {
	private String text;

	public SymbolEntity(String text) {
		super();
		this.text = text;
	}

	public String getText() {
		return text;
	}

	@Override
	public String toString() {
		return "SymbolEntity [text=" + text + "]";
	}

}
