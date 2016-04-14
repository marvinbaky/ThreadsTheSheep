package model.client;

import java.util.Scanner;

public class Driver {
	public static void main(String[] args) throws InterruptedException {
		Scanner sc = new Scanner(System.in);
		//String username = sc.next();
		
		System.out.println("Num of user: ");
		int numUser = sc.nextInt();
		
		
		
		ClientThread[] clients = new ClientThread[numUser];
		for(int i = 0; i < numUser; i++) {
			//System.out.println(i);
			clients[i] = new ClientThread("Player " + i, 1000);
		}
		
		for(int i = 0; i < numUser; i++) {
			clients[i].start();
		}

		for(int i = 0; i < numUser; i++) {
			clients[i].join();
		}
		
		//Client client = new Client(username);
		//Sheep sheep = client.getSheep();
		
//		Random rand = new Random();
//
//		while(true) {
//			int  n = rand.nextInt(5) + 1;
//			String direction = "";
//			switch (n){
//			 case 1:direction = "up"; break;
//			 case 2:direction = "down"; break;
//			 case 3:direction = "left"; break;
//			 case 4:direction = "right"; break;
//			 case 5:direction = "eat"; break;
//			}
//			
//			if(direction.equals("eat")) {
//				sheep.setEatState(true);
//			} else {
//				sheep.move(direction);
//			}
//			Thread.sleep(1);
//			
//		}
	}
}
