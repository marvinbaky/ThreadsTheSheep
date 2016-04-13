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
	private Socket mainClient;
	private String username;
	private static Farm farm;
	private GridPanel grid;
	private Object lock;

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
		lock = new Object();
	}

	public static void initializeClientStream(List<ObjectOutputStream> allClientStream) {
		ServerThread.allClientStream = allClientStream;
	}

	public static void initializeServerDataQueue(BlockingQueue<ServerDataDTO> serverDataQueue) {
		ServerThread.serverDataDtos = serverDataQueue;
	}

	public void run() {
		// System.out.println("Accepted a connection.");
		try {
			ObjectInputStream in = new ObjectInputStream(mainClient.getInputStream());
			SheepDTO sheepDto;
			Sheep sheep;
			while (true) {
				sheepDto = (SheepDTO) in.readObject();
				if (username == null) {
					username = sheepDto.getUsername();
					//if(sheepD)
					sheep = new Sheep(username);
					// System.out.println("****" + sheep.getX() + " " +
					// sheep.getY());
					sheep = farm.putNewSheep(sheep);
				} else {
					if (sheepDto.isEat()) {
						sheep = new Sheep(username, sheepDto.getX(), sheepDto.getY());
						sheep = farm.eat(sheep);
					} else {
						sheep = new Sheep(username, sheepDto.getX(), sheepDto.getY());
						sheep = farm.move(sheep, sheepDto.getDirection());
					}
				}

				grid.updateGrid(farm);
				grid.repaint();
				ServerDataDTO serverData = new ServerDataDTO(farm.getSheepDTOs(), sheep.getName(), sheep.getX(),
						sheep.getY());

				serverDataDtos.put(serverData);
			}

			// Thread.sleep(50);
			// synchronized (allClientStream) {
			// Iterator<ObjectOutputStream> i = allClientStream.iterator();
			// while (i.hasNext()) {
			// i.next().writeObject(serverData);
			// }
			// }

			// ObjectInputStream in = new
			// ObjectInputStream(mainClient.getInputStream());
			// SheepDTO sheepDto;
			// Sheep sheep;
			//
			// sheepDto = (SheepDTO) in.readObject();
			// // System.out.println("From client: " + sheepDto.getUsername());
			//
			// username = sheepDto.getUsername();
			// System.out.println("Username: " + username);
			// sheep = new Sheep(username);
			// List<Sheep> sheeps = farm.getSheeps();
			// System.out.println("s: " + sheep.getName());
			// if (sheeps.contains(sheep)) {
			// System.out.println("From list: " + sheep.getName());
			// }

			// System.out.println("sheeps: " + sheeps.size());
			// if (sheeps.contains(sheep)) {
			// if (sheepDto.isEat()) {
			// sheep = new Sheep(username, sheepDto.getX(), sheepDto.getY());
			// sheep = farm.eat(sheep);
			// } else {
			// sheep = new Sheep(username, sheepDto.getX(), sheepDto.getY());
			// sheep = farm.move(sheep, sheepDto.getDirection());
			// }
			// } else {
			// sheep = farm.putNewSheep(sheep);
			// }

			// if (username == null) {
			// username = sheepDto.getUsername();
			// sheep = new Sheep(username);
			// // System.out.println("****" + sheep.getX() + " " +
			// // sheep.getY());
			// sheep = farm.putNewSheep(sheep);
			// } else {
			// if (sheepDto.isEat()) {
			// sheep = new Sheep(username, sheepDto.getX(),
			// sheepDto.getY());
			// sheep = farm.eat(sheep);
			// } else {
			// sheep = new Sheep(username, sheepDto.getX(),
			// sheepDto.getY());
			// sheep = farm.move(sheep, sheepDto.getDirection());
			// }
			// }

			// grid.updateGrid(farm);
			// grid.repaint();
			// ServerDataDTO serverData = new ServerDataDTO(farm.getSheepDTOs(),
			// sheep.getName(), sheep.getX(),
			// sheep.getY());

			// serverDataDtos.put(serverData);

			// List<ObjectOutputStream> listOut = new ArrayList<>();
			// synchronized (allClientStream) {
			// Iterator<ObjectOutputStream> i = allClientStream.iterator();
			// System.out.println("clientstream: " +
			// allClientStream.size());
			// while (i.hasNext()) {
			// // System.out.println("writed");
			// ObjectOutputStream out = null;
			// try {
			// out = i.next();
			// //System.out.println("Curr user: " + serverData.getCurrUser());
			// out.writeObject(serverData);
			// } catch (SocketException e) {
			// listOut.add(out);
			//
			// }
			// }
			// // System.out.println("done");
			// allClientStream.removeAll(listOut);
			// }

			// Thread.sleep(50);
			// for(int i = 0; i < allClientStream.size(); i++) {
			// try {
			// synchronized(allClientStream.get(i)) {
			// allClientStream.get(i).writeObject(serverData);
			// }
			// } catch (SocketException e) {
			// listOut.add(allClientStream.get(i));
			// }
			// }
			//
			// synchronized(lock) {
			// allClientStream.removeAll(listOut);
			// }
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
