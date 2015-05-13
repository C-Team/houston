package com.cteam.houston.network;

public class Packet {
	// 3 bytes
	// command - 1 byte
	// data - up to 2 bytes

	byte[] payload = new byte[3];

	public Packet(Command command, byte... data) {
		if (data.length > 2) {
			throw new IllegalArgumentException("Packet payload can only be 2 bytes long, "
					+ "but is " + data.length + " bytes!");
		}
		payload[0] = command.value;
		for (int i = 0; i < data.length; i++) {
			payload[i + 1] = data[i];
		}
	}

	public byte[] getPayload() {
		return payload;
	}
}
