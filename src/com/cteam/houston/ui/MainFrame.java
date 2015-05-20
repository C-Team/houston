package com.cteam.houston.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.cteam.houston.Houston;
import com.cteam.houston.network.Command;

public class MainFrame extends JFrame {
	private static final long serialVersionUID = -7102600235458420220L;

	private static final String DEFAULT_IP = "192.168.0.103";
	
	private static final int WIDTH = 350;
	private static final int HEIGHT = 400;
	
	private JTextField hostText;
	private JButton connectButton;
	private JLabel connectionLabel;
	private JLabel commandLabel;
	private JLabel speedLabel;
	private JLabel directionLabel;
	private JLabel diggerWheelSpeedLabel;
	private JLabel diggerVerticalSpeedLabel;
	private JLabel diggerVerticalPositionLabel;
	private JLabel largeConveyorSpeedLabel;
	private JLabel largeConveyorStateLabel;
	private JLabel smallConveyorSpeedLabel;
	private JLabel smallConveyorStateLabel;
	
	private MainFrame() {}
	
	public static MainFrame createFrame() {
		MainFrame frame = new MainFrame();
		frame.build();
		frame.setSize(WIDTH, HEIGHT);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		return frame;
	}
	
	private void build() {
		connectButton = new JButton("Connect");
		connectButton.setFocusable(false);
		connectButton.addActionListener(new ActionListener() {
			private boolean connect = true;;
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (connect) {
					connectButton.setText("Disconnect");
					Houston.connect(hostText.getText());
				} else {
					connectButton.setText("Connect");
					Houston.disconnect();
				}
				connect = !connect;
			}
		});
		connectButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				initLabels();
				connectButton.removeActionListener(this);
			}
		});
		
		hostText = new JTextField(DEFAULT_IP);
		hostText.setToolTipText("Apollo's IP Address");
		hostText.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MainFrame.this.requestFocus();
			}
		});
		
		JLabel hostTextLabel = new JLabel("IP Address:");
		Box hostBox = Box.createHorizontalBox();
		hostBox.add(Box.createHorizontalStrut(10));
		hostBox.add(hostTextLabel);
		hostBox.add(hostText);
		hostBox.add(Box.createHorizontalStrut(10));
		
		connectionLabel = new JLabel("Connection Status: Not Connected");
		connectionLabel.setForeground(Color.RED);
		commandLabel = new JLabel("Command: Awaiting command...");
		speedLabel = new JLabel("Power: Over 9000!");
		directionLabel = new JLabel("Direction: To the cloud!");
		diggerWheelSpeedLabel = new JLabel("Digger Wheel Speed: wat");
		diggerVerticalPositionLabel = new JLabel("Actuator Position: watman");
		diggerVerticalSpeedLabel = new JLabel("Actuator Speed: man");
		largeConveyorSpeedLabel = new JLabel("Large Conveyor Speed: 88mph");
		largeConveyorStateLabel = new JLabel("Large Conveyor State: drunk");
		smallConveyorSpeedLabel = new JLabel("Small Conveyor Speed: 420mph");
		smallConveyorStateLabel = new JLabel("Small Conveyor State: high");
		
		
		Box labelBox = Box.createVerticalBox();
		labelBox.add(Box.createVerticalGlue());
		labelBox.add(Box.createVerticalStrut(10));
		labelBox.add(hostBox);
		labelBox.add(Box.createVerticalStrut(10));
		labelBox.add(connectButton);
		labelBox.add(Box.createVerticalStrut(10));
		labelBox.add(connectionLabel);
		labelBox.add(Box.createVerticalStrut(10));
		labelBox.add(commandLabel);
		labelBox.add(Box.createVerticalStrut(10));
		labelBox.add(speedLabel);
		labelBox.add(Box.createVerticalStrut(10));
		labelBox.add(directionLabel);
		labelBox.add(Box.createVerticalStrut(10));
		labelBox.add(diggerWheelSpeedLabel);
		labelBox.add(Box.createVerticalStrut(10));
		labelBox.add(diggerVerticalSpeedLabel);
		labelBox.add(Box.createVerticalStrut(10));
		labelBox.add(diggerVerticalPositionLabel);
		labelBox.add(Box.createVerticalStrut(10));
		labelBox.add(largeConveyorSpeedLabel);
		labelBox.add(Box.createVerticalStrut(10));
		labelBox.add(largeConveyorStateLabel);
		labelBox.add(Box.createVerticalStrut(10));
		labelBox.add(smallConveyorSpeedLabel);
		labelBox.add(Box.createVerticalStrut(10));
		labelBox.add(smallConveyorStateLabel);
		labelBox.add(Box.createVerticalStrut(10));
		labelBox.add(Box.createVerticalGlue());
		
		Box mainBox = Box.createHorizontalBox();
		mainBox.add(Box.createHorizontalGlue());
		mainBox.add(Box.createHorizontalStrut(15));
		mainBox.add(labelBox);
		mainBox.add(Box.createHorizontalStrut(15));
		mainBox.add(Box.createHorizontalGlue());
		
		Container mainContent = getContentPane();
		mainContent.setLayout(new BorderLayout());
		mainContent.add(mainBox, "Center");
		
		// Make sure when we click outside the text box, the window 
		// gets focus so keyboard events will work.
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				requestFocus();
				e.consume();
			}
		});
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
			case DIGGER_WHEEL_SPEED:
				commandLabel.setText("Command: Digger Wheel Speed");
				diggerWheelSpeedLabel.setText("Digger Wheel Speed: " + String.valueOf(value));
				break;
			case DIGGER_VERTICAL_POSITION:
				commandLabel.setText("Command: Actuator Position");
				diggerVerticalPositionLabel.setText("Actuator Position: " + String.valueOf(value));
				break;
			case DIGGER_VERTICAL_SPEED:
				commandLabel.setText("Command: Actuator Speed");
				diggerVerticalSpeedLabel.setText("Actuator Speed: " + String.valueOf(value));
				break;
			case LARGE_CONVEYOR_SPEED:
				commandLabel.setText("Command: Large Conveyor Speed");
				largeConveyorSpeedLabel.setText("Large Conveyor Speed: " + String.valueOf(value));
				break;
			case LARGE_CONVEYOR_STATE:
				commandLabel.setText("Command: Large Conveyor State");
				largeConveyorStateLabel.setText("Large Conveyor State: " + (value == 1 ? "ON" : "OFF"));
				break;
			case SMALL_CONVEYOR_SPEED:
				commandLabel.setText("Command: Small Conveyor Speed");
				smallConveyorSpeedLabel.setText("Small Conveyor Speed: " + String.valueOf(value));
				break;
			case SMALL_CONVEYOR_STATE:
				commandLabel.setText("Command: Small Conveyor State");
				smallConveyorStateLabel.setText("Small Conveyor State: " + (value == 1 ? "ON" : "OFF"));
				break;
		default:
			break;
		}
		
	}
	
	private void initLabels() {
		commandLabel.setText("Command: Awaiting command...");
		speedLabel.setText("Power: 0");
		directionLabel.setText("Direction: 0");
		diggerWheelSpeedLabel.setText("Digger Wheel Speed: 0");
		diggerVerticalPositionLabel.setText("Actuator Position: N/A");
		diggerVerticalSpeedLabel.setText("Actuator Speed: 0");
		largeConveyorSpeedLabel.setText("Large Conveyor Speed: 0");
		largeConveyorStateLabel.setText("Large Conveyor State: OFF");
		smallConveyorSpeedLabel.setText("Small Conveyor Speed: 0");
		smallConveyorStateLabel.setText("Small Conveyor State: OFF");
	}
}
