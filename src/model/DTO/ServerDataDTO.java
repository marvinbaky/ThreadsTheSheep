package model.DTO;

import java.io.Serializable;
import java.util.List;

public class ServerDataDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<SheepDTO> sheepDtos;
	private List<ServerInfoDTO> serverInfos;
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
	
	public ServerDataDTO(String username, int x, int y) {
		currUser = username;
		this.x = x;
		this.y = y;
	}
	
	public ServerDataDTO(List<ServerInfoDTO> serverInfos) {
		this.serverInfos = serverInfos;
	}
	
	public void setSheepDTOs(List<SheepDTO> sheepDtos) {
		this.sheepDtos = sheepDtos;
	}
	
	public List<SheepDTO> getSheepDtos() {
		return sheepDtos;
	}
	
	public List<ServerInfoDTO> getServerInfos() {
		return serverInfos;
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
