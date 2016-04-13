package controller.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.concurrent.Semaphore;

import model.DTO.SheepDTO;
import model.client.Sheep;

public class WriteThread extends Thread {

	private BufferedReader systemInput;
	private ObjectOutputStream out;
	private Sheep sheep;
	private Semaphore waitMove;

	public WriteThread(BufferedReader systemInput, ObjectOutputStream out) {
		this.systemInput = systemInput;
		this.out = out;

	}

	public WriteThread(Sheep sheep, ObjectOutputStream out) {
		this.sheep = sheep;
		this.out = out;

		waitMove = new Semaphore(0);
		this.sheep.setWaitMove(waitMove);
	}

	public void run() {
		while (true) {
			try {
				waitMove.acquire();
				//System.out.println(sheep.getEatState());
				SheepDTO sheepDTO = new SheepDTO(sheep.getName(), sheep.getX(), 
						sheep.getY(), sheep.getEatState(), sheep.getDirection());
				out.writeObject(sheepDTO);
				sheep.setEatState(false);
				//System.out.println();
			} catch (InterruptedException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
}
