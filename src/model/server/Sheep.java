package model.server;

import java.util.Random;

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
	private int score;
	private boolean willTransfer;
	
	public Sheep(String name) {
		this.name = name;
		
		Random r = new Random();
		this.x = r.nextInt(99);
		this.y = r.nextInt(99);
		score = 0;
	}
	
	public Sheep(String username, int x, int y) {
		name = username;
		this.x = x;
		this.y = y;
	}

	public String getName() {
		return name;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public int getScore() {
		return score;
	}
	
	public void incrementScore(){
		score++;
	}
	
	public void setTransfer(boolean bool) {
		willTransfer = bool;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Sheep other = (Sheep) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
