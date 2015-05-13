package com.cteam.houston;

import sun.util.resources.cldr.ts.CurrencyNames_ts;

import com.cteam.houston.network.Command;
import com.cteam.houston.network.Packet;
import com.cteam.houston.network.PacketManager;
import com.cteam.houston.ui.KeyboardController;
import com.cteam.houston.ui.MainFrame;

public class Houston {

	private static MainFrame main;
	private static Command currentOperation = Command.DRIVE_FORWARD;
	private static byte currentSpeed = 0;
	
	public static void speedUp() {
		currentSpeed += (byte) 10;
		if (currentSpeed < 0) currentSpeed = 127;
		sendCommand();
	}
	
	public static void slowDown() {
		currentSpeed -= (byte) 10;
		if (currentSpeed < 0) currentSpeed = 0;
		sendCommand();
	}
	
	public static void forward() {
		currentOperation = Command.DRIVE_FORWARD;
		sendCommand();
	}
	
	public static void backward() {
		currentOperation = Command.DRIVE_BACKWARD;
		sendCommand();
	}
	
	public static void turnRight() {
		currentOperation = Command.TURN_RIGHT;
		sendCommand();
	}
	
	public static void turnLeft() {
		currentOperation = Command.TURN_LEFT;
		sendCommand();
	}
	
	public static void stop() {
		currentOperation = Command.DRIVE_FORWARD;
		currentSpeed = 0;
		sendCommand();
		main.updateValues(currentOperation, -1);
	}
	private static void sendCommand() {
		System.out.println("Houston, sending command: " + currentOperation.name() + ", " + currentSpeed);
		Packet packet = new Packet(currentOperation, currentSpeed);
		PacketManager.instance().sendPacket(packet);
		main.updateValues(currentOperation, currentSpeed);
	}
	
	public static void main(String[] args) {
		PacketManager.instance().setUp();
		main = MainFrame.createFrame();
		main.addKeyListener(new KeyboardController());
	}
	
}
