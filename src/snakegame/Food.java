package snakegame;

public class Food extends GridObject {
	
	public Food(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public boolean isObstruction() {
		return false;
	}
	
	@Override
	public boolean isEdible() {
		return true;
	}
}
