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
	private static byte currentDiggerWheelSpeed = 0;
	private static byte currentDiggerSpeed = 0;
	private static byte currentDiggerPosition = 0;
	private static byte currentLargeConveyorSpeed = 0;
	private static boolean currentLargeConveyorState = false;
	private static byte currentSmallConveyorSpeed = 0;
	private static boolean currentSmallConveyorState = false;
	
	/////////////// MOVEMENT ///////////////////////////////////
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
		currentDiggerSpeed = 0;
		currentDiggerWheelSpeed = 0;
		
		sendCommand(Command.SPEED);
		sendCommand(Command.DIRECTION);
		sendCommand(Command.DIGGER_VERTICAL_SPEED);
		sendCommand(Command.DIGGER_WHEEL_SPEED);
	}
	
	public static void setSpeed(byte speed) {
		currentSpeed = speed;
		sendCommand(Command.SPEED);
	}
	
	public static void setDirection(byte direction) {
		currentDirection = direction;
		sendCommand(Command.DIRECTION);
	}
	/////////////// END MOVEMENT ///////////////////////////////////
	
	//////////////////// DIGGER ////////////////////////////////////////
	public static void increaseDiggerWheelSpeed() {
		currentDiggerWheelSpeed += 10;
		if (currentDiggerWheelSpeed > 63) currentDiggerWheelSpeed = 63;
		sendCommand(Command.DIGGER_WHEEL_SPEED);
	}
	
	public static void decreaseDiggerWheelSpeed() {
		currentDiggerWheelSpeed -= 10;
		if (currentDiggerWheelSpeed < -64) currentDiggerWheelSpeed = -64;
		sendCommand(Command.DIGGER_WHEEL_SPEED);
	}
	
	public static void increaseDiggerVerticalPosition() {
		currentDiggerPosition += 1;
		if (currentDiggerPosition > 16) currentDiggerPosition = 16;
		sendCommand(Command.DIGGER_VERTICAL_POSITION);
	}
	
	public static void decreaseDiggerVerticalPosition() {
		currentDiggerPosition -= 1;
		if (currentDiggerPosition < 0) currentDiggerPosition = 0;
		sendCommand(Command.DIGGER_VERTICAL_POSITION);
	}
	
	public static void increaseDiggerVerticalSpeed() {
		currentDiggerSpeed += 10;
		if (currentDiggerSpeed > 63) currentDiggerSpeed = 63;
		sendCommand(Command.DIGGER_VERTICAL_SPEED);
	}
	
	public static void decreaseDiggerVerticalSpeed() {
		currentDiggerSpeed -= 10;
		if (currentDiggerSpeed < -64) currentDiggerSpeed = -64;
		sendCommand(Command.DIGGER_VERTICAL_SPEED);
	}
	
	public static void setDiggerVerticalPosition(byte position) {
		currentDiggerPosition = position;
		sendCommand(Command.DIGGER_VERTICAL_POSITION);
	}
	
	public static void setDiggerVerticalSpeed(byte speed) {
		currentDiggerSpeed = speed;
		sendCommand(Command.DIGGER_VERTICAL_SPEED);
	}
	////////////////////// END DIGGER /////////////////////////////
	
	
	///////////////////// LARGE CONVEYOR //////////////////////////
	public static void increaseLargeConveyorSpeed() {
		currentLargeConveyorSpeed += 10;
		if (currentLargeConveyorSpeed > 63) currentLargeConveyorSpeed = 63;
		sendCommand(Command.LARGE_CONVEYOR_SPEED);
	}
	
	public static void decreaseLargeConveyorSpeed() {
		currentLargeConveyorSpeed -= 10;
		if (currentLargeConveyorSpeed < -64) currentLargeConveyorSpeed = -64;
		sendCommand(Command.LARGE_CONVEYOR_SPEED);
	}
	
	public static void toggleLargeConveyorState() {
		currentLargeConveyorState = !currentLargeConveyorState;
		sendCommand(Command.LARGE_CONVEYOR_STATE);
	}
	//////////////////// END LARGE CONVEYOR ///////////////////////
	
	//////////////////// SMALL CONVEYOR //////////////////////////
	public static void increaseSmallConveyorSpeed() {
		currentSmallConveyorSpeed += 10;
		if (currentSmallConveyorSpeed > 63) currentSmallConveyorSpeed = 63;
		sendCommand(Command.SMALL_CONVEYOR_SPEED);
	}
	
	public static void decreaseSmallConveyorSpeed() {
		currentSmallConveyorSpeed -= 10;
		if (currentSmallConveyorSpeed < -64) currentSmallConveyorSpeed = -64;
		sendCommand(Command.SMALL_CONVEYOR_SPEED);
	}
	
	public static void toggleSmallConveyorState() {
		currentSmallConveyorState = !currentSmallConveyorState;
		sendCommand(Command.SMALL_CONVEYOR_STATE);
	}
	//////////////////// END SMALL CONVEYOR //////////////////////////
	
	
	private static void sendCommand(Command command) {
		byte value;
		switch (command) {
			case SPEED:
				value = currentSpeed;
				break;
			case DIRECTION:
				value = currentDirection;
				break;
			case DIGGER_WHEEL_SPEED:
				value = currentDiggerWheelSpeed;
				break;
			case DIGGER_VERTICAL_POSITION:
				value = currentDiggerPosition;
				break;
			case DIGGER_VERTICAL_SPEED:
				value = currentDiggerSpeed;
				break;
			case LARGE_CONVEYOR_SPEED:
				value = currentLargeConveyorSpeed;
				break;
			case LARGE_CONVEYOR_STATE:
				value = currentLargeConveyorState ? (byte) 1 : (byte) 0;
				break;
			case SMALL_CONVEYOR_SPEED:
				value = currentSmallConveyorSpeed;
				break;
			case SMALL_CONVEYOR_STATE:
				value = currentSmallConveyorState ? (byte) 1 : (byte) 0;
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
