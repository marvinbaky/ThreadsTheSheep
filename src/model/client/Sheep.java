package model.client;

import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;

import model.DTO.ServerInfoDTO;

public class Sheep {

	private String name;

	/**
	 * Current x coordinate of the sheep in the map
	 */
	private int x;
	/**
	 * Current y coordinate of the sheep in the map
	 */
	private int y;
	private boolean eat;
	private int score;
	private String direction;
	private ServerInfoDTO currServerInfo;
	private boolean willTransfer = false;
	private Semaphore waitMove;
	private final Object lock = new Object();

	public Sheep(String name, List<ServerInfoDTO> serverInfoDto) {
		Random rand = new Random();
		
		this.name = name;
		this.x = rand.nextInt(100);
		this.y = rand.nextInt(100);
		
		for(ServerInfoDTO s: serverInfoDto) {
			if(x >= s.getStartX() && x <= s.getEndX() 
					&& y >= s.getStartY() && y <= s.getEndY()) {
				currServerInfo = s;
				break;
			}
		}
		
		this.direction = "up";
	}
	
	//public Sheep	
	
	public String getName() {
		return name;
	}

	public int getX() {
		return x;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setY(int y) {
		this.y = y;
	}

	public String getPosition() {
		synchronized (lock) {
			return x + "," + y;
		}
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getScore() {
		return score;
	}

	public boolean getEatState() {
		return eat;
	}
	
	public void setEatState(boolean bool) {
		eat = bool;
	}
	
	public String getDirection() {
		return direction;
	}

	public void setWaitMove(Semaphore semaphore) {
		waitMove = semaphore;
	}

	public void move(String direction) {
		this.direction = direction;
	}
	
	public void setNewServerInfo(ServerInfoDTO serverInfo) {
		currServerInfo = serverInfo;
	}
	
	public ServerInfoDTO getServerInfo() {
		return currServerInfo;
	}
	 
	public void setTransfer(boolean bool) {
		willTransfer = bool;
	}
	
	public boolean willTransfer() {
		return willTransfer;
	}

}
