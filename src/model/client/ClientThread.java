package model.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Random;

import controller.client.ReadThread;
import controller.client.WriteThread;
import model.DTO.ServerDataDTO;
import model.DTO.ServerInfoDTO;
import model.DTO.SheepDTO;

public class ClientThread extends Thread {

	private Socket clientSocket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private ServerDataDTO serverData;
	private Sheep sheep;
	private String username;
	private List<ServerInfoDTO> serverInfos;
	private Farm farm;

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
		farm = new Farm();
	}

	public void run() {
		if (clientSocket != null && out != null && in != null) {
			try {
				System.out.println("The client started.");
				out.writeObject(new SheepDTO(username));
				// System.out.println("");

				try {
					serverData = (ServerDataDTO) in.readObject();
					if (sheep == null) {
						serverInfos = serverData.getServerInfos();
						sheep = new Sheep(username, serverData.getServerInfos());
						clientSocket.close();
						clientSocket = new Socket(sheep.getServerInfo().getAddrName(), sheep.getServerInfo().getPort());
						out = new ObjectOutputStream(clientSocket.getOutputStream());
						in = new ObjectInputStream(clientSocket.getInputStream());
						//startWriter();
					}
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				ReadThread reader = new ReadThread(username, in, sheep, farm);
				reader.start();
				Random rand = new Random();

				while (true) {
					int n = rand.nextInt(5) + 1;
					String direction = "";
					switch (n) {
					case 1:
						direction = "up";
						break;
					case 2:
						direction = "down";
						break;
					case 3:
						direction = "left";
						break;
					case 4:
						direction = "right";
						break;
					case 5:
						direction = "eat";
						break;
					}

					if (direction.equals("eat")) {
						sheep.setEatState(true);
					} else {
						sheep.move(direction);
						farm.move(sheep);

						if (sheep.willTransfer()) {
							findNewServerInfo();
							ServerInfoDTO newServerInfo = sheep.getServerInfo();
							SheepDTO sheepDTO = new SheepDTO(sheep.getName(), sheep.getX(), 
									sheep.getY(), sheep.getEatState(), sheep.getDirection());
							sheepDTO.willTransfer();
							out.writeObject(sheepDTO);
							clientSocket.close();
							clientSocket = new Socket(newServerInfo.getAddrName(), newServerInfo.getPort());
							out = new ObjectOutputStream(clientSocket.getOutputStream());
							in = new ObjectInputStream(clientSocket.getInputStream());
							reader = new ReadThread(username, in, sheep, farm);
							reader.start();
						}
						sheep.setTransfer(false);
					}
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					SheepDTO sheepDTO = new SheepDTO(sheep.getName(), sheep.getX(), 
							sheep.getY(), sheep.getEatState(), sheep.getDirection());
					out.writeObject(sheepDTO);
					sheep.setEatState(false);
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

	private void findNewServerInfo() {
		for (ServerInfoDTO s : serverInfos) {
			if (sheep.getX() >= s.getStartX() && sheep.getX() <= s.getEndX() && sheep.getY() >= s.getStartY()
					&& sheep.getY() <= s.getEndY()) {
				sheep.setNewServerInfo(s);
				break;
			}
		}
	}

}
