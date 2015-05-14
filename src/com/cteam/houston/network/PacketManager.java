package com.cteam.houston.network;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;

import javax.swing.SwingUtilities;

public class PacketManager {
	private static final String HOST = "192.168.7.2";
	private static final int PORT = 9001;
	private static final int RETRY_RATE = 250; // ms
	private static final int CONNECT_TIMEOUT = 1000; // ms
	private static final int MAX_QUEUE_SIZE = 100;
	
	private static PacketManager instance;
	
	private ConnectionListener listener;
	private Socket socket;
	private LinkedBlockingQueue<Packet> packetQueue;
	
	private boolean isConnected;
	
	private PacketManager() {
		packetQueue = new LinkedBlockingQueue<>(MAX_QUEUE_SIZE);
	}
	
	public static PacketManager instance() {
		if (instance == null) {
			instance = new PacketManager();
		}
		return instance;
	}
	
	public void setConnectionListener(ConnectionListener listener) {
		this.listener = listener;
	}
	
	public boolean isConnected() {
		return isConnected;
	}
	
	public void setUp() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// This will block until a connection is established or we are told to bail.
				if (!establishConnection()) return; 
				while (true) {
					try {
						Packet packet = packetQueue.take();
						while (!sendPacketInternal(packet)) {
							if (listener != null) {
								SwingUtilities.invokeLater(new Runnable() {
									@Override
									public void run() {
										isConnected = false;
										listener.onDisconnect();
									}
								});
							}
							// There is an issue with the connection, try to reestablish.
							try {
								socket.close();
							} catch (IOException e) {}
							socket = null;
							// This will block until a connection is established or we are told to bail.
							if (!establishConnection()) return;
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}, "Network Thread").start();
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
	}
	
	public void sendPacket(Packet packet) {
		if (socket != null && socket.isConnected()) {
			try {
				packetQueue.add(packet);
			} catch (IllegalStateException except) {
				// This occurs when the queue is full. Let's throw out the oldest packet and add this one.
				packetQueue.remove();
				packetQueue.add(packet);
			}
		}
	}
	
	private boolean establishConnection() {
		InetSocketAddress address = new InetSocketAddress(HOST, PORT);
		
		while (socket == null) {
			try {
				socket = new Socket();
				socket.connect(address, CONNECT_TIMEOUT);
				socket.setKeepAlive(true);
			} catch (Exception except) {
				socket = null;
				
				System.err.println("Houston, we have a problem: Failed to create socket. Retrying...\n");
				except.printStackTrace(System.err);
				
				try {
					Thread.sleep(RETRY_RATE);
				} catch (InterruptedException e) {
					// We are bailing out
					System.err.println("Houston, we are interrupted. Bailing out!");
					return false;
				}
			}
		}
		System.out.println("Successfuly connected to Apollo 13!");
		if (listener != null) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					isConnected = true;
					listener.onConnect();
				}
			});
		}
		return true;
	}
	
	private boolean sendPacketInternal(Packet packet) {
		try {
			OutputStream stream = socket.getOutputStream();
			stream.write(packet.getPayload());
			stream.flush();
			return true;
		} catch (IOException e) {
			return false;
		}
	}
}
