package com.cteam.houston;

import com.cteam.houston.input.GamepadHandler;
import com.cteam.houston.input.GamepadListener;
import com.cteam.houston.input.NativeGamepad;
import com.cteam.houston.network.Command;
import com.cteam.houston.network.ConnectionListener;
import com.cteam.houston.network.Packet;
import com.cteam.houston.network.NetworkManager;
import com.cteam.houston.ui.KeyboardController;
import com.cteam.houston.ui.MainFrame;

public class Houston {

	private static MainFrame main;
	private static byte currentSpeed = 0;
	private static byte currentDirection = 0;
	private static byte currentDildoWheelSpeed = 0;
	private static byte currentDildoSpeed = 0;
	private static byte currentDildoPosition = 0;
	
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
		currentDildoSpeed = 0;
		currentDildoWheelSpeed = 0;
		
		sendCommand(Command.SPEED);
		sendCommand(Command.DIRECTION);
		sendCommand(Command.DILDO_VERTICAL_SPEED);
		sendCommand(Command.DILDO_WHEEL_SPEED);
	}
	
	public static void setSpeed(byte speed) {
		currentSpeed = speed;
		sendCommand(Command.SPEED);
	}
	
	public static void setDirection(byte direction) {
		currentDirection = direction;
		sendCommand(Command.DIRECTION);
	}
	
	public static void increaseDildoWheelSpeed() {
		currentDildoWheelSpeed += 10;
		if (currentDildoWheelSpeed > 63) currentDildoWheelSpeed = 63;
		sendCommand(Command.DILDO_WHEEL_SPEED);
	}
	
	public static void decreaseDildoWheelSpeed() {
		currentDildoWheelSpeed -= 10;
		if (currentDildoWheelSpeed < -64) currentDildoWheelSpeed = -64;
		sendCommand(Command.DILDO_WHEEL_SPEED);
	}
	
	public static void increaseDildoVerticalPosition() {
		currentDildoPosition += 1;
		if (currentDildoPosition > 16) currentDildoPosition = 16;
		sendCommand(Command.DILDO_VERTICAL_POSITION);
	}
	
	public static void decreaseDildoVerticalPosition() {
		currentDildoPosition -= 1;
		if (currentDildoPosition < 0) currentDildoPosition = 0;
		sendCommand(Command.DILDO_VERTICAL_POSITION);
	}
	
	public static void increaseDildoVerticalSpeed() {
		currentDildoSpeed += 10;
		if (currentDildoSpeed > 63) currentDildoSpeed = 63;
		sendCommand(Command.DILDO_VERTICAL_SPEED);
	}
	
	public static void decreaseDildoVerticalSpeed() {
		currentDildoSpeed -= 10;
		if (currentDildoSpeed < -64) currentDildoSpeed = -64;
		sendCommand(Command.DILDO_VERTICAL_SPEED);
	}
	
	public static void setDildoVerticalPosition(byte position) {
		currentDildoPosition = position;
		sendCommand(Command.DILDO_VERTICAL_POSITION);
	}
	
	public static void setDildoVerticalSpeed(byte speed) {
		currentDildoSpeed = speed;
		sendCommand(Command.DILDO_VERTICAL_SPEED);
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
			case DILDO_WHEEL_SPEED:
				value = currentDildoWheelSpeed;
				break;
			case DILDO_VERTICAL_POSITION:
				value = currentDildoPosition;
				break;
			case DILDO_VERTICAL_SPEED:
				value = currentDildoSpeed;
				break;
			default:
				throw new IllegalArgumentException("Invalid command given!");
		}
		System.out.println("Houston, sending command: " + command.name() + ", " + value);
		Packet packet = new Packet(command, value);
		NetworkManager.instance().sendPacket(packet);
		main.updateValues(command, value);
	}
	
	public static void connect(String host) {
		NetworkManager.instance().setUp(host);
	}
	
	public static void disconnect() {
		NetworkManager.instance().tearDown();
	}
	
	public static void main(String[] args) {
		main = MainFrame.createFrame();
		main.addKeyListener(new KeyboardController());
		
		NetworkManager.instance().setConnectionListener(new ConnectionListener() {
			@Override
			public void onDisconnect() {
				main.setConnectionStatus(false);
			}
			
			@Override
			public void onConnect() {
				main.setConnectionStatus(true);
			}
		});
		
		GamepadListener.getInstance().addDeviceListener(new GamepadHandler());
		NativeGamepad.addListener(GamepadListener.getInstance());
		NativeGamepad.start();
		
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("Cleaning up...");
				NetworkManager.instance().tearDown();
			}
		}));
	}
	
}
