package snakegame;

import java.awt.Point;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class GameSpace {
	
	public static final Point UP= new Point(0,-1);
	public static final Point DOWN = new Point(0,1);
	public static final Point LEFT = new Point(-1,0);
	public static final Point RIGHT = new Point(1,0);
	
	private GridObject[][] grid;
	private Random random;
	private Snake snake;
	private Food food;
	private Set<Poison> poisons;
	private GameState gameState;
	
	public GameSpace(int width, int height) {
		grid = new GridObject[width][height];
		random = new Random();
		snake = new Snake(getGameSpaceCenterX(), getGameSpaceCenterY(), UP);
		setTileContents(snake.getHead());
		setTileContents(snake.getTail());
		poisons = new HashSet<Poison>();
		placeFood();
		gameState = GameState.PLAY;
	}
	
	public void setTileContents(GridObject object) {
		grid[object.x][object.y] = object;
	}
	
	public void clearTileContents(GridObject object) {
		grid[object.x][object.y] = null;
	}
	
	public GridObject getTileContents(Point tile) {
		return grid[tile.x][tile.y];
	}
	
	public int getGameSpaceWidth() {
		return grid.length;
	}
	
	public int getGameSpaceHeight() {
		return grid[0].length;
	}
	
	public int getGameSpaceCenterX() {
		return (int)(grid.length/2);
	}
	
	public int getGameSpaceCenterY() {
		return (int)(grid[0].length/2);
	}
	
	public Snake getSnake() {
		return snake;
	}
	
	public void setSnake(Snake s) {
		snake = s;
	}
	
	public GameState getGameState() {
		return gameState;
	}
	
	public void setGameState(GameState g) {
		gameState = g;
	}
	
	public int getNumPoisons() {
		return poisons.size();
	}
	
	public Point getSnakeDestination() {
		Point destination = new Point(snake.getHead().x+snake.getDirection().x, snake.getHead().y+snake.getDirection().y);
		return destination;
	}
	
	public boolean isEmpty(Point tile) {
		if (getTileContents(tile) == null) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isValidMovement(Point snakeDestination) {
		if (0 <= snakeDestination.x && snakeDestination.x < getGameSpaceWidth() &&
				0 <= snakeDestination.y && snakeDestination.y < getGameSpaceHeight()) {
			if (isEmpty(snakeDestination)) {
				return true;
			} else if (!getTileContents(snakeDestination).isObstruction()) {
				return true;
			}
		}
		return false;
	}
	
	//spaces occupied by snake, food, poison, and the snake's next destination are not available
	public boolean isSpaceAvailable() {
		if ((2+snake.getLength()+poisons.size()) < (getGameSpaceWidth()*getGameSpaceHeight())) {
			return true;
		} else {
			return false;
		}
	}
	
	//assumes an empty tile exists
	public Point getRandomEmptyTile() {
		int proposedX;
		int proposedY;
		Point location;
		while (true) {
			proposedX = random.nextInt(getGameSpaceWidth());
			proposedY = random.nextInt(getGameSpaceHeight());
			location = new Point(proposedX, proposedY);
			if (isEmpty(location) && !location.equals(getSnakeDestination())) {
				break;
			}
		}
		return location;
	}
	
	public void feedSnake(Point snakeDestination) {
		SnakeSegment newHead = new SnakeSegment(snakeDestination.x, snakeDestination.y);
		snake.getHead().setPreviousSegment(newHead);
		snake.setHead(newHead);
		setTileContents(snake.getHead());
		snake.growSnake();
		snake.increaseSpeed();
		placeFood();
		placePoison();
	}
	
	public void moveSnake(Point snakeDestination) {
		SnakeSegment newHead = new SnakeSegment(snakeDestination.x, snakeDestination.y);
		snake.getHead().setPreviousSegment(newHead);
		snake.setHead(newHead);
		setTileContents(snake.getHead());
		clearTileContents(snake.getTail());
		snake.setTail(snake.getTail().getPreviousSegment());
		setTileContents(snake.getTail());
	}
	
	public void poisonSnake(Point snakeDestination) {
		poisons.remove(getTileContents(snakeDestination));
		snake.shrinkSnake();
		if (snake.getLength() < 2) {
			gameState = GameState.END;
		} else {
			SnakeSegment newHead = new SnakeSegment(snakeDestination.x, snakeDestination.y);
			snake.getHead().setPreviousSegment(newHead);
			snake.setHead(newHead);
			setTileContents(snake.getHead());
			clearTileContents(snake.getTail());
			clearTileContents(snake.getTail().getPreviousSegment());
			snake.setTail(snake.getTail().getPreviousSegment().getPreviousSegment());
			setTileContents(snake.getTail());
			placePoison();	
		}
	}
	
	public void placeFood() {
		if (isSpaceAvailable()) {
			Point destination = getRandomEmptyTile();
			food = new Food(destination.x, destination.y);
			setTileContents(food);
		}
	}
	
	public void placePoison() {
		if (isSpaceAvailable()) {
			Point destination = getRandomEmptyTile();
			Poison poison = new Poison(destination.x, destination.y);
			poisons.add(poison);
			setTileContents(poison);
		}
	}
	
	public void updateGameSpace() {
		Point snakeDestination = getSnakeDestination();
		if (isValidMovement(snakeDestination)) {
			if (isEmpty(snakeDestination)) {
				moveSnake(snakeDestination);
			} else if (getTileContents(snakeDestination).isEdible()) {
				feedSnake(snakeDestination);
			} else {
				poisonSnake(snakeDestination);
			}
		} else {
			gameState = GameState.END;
		}
	}
	
	public void turnSnake(Point newDirection) {
		if ((((snake.getDirection().x + newDirection.x) != 0) || ((snake.getDirection().y + newDirection.y) != 0))) {
			snake.setDirection(newDirection);
		}
	}

}
