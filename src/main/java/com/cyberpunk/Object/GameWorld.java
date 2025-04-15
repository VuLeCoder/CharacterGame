package com.cyberpunk.Object;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.cyberpunk.Effect.DataLoader;
import com.cyberpunk.StartGame.GameFrame;

public class GameWorld {
	public static int TILESIZE = 32;
	
	private final BufferedImage bufferedImage;
	private final MapGame mapGame;
	private final int[][] animatedMap;
	
	private int[][] collisionMap;
	private AnimatedObjectManager animatedObjectManager;

	public GameWorld() {
		bufferedImage = new BufferedImage(GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT, BufferedImage.TYPE_INT_ARGB);
		mapGame = new MapGame();
		collisionMap = DataLoader.getInstance().getCollisionMap();
		animatedMap = DataLoader.getInstance().getAnimatedMap();
		
		animatedObjectManager = new AnimatedObjectManager(this);
		addAllAnimatedObject();
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
	
	public BufferedImage getBufferedImage(){
        return bufferedImage;
    }
	
	public void setCollisionMap(int x, int y, int val) {
		collisionMap[x][y] = val;
	}
	
	public int getAnimatedMap(int x, int y) {
		return animatedMap[x][y];
	}
	
	public void Update() {
		animatedObjectManager.UpdateObjects();
	}
	
	public void Render() {
		Graphics2D g2 = (Graphics2D) bufferedImage.getGraphics();
		if(g2 == null) {
			return;
		}
		
		mapGame.draw(g2);
		animatedObjectManager.draw(g2);
	}
	
}
