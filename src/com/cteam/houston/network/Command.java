package com.cteam.houston.network;

public enum Command {
	DRIVE_FORWARD(0), DRIVE_BACKWARD(1), TURN_LEFT(2), TURN_RIGHT(3);
	
	public final byte value;
	
	private Command(int byteVal) {
		this((byte) byteVal);
	}
	
	private Command(byte value) {
		this.value = value;
	}
}
