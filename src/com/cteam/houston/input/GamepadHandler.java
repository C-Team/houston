package com.cteam.houston.input;

public class GamepadHandler implements DeviceListener {

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
					axisId + " New Value: " + newValue + " Last Value: " + lastValue);;
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
