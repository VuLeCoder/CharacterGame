package com.cyberpunk.Object;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.cyberpunk.Effect.DataLoader;

public class MapGame {
	public static final int WALL_TILE = 1;
	public static final int TRANSPORT_LEFT_TILE = 4;
	public static final int DEATH_TILE = -1;

	public static final String OUTSIDE	= "outside";
	public static final String INSIDE 	= "inside";
	public static final String WALL		= "wall";
	public static final String OBJECT	= "object";
	
	private final int[][] outsideMap;
	private final int[][] insideMap;
	private final int[][] wallMap;
	private final int[][] objectMap;
	private final int[][] ladderMap;
	
	private int[][] collisionMap;

	public MapGame() {
		outsideMap	=	DataLoader.getInstance().getOutsideMap();
		insideMap 	= 	DataLoader.getInstance().getInsideMap();
		objectMap 	= 	DataLoader.getInstance().getObjectMap();
		ladderMap 	= 	DataLoader.getInstance().getLadderMap();
		wallMap 	= 	DataLoader.getInstance().getWallMap();
		
		collisionMap = DataLoader.getInstance().getCollisionMap();
	}
	
	private void drawMap(Graphics2D g2, String name, int[][] Map) {
		if(name == "") {
			for(int i=0; i<Map.length; ++i) {
				for(int j=0; j<Map[0].length; ++j) {
					drawCollision(g2, i, j);
				}
			}
			return;
		}
		
		for(int i=0; i<Map.length; ++i) {
			for(int j=0; j<Map[0].length; ++j) {
				drawTileset(g2, name, Map, i, j);
			}
		}
	}
	
	public void draw(Graphics2D g2) {
//		drawMap(g2, "", 		collisionMap);
		drawMap(g2, OUTSIDE,	outsideMap);
		drawMap(g2, INSIDE,		insideMap);
		drawMap(g2, WALL,		wallMap);
		drawMap(g2, OBJECT,		ladderMap);
		drawMap(g2, OBJECT,		objectMap);
	}

	public void drawTileset(Graphics2D g2, String name, int[][] Map, int x, int y) {
		if(Map[x][y] == -1) {
			return;
		}
		g2.drawImage(DataLoader.getInstance().getFrameImage(name + Map[x][y]).getImage(),
                    		y * GameWorld.TILESIZE, x * GameWorld.TILESIZE, null);
	}
	
	public void drawCollision(Graphics2D g2, int i, int j) {
		
		switch(collisionMap[i][j]) {
			case -1:
				g2.setColor(Color.red);
				g2.fillRect(j * GameWorld.TILESIZE, i * GameWorld.TILESIZE, GameWorld.TILESIZE, GameWorld.TILESIZE);
				break;
				
			case 0:
				g2.setColor(Color.white);
				g2.fillRect(j * GameWorld.TILESIZE, i * GameWorld.TILESIZE, GameWorld.TILESIZE, GameWorld.TILESIZE);
				break;
				
			case 1:
				g2.setColor(Color.gray);
				g2.fillRect(j * GameWorld.TILESIZE, i * GameWorld.TILESIZE, GameWorld.TILESIZE, GameWorld.TILESIZE);
				break;
				
			case 2: 
				g2.setColor(Color.gray);
				g2.fillRect(j * GameWorld.TILESIZE, i * GameWorld.TILESIZE, GameWorld.TILESIZE, GameWorld.TILESIZE / 2);
				break;
				
			case 3:
				g2.setColor(Color.cyan);
				g2.fillRect(j * GameWorld.TILESIZE, i * GameWorld.TILESIZE, GameWorld.TILESIZE, GameWorld.TILESIZE);
				break;
		}
		
	}
	
	public Rectangle haveCollisionWithLand(Rectangle rect, Object object) {
		int posX1 = rect.x / GameWorld.TILESIZE;
		int posX2 = (rect.x + rect.width) / GameWorld.TILESIZE;
		int posY = (rect.y + rect.height) / GameWorld.TILESIZE;

		if (posX1 < 0) {
			posX1 = 0;
		}

		if (posX2 >= getCollisionMap()[0].length) {
			posX2 = getCollisionMap()[0].length - 1;
		}

		object.setSpeedX(0);
		Rectangle collisionRect = null;
		Rectangle tileRect;
		int tile;

		for (int y = posY; y < getCollisionMap().length; ++y) {
			for (int x = posX1; x <= posX2; ++x) {

				tileRect = new Rectangle(x * GameWorld.TILESIZE, y * GameWorld.TILESIZE, GameWorld.TILESIZE,
						GameWorld.TILESIZE);
				
				tile = getCollisionMap()[y][x];
				if (tile == TRANSPORT_LEFT_TILE && rect.intersects(tileRect)) {
					object.setSpeedX(AnimatedObject.TRANSPORT_SPEED * Object.LEFT_DIR);
					collisionRect = tileRect;
				}

				if (tile == WALL_TILE && rect.intersects(tileRect)) {
					collisionRect = tileRect;
				}
				
				if(tile == DEATH_TILE && rect.intersects(tileRect)) {
					collisionRect = tileRect;
					object.setHealth(-1);
				}
			}
		}
		return collisionRect;
	}

	public Rectangle haveCollisionWithWallLeft(Rectangle rect) {
		int posX = rect.x / GameWorld.TILESIZE;
		int posY1 = rect.y / GameWorld.TILESIZE;
		int posY2 = (rect.y + rect.height) / GameWorld.TILESIZE;
		posY2 = posY1;
		
		if (posY1 < 0) {
			posY1 = 0;
		}

		if (posY2 >= getCollisionMap()[0].length) {
			posY2 = getCollisionMap()[0].length - 1;
		}
		
		Rectangle collisionRect = null;
		Rectangle tileRect;
		int tile;
		
		for (int x = posX; x >= 0; --x) {
			for (int y = posY1; y <= posY2; ++y) {
//				System.out.println(y + " " + posX + " " + getCollisionMap()[y][posX]);

				tileRect = new Rectangle(posX * GameWorld.TILESIZE, y * GameWorld.TILESIZE, GameWorld.TILESIZE,
						GameWorld.TILESIZE);
				
				tile = getCollisionMap()[y][posX];
				if (tile == WALL_TILE && rect.intersects(tileRect)) {
					collisionRect = tileRect;
					break;
				}
			}
		}
		return collisionRect;
	}
	
	public Rectangle haveCollisionWithWallRight(Rectangle rect) {
		int posX = (rect.x + rect.width) / GameWorld.TILESIZE;
		int posY1 = rect.y / GameWorld.TILESIZE;
		int posY2 = (rect.y + rect.height) / GameWorld.TILESIZE;
		posY2 = posY1;
		
		if (posY1 < 0) {
			posY1 = 0;
		}

		if (posY2 >= getCollisionMap()[0].length) {
			posY2 = getCollisionMap()[0].length - 1;
		}
		
		Rectangle collisionRect = null;
		Rectangle tileRect;
		int tile;
		
		for (int x = posX; x < getCollisionMap()[0].length; ++x) {
			for (int y = posY1; y <= posY2; ++y) {

				tileRect = new Rectangle(x * GameWorld.TILESIZE, y * GameWorld.TILESIZE, GameWorld.TILESIZE,
						GameWorld.TILESIZE);
				
				tile = getCollisionMap()[y][x];
				if (tile == WALL_TILE && rect.intersects(tileRect)) {
					collisionRect = tileRect;
					break;
				}
			}
		}
		
		return collisionRect;
	}
	
	public Rectangle haveCollisionWithTop(Rectangle rect, Object object) {
		int posX1 = (rect.x + 2) / GameWorld.TILESIZE;
		int posX2 = (rect.x + rect.width - 2) / GameWorld.TILESIZE;
		int posY = rect.y / GameWorld.TILESIZE;

		System.out.println(posX1 + " " + posX2 + " " + posY + " " + getCollisionMap()[posY][posX1] + " " + getCollisionMap()[posY][posX2]); 
		
		if (posX1 < 0) {
			posX1 = 0;
		}

		if (posX2 >= getCollisionMap()[0].length) {
			posX2 = getCollisionMap()[0].length - 1;
		}
		
		Rectangle collisionRect = null;
		Rectangle tileRect;
		int tile;

//		for (int y = posY; y < getCollisionMap().length; ++y) {
		int y = posY;
			for (int x = posX1; x <= posX2; ++x) {

				tileRect = new Rectangle(x * GameWorld.TILESIZE, y * GameWorld.TILESIZE, GameWorld.TILESIZE,
						GameWorld.TILESIZE);
				
				tile = getCollisionMap()[y][x];

				if (tile == WALL_TILE && rect.intersects(tileRect)) {
					collisionRect = tileRect;
					object.beHurt(10);
					break;
				}
				
				if(tile == DEATH_TILE && rect.intersects(tileRect)) {
					collisionRect = tileRect;
					object.setHealth(-1);
				}
			}
//		}
		
		return collisionRect;
	}
	
	public int[][] getInsideMap() {
		return insideMap;
	}
	
	public void setCollisionMap(int x, int y, int val) {
		collisionMap[x][y] = val;
	}

	public int[][] getObjectMap() {
		return objectMap;
	}

	public int[][] getWallMap() {
		return wallMap;
	}

	public int[][] getCollisionMap() {
		return collisionMap;
	}	
	
}
