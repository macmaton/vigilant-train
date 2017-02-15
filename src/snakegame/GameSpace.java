package snakegame;

import java.awt.Point;
import java.util.Random;

public class GameSpace {
	
	public static final Point UP= new Point(0,-1);
	public static final Point DOWN = new Point(0,1);
	public static final Point LEFT = new Point(-1,0);
	public static final Point RIGHT = new Point(1,0);
	
	GridObject[][] grid;
	Random random;
	Snake snake;
	Food food;
	GameState gameState;
	
	public GameSpace(int width, int height) {
		grid = new GridObject[width][height];
		random = new Random();
		snake = new Snake(getGameSpaceCenterX(), getGameSpaceCenterY(), UP);
		setTileContents(snake.getHead());
		setTileContents(snake.getTail());
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
	
	public Point getSnakeDestination() {
		Point destination = new Point(snake.getHead().x+snake.getDirection().x, snake.getHead().y+snake.getDirection().y);
		return destination;
	}
	
	public Boolean isEmpty(Point tile) {
		if (getTileContents(tile) == null) {
			return true;
		} else {
			return false;
		}
	}
	
	public Boolean isValidMovement(Point snakeDestination) {
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
	
	public void feedSnake(Point snakeDestination) {
		SnakeSegment newHead = new SnakeSegment(snakeDestination.x, snakeDestination.y);
		snake.getHead().setPreviousSegment(newHead);
		snake.setHead(newHead);
		setTileContents(snake.getHead());
		snake.growSnake();
		snake.increaseSpeed();
		placeFood();
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
		snake.shrinkSnake();
		if (snake.getLength() < 2) {
			gameState = GameState.END;
		}
		SnakeSegment newHead = new SnakeSegment(snakeDestination.x, snakeDestination.y);
		snake.getHead().setPreviousSegment(newHead);
		snake.setHead(newHead);
		setTileContents(snake.getHead());
		clearTileContents(snake.getTail());
		clearTileContents(snake.getTail().getPreviousSegment());
		snake.setTail(snake.getTail().getPreviousSegment().getPreviousSegment());
		setTileContents(snake.getTail());
		//placePoison();
	}
	
	public void placeFood() {
		int proposedX;
		int proposedY;
		Food foodDestination;
		while (true) {
			proposedX = random.nextInt(getGameSpaceWidth());
			proposedY = random.nextInt(getGameSpaceHeight());
			foodDestination = new Food(proposedX, proposedY);
			if (isEmpty(foodDestination)) {
				break;
			}
		}
		food = foodDestination;
		setTileContents(food);
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
