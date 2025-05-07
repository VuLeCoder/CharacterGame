package com.cyberpunk.Object;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.cyberpunk.Effect.DataLoader;
import com.cyberpunk.Screen.ScreenManager;
import com.cyberpunk.StartGame.GamePanel;

public class GameWorld{
	public static final int TILESIZE = 32;
	private final static int pointX = 22, pointY = 14; // Điểm rơi hộp
	
	private final BufferedImage bufferedImage;
//	private final StartScreen startScreen;
	private final ScreenManager screenManager;
	private final MapGame mapGame;
	private final int[][] animatedMap;
	
	private boolean isStartGame = false;
	private boolean isFirstDrawMap = true;

	private final Camera camera;
	private final ObjectManager objectManager;
	
	private final BaseCharacter P1;
	private final BaseCharacter P2;

	public GameWorld() {
		bufferedImage = new BufferedImage(GamePanel.MAP_WIDTH, GamePanel.MAP_HEIGHT, BufferedImage.TYPE_INT_ARGB);
		// bufferedImage = new BufferedImage(GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT, BufferedImage.TYPE_INT_ARGB);
		
//		startScreen = new StartScreen();
		screenManager = new ScreenManager();
		
		mapGame = new MapGame();
		animatedMap = DataLoader.getInstance().getAnimatedMap();

		objectManager = new ObjectManager(this);
		
		P1 = new BaseCharacter(100, 500, "biker", this);
		objectManager.addObject(P1);
		
		
		P2 = new BaseCharacter(100, 500, "biker", this);
		objectManager.addObject(P2);
		
		camera = new Camera(0, 0, GamePanel.MAP_WIDTH, GamePanel.MAP_HEIGHT, this);
//		camera = new Camera(GamePanel.MAP_DRAW_X, GamePanel.MAP_DRAW_Y, GamePanel.MAP_WIDTH, GamePanel.MAP_HEIGHT, this);

		addAllAnimatedObject();
	}
	
	public boolean isStartGame() {
		return isStartGame;
	}
	
	public void StartGame() {
		isStartGame = true;
	}
	
	public BufferedImage getBufferedImage(){
        return bufferedImage;
    }
	
	public int getAnimatedMap(int x, int y) {
		return animatedMap[x][y];
	}

	public MapGame getMapGame() {
		return mapGame;
	}
	
	public ScreenManager getScreenManager() {
		return screenManager;
	}
	
	public ObjectManager getObjectManager() {
		return objectManager;
	}
	
	public BaseCharacter getP1() {
		return P1;
	}

	public BaseCharacter getP2() {
		return P2;
	}
	
	public Camera getCamera() {
		return camera;
	}
	
	private void addAllAnimatedObject() {
		for(int i=0; i<animatedMap.length; ++i) {
			for(int j=0; j<animatedMap[0].length; ++j) {
				if(animatedMap[i][j] == -1) {
					continue;
				}

				objectManager.addObject(new MapObject(j * GameWorld.TILESIZE, i * GameWorld.TILESIZE, this, animatedMap[i][j]));
			}
		}
	}

	public void Update() {
//		if(!isStartGame) {
//			startScreen.Update();
//			return;
//		}
		if(screenManager.isNotInitializedGameYet()) {
			screenManager.Update();
			return;
		}
		
		camera.Update();
		objectManager.dropBox(pointX, pointY, System.nanoTime());
		objectManager.UpdateObjects();
//		while (!objectManager.checkCharacter()) {
//			objectManager.addObject(new BaseCharacter(100, 500, "biker", this));
//		}
	}
	
	float currentZoomX = 1.0f, currentZoomY = 1.0f;
	
	public void Render() {
		Graphics2D g2 = (Graphics2D) bufferedImage.getGraphics();
		if(g2 == null) {
			return;
		}

//		if(!isStartGame) {
//			startScreen.draw(g2);
//			return;
//		}
		
		if(screenManager.isNotInitializedGameYet()) {
			screenManager.draw(g2);
			return;
		}

		
		g2.setColor(Color.BLACK);
		// g2.fillRect(0, 0, GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT);
		g2.fillRect(0, 0, GamePanel.MAP_WIDTH, GamePanel.MAP_HEIGHT);
		
		float targetZoomX = GamePanel.MAP_WIDTH / camera.getWidthView();
		float targetZoomY = GamePanel.MAP_HEIGHT / camera.getHeightView();

		currentZoomX += (targetZoomX - currentZoomX) * Camera.SMOOTH_FACTOR;
		currentZoomY += (targetZoomY - currentZoomY) * Camera.SMOOTH_FACTOR;
		g2.scale(currentZoomX, currentZoomY);
		
//		if(isFirstDrawMap) {
//			mapGame.draw(g2);
//			isFirstDrawMap = false;
//		}

		g2.translate(-camera.getPosX(),- camera.getPosY());
		
		// Vẽ map
		g2.drawImage(mapGame.getCachedMapImage(), 0, 0, null);
//		g2.drawImage(mapGame.getCachedMapImage(), GamePanel.MAP_DRAW_X, -GamePanel.MAP_DRAW_Y, null);
		
		camera.draw(g2);
		objectManager.draw(g2);
	}
}
