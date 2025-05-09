package com.cyberpunk.StartGame;

import java.awt.Graphics;

import javax.swing.JPanel;

import com.cyberpunk.Object.GameWorld;

public class GamePanel extends JPanel implements Runnable {
	private static final long serialVersionUID = 1L;
	
	private final GameFrame window;

	// Vị trí vẽ map game
	public static final int MAP_WIDTH = GameWorld.TILESIZE * 40;
    public static final int MAP_HEIGHT = GameWorld.TILESIZE * 20;
    public static final int HUD_HEIGHT = 100; //Heads-Up Display
    
    private boolean isChangeScreenSize;

	private Thread gameThread;
	private boolean isRunning;
	public GameWorld gameWorld;
	public KeyConfig keyConfig;
	public InputManager inputManager1, inputManager2;

	private final long FPS = 60;
	private final long miliSecond = 1000;
	private final long nanoMiliSecond = 1000000;

	public GamePanel(GameFrame gameFrame) {
		this.window = gameFrame;
		setDoubleBuffered(true);
		
		isChangeScreenSize = false;
		isRunning = true;
		gameWorld = new GameWorld(this);
		keyConfig = new KeyConfig();
		
		// Sự kiện bàn phím
		this.setFocusable(true);
		inputManager1 = new InputManager(gameWorld.getP1(), keyConfig.getP1_KeyMap());
		inputManager1.register(this);
		
		inputManager2 = new InputManager(gameWorld.getP2(), keyConfig.getP2_KeyMap());
		inputManager2.register(this);
	}

	public void startRunning() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(gameWorld.getBufferedImage(), 0, 0, this);
	}
	
	@Override
	public void run() {
		long period = miliSecond * nanoMiliSecond / FPS;
		long beginTime = System.nanoTime();
		long sleepTime;

		while (isRunning) {

			gameWorld.Update();
			if(!gameWorld.getScreenManager().isNotInitializedGameYet()) {
				UpdateScreen(GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT + HUD_HEIGHT);
			}
			
			gameWorld.Render();
			repaint();

			long deltaTime = System.nanoTime() - beginTime;
			sleepTime = period - deltaTime;

			try {
				if (sleepTime > 0) {
					Thread.sleep(sleepTime / nanoMiliSecond);
				} else {
					Thread.sleep(period / (2 * nanoMiliSecond));
				}

			} catch (InterruptedException ex) {
			}

			beginTime = System.nanoTime();
		}
	}
	
	private void UpdateScreen(int width, int height) {
		if(isChangeScreenSize) {
			return;
		}
		
		isChangeScreenSize = true;
		window.setScreenSize(width, height);
	}
}
