package _02_Chat_Application;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;

/*
 * Using the Click_Chat example, write an application that allows a server computer to chat with a client computer.
 */



public class ChatApp {
	
	int port;
	String ip;
	
	//Server Stuff
	JFrame window;
	JButton button;
	Server s;
	
	//Client Stuff
	JFrame clientWindow;
	JButton clientButton;
	Client c;
	
	public static void main(String[] args) {
		
		ChatApp app = new ChatApp();
		
		try {
			app.ip = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			app.ip = "192.168.2.200";
		}
		app.port = 9123;
		
		//Make Server
		app.s = new Server(app.ip, app.port);
		Thread t = new Thread(() -> app.s.begin());
		app.makeWindow();
		t.start();
		
		//Make Client
		app.c = new Client(app.ip, app.port);
		Thread dt = new Thread(() -> app.c.begin());
		app.makeClientWindow();
		dt.start();
		
		
	}
	
	void makeWindow() {
		window = new JFrame();
		button = new JButton();
		button.setText("Send Message to Client");
		button.addActionListener((e) -> {
			this.s.sendMessage();
		});
		window.add(button);
		window.pack();
		window.setVisible(true);
	}
	
	void makeClientWindow() {
		clientWindow = new JFrame();
		clientButton = new JButton();
		clientButton.setText("Send Message to Server");
		clientButton.addActionListener((e) -> {
			this.c.sendMessage();
		});
		clientWindow.add(clientButton);
		clientWindow.pack();
		clientWindow.setVisible(true);
		
		
	}
}
