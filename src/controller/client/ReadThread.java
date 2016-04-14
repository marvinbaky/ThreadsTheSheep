package controller.client;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

import model.DTO.ServerDataDTO;
import model.DTO.SheepDTO;
import model.client.Farm;
import model.client.Sheep;

public class ReadThread extends Thread {

	private ObjectInputStream in;
	private Sheep sheep;
	private String username;
	private Farm farm;

	public ReadThread(ObjectInputStream in, Sheep sheep) {
		this.in = in;
		this.sheep = sheep;
	}
	
	public ReadThread(String name, ObjectInputStream in, Sheep sheep) {
		username = name;
		this.in = in;
		this.sheep = sheep;
	}
	
	public ReadThread(String name, ObjectInputStream in, Sheep sheep, Farm farm) {
		username = name;
		this.in = in;
		this.sheep = sheep;
		this.farm = farm;
	}

	public void run() {

		ServerDataDTO serverData;
		try {
			while (true) {
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
					bw.write(ms + "\n");
					bw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				if (serverData.getCurrUser() == sheep.getName()) {
					sheep.setX(serverData.getX());
					sheep.setY(serverData.getY());
				}
				
				if(farm.hasTiles()) {
					farm.plot(serverData.getSheepDtos());
				}
				
//				List<SheepDTO> sheepDtos = serverData.getSheepDtos();
//				for (int i = 0; i < sheepDtos.size(); i++) {
//					System.out.println("Connected player: "
//							+ sheepDtos.get(i).getUsername() + " x: "
//							+ sheepDtos.get(i).getX() + " y: "
//							+ sheepDtos.get(i).getY());
//				}

				// List<SheepDTO> sheepDtos = serverData.getSheepDtos();
				// for (SheepDTO sheepDto : sheepDtos) {
				// System.out.println(sheepDto.getUsername());
				// System.out.println(sheepDto.getScore());
				// }
			}
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}
}
