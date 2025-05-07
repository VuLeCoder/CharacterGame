package com.cyberpunk.StartGame;

import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JPanel;

import com.cyberpunk.Object.GameWorld;
import com.cyberpunk.Object.StartScreen;

public class GamePanel extends JPanel implements Runnable {
	private static final long serialVersionUID = 1L;

	// Vị trí vẽ map game
//	public static int MAP_DRAW_X = 0, MAP_DRAW_Y = 0;
	public static final int MAP_WIDTH = GameWorld.TILESIZE * 40;
    public static final int MAP_HEIGHT = GameWorld.TILESIZE * 20;

	private Thread gameThread;
	private boolean isRunning;
	public GameWorld gameWorld;
	public KeyConfig keyConfig;
	public InputManager inputManager1, inputManager2;

//	private long FPS = 50;
	private long FPS = 60;
	private long miliSecond = 1000;
	private long nanoMiliSecond = 1000000;
	
	private final Rectangle buttonBounds = new Rectangle(StartScreen.BUTTON_X, StartScreen.BUTTON_Y, StartScreen.BUTTON_WIDTH, StartScreen.BUTTON_HEIGHT);
    private boolean isHovering = false;

	public GamePanel() {
		setDoubleBuffered(true);
		
		isRunning = true;
		gameWorld = new GameWorld();
		keyConfig = new KeyConfig();
		
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				if (gameWorld.isStartGame()) {
					return;
				}

				boolean hovering = buttonBounds.contains(e.getPoint());
				if (hovering != isHovering) {
					isHovering = hovering;
					setCursor(hovering ? Cursor.getPredefinedCursor(Cursor.HAND_CURSOR) : Cursor.getDefaultCursor());
				}
			}
		});
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (gameWorld.isStartGame()) {
					return;
				}
				
				if (buttonBounds.contains(e.getPoint())) {
					gameWorld.StartGame();
					setCursor(Cursor.getDefaultCursor());
				}
			}
		});
		
		this.setFocusable(true);
		inputManager1 = new InputManager(gameWorld.getP1(), keyConfig.getP1_KeyMap());
		inputManager1.register(this);
		
		inputManager2 = new InputManager(gameWorld.getP2(), keyConfig.getP2_KeyMap());
		inputManager2.register(this);
	}

	public void startGame() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(gameWorld.getBufferedImage(), 0, 0, this);
//		g.drawImage(gameWorld.getBufferedImage(), MAP_DRAW_X, MAP_DRAW_Y, this);
	}
	
	@Override
	public void run() {
		long period = miliSecond * nanoMiliSecond / FPS;
		long beginTime = System.nanoTime();
		long sleepTime;

		while (isRunning) {

			gameWorld.Update();
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
}
