package com.cyberpunk.Object;

import java.awt.Graphics2D;

import com.cyberpunk.Effect.Animation;
import com.cyberpunk.Effect.DataLoader;

public class AnimatedObject{
	private static final int ID_PLATFORM = 0;
	private static final int[] PLATFORM = {0, 1, 2, 3, 4, 5, 6, 7};
	private static final int[] IS_PLATFORM_CLOSE = {0, 1, 2, 6, 7};
	
	private static final int ID_TRANSPORT = 1;
	private static final int[] TRANSPORT1 = {8, 9, 10, 11, 12, 13, 14, 15};
	private static final int[] TRANSPORT2 = {16, 17, 18, 19, 20, 21, 22, 23};
	private static final int[] TRANSPORT3 = {24, 25, 26, 27, 28, 29, 30, 31};
	
	private static final int ID_ENTRY = 2;
	private static final int[] ENTRY_LEFT = {32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47};
	private static final int[] ENTRY_RIGHT = {48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63};
	private static final int[] IS_ENTRY_CLOSE = {0, 1, 2, 6, 7};
	
	private static final int ID_HAMER = 3; 
	private static final int[] HAMER = {64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79};
	
	private final GameWorld gameWorld;
	private final Animation animation;
	private final int posX, posY;
	
	private int whoAmI;
	private String name;

	public AnimatedObject(int posX, int posY, GameWorld gameWorld) {
		this.gameWorld = gameWorld;
		this.posX = posX;
		this.posY = posY;
		
		setName();
//		System.out.println(name);
		animation = DataLoader.getInstance().getAnimation(name);
	}
	
	private void setName() {
		
		int num_id = gameWorld.getAnimatedMap(posX, posY);
		
		for(int id : PLATFORM) {
			if(num_id == id) {
				whoAmI = ID_PLATFORM;
				name = "platform";
				return;
			}
		}
		
		for(int id : TRANSPORT1) {
			if(num_id == id) {
				whoAmI = ID_TRANSPORT;
				name = "transport1";	
				name += ((num_id >= TRANSPORT1[TRANSPORT1.length / 2]) ? "_toLeft" : "_toRight");

				return;
			}
		}
		
		for(int id : TRANSPORT2) {
			if(num_id == id) {
				whoAmI = ID_TRANSPORT;
				name = "transport2";
				name += ((num_id >= TRANSPORT2[TRANSPORT2.length / 2]) ? "_toLeft" : "_toRight");
				return;
			}
		}
		
		for(int id : TRANSPORT3) {
			if(num_id == id) {
				whoAmI = ID_TRANSPORT;
				name = "transport3";
				name += ((num_id >= TRANSPORT3[TRANSPORT3.length / 2]) ? "_toLeft" : "_toRight");
				return;
			}
		}
		
		for(int id : ENTRY_LEFT) {
			if(num_id == id) {
				whoAmI = ID_ENTRY;
				name = "entryLeft";
				name += ((num_id < ENTRY_LEFT[ENTRY_LEFT.length / 2]) ? "_Up" : "_Down");
				return;
			}
		}
		
		for(int id : ENTRY_RIGHT) {
			if(num_id == id) {
				whoAmI = ID_ENTRY;
				name = "entryRight";
				name += ((num_id < ENTRY_RIGHT[ENTRY_RIGHT.length / 2]) ? "_Up" : "_Down");
				return;
			}
		}
		
		whoAmI = ID_HAMER;
		name = "hammer";
		name += ((num_id < HAMER[HAMER.length / 2]) ? "_Up" : "_Down");
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
		gameWorld.getMapGame().drawTileset(g2, MapGame.INSIDE, gameWorld.getMapGame().getInsideMap(), posX, posY);
		g2.drawImage(animation.getCurrentImage(), posY * GameWorld.TILESIZE, posX * GameWorld.TILESIZE, null);
	}

}
