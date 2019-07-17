package _02_Chat_Application;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JOptionPane;

public class Client {

	String ip;
	int port;
	Socket connection;
	
	DataInputStream dis;
	DataOutputStream dos;
	
	boolean canChat;
	
	Client(String ip, int port) {
		this.ip = ip;
		this.port = port;
		this.canChat = false;
	}
	
	public void begin() {
		try {
			connection = new Socket(ip, port);
			dis = new DataInputStream(connection.getInputStream());
			dos = new DataOutputStream(connection.getOutputStream());
			dos.flush();
			canChat = true;
			while(connection.isConnected()) {
				if(canChat == false) {
					connection.close();
				}
				System.out.println(dis.readUTF());
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sendMessage() {
		if(canChat == true) {
			String msg = JOptionPane.showInputDialog("What is the message?");
			if(msg.equals("End")) {
				canChat = false;
			}
			else {
				try {
					dos.writeUTF(msg + " (From Client)");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		else {
			JOptionPane.showMessageDialog(null, "Server Not Found");
		}
	}
}
