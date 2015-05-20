package com.cteam.houston.ui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.cteam.houston.Houston;

public class KeyboardController implements KeyListener {

	@Override
	public void keyTyped(KeyEvent e) {
		// no-op
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_UP:
				Houston.speedUp();
				break;
			case KeyEvent.VK_DOWN:
				Houston.slowDown();
				break;
			case KeyEvent.VK_W:
				Houston.forward();
				break;
			case KeyEvent.VK_A:
				Houston.turnLeft();
				break;
			case KeyEvent.VK_S:
				Houston.backward();
				break;
			case KeyEvent.VK_D:
				Houston.turnRight();
				break;
			case KeyEvent.VK_SPACE:
				Houston.stop();
				break;
			case KeyEvent.VK_J:
				Houston.increaseDiggerWheelSpeed();
				break;
			case KeyEvent.VK_K:
				Houston.decreaseDiggerWheelSpeed();
				break;
			case KeyEvent.VK_U:
				Houston.increaseDiggerVerticalSpeed();
				break;
			case KeyEvent.VK_I:
				Houston.decreaseDiggerVerticalSpeed();
				break;
			case KeyEvent.VK_M:
				Houston.increaseDiggerVerticalPosition();
				break;
			case KeyEvent.VK_COMMA:
				Houston.decreaseDiggerVerticalPosition();
				break;
			case KeyEvent.VK_R:
				Houston.increaseLargeConveyorSpeed();
				break;
			case KeyEvent.VK_T:
				Houston.decreaseLargeConveyorSpeed();
				break;
			case KeyEvent.VK_Y:
				Houston.toggleLargeConveyorState();
				break;
			case KeyEvent.VK_F:
				Houston.increaseSmallConveyorSpeed();
				break;
			case KeyEvent.VK_G:
				Houston.decreaseSmallConveyorSpeed();
				break;
			case KeyEvent.VK_H:
				Houston.toggleSmallConveyorState();
				break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// no-op;
	}

}
