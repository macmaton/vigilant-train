package snakegame;

import java.awt.Point;

public class Snake {
	private SnakeSegment head;
	private SnakeSegment tail;
	private Point direction;
	private int length;
	private int maxLength;
	private int speed;
	
	public Snake(int headX, int headY, Point direction) {
		head = new SnakeSegment(headX, headY);
		tail = new SnakeSegment(headX-direction.x, headY-direction.y, head);
		this.direction = direction;
		length = 2;
		maxLength = length;
		speed = 1;
	}
	
	public SnakeSegment getHead() {
		return head;
	}
	
	public void setHead(SnakeSegment head) {
		this.head = head;
	}
	
	public SnakeSegment getTail() {
		return tail;
	}
	
	public void setTail(SnakeSegment tail) {
		this.tail = tail;
	}
	
	public Point getDirection() {
		return direction;
	}
	
	public void setDirection(Point direction) {
		this.direction = direction;
	}
	
	public void growSnake() {
		length++;
		if (length > maxLength) {
			maxLength++;
		}
	}
	
	public void shrinkSnake() {
		length--;
	}
	
	public int getLength() {
		return length;
	}
	
	public int getMaxLength() {
		return maxLength;
	}
	
	public int getSpeed() {
		return speed;
	}
	
	public void increaseSpeed() {
		speed++;
	}
}
	
