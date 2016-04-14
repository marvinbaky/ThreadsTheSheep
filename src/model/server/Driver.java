package model.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import model.DTO.ServerDataDTO;
import model.DTO.ServerInfoDTO;
import model.DTO.SheepDTO;

public class Driver {
	public static void main(String[] args) {
		//Server server = Server.getServerInstance();
		//Farm farm = new Farm();
		//server.acceptConnection(farm);
		
		List<ServerInfoDTO> serverInfos = new ArrayList<>();
		//ServerInfoDTO serverInfo1 = new ServerInfoDTO("localhost", 2000, 0, 99, 0, 99);
		ServerInfoDTO serverInfo1 = new ServerInfoDTO("localhost", 2000, 0, 49, 0, 99);
		ServerInfoDTO serverInfo2 = new ServerInfoDTO("localhost", 2001, 50, 99, 0, 99);
		
		serverInfos.add(serverInfo1);
		serverInfos.add(serverInfo2);
		
		ServerDataDTO serverData = new ServerDataDTO(serverInfos);
		ServerSocket server = null;
		try {
			server = new ServerSocket(1000);
			System.out.println("Coordinator Server started at port 1000. Now accepting connections.");
			while (true) {
				Socket newClient = server.accept();
				ObjectInputStream in = new ObjectInputStream(newClient.getInputStream());
				ObjectOutputStream out = new ObjectOutputStream(newClient.getOutputStream());
				
				out.writeObject(serverData);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
