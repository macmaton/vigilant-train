package snakegame;

public class Poison extends GridObject {

	public Poison(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public boolean isObstruction() {
		return false;
	}
	
	@Override
	public boolean isEdible() {
		return false;
	}

}
