package _02_Chat_Application;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

import javax.swing.JOptionPane;

public class Server {

	int port;
	String ip;
	ServerSocket server;
	DataInputStream dis;
	DataOutputStream dos;
	boolean canChat;
	
	Server(String i, int p) {
		this.port = p;
		this.ip = i;
		this.canChat = false;
	}
	
	public void begin() {
		try {
			server = new ServerSocket(port);
		}
		catch(IOException e) {
			System.out.println("IOException caught.");
		}
		
		Socket connection;
		boolean bool = true;
		try {
			while(bool == true) {
				connection = server.accept();
				dis = new DataInputStream(connection.getInputStream());
				dos = new DataOutputStream(connection.getOutputStream());
				dos.flush();
				canChat = true;
				while(connection.isConnected()) {
					if(canChat == false) {
						connection.close();
						break;
					}
					System.out.println(dis.readUTF());
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			bool = false;
		}
		
		
		
	}
	
	public void sendMessage() {
		if(canChat == true) {
			String msg = JOptionPane.showInputDialog("What do you want to send?");
			try {
				if(msg.equals("End")) {
					canChat = false;
				}
				else {
					dos.writeUTF(msg + " (From Server)");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null, "There was an issue sending the message");
			}
		}
		else {
			JOptionPane.showMessageDialog(null, "Client isn't connected");
		}
	}
	
	
}
