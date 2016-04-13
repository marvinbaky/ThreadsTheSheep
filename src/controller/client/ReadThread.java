package controller.client;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

import model.DTO.ServerDataDTO;
import model.DTO.SheepDTO;
import model.client.Sheep;

public class ReadThread extends Thread {
	
	private ObjectInputStream in;
	private Sheep sheep;
	private String username;
	
	public ReadThread(ObjectInputStream in, Sheep sheep) {
		this.in = in;
		this.sheep = sheep;
	}
	
	public ReadThread(String name, ObjectInputStream in, Sheep sheep) {
		username = name;
		this.in = in;
		this.sheep = sheep;
	}
	
	public void run() {
		while(true) {
			ServerDataDTO serverData;
			try {
				long startTime = System.nanoTime();
				serverData = (ServerDataDTO) in.readObject();
				long endTime = System.nanoTime();
				
				long duration = (endTime - startTime); 
				long ms = duration / 1000000;
				
				File file = new File("ping.txt");
				
				if (!file.exists()) {
					try {
						file.createNewFile();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				FileWriter fw;
				try {
					fw = new FileWriter(file.getAbsoluteFile(), true);
					BufferedWriter bw = new BufferedWriter(fw);
					bw.write(username + ": " + ms + "\n");
					bw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//System.out.println(username + ": " + duration);
				if(serverData.getCurrUser() == sheep.getName()) {
					sheep.setX(serverData.getX());
					sheep.setY(serverData.getY());
				}
//				List<SheepDTO> sheepDtos = serverData.getSheepDtos();
//				for (SheepDTO sheepDto : sheepDtos) {
//					System.out.println(sheepDto.getUsername());
//					System.out.println(sheepDto.getScore());
//				}
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
