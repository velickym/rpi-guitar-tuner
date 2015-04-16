package com.velicky.martin.rpigt;

public class Pair<A, B> {

	private final A key;
	private final B value;
	
	public Pair(A key, B value) {
		super();
		this.key = key;
		this.value = value;
	}

	public A getKey() {
		return key;
	}

	public B getValue() {
		return value;
	}

	@Override
	public String toString() {
		return "Pair [key=" + key + ", value=" + value + "]";
	}
	
}
