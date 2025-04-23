package com.cyberpunk.Object;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.cyberpunk.Effect.DataLoader;
import com.cyberpunk.StartGame.GameFrame;

public class GameWorld {
	public static int TILESIZE = 32;
	
	private final BufferedImage bufferedImage;
	private final MapGame mapGame;
	private final int[][] animatedMap;
	
	private boolean isFirstDrawMap = true;
	private int[][] collisionMap;
	private AnimatedObjectManager animatedObjectManager;
	public BaseCharacter baseCharacter;	

	public GameWorld() {
		bufferedImage = new BufferedImage(GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT, BufferedImage.TYPE_INT_ARGB);
		mapGame = new MapGame();
		collisionMap = DataLoader.getInstance().getCollisionMap();
		animatedMap = DataLoader.getInstance().getAnimatedMap();
		
		baseCharacter = new BaseCharacter(100, 500, "biker", this);
		
		animatedObjectManager = new AnimatedObjectManager(this);
		addAllAnimatedObject();
	}
	
	public BufferedImage getBufferedImage(){
        return bufferedImage;
    }
	
	public void setCollisionMap(int x, int y, int val) {
		collisionMap[x][y] = val;
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
				
				animatedObjectManager.addObject(new AnimatedObject(i, j, this));
			}
		}
	}
	
	public void Update() {
		animatedObjectManager.UpdateObjects();
		
		baseCharacter.Update();
	}
	
	public void Render() {
		Graphics2D g2 = (Graphics2D) bufferedImage.getGraphics();
		if(g2 == null) {
			return;
		}
		
		if(isFirstDrawMap) {
			
			isFirstDrawMap = false;
		}
//		mapGame.draw(g2);
//		animatedObjectManager.draw(g2);
		g2.setColor(Color.white);
		g2.fillRect(0, 0, GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT);
		baseCharacter.draw(g2);
	}
	
}
