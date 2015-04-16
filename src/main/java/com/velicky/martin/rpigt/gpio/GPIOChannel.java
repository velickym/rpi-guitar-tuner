package com.velicky.martin.rpigt.gpio;

public enum GPIOChannel {
	
	GPIO0(0), 
	GPIO1(1), 
	GPIO4(4), 
	GPIO7(7), 
	GPIO8(8), 
	GPIO9(9), 
	GPIO10(10), 
	GPIO11(11), 
	GPIO14(14), 
	GPIO15(15), 
	GPIO17(17), 
	GPIO18(18), 
	GPIO21(21), 
	GPIO22(22), 
	GPIO23(23), 
	GPIO24(24), 
	GPIO25(25);

	private final int channelId;

	private GPIOChannel(int number) {
		this.channelId = number;
	}

	public int getChannelId() {
		return channelId;
	}
}
