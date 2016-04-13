package model.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Tile {
	
	private boolean hasGrass;
	private int x;
	private int y;
	private List<Sheep> sheeps;
	
	public Tile(int x, int y) {
		this.x = x;
		this.y = y;
		hasGrass = true;
		sheeps = Collections.synchronizedList(new ArrayList<>());
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public boolean hasGrass() {
		return hasGrass;
	}
	
	public void setHasGrass(boolean bool) {
		hasGrass = bool;
	}
	
	public void addSheep(Sheep sheep) {
		sheeps.add(sheep);
	}
	
	public void removeSheep(Sheep sheep) {
		sheeps.remove(sheep);
	}
	
	public boolean hasSheep() {
		if(sheeps.size() > 0) {
			return true;
		}
		return false;
	}
	
}
