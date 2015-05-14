package com.cteam.houston.input;

import com.cteam.houston.Houston;

public class GamepadHandler implements DeviceListener {
	
	private enum Axis {
		LEFT_STICK_X(0), LEFT_STICK_Y(1), 
		RIGHT_STICK_X(2), RIGHT_STICK_Y(3),
		LEFT_TRIGGER(4), RIGHT_TRIGGER(5);
		
		private int id;
		
		private Axis(int id) {
			this.id = id;
		}
	}
	
	private enum Button {
		DPAD_UP(5), DPAD_DOWN(6), DPAD_LEFT(7), DPAD_RIGHT(8),
		START(9), BACK(10),
		LEFT_STICK_THUMB(11), RIGHT_STICK_THUMB(12),
		LEFT_BUMPER(13), RIGHT_BUMPER(14),
		SPECIAL(15),
		A(16), B(17), X(18), Y(19);
		
		private final int id;
		
		private Button(int id) {
			this.id = id;
		}
	}
	
	@Override
	public void handleButton(Device device, int buttonId, boolean pressed) {
		// TODO Implement this
		System.out.println("Button Input: " + "Device: " + device + " Button: " + 
					buttonId + " State: " + pressed);
	}

	@Override
	public void handleAxis(Device device, int axisId, float newValue,
			float lastValue) {
		// TODO Implement this
		System.out.println("Axis Input: " + "Device: " + device + " Axis: " +
					axisId + " New Value: " + newValue + " Last Value: " + lastValue);
		Axis axis = Axis.values()[axisId];
		switch (axis) {
			case LEFT_STICK_Y:
				byte speed = (byte) (newValue * 63);
				Houston.setSpeed(speed);
				break;
			case RIGHT_STICK_X:
				byte direction = (byte) (newValue * 63);
				Houston.setDirection(direction);
				break;
			default:
				break;
		}
	}

	@Override
	public void handleDeviceAdded(Device device) {
		// TODO Implement this
		System.out.println("Device Added: " + device);
	}

	@Override
	public void handleDeviceRemoved(Device device) {
		// TODO Implement this
		System.out.println("Device Removed: " + device);
	}

}
