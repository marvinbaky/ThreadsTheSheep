package model.server;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import config.MapConfig;
import model.DTO.SheepDTO;

public class Farm {

	/**
	 * 
	 */
	private int[][] map;
	private Tile[][] tiles;
	private static List<Sheep> sheeps;

	public Farm() {
		int width = MapConfig.MAP_WIDTH;
		int height = MapConfig.MAP_HEIGHT;
		
		map = new int[width][height];
		tiles = new Tile[width][height];
		sheeps = Collections.synchronizedList(new ArrayList<>());
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				tiles[i][j] = new Tile(i, j);
			}
		}
	}

	/** code for controller **/
	public Boolean cellHasSheep (int x , int y){
		return map[x][y] == 1;
	}
	public Boolean cellHasGrass(int x, int y){
		return tiles[x][y].hasGrass();
	}
	/** **/
	
	public Sheep putNewSheep(Sheep sheep) {
		sheeps.add(sheep);
		//System.out.println("Sheep num: " + sheeps.size());
		map[sheep.getY()][sheep.getX()] = 1;
		tiles[sheep.getY()][sheep.getX()].addSheep(sheep);
		return sheep;
	}

//	public Sheep move(Sheep sheep, String direction) {
//		//System.out.println(sheep.getName() + " " + sheep.getX() + " " + sheep.getY() + " " + direction);
//		Sheep s = findSheep(sheep);
//		if (map[s.getY()][s.getX()] == 1) {
//			int temp;
//			switch (direction) {
//			case "up":
//				temp = s.getY() - 1;
//				if (temp >= 0) {
//					tiles[s.getY()][s.getX()].removeSheep(sheep);
//					if(!tiles[s.getY()][s.getX()].hasSheep()) {
//						map[s.getY()][s.getX()] = 0;
//					}
//					map[temp][s.getX()] = 1;
//					tiles[temp][s.getX()].addSheep(sheep);
//					s.setY(temp);
//				}
//				break;
//			case "down":
//				temp = s.getY() + 1;
//				if (temp <= 99) {
//					tiles[s.getY()][s.getX()].removeSheep(sheep);
//					if(!tiles[s.getY()][s.getX()].hasSheep()) {
//						map[s.getY()][s.getX()] = 0;
//					}
//					map[temp][s.getX()] = 1;
//					tiles[temp][s.getX()].addSheep(sheep);
//					s.setY(temp);
//				}
//				break;
//			case "left":
//				temp = s.getX() - 1;
//				if (temp >= 0) {
//					tiles[s.getY()][s.getX()].removeSheep(sheep);
//					if(!tiles[s.getY()][s.getX()].hasSheep()) {
//						map[s.getY()][s.getX()] = 0;
//					}
//					map[s.getY()][temp] = 1;
//					tiles[s.getY()][temp].addSheep(sheep);
//					s.setX(temp);
//				}
//				break;
//			case "right":
//				temp = s.getX() + 1;
//				if (temp <= 99) {
//					tiles[s.getY()][s.getX()].removeSheep(sheep);
//					if(!tiles[s.getY()][s.getX()].hasSheep()) {
//						map[s.getY()][s.getX()] = 0;
//					}
//					map[s.getY()][temp] = 1;
//					tiles[s.getY()][temp].addSheep(sheep);
//					s.setX(temp);
//				} else {//new code
//					s.setTransfer(true);
//				}
//				break;
//			}
//		} else {
//			// Sheep Error, not sheep
//			//System.out.println("NO SHEEP!");
//		}
//		return s;
//	}
	
	public Sheep plot(Sheep sheep, SheepDTO sheepDto) {
		Sheep s = findSheep(sheep);
		if (map[s.getY()][s.getX()] == 1) {
			tiles[s.getY()][s.getX()].removeSheep(sheep);
			if(!tiles[s.getY()][s.getX()].hasSheep()) {
				map[s.getY()][s.getX()] = 0;
			}
			map[sheepDto.getY()][sheepDto.getX()] = 1;
			tiles[sheepDto.getY()][sheepDto.getX()].addSheep(sheep);
			s.setY(sheepDto.getY());
			s.setX(sheepDto.getX());
		}
		return s;
		
	}
	
	public void remove(Sheep sheep) {
		Sheep s = findSheep(sheep);
		if (map[s.getY()][s.getX()] == 1) {
			tiles[s.getY()][s.getX()].removeSheep(sheep);
			if(!tiles[s.getY()][s.getX()].hasSheep()) {
				map[s.getY()][s.getX()] = 0;
			}
			removeSheep(s);
		}
		
	}
	
	public Sheep eat(Sheep sheep) {
		Sheep s = findSheep(sheep);
		if (map[s.getY()][s.getX()] == 1) {
			Tile tile = tiles[s.getY()][s.getX()];
			synchronized(tile) {
				if (tile.hasGrass()) {
					tile.setHasGrass(false);
					s.incrementScore();
				} else {
					// Error, no more grass
					//System.out.println("No more grass :(");
				}
			}
		}
		return s;
	}

	private Sheep findSheep(Sheep sheep) {
		return sheeps.get(sheeps.indexOf(sheep));
	}
	
	private void removeSheep(Sheep sheep) {
		sheeps.remove(sheep);
	}
	
	public List<SheepDTO> getSheepDTOs() {
		List<SheepDTO> sheepDtos = Collections.synchronizedList(new ArrayList<>());
//		for (Sheep sheep : sheeps) {
//			//System.out.println("b " + sheep.getX() + " " + sheep.getY());
//			sheepDtos.add(new SheepDTO(sheep.getName(), sheep.getX(), sheep.getY(), sheep.getScore()));
//		}
		
		synchronized(sheeps) {
			Iterator<Sheep> i = sheeps.iterator();
			while(i.hasNext()) {
				Sheep sheep = i.next();
				sheepDtos.add(new SheepDTO(sheep.getName(), sheep.getX(), sheep.getY(), sheep.getScore()));
			}
		}
		
		return sheepDtos;
	}
	
	public List<Sheep> getSheeps() {
		return sheeps;
	}
	
	public int[][] getFarmMap() {
		return map;
	}
}
