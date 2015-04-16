package com.velicky.martin.rpigt.gpio;

public enum Direction {

	IN("in"), OUT("out");

	private Direction(String value) {
		this.value = value;
	}

	private final String value;

	public String getValue() {
		return value;
	}

}
