package com.velicky.martin.rpigt.gpio;

import java.util.Map;
import java.util.TreeMap;

public class LEDFrequency {

	public static final Map<Integer, String> LED_FREQUENCIES = new TreeMap<Integer, String>();
	
	static {
		LED_FREQUENCIES.put(-7, "0x01");
		LED_FREQUENCIES.put(-6, "0x02");
		LED_FREQUENCIES.put(-5, "0x04");
		LED_FREQUENCIES.put(-4, "0x08");
		LED_FREQUENCIES.put(-3, "0x10");
		LED_FREQUENCIES.put(-2, "0x20");
		LED_FREQUENCIES.put(-1, "0x40");
		LED_FREQUENCIES.put(0, "0x80");
		LED_FREQUENCIES.put(1, "0x80");
		LED_FREQUENCIES.put(2, "0x40");
		LED_FREQUENCIES.put(3, "0x20");
		LED_FREQUENCIES.put(4, "0x10");
		LED_FREQUENCIES.put(5, "0x08");
		LED_FREQUENCIES.put(6, "0x04");
		LED_FREQUENCIES.put(7, "0x02");
	}
}
