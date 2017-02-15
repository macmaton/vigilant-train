package snakegame;

import java.awt.Point;

public class GridObject extends Point {
	
	//for snake collision detection
	public boolean isObstruction() {
		return true;
	}
	
	public boolean isEdible() {
		return true;
	}
}
