package model.DTO;

import java.io.Serializable;

public class SheepDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String username;
	private int score;
	private int x;
	private int y;
	private boolean eat;
	private String direction;
	private boolean willTransfer;
	
	public SheepDTO(String username) {
		this.username = username;
		score = 0;
	}
	
	/**
	 * Used by the server.
	 * @param username
	 * @param x
	 * @param y
	 * @param score - Current score of the user.
	 */
	public SheepDTO(String username, int x, int y, int score) {
		this.username = username;
		this.score = score;
		this.x = x;
		this.y = y;
	}
	
	
	public SheepDTO(String username, int x, int y, int score, boolean eat) {
		this.username = username;
		this.score = score;
		this.x = x;
		this.y = y;
		this.eat = eat;
	}
	
	public SheepDTO(String username, int prevX, int prevY, int x, int y, int score, boolean eat) {
		this.username = username;
		this.score = score;
		this.x = x;
		this.y = y;
		this.eat = eat;
	}
	
	/**
	 * Used by the client.
	 * @param username
	 * @param x - Current x of the user.
	 * @param y - Current y of the user.
	 * @param eat - boolean state if the sheep will eat.
	 */
	public SheepDTO(String username, int x, int y, boolean eat, String direction) {
		this.username = username;
		this.x = x;
		this.y = y;
		this.eat = eat;
		this.direction = direction;
	}
	
	public SheepDTO(String username, int x, int y, boolean eat, String direction, boolean willTransfer) {
		this.username = username;
		this.x = x;
		this.y = y;
		this.eat = eat;
		this.direction = direction;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
	public int getScore() {
		return score;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public boolean isEat() {
		return eat;
	}
	
	public String getDirection() {
		return direction;
	}
	
	public void willTransfer() {
		willTransfer = true;
	}
	
	public boolean getTransfer() {
		return willTransfer;
	}

	public void setTransfer(boolean b) {
		// TODO Auto-generated method stub
		willTransfer = b;
	}
}
