package com.cyberpunk.StartGame;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

import com.cyberpunk.Object.GameWorld;

public class GamePanel extends JPanel implements Runnable, KeyListener{
	private static final long serialVersionUID = 1L;
	
	// Vị trí vẽ map game
	public static int posX = 0, posY = 0;
	
    private Thread gameThread;
    private boolean isRunning;
    private InputManager inputManager;
    public GameWorld gameWorld;
    
    private long FPS = 30;
    private long miliSecond = 1000;
    private long nanoMiliSecond = 1000000;
    
     public GamePanel() {
    	 isRunning = true;
    	 gameWorld = new GameWorld();
    	 inputManager = new InputManager(gameWorld);
     }
     
     public void startGame(){
         gameThread = new Thread(this);
         gameThread.start();
     }
     
     @Override
     protected void paintComponent(Graphics g) {
         super.paintComponent(g);
         g.drawImage(gameWorld.getBufferedImage(), posX, posY, this);
     }

     
//    @Override
//    public void paint(Graphics g) {
//    	g.drawImage(gameWorld.getBufferedImage(), 0, 0, this);
//    }

    @Override
    public void run() {
    	long period = miliSecond * nanoMiliSecond / FPS;
    	long beginTime = System.nanoTime();
    	long sleepTime;
    	
    	while(isRunning) {
    		
    		gameWorld.Update();
    		gameWorld.Render();
    		repaint();
    		
    		long deltaTime = System.nanoTime() - beginTime;
    		sleepTime = period - deltaTime;
    		
    		try {
    			if(sleepTime > 0) {
    				Thread.sleep(sleepTime / nanoMiliSecond);
    			} else {
    				Thread.sleep(period / (2 * nanoMiliSecond));
    			}
    			
			} catch (InterruptedException ex) {}
    		
    		beginTime = System.nanoTime();
    	}
    }

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		inputManager.processKeyPressed(e.getKeyCode());
	}

	@Override
	public void keyReleased(KeyEvent e) {
		inputManager.processKeyReleased(e.getKeyCode());
	}
}
