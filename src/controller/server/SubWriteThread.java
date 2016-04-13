package controller.server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.SocketException;
import java.util.List;

import model.DTO.ServerDataDTO;

public class SubWriteThread extends Thread {

	private List<ObjectOutputStream> clientStream;
	private ServerDataDTO serverData;
	
	public SubWriteThread(List<ObjectOutputStream> clientStream, ServerDataDTO serverData) {
		this.clientStream = clientStream;
		this.serverData = serverData;
	}
	
	public void run() {
		for (int i = 0; i < clientStream.size(); i++) {
			try {
				clientStream.get(i).writeObject(serverData);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
