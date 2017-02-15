package snakegame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements KeyListener {
	
	GameSpace gamespace;
	
	public GamePanel(GameSpace g) {
		super();
		gamespace = g;
		this.addKeyListener(this);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(getBackground());
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		for (int i = 0; i < gamespace.getGameSpaceWidth(); i++) {
			for (int j = 0; j < gamespace.getGameSpaceHeight(); j++) {
				Point tile = new Point(i,j);
				if (gamespace.getTileContents(tile) == null) {
					g.setColor(getBackground());
//					g.fillRect(10*i, 10*j, 10, 10);
				} else {
					if (gamespace.getTileContents(tile) instanceof snakegame.SnakeSegment) {
						g.setColor(Color.BLUE);
						g.fillOval(10*i, 10*j, 10, 10);
					} else {
						if (gamespace.getTileContents(tile) instanceof snakegame.Food) {
							g.setColor(Color.GREEN);
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
