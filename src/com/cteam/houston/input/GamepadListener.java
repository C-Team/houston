package com.cteam.houston.input;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Listens to controllers connected to this computer and gives any gamepad to the gamepad handler
 */
public class GamepadListener implements NativeGamepadListener {
	private HashMap<Integer, Device> devices;
	private List<DeviceListener> listeners;
	
	private static GamepadListener singleton;
	
	public static GamepadListener getInstance() {
		if (singleton == null) {
			singleton = new GamepadListener();
		}
		return singleton;
	}
	
	private GamepadListener() {
		devices = new HashMap<Integer, Device>();
		listeners = new LinkedList<DeviceListener>();
	}
	
	public int deviceCount() {
		return devices.size();
	}
	
	public void addDeviceListener(DeviceListener listener) {
		listeners.add(listener);
	}
	
	public List<DeviceListener> getListeners() {
		return Collections.unmodifiableList(listeners);
	}
	
	public void removeListener(DeviceListener listener) {
		listeners.remove(listener);
	}
	
	public void deviceAttached(int deviceId, int numButtons, int numAxes) {
		Device dev = new Device(deviceId, numButtons, numAxes);
		
		devices.put(deviceId, dev);
		
		for (DeviceListener listener : listeners) {
			listener.handleDeviceAdded(dev);
		}
	}

	public void deviceRemoved(int deviceId) {
		Device dev = devices.get(deviceId);
		if (dev == null) {
			return;
		}
		
		for (DeviceListener listener : listeners) {
			listener.handleDeviceRemoved(dev);
		}
		
		devices.remove(deviceId);
	}

	public void buttonDown(int deviceId, int buttonId) {
		Device dev = devices.get(deviceId);
		for (DeviceListener listener : listeners) {
			listener.handleButton(dev, buttonId, true);
		}
	}

	public void buttonUp(int deviceId, int buttonId) {
		Device dev = devices.get(deviceId);
		for (DeviceListener listener : listeners) {
			listener.handleButton(dev, buttonId, false);
		}
	}

	public void axisMoved(int deviceId, int axisId, float value, float lastValue) {
		Device dev = devices.get(deviceId);
		for (DeviceListener listener : listeners) {
			listener.handleAxis(dev, axisId, value, lastValue);
		}
	}

}
