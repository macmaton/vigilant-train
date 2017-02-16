package snakegame;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameCanvas extends Canvas implements KeyListener {
	
	GameSpace gamespace;
	
	public GameCanvas(GameSpace g) {
		super();
		gamespace = g;
		this.addKeyListener(this);
	}
	
	public void render(Graphics2D g) {
		g.setColor(this.getBackground());
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		for (int i = 0; i < gamespace.getGameSpaceWidth(); i++) {
			for (int j = 0; j < gamespace.getGameSpaceHeight(); j++) {
				Point tile = new Point(i,j);
				if (gamespace.getTileContents(tile) == null) {
					g.setColor(this.getBackground());
//					g.fillRect(10*i, 10*j, 10, 10);
				} else {
					if (gamespace.getTileContents(tile).isObstruction()) {
						g.setColor(Color.BLUE);
						g.fillOval(10*i, 10*j, 10, 10);
					} else {
						if (gamespace.getTileContents(tile).isEdible()) {
							g.setColor(Color.GREEN);
							g.fillOval(10*i, 10*j, 10, 10);
						} else {
							g.setColor(Color.RED);
							g.fillOval(10*i, 10*j, 10, 10);
						}
					}
				}
			}
		}
	}
	
	@Override
	public void keyPressed(KeyEvent event) {
		int keyCode = event.getKeyCode();
		if (keyCode == event.VK_LEFT) {
			gamespace.turnSnake(GameSpace.LEFT);
		} else if (keyCode == event.VK_RIGHT) {
			gamespace.turnSnake(GameSpace.RIGHT);
		} else if (keyCode == event.VK_UP) {
			gamespace.turnSnake(GameSpace.UP);
		} else if (keyCode == event.VK_DOWN) {
			gamespace.turnSnake(GameSpace.DOWN);
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
