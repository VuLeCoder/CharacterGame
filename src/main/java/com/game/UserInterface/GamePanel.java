package com.game.UserInterface;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

import com.game.GameObjects.GameWorld;

public class GamePanel extends JPanel implements Runnable, KeyListener{
	private static final long serialVersionUID = 1L;
	
    private Thread gameThread;
    
    private boolean isRunning = true;
    
    private static long oneM = 1000000;
    
    private InputManager inputManager;
    
    public GameWorld gameWorld;
    
     public GamePanel() {
    	 gameWorld = new GameWorld();
    	 inputManager = new InputManager(gameWorld);
     }
     
     public void startGame(){
         gameThread = new Thread(this);
         gameThread.start();
     }

    @Override
    public void paint(Graphics g) {
    	g.drawImage(gameWorld.getBufferedImage(), 0, 0, this);
    }

    @Override
    public void run() {
    	long FPS = 70;
    	long period = 1000 * oneM / FPS;
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
    				Thread.sleep(sleepTime / oneM);
    			} else {
    				Thread.sleep(period / (2 * oneM));
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
