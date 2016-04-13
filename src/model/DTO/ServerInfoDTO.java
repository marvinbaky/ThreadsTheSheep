package model.DTO;

import java.io.Serializable;

public class ServerInfoDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String addrName;
	private int port;
	private int startX;
	private int endX;
	private int startY;
	private int endY;

	public ServerInfoDTO(String addrName, int port, int startX, int endX, 
			int startY, int endY) {
		this.addrName = addrName;
		this.port = port;
		this.startX = startX;
		this.endX = endX;
		this.startY = startY;
		this.endY = endY;
	}

	public String getAddrName() {
		return addrName;
	}

	public int getPort() {
		return port;
	}

	public int getStartX() {
		return startX;
	}

	public int getEndX() {
		return endX;
	}

	public int getStartY() {
		return startY;
	}

	public int getEndY() {
		return endY;
	}

}
