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
	private static byte speed = 0;
	private static byte direction = 0;
	private static byte diggerWheelSpeed = 0;
	private static byte diggerSpeed = 0;
	private static byte diggerPosition = 0;
	private static byte largeConveyorSpeed = 0;
	private static boolean largeConveyorState = false;
	private static byte smallConveyorSpeed = 0;
	private static boolean smallConveyorState = false;
	
	/////////////// MOVEMENT ///////////////////////////////////
	public static void speedUp() {
		speed += (byte) 10;
		if (speed > 63) speed = 63;
		sendCommand(Command.SPEED);
	}
	
	public static void slowDown() {
		speed -= (byte) 10;
		if (speed < -64) speed = -64;
		sendCommand(Command.SPEED);
	}
	
	public static void forward() {
		direction = 0;
		sendCommand(Command.DIRECTION);
	}
	
	public static void backward() {
		direction = 0;
		sendCommand(Command.DIRECTION);
	}
	
	public static void turnRight() {
		direction += 10;
		if (direction > 63) direction = 63;
		sendCommand(Command.DIRECTION);
	}
	
	public static void turnLeft() {
		direction -= 10;
		if (direction < -64) direction = -64;
		sendCommand(Command.DIRECTION);
	}
	
	public static void stop() {
		direction = 0;
		speed = 0;
		diggerSpeed = 0;
		diggerWheelSpeed = 0;
		largeConveyorState = false;
		smallConveyorState = false;
		
		sendCommand(Command.SPEED);
		sendCommand(Command.DIRECTION);
		sendCommand(Command.DIGGER_VERTICAL_SPEED);
		sendCommand(Command.DIGGER_WHEEL_SPEED);
		sendCommand(Command.LARGE_CONVEYOR_STATE);
		sendCommand(Command.SMALL_CONVEYOR_STATE);
	}
	
	public static void setSpeed(byte newSpeed) {
		speed = newSpeed;
		sendCommand(Command.SPEED);
	}
	
	public static void setDirection(byte newDirection) {
		direction = newDirection;
		sendCommand(Command.DIRECTION);
	}
	/////////////// END MOVEMENT ///////////////////////////////////
	
	//////////////////// DIGGER ////////////////////////////////////////
	public static void decreaseDiggerWheelSpeed() {
		diggerWheelSpeed += 10;
		if (diggerWheelSpeed > 63) diggerWheelSpeed = 63;
		sendCommand(Command.DIGGER_WHEEL_SPEED);
	}
	
	public static void increaseDiggerWheelSpeed() {
		diggerWheelSpeed -= 10;
		if (diggerWheelSpeed < -64) diggerWheelSpeed = -64;
		sendCommand(Command.DIGGER_WHEEL_SPEED);
	}
	
	public static void increaseDiggerVerticalPosition() {
		diggerPosition += 1;
		if (diggerPosition > 16) diggerPosition = 16;
		sendCommand(Command.DIGGER_VERTICAL_POSITION);
	}
	
	public static void decreaseDiggerVerticalPosition() {
		diggerPosition -= 1;
		if (diggerPosition < 0) diggerPosition = 0;
		sendCommand(Command.DIGGER_VERTICAL_POSITION);
	}
	
	public static void increaseDiggerVerticalSpeed() {
		diggerSpeed += 10;
		if (diggerSpeed > 63) diggerSpeed = 63;
		sendCommand(Command.DIGGER_VERTICAL_SPEED);
	}
	
	public static void decreaseDiggerVerticalSpeed() {
		diggerSpeed -= 10;
		if (diggerSpeed < -64) diggerSpeed = -64;
		sendCommand(Command.DIGGER_VERTICAL_SPEED);
	}
	
	public static void setDiggerVerticalPosition(byte position) {
		diggerPosition = position;
		sendCommand(Command.DIGGER_VERTICAL_POSITION);
	}
	
	public static void setDiggerVerticalSpeed(byte speed) {
		diggerSpeed = speed;
		sendCommand(Command.DIGGER_VERTICAL_SPEED);
	}
	////////////////////// END DIGGER /////////////////////////////
	
	
	///////////////////// LARGE CONVEYOR //////////////////////////
	public static void decreaseLargeConveyorSpeed() {
		largeConveyorSpeed += 10;
		if (largeConveyorSpeed > 63) largeConveyorSpeed = 63;
		sendCommand(Command.LARGE_CONVEYOR_SPEED);
	}
	
	public static void increaseLargeConveyorSpeed() {
		largeConveyorSpeed -= 10;
		if (largeConveyorSpeed < -64) largeConveyorSpeed = -64;
		sendCommand(Command.LARGE_CONVEYOR_SPEED);
	}
	
	public static void toggleLargeConveyorState() {
		largeConveyorState = !largeConveyorState;
		sendCommand(Command.LARGE_CONVEYOR_STATE);
	}
	//////////////////// END LARGE CONVEYOR ///////////////////////
	
	//////////////////// SMALL CONVEYOR //////////////////////////
	public static void decreaseSmallConveyorSpeed() {
		smallConveyorSpeed += 10;
		if (smallConveyorSpeed > 63) smallConveyorSpeed = 63;
		sendCommand(Command.SMALL_CONVEYOR_SPEED);
	}
	
	public static void increaseSmallConveyorSpeed() {
		smallConveyorSpeed -= 10;
		if (smallConveyorSpeed < -64) smallConveyorSpeed = -64;
		sendCommand(Command.SMALL_CONVEYOR_SPEED);
	}
	
	public static void toggleSmallConveyorState() {
		smallConveyorState = !smallConveyorState;
		sendCommand(Command.SMALL_CONVEYOR_STATE);
	}
	//////////////////// END SMALL CONVEYOR //////////////////////////
	
	
	private static void sendCommand(Command command) {
		byte value;
		switch (command) {
			case SPEED:
				value = speed;
				break;
			case DIRECTION:
				value = direction;
				break;
			case DIGGER_WHEEL_SPEED:
				value = diggerWheelSpeed;
				break;
			case DIGGER_VERTICAL_POSITION:
				value = diggerPosition;
				break;
			case DIGGER_VERTICAL_SPEED:
				value = diggerSpeed;
				break;
			case LARGE_CONVEYOR_SPEED:
				value = largeConveyorSpeed;
				break;
			case LARGE_CONVEYOR_STATE:
				value = largeConveyorState ? (byte) 1 : (byte) 0;
				break;
			case SMALL_CONVEYOR_SPEED:
				value = smallConveyorSpeed;
				break;
			case SMALL_CONVEYOR_STATE:
				value = smallConveyorState ? (byte) 1 : (byte) 0;
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
