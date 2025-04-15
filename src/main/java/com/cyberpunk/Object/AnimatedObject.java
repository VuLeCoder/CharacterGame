package com.cyberpunk.Object;

import java.awt.Graphics2D;

import com.cyberpunk.Effect.Animation;
import com.cyberpunk.Effect.DataLoader;

public class AnimatedObject{
	private static int[] isPlatform = {0, 1, 2, 6, 7};
	private static int PLATFORM = 0;
	
	private final GameWorld gameWorld;
	private final Animation animation;
	private final int posX, posY;
	private final int whoAmI;
	private final String name;

	public AnimatedObject(int posX, int posY, GameWorld gameWorld) {
		this.gameWorld = gameWorld;
		this.posX = posX;
		this.posY = posY;
		
		whoAmI = gameWorld.getAnimatedMap(posX, posY);
		if(whoAmI == PLATFORM) {
			name = "platform";
		} else {
			name = "";
		}
		
		animation = DataLoader.getInstance().getAnimation(name);
	}

	public void Update() {
		animation.Update(System.nanoTime());
		
		int id = animation.getCurrentFrame();
		
//		if(whoAmI == PLATFORM) {
//			gameWorld.setCollisionMap(posX, posY, 0);
//			for(int i : isPlatform) {
//				if(id == i) {
//					gameWorld.setCollisionMap(posX, posY, 1);
//				}
//			}
//		}
		
	}
	
	public void draw(Graphics2D g2) {
		g2.drawImage(animation.getCurrentImage(), posY * GameWorld.TILESIZE, posX * GameWorld.TILESIZE, null);
	}

}
