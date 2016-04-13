package model.DTO;

import java.io.Serializable;
import java.util.List;

public class ServerDataDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<SheepDTO> sheepDtos;
	private String currUser;
	private int x;
	private int y;

	public ServerDataDTO(List<SheepDTO> sheepDtos, String username,
			int x, int y) {
		this.sheepDtos = sheepDtos;
		currUser = username;
		this.x = x;
		this.y = y;
	}
	
	public List<SheepDTO> getSheepDtos() {
		return sheepDtos;
	}

	public String getCurrUser() {
		return currUser;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}
