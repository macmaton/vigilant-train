package snakegame;

public class SnakeSegment extends GridObject {
	private SnakeSegment previous;
	
	public SnakeSegment(int x, int y, SnakeSegment previous) {
		this.previous = previous;
		this.x = x;
		this.y = y;
	}
	
	public SnakeSegment(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public SnakeSegment getPreviousSegment() {
		return previous;
	}
	
	public void setPreviousSegment(SnakeSegment previous) {
		this.previous = previous;
	}
	
	@Override
	public boolean isObstruction() {
		return true;
	}
	
	@Override
	public boolean isEdible() {
		return false;
	}

}
