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
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// no-op;
	}

}
