package com.cyberpunk.StartGame;

import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JPanel;

import com.cyberpunk.Object.GameWorld;
import com.cyberpunk.Object.StartScreen;

public class GamePanel extends JPanel implements Runnable, KeyListener {
	private static final long serialVersionUID = 1L;

	// Vị trí vẽ map game
	public static int posX = 0, posY = 0;

	private Thread gameThread;
	private boolean isRunning;
	private InputManager inputManager;
	public GameWorld gameWorld;

	private long FPS = 60;
	private long miliSecond = 1000;
	private long nanoMiliSecond = 1000000;
	
	private final Rectangle buttonBounds = new Rectangle(StartScreen.BUTTON_X, StartScreen.BUTTON_Y, StartScreen.BUTTON_WIDTH, StartScreen.BUTTON_HEIGHT);
    private boolean isHovering = false;

	public GamePanel() {
		setDoubleBuffered(true);
		
		isRunning = true;
		gameWorld = new GameWorld();
		inputManager = new InputManager(gameWorld);
		
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

	}

	public void startGame() {
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
		float zoom = 1.0f;

		while (isRunning) {

			if ((this.getWidth() * this.getHeight() != 0)) {
				zoom = GameFrame.SCREEN_WIDTH * GameFrame.SCREEN_HEIGHT / (this.getWidth() * this.getHeight());
			} else {
				zoom = 1.0f;
			}
//    		System.setProperty("sun.java2d.uiScale", "" + zoom);
//    		System.out.println(zoom);

			gameWorld.Update();
			gameWorld.Render(zoom);
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

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		inputManager.processKeyPressed(e.getKeyCode());
	}

	@Override
	public void keyReleased(KeyEvent e) {
		inputManager.processKeyReleased(e.getKeyCode());
	}
}
