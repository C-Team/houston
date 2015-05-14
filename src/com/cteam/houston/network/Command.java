package com.cteam.houston.network;

public enum Command {
	SPEED(0), DIRECTION(1);
	
	public final byte value;
	
	private Command(int byteVal) {
		this((byte) byteVal);
	}
	
	private Command(byte value) {
		this.value = value;
	}
}
