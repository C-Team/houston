package com.cteam.houston;

import com.cteam.houston.input.GamepadHandler;
import com.cteam.houston.input.GamepadListener;
import com.cteam.houston.input.NativeGamepad;
import com.cteam.houston.network.Command;
import com.cteam.houston.network.ConnectionListener;
import com.cteam.houston.network.Packet;
import com.cteam.houston.network.PacketManager;
import com.cteam.houston.ui.KeyboardController;
import com.cteam.houston.ui.MainFrame;

public class Houston {

	private static MainFrame main;
	private static byte currentSpeed = 0;
	private static byte currentDirection = 0;
	
	public static void speedUp() {
		currentSpeed += (byte) 10;
		if (currentSpeed > 63) currentSpeed = 63;
		sendCommand(Command.SPEED);
	}
	
	public static void slowDown() {
		currentSpeed -= (byte) 10;
		if (currentSpeed < -64) currentSpeed = -64;
		sendCommand(Command.SPEED);
	}
	
	public static void forward() {
		currentDirection = 0;
		sendCommand(Command.DIRECTION);
	}
	
	public static void backward() {
		currentDirection = 0;
		sendCommand(Command.DIRECTION);
	}
	
	public static void turnRight() {
		currentDirection += 10;
		if (currentDirection > 63) currentDirection = 63;
		sendCommand(Command.DIRECTION);
	}
	
	public static void turnLeft() {
		currentDirection -= 10;
		if (currentDirection < -64) currentDirection = -64;
		sendCommand(Command.DIRECTION);
	}
	
	public static void stop() {
		currentDirection = 0;
		currentSpeed = 0;
		sendCommand(Command.SPEED);
		sendCommand(Command.DIRECTION);
	}
	
	public static void setSpeed(byte speed) {
		currentSpeed = speed;
		sendCommand(Command.SPEED);
	}
	
	public static void setDirection(byte direction) {
		currentDirection = direction;
		sendCommand(Command.DIRECTION);
	}
	
	private static void sendCommand(Command command) {
		byte value;
		switch (command) {
			case SPEED:
				value = currentSpeed;
				break;
			case DIRECTION:
				value = currentDirection;
				break;
			default:
				throw new IllegalArgumentException("Invalid command given!");
		}
		System.out.println("Houston, sending command: " + command.name() + ", " + value);
		Packet packet = new Packet(command, value);
		PacketManager.instance().sendPacket(packet);
		main.updateValues(command, value);
	}
	
	public static void main(String[] args) {
		main = MainFrame.createFrame();
		main.addKeyListener(new KeyboardController());
		
		PacketManager.instance().setConnectionListener(new ConnectionListener() {
			@Override
			public void onDisconnect() {
				main.setConnectionStatus(false);
			}
			
			@Override
			public void onConnect() {
				main.setConnectionStatus(true);
			}
		});
		PacketManager.instance().setUp();
		
		GamepadListener.getInstance().addDeviceListener(new GamepadHandler());
		NativeGamepad.addListener(GamepadListener.getInstance());
		NativeGamepad.start();
		
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("Cleaning up...");
				PacketManager.instance().tearDown();
			}
		}));
	}
	
}
