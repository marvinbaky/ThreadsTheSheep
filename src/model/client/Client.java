package model.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

import controller.client.ReadThread;
import controller.client.WriteThread;
import model.DTO.ServerDataDTO;
import model.DTO.SheepDTO;

public class Client {

	private Socket clientSocket;
	private ObjectInputStream in;
	private BufferedReader systemInput;
	private ObjectOutputStream out;
	private ServerDataDTO serverData;
	private Sheep sheep;

	public Client(String username) {
		try {
			clientSocket = new Socket("localhost", 2000);
			out = new ObjectOutputStream(clientSocket.getOutputStream());
			in = new ObjectInputStream(clientSocket.getInputStream());
			systemInput = new BufferedReader(new InputStreamReader(System.in));
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host");
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to host");
		}
		startClient(username);
	}

	private void startClient(String username) {
		if (clientSocket != null && out != null && in != null) {
			try {
				System.out.println("The client started.");
				out.writeObject(new SheepDTO(username));
				System.out.println("");
				
				try {
					serverData = (ServerDataDTO) in.readObject();
					if (sheep == null) {
						//sheep = new Sheep(serverData.getCurrUser());
						startWriter();
					}
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				ReadThread reader = new ReadThread(in, sheep);
				reader.start();
//				WriteThread writer;
				// out.writeBytes(username);
				// WriteThread writer = new WriteThread(systemInput, out);
				// writer.start();
//				while (true) {
//					ServerDataDTO serverData;
//					serverData = (ServerDataDTO) in.readObject();
//					List<SheepDTO> sheepDtos = serverData.getSheepDtos();
//					if (sheep == null) {
//						sheep = new Sheep(serverData.getCurrUser(), serverData.getX(), serverData.getY());
//						writer = new WriteThread(sheep, out);
//						writer.start();
//					}
//					for (SheepDTO sheepDto : sheepDtos) {
//						System.out.println(sheepDto.getUsername());
//						System.out.println(sheepDto.getScore());
//					}
//
//				}
			} catch (UnknownHostException e) {
				System.err.println("Trying to connect to unknown host: " + e);
			} catch (IOException e) {
				System.err.println("IOException:  " + e);
			}
		}
	}
	
	public Sheep getSheep() {
		return sheep;
	}
	
	public void setSheep(Sheep sheep) {
		this.sheep = sheep;
	}
	
	public ServerDataDTO getServerDataDTO() {
		return serverData;
	}
	
	public void setServerDataDTO(ServerDataDTO serverData) {
		this.serverData = serverData;
	}
	
	public void startWriter() {
		WriteThread writer;
		writer = new WriteThread(sheep, out);
		writer.start();
	}
}	
