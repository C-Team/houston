package com.cteam.houston.network;

public enum Command {
	SPEED(0), DIRECTION(1), DILDO_WHEEL_SPEED(2), DILDO_VERTICAL_SPEED(3), 
	DILDO_VERTICAL_POSITION(4);
	
	public final byte value;
	
	private Command(int byteVal) {
		this((byte) byteVal);
	}
	
	private Command(byte value) {
		this.value = value;
	}
}
