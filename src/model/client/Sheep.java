package model.client;

import java.util.concurrent.Semaphore;

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
	private Semaphore waitMove;
	private final Object lock = new Object();

	public Sheep(String name, int x, int y) {
		this.name = name;
		this.x = x;
		this.y = y;
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
		if(eat) {
			waitMove.release();
		}
	}
	
	public String getDirection() {
		return direction;
	}

	public void setWaitMove(Semaphore semaphore) {
		waitMove = semaphore;
	}

	public void move(String direction) {
		this.direction = direction;
		waitMove.release();
	}

}
