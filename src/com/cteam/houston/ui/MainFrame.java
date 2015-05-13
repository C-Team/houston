package com.cteam.houston.ui;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.cteam.houston.network.Command;

public class MainFrame extends JFrame {
	private static final long serialVersionUID = -7102600235458420220L;

	private static final int HEIGHT = 600;
	private static final int WIDTH = 800;
	
	private JLabel commandLabel;
	private JLabel powerLabel;
	
	private MainFrame() {}
	
	public static MainFrame createFrame() {
		MainFrame frame = new MainFrame();
		frame.build();
		frame.setSize(WIDTH, HEIGHT);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		return frame;
	}
	
	private void build() {
		Container mainContent = getContentPane();
		mainContent.setLayout(new BorderLayout());
		
		commandLabel = new JLabel("Command: Awaiting command...");
		powerLabel = new JLabel("Power: Over 9000!");
		
		Box labelBox = Box.createVerticalBox();
		labelBox.add(Box.createVerticalGlue());
		labelBox.add(commandLabel);
		labelBox.add(Box.createVerticalStrut(20));
		labelBox.add(powerLabel);
		labelBox.add(Box.createVerticalGlue());
		
		Box mainBox = Box.createHorizontalBox();
		mainBox.add(Box.createHorizontalGlue());
		mainBox.add(labelBox);
		mainBox.add(Box.createHorizontalGlue());
		mainContent.add(mainBox, "Center");
	}
	
	public void updateValues(Command command, int power) {
		if (power == -1) {
			setCommandText("Awaiting command...");
			setPowerText("Over 9000!");
			return;
		}
		switch (command) {
			case DRIVE_BACKWARD:
				setCommandText("Drive Backwards");
				break;
			case DRIVE_FORWARD:
				setCommandText("Drive Forwards");
				break;
			case TURN_LEFT:
				setCommandText("Turn Left");
				break;
			case TURN_RIGHT:
				setCommandText("Turn Right");
				break;
		}
		setPowerText(String.valueOf(power));
	}
	
	private void setCommandText(String text) {
		commandLabel.setText("Command: " + text);
	}
	
	private void setPowerText(String text) {
		powerLabel.setText("Power: " + text);
	}
}
