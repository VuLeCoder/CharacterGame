package com.cyberpunk.Object;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.cyberpunk.Effect.DataLoader;
import com.cyberpunk.StartGame.GameFrame;

public class GameWorld{
	public static int TILESIZE = 32;
	private static int pointX = 22, pointY = 14; // Điểm rơi hộp
	
	private final BufferedImage bufferedImage;
	private final MapGame mapGame;
	private final int[][] animatedMap;
	private final StartScreen startScreen;
	
	private boolean isStartGame = false;
	private boolean isFirstDrawMap = true;
//	private AnimatedObjectManager animatedObjectManager;
	private ObjectManager objectManager;
	public SkillManager skillManager;
	
	private BaseCharacter P1;
	public BaseCharacter P2;

	public GameWorld() {
		bufferedImage = new BufferedImage(GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT, BufferedImage.TYPE_INT_ARGB);
		
		startScreen = new StartScreen();
		mapGame = new MapGame();
		animatedMap = DataLoader.getInstance().getAnimatedMap();

		
//		animatedObjectManager = new AnimatedObjectManager(this);
		objectManager = new ObjectManager(this);
		skillManager = new SkillManager(this);
		
		P1 = new BaseCharacter(100, 500, "biker", this);
		objectManager.addObject(P1);
		
		P2 = new BaseCharacter(900, 500, "biker", this);
		objectManager.addObject(P2);
		
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
	
	public ObjectManager getObjectManager() {
		return objectManager;
	}
	
	public BaseCharacter getP1() {
		return P1;
	}

	public BaseCharacter getP2() {
		return P2;
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
		if(!isStartGame) {
			startScreen.Update();
			return;
		}
		
		objectManager.dropBox(pointX, pointY, System.nanoTime());
		objectManager.UpdateObjects();
//		while (!objectManager.checkCharacter()) {
//			objectManager.addObject(new BaseCharacter(100, 500, "biker", this));
//		}
		
		skillManager.UpdateObjects();
	}
	
	public void Render(float zoom) {
		Graphics2D g2 = (Graphics2D) bufferedImage.getGraphics();
		if(g2 == null) {
			return;
		}

		if(!isStartGame) {
			startScreen.draw(g2);
			return;
		}
		
//		g2.scale(2f, 2f);
//		if(isFirstDrawMap) {
//			mapGame.draw(g2);
//			isFirstDrawMap = false;
//		}
		g2.drawImage(mapGame.getCachedMapImage(), 0, 0, null);
		
		objectManager.draw(g2);
		
		skillManager.draw(g2);
	}
}
