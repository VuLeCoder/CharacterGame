package com.game.GameObjects;

import java.awt.Color;
import java.awt.Graphics2D;

import com.game.Effect.CacheDataLoader;
import com.game.UserInterface.GameFrame;

public class BackgroundMap extends GameObject {
	
	public int[][] map;
	private int tileSize;

	public BackgroundMap(float posX, float posY, GameWorld gameWorld) {
		super(posX, posY, gameWorld);
		map = CacheDataLoader.getInstance().getBackgroundMap();
		tileSize = 30;
	}
	
	@Override
	public void Update() {}
	
	public void draw(Graphics2D g2) {
		Camera camera = getGameWorld().camera;
		
		g2.setColor(Color.RED);
		for(int i=0; i<map.length; ++i) {
			for(int j=0; j<map[0].length; ++j) {
				
				if(map[i][j] !=0 && 
					j * tileSize - camera.getPosX() > -30 && j * tileSize - camera.getPosX() < GameFrame.SCREEN_WIDTH &&
                    i * tileSize - camera.getPosY() > -30 && i * tileSize - camera.getPosY() < GameFrame.SCREEN_HEIGHT) {
					
                    g2.drawImage(CacheDataLoader.getInstance().getFrameImage("tiled" + map[i][j]).getImage(),
                    			 (int) getPosX() + j*tileSize - (int) camera.getPosX(), 
                    			 (int) getPosY() + i*tileSize - (int) camera.getPosY(), 
                    			 null);
				}
			}
		}
	}
}
