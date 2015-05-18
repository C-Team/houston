package com.cteam.houston.network;

public enum Command {
	SPEED(0), DIRECTION(1), DIGGER_WHEEL_SPEED(2), DIGGER_VERTICAL_SPEED(3), 
	DIGGER_VERTICAL_POSITION(4);
	
	public final byte value;
	
	private Command(int byteVal) {
		this((byte) byteVal);
	}
	
	private Command(byte value) {
		this.value = value;
	}
}
