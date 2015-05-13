package com.cteam.houston.network;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class PacketManager {
	private static final String HOST = "TBD";
	private static final int PORT = 9001;

	private static PacketManager instance;
	
	private Socket socket;
	
	private boolean isSetUp;
	
	private PacketManager() {}
	
	public static PacketManager instance() {
		if (instance == null) {
			instance = new PacketManager();
		}
		return instance;
	}
	
	public boolean setUp() {
		if (isSetUp) return true;
		isSetUp = true;
		try {
			socket = new Socket(HOST, PORT);
		} catch (Exception except) {
			System.err.println("Houston, we have a problem: Failed to create socket.\n" 
					+ except.getMessage());
			isSetUp = false;
		}
		return isSetUp;
	}
	
	public void tearDown() {
		if (socket != null) {
			try {
				socket.close();
			} catch (IOException except) {
				// Nothing we can do
				except.printStackTrace();
			}
		}
		isSetUp = false;
	}
	
	public void sendPacket(Packet packet) {
		try {
			OutputStream stream = socket.getOutputStream();
			stream.write(packet.getPayload());
			stream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
