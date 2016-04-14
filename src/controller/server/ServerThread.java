package controller.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import model.DTO.ServerDataDTO;
import model.DTO.SheepDTO;
import model.server.Farm;
import model.server.Sheep;
import view.server.GridPanel;

public class ServerThread extends Thread {

	private static List<ObjectOutputStream> allClientStream;
	private static BlockingQueue<ServerDataDTO> serverDataDtos;
	private static BlockingQueue<SheepDTO> sheepDtos;
	private Socket mainClient;
	private String username;
	private static Farm farm;
	private GridPanel grid;
	private Sheep sheep;

	public ServerThread(Socket clientSocket, Farm farm, GridPanel grid) {

		this.mainClient = clientSocket;
		ServerThread.farm = farm;
		this.grid = grid;
		grid.updateGrid(farm);// initialize empty grid
		try {
			allClientStream.add(new ObjectOutputStream(mainClient.getOutputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void initializeClientStream(List<ObjectOutputStream> allClientStream) {
		ServerThread.allClientStream = allClientStream;
	}

	public static void initializeServerDataQueue(BlockingQueue<ServerDataDTO> serverDataQueue) {
		ServerThread.serverDataDtos = serverDataQueue;
	}

	public static void initializeSheepDTOs(BlockingQueue<SheepDTO> sheepDtos) {
		ServerThread.sheepDtos = sheepDtos;
	}

	public void run() {
		// System.out.println("Accepted a connection.");
		try {
			ObjectInputStream in = new ObjectInputStream(mainClient.getInputStream());
			SheepDTO sheepDto;
			while (true) {
				sheepDto = (SheepDTO) in.readObject();

				if (username == null) {
					username = sheepDto.getUsername();
					// if(sheepD)
					sheep = new Sheep(username, sheepDto.getX(), sheepDto.getY());
					// System.out.println("****" + sheep.getX() + " " +
					// sheep.getY());
					sheep = farm.putNewSheep(sheep);
				} else {
					if (sheepDto.getTransfer()) {
						farm.remove(sheep);
						grid.updateGrid(farm);
						grid.repaint();
						break;
					} else if (sheepDto.isEat()) {
						sheep = new Sheep(username, sheepDto.getX(), sheepDto.getY());
						sheep = farm.eat(sheep);
					} else {
						sheep = new Sheep(username, sheepDto.getX(), sheepDto.getY());
						sheep = farm.plot(sheep, sheepDto);
					}
				}

				grid.updateGrid(farm);
				grid.repaint();
				ServerDataDTO serverData = new ServerDataDTO(sheep.getName(), sheep.getX(), sheep.getY());
				SheepDTO toPassSheep = new SheepDTO(sheep.getName(), sheep.getX(), sheep.getY(), sheep.getScore(),
						sheep.hasEaten());

				serverDataDtos.put(serverData);
				sheepDtos.put(toPassSheep);

			}

		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			farm.remove(sheep);
			grid.updateGrid(farm);
			grid.repaint();

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
