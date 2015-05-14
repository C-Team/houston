package com.cteam.houston.input;

public interface DeviceListener {
	public void handleButton(Device device, int buttonId, boolean pressed);
	public void handleAxis(Device device, int axisId, float newValue, float lastValue);
	public void handleDeviceAdded(Device device);
	public void handleDeviceRemoved(Device device);
}
