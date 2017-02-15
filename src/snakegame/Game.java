package snakegame;

import java.awt.Color;
import java.awt.Dimension;
import java.lang.Thread;

import javax.swing.*;

public class Game {

	public static void main(String[] args) throws InterruptedException {
		
		GameSpace gamespace = new GameSpace(20,30);
		
		JFrame frame = new JFrame("Snake Game");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		GamePanel panel = new GamePanel(gamespace);
		panel.setPreferredSize(new Dimension(10*gamespace.getGameSpaceWidth(), 10*gamespace.getGameSpaceHeight()));
		panel.setBackground(Color.WHITE);
		panel.setIgnoreRepaint(true);
		
		frame.add(panel);
		frame.pack();
		frame.setLayout(null);
		frame.setVisible(true);
		
		panel.requestFocusInWindow();
		
		long lastUpdateTime = System.nanoTime();
		long delta;
		long maxDelta = 5000000000L;
		long smoothing = 0;
		
		while (true)
		{
			delta = 50000000 + (maxDelta/(10+gamespace.snake.getSpeed()));
			long now = System.nanoTime();
			long actualDelta = (now-lastUpdateTime) + smoothing; 
			if (actualDelta > delta) {
				lastUpdateTime = now;
				gamespace.updateGameSpace();
				panel.repaint(0);
				smoothing = delta - actualDelta;
			}
			
			Thread.sleep(5);
		}

	}
}
