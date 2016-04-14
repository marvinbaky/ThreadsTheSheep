package model.server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import controller.server.ServerThread;
import controller.server.WriteThread;
import model.DTO.ServerDataDTO;
import model.DTO.SheepDTO;
import view.server.GridPanel;

public class Server {

	private static Server serverInstance;
	private ServerSocket server;

	private Server() {
		try {
			server = new ServerSocket(2000);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Server started at port 2000");
	}

	public static Server getServerInstance() {
		if (serverInstance == null) {
			serverInstance = new Server();
		}
		return serverInstance;
	}

	public void acceptConnection(Farm farm, GridPanel grid) {
		try {
			ServerThread serverThread;
			List<ObjectOutputStream> allClientStream = Collections.synchronizedList(new ArrayList<>());
			ServerThread.initializeClientStream(allClientStream);
			WriteThread.initializeClientStream(allClientStream);
			
			BlockingQueue<ServerDataDTO> serverDataQueue = 
					new LinkedBlockingQueue<>();
			ServerThread.initializeServerDataQueue(serverDataQueue);
			WriteThread.initializeServerDataQueue(serverDataQueue);
			
			BlockingQueue<SheepDTO> sheepDtos = new LinkedBlockingQueue<>();
			ServerThread.initializeSheepDTOs(sheepDtos);
			WriteThread.initializeSheepDTOs(sheepDtos);
			
			WriteThread writer = new WriteThread();
			writer.start();
			
			while (true) {
				try {
					Socket client = server.accept();
					serverThread = new ServerThread(client, farm, grid);
					
					serverThread.start();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} finally {
			closeServer();
		}
	}

	public void closeServer() {
		try {
			server.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
