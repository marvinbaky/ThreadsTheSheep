package model.client;

import java.util.ArrayList;
import java.util.List;

import model.DTO.ServerInfoDTO;
import model.DTO.SheepDTO;
import model.client.Tile;

//Client Farm
public class Farm {
	private int[][] map;
	private Tile[][] tiles;
	private List<Sheep> sheeps;
	private boolean hasTiles;

	public Farm() {
		map = new int[100][100];
		tiles = new Tile[100][100];
		sheeps = new ArrayList<>();
		
//		for (int i = 0; i < 100; i++) {
//			for (int j = 0; j < 100; j++) {
//				tiles[i][j] = new Tile(i, j);
//			}
//		}
	}
	
	public Farm(Tile[][] tiles) {
		map = new int[100][100];
		this.tiles = tiles;
		sheeps = new ArrayList<>();
		hasTiles = true;
	}
	
	public boolean hasTiles() {
		return hasTiles;
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

	public void plot(List<SheepDTO> sheeps) {

		Sheep currSheep;
		SheepDTO currSheepDto;
		for (int i = 0; i < sheeps.size(); i++) {
			currSheepDto = sheeps.get(i);
			currSheep = findSheep(currSheepDto);
			if (currSheep == null) {
				currSheep = new Sheep(currSheepDto.getUsername(), currSheepDto.getX(), currSheepDto.getY(),
						currSheepDto.getScore());
				this.sheeps.add(currSheep);
				
				map[currSheepDto.getY()][currSheepDto.getX()] = 1;
				tiles[currSheepDto.getY()][currSheepDto.getX()].addSheep(currSheep);
				if(currSheepDto.isEat()) {
					tiles[currSheepDto.getY()][currSheepDto.getX()].setHasGrass(false);
				}
				currSheep.setY(currSheepDto.getY());
				currSheep.setX(currSheepDto.getX());
			} else {
				if (map[currSheep.getY()][currSheep.getX()] == 1) {
					tiles[currSheep.getY()][currSheep.getX()].removeSheep(currSheep);
					if(!tiles[currSheep.getY()][currSheep.getX()].hasSheep()) {
						map[currSheep.getY()][currSheep.getX()] = 0;
					}
					map[currSheepDto.getY()][currSheepDto.getX()] = 1;
					tiles[currSheepDto.getY()][currSheepDto.getX()].addSheep(currSheep);
					if(currSheepDto.isEat()) {
						tiles[currSheepDto.getY()][currSheepDto.getX()].setHasGrass(false);
					}
					currSheep.setY(currSheepDto.getY());
					currSheep.setX(currSheepDto.getX());
				}

			}
			
			
		}
	}

	public Sheep findSheep(SheepDTO sheepDto) {
		Sheep sheep = new Sheep(sheepDto.getUsername(), sheepDto.getX(), sheepDto.getY(),
				sheepDto.getScore());
		if (sheeps.contains(sheep)) {
			return sheeps.get(sheeps.indexOf(sheep));
		}
		return null;
	}
}
