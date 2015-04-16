package com.velicky.martin.rpigt.gpio;

import static com.velicky.martin.rpigt.gpio.GPIOChannel.*;

public enum LEDNote {

	C	(new GPIOChannel[]{GPIO7, GPIO8, GPIO23, GPIO25}),
	D	(new GPIOChannel[]{GPIO7, GPIO8, GPIO9, GPIO10, GPIO24}),
	E	(new GPIOChannel[]{GPIO7, GPIO8, GPIO10, GPIO23, GPIO25}),
	F	(new GPIOChannel[]{GPIO8, GPIO10, GPIO23, GPIO25}),
	G	(new GPIOChannel[]{GPIO7, GPIO8, GPIO9, GPIO23, GPIO25}),
	A	(new GPIOChannel[]{GPIO8, GPIO9, GPIO10, GPIO23, GPIO24, GPIO25}),
	B	(new GPIOChannel[]{GPIO7, GPIO8, GPIO9, GPIO10, GPIO23});
	
	private final GPIOChannel[] gpioChannels;
	
	private LEDNote(final GPIOChannel[] gpioChannels) {
		this.gpioChannels = gpioChannels;
	}
	
	public GPIOChannel[] getGPIOChannels() {
		return gpioChannels;
	}
}
