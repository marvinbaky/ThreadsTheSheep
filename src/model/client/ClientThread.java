package model.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;

import model.DTO.ServerDataDTO;
import model.DTO.SheepDTO;
import controller.client.ReadThread;
import controller.client.WriteThread;

public class ClientThread extends Thread{
	
	private Socket clientSocket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private ServerDataDTO serverData;
	private Sheep sheep;
	private String username;
	
	public ClientThread(String username, int port) {
		try {
			clientSocket = new Socket("localhost", port);
			out = new ObjectOutputStream(clientSocket.getOutputStream());
			in = new ObjectInputStream(clientSocket.getInputStream());
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host");
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to host");
		}
		this.username = username;
	}
	
	public void run() {
		if (clientSocket != null && out != null && in != null) {
			try {
				System.out.println("The client started.");
				out.writeObject(new SheepDTO(username));
				//System.out.println("");
				
				try {
					serverData = (ServerDataDTO) in.readObject();
					if (sheep == null) {
						sheep = new Sheep(serverData.getCurrUser(), serverData.getX(), serverData.getY());
						startWriter();
					}
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				ReadThread reader = new ReadThread(username, in, sheep);
				reader.start();
				
				Random rand = new Random();

				while(true) {
					int  n = rand.nextInt(5) + 1;
					String direction = "";
					switch (n){
					 case 1:direction = "up"; break;
					 case 2:direction = "down"; break;
					 case 3:direction = "left"; break;
					 case 4:direction = "right"; break;
					 case 5:direction = "eat"; break;
					}
					
					if(direction.equals("eat")) {
						sheep.setEatState(true);
					} else {
						if(sheep.getY() == 49 && direction.equals("down")) {
							clientSocket.close();
							ClientThread clientThread = new ClientThread(sheep.getName(), 2001);
							clientThread.start();
						} else if(sheep.getY() == 50 && direction.equals("up")) {
							clientSocket.close();
							ClientThread clientThread = new ClientThread(sheep.getName(), 2000);
							//client
						}
						sheep.move(direction);
					}
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} catch (UnknownHostException e) {
				System.err.println("Trying to connect to unknown host: " + e);
			} catch (IOException e) {
				System.err.println("IOException:  " + e);
			}
		}
	}
	
	public void startWriter() {
		WriteThread writer;
		writer = new WriteThread(sheep, out);
		writer.start();
	}
	
}
