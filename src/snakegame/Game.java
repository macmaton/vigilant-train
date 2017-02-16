package snakegame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.lang.Thread;
import java.util.Random;

import javax.swing.*;

public class Game {

	public static void main(String[] args) throws InterruptedException {
		
		GameSpace gamespace = new GameSpace(20,20);
		
		JFrame frame = new JFrame("Snake Game");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setIgnoreRepaint(true);
		
		JLabel snakeLength = new JLabel("Current Snake Length: " + gamespace.getSnake().getLength());
		frame.add(snakeLength);
		JLabel maxSnakeLength = new JLabel("Maximum Snake Length: " + gamespace.getSnake().getMaxLength());
		frame.add(maxSnakeLength);
		JLabel numPoisons = new JLabel("Number of Poisons: " + gamespace.getNumPoisons());
		frame.add(numPoisons);
		
		GameCanvas canvas = new GameCanvas(gamespace);
		canvas.setPreferredSize(new Dimension(10*gamespace.getGameSpaceWidth(), 10*gamespace.getGameSpaceHeight()));
		canvas.setIgnoreRepaint(true);
		canvas.setBackground(Color.WHITE);
		canvas.setFocusable(true);
		frame.add(canvas);
		
		frame.pack();
		frame.setLayout(null);
		frame.setVisible(true);
		
		canvas.requestFocusInWindow();

		canvas.createBufferStrategy(2);
		BufferStrategy buffer = canvas.getBufferStrategy();
		
		GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice device = environment.getDefaultScreenDevice();
		GraphicsConfiguration configuration = device.getDefaultConfiguration();
		
		BufferedImage image = configuration.createCompatibleImage(canvas.getWidth(), canvas.getHeight());
		
		Graphics graphics;
		Graphics2D g2d;

//		Variable update time version
		long lastUpdateTime = System.nanoTime();
		long delta;
		long maxDelta = 6000000000L;
		long smoothing = 0;
		
		while (true)
		{
			delta = 10000000 + (maxDelta/(10+(2*gamespace.getSnake().getSpeed())));
			long now = System.nanoTime();
			long actualDelta = (now-lastUpdateTime) + smoothing; 
			if (actualDelta > delta) {
				lastUpdateTime = now;
				gamespace.updateGameSpace();
				
				//update graphics
				g2d = image.createGraphics();
				canvas.render(g2d);
				graphics = buffer.getDrawGraphics();
				graphics.drawImage(image, 0, 0, null);
				if(!buffer.contentsLost()) {
					buffer.show();
					Thread.yield();
					
				}
				if(graphics != null) {
					graphics.dispose();
				}
				if(g2d != null) {
					g2d.dispose();
				}

				smoothing = delta - actualDelta;
			}
			
			Thread.sleep(5);
		}
	}	
}
