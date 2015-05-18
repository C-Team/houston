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
	private static final int HEIGHT = 300;
	
	private JTextField hostText;
	private JButton connectButton;
	private JLabel connectionLabel;
	private JLabel commandLabel;
	private JLabel speedLabel;
	private JLabel directionLabel;
	private JLabel diggerSpeedLabel;
	private JLabel dildoSpeedLabel;
	private JLabel dildoPositionLabel;
	
	
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
		diggerSpeedLabel = new JLabel("Digger Wheel Speed: wat");
		dildoPositionLabel = new JLabel("Actuator Position: watman");
		dildoSpeedLabel = new JLabel("Actuator Speed: man");
		
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
		labelBox.add(diggerSpeedLabel);
		labelBox.add(Box.createVerticalStrut(10));
		labelBox.add(dildoSpeedLabel);
		labelBox.add(Box.createVerticalStrut(10));
		labelBox.add(dildoPositionLabel);
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
				diggerSpeedLabel.setText("Digger Wheel Speed: " + String.valueOf(value));
				break;
			case DIGGER_VERTICAL_POSITION:
				commandLabel.setText("Command: Actuator Position");
				dildoPositionLabel.setText("Actuator Position: " + String.valueOf(value));
				break;
			case DIGGER_VERTICAL_SPEED:
				commandLabel.setText("Command: Actuator Speed");
				dildoSpeedLabel.setText("Actuator Speed: " + String.valueOf(value));
				break;
		default:
			break;
		}
		
	}
}
