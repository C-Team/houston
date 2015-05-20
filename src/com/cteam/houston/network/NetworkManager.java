package com.cteam.houston.network;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;

import javax.swing.SwingUtilities;

public class NetworkManager {
	private static final int PORT = 9001;
	private static final int RETRY_RATE = 250; // ms
	private static final int CONNECT_TIMEOUT = 1000; // ms
	private static final int MAX_QUEUE_SIZE = 100;
	
	private static NetworkManager instance;
	
	private ConnectionListener listener;
	private Socket socket;
	private LinkedBlockingQueue<Packet> packetQueue;
	private Thread networkThread;
	
	private boolean shouldRun;
	private boolean isConnected;
	
	private NetworkManager() {
		packetQueue = new LinkedBlockingQueue<>(MAX_QUEUE_SIZE);
	}
	
	public static NetworkManager instance() {
		if (instance == null) {
			instance = new NetworkManager();
		}
		return instance;
	}
	
	public void setConnectionListener(ConnectionListener listener) {
		this.listener = listener;
	}
	
	public boolean isConnected() {
		return isConnected;
	}
	
	public void setUp(final String host) {
		shouldRun = true;
		networkThread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// This will block until a connection is established or we are told to bail.
				if (!establishConnection(host)) return; 
				while (shouldRun) {
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
							if (!establishConnection(host)) return;
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}, "Network Thread"); 
		networkThread.start();
	}
	
	public void tearDown() {
		shouldRun = false;
		if (networkThread != null) {
			networkThread.interrupt();
		}
		if (socket != null) {
			try {
				if (socket.isConnected()) {
					socket.shutdownOutput();
					socket.getInputStream().read();
				}
				socket.close();
				socket = null;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (listener != null) {
			listener.onDisconnect();
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
	
	private boolean establishConnection(String host) {
		InetSocketAddress address = new InetSocketAddress(host, PORT);
		
		while (socket == null && shouldRun) {
			try {
				socket = new Socket();
				socket.connect(address, CONNECT_TIMEOUT);
				socket.setKeepAlive(true);
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
			} catch (Exception except) {
				socket = null;
				
				System.err.println("Houston, we have a problem: Failed to connect socket. Retrying...");
				
				try {
					Thread.sleep(RETRY_RATE);
				} catch (InterruptedException e) {
					// We are bailing out
					System.err.println("Houston, we are interrupted. Bailing out!");
					return false;
				}
			}
		}
		
		return false;
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
