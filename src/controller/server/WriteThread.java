package controller.server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import model.DTO.ServerDataDTO;
import model.DTO.SheepDTO;

public class WriteThread extends Thread {
	private static List<ObjectOutputStream> allClientStream;
	private static BlockingQueue<ServerDataDTO> serverDataDtos;
	private static List<SheepDTO> sheepDtos;

	public WriteThread() {
	}

	public static void initializeClientStream(List<ObjectOutputStream> allClientStream) {
		WriteThread.allClientStream = allClientStream;
	}

	public static void initializeServerDataQueue(BlockingQueue<ServerDataDTO> serverDataQueue) {
		WriteThread.serverDataDtos = serverDataQueue;
	}
	
	public static void  initializeSheepDTOs(List<SheepDTO> sheepDtos) {
		WriteThread.sheepDtos = sheepDtos;
	}

	public void run() {

		while (true) {
			// try {
			// Thread.sleep(50);
			// } catch (InterruptedException e1) {
			// // TODO Auto-generated catch block
			// e1.printStackTrace();i
			// }
			// int startMod = (int) Math.floor(allClientStream.size() / 4.0);
			// int endMod = (int) Math.ceil(allClientStream.size() / 4.0);
			// // System.out.println("endmod " + endMod);
			// SubWriteThread[] subWriters = new SubWriteThread[4];
			// ServerDataDTO data = null;
			// try {
			// if (!allClientStream.isEmpty())
			// data = serverDataDtos.take();
			// } catch (InterruptedException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			// for (int i = 0; i < 4; i++) {
			// int start = i * startMod;
			// int end = start + endMod;
			// // System.out.println(start + " " + end + " " +
			// // allClientStream.size());
			// List<ObjectOutputStream> clientStreams = new ArrayList<>();
			//
			// clientStreams.addAll(allClientStream.subList(start, end));
			// ServerDataDTO serverData = null;
			// if (!allClientStream.isEmpty() && data != null) {
			// serverData = new ServerDataDTO(data.getSheepDtos(),
			// data.getCurrUser(), data.getX(),
			// data.getY());
			//
			// subWriters[i] = new SubWriteThread(clientStreams, serverData);
			// subWriters[i].start();
			// }
			// }
			//
			// for (int i = 0; i < subWriters.length; i++) {
			// try {
			// if(subWriters[i] != null)
			// subWriters[i].join();
			// } catch (InterruptedException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			// }

			try {
				Thread.sleep(50);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			ServerDataDTO data = null;
			try {
				data = serverDataDtos.take();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			synchronized(sheepDtos) {
				data.setSheepDTOs(sheepDtos);
				List<SheepDTO> sheepDtos = Collections.synchronizedList(new ArrayList<>());
				ServerThread.initializeSheepDTOs(sheepDtos);
				WriteThread.initializeSheepDTOs(sheepDtos);
			}
			
			for (int i = 0; i < allClientStream.size(); i++) {
				try {
					allClientStream.get(i).writeObject(data);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					allClientStream.remove(allClientStream.get(i));
				}
			}
		}
	}
}
