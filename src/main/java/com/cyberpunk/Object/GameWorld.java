package com.cyberpunk.Object;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.cyberpunk.Effect.DataLoader;
import com.cyberpunk.StartGame.GameFrame;

public class GameWorld {
	public static int TILESIZE = 32;
	private static int pointX = 22, pointY = 14; // Điểm rơi hộp
	
	private final BufferedImage bufferedImage;
	private final MapGame mapGame;
	private final int[][] animatedMap;
	private final StartScreen startScreen;
	
	private boolean isStartGame = false;
	private boolean isFirstDrawMap = true;
	private AnimatedObjectManager animatedObjectManager;
	public BaseCharacter baseCharacter;

	public GameWorld() {
		bufferedImage = new BufferedImage(GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT, BufferedImage.TYPE_INT_ARGB);
		
		startScreen = new StartScreen();
		mapGame = new MapGame();
		
		baseCharacter = new BaseCharacter(100, 500, "biker", this);

		animatedMap = DataLoader.getInstance().getAnimatedMap();
		
		animatedObjectManager = new AnimatedObjectManager(this);
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
	
	private void addAllAnimatedObject() {
		for(int i=0; i<animatedMap.length; ++i) {
			for(int j=0; j<animatedMap[0].length; ++j) {
				if(animatedMap[i][j] == -1) {
					continue;
				}
				
				animatedObjectManager.addObject(new AnimatedObject(j * GameWorld.TILESIZE, i * GameWorld.TILESIZE, this, animatedMap[i][j]));
			}
		}
	}
	
	public void Update() {
		if(!isStartGame) {
			startScreen.Update();
			return;
		}
		animatedObjectManager.dropBox(pointX, pointY, System.nanoTime());
		animatedObjectManager.UpdateObjects();
		baseCharacter.Update();
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
		
//		g2.scale(zoom, zoom);
		if(isFirstDrawMap) {
			mapGame.draw(g2);
//			isFirstDrawMap = false;
		}
		
		animatedObjectManager.draw(g2);
		
		baseCharacter.draw(g2);
	}

	
	
}
