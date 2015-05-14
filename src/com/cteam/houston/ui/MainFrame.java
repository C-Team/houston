package com.cteam.houston.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.cteam.houston.network.Command;

public class MainFrame extends JFrame {
	private static final long serialVersionUID = -7102600235458420220L;

	private static final int HEIGHT = 600;
	private static final int WIDTH = 800;
	
	private JLabel connectionLabel;
	private JLabel commandLabel;
	private JLabel speedLabel;
	private JLabel directionLabel;
	
	
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
		
		connectionLabel = new JLabel("Connection Status: Not Connected");
		connectionLabel.setForeground(Color.RED);
		commandLabel = new JLabel("Command: Awaiting command...");
		speedLabel = new JLabel("Power: Over 9000!");
		directionLabel = new JLabel("Direction: To the cloud!");
		
		Box labelBox = Box.createVerticalBox();
		labelBox.add(Box.createVerticalGlue());
		labelBox.add(connectionLabel);
		labelBox.add(Box.createVerticalStrut(20));
		labelBox.add(commandLabel);
		labelBox.add(Box.createVerticalStrut(20));
		labelBox.add(speedLabel);
		labelBox.add(Box.createVerticalStrut(20));
		labelBox.add(directionLabel);
		labelBox.add(Box.createVerticalGlue());
		
		Box mainBox = Box.createHorizontalBox();
		mainBox.add(Box.createHorizontalGlue());
		mainBox.add(labelBox);
		mainBox.add(Box.createHorizontalGlue());
		
		mainContent.add(mainBox, "Center");
	}
	
	public void setConnectionStatus(boolean connected) {
		connectionLabel.setText("Connection Status: " 
				+ (connected ? "Connected" : "Not Connected"));
		connectionLabel.setForeground(connected ? Color.GREEN : Color.RED);
	}
	
	public void updateValues(Command command, int value) {
		switch (command) {
			case SPEED:
				commandLabel.setText("Command: Speed");
				speedLabel.setText("Speed: " + String.valueOf(value));
				break;
			case DIRECTION:
				commandLabel.setText("Command: Direction");
				directionLabel.setText("Direction: " + String.valueOf(value));
				break;
		}
		
	}
}
