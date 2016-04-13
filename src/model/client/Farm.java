package model.client;

import model.DTO.ServerInfoDTO;

//Client Farm
public class Farm {
	private int[][] map;

	public Farm() {
		map = new int[100][100];
	}

	public void move(Sheep sheep) {
		int temp;
		ServerInfoDTO serverInfo = sheep.getServerInfo();
		switch (sheep.getDirection()) {
		case "up":
			temp = sheep.getY() - 1;
			if (temp >= serverInfo.getStartY()) {
				sheep.setY(temp);
			} else {
				if (temp >= 0) {
					sheep.setY(temp);
					sheep.setTransfer(true);
				}
			}
			break;
		case "down":
			temp = sheep.getY() + 1;
			if (temp <= serverInfo.getEndY()) {
				sheep.setY(temp);
			} else {
				if (temp <= 99) {
					sheep.setY(temp);
					sheep.setTransfer(true);
				}
			}
			break;
		case "left":
			temp = sheep.getX() - 1;
			if (temp >= serverInfo.getStartX()) {
				sheep.setX(temp);
			} else {
				if (temp >= 0) {
					sheep.setX(temp);
					sheep.setTransfer(true);
				}
			}
			break;
		case "right":
			temp = sheep.getX() + 1;
			if (temp <= serverInfo.getEndX()) {
				sheep.setX(temp);
			} else {
				if (temp <= 99) {
					sheep.setX(temp);
					sheep.setTransfer(true);
				}
			}
			break;
		default:
			break;
		}
	}
}
