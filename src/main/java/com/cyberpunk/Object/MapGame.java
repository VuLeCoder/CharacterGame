package com.cyberpunk.Object;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.cyberpunk.Effect.DataLoader;

public class MapGame {
	public static final int WALL_TILE = 1;
	public static final int PLATFORM_TILE = 2;
	public static final int LADDER_TILE = 3;
	public static final int TRANSPORT_LEFT_TILE = 4;
	public static final int DEATH_TILE = -1;
	public static final int HAMMER_TILE = -2;

	public static final String OUTSIDE = "outside";
	public static final String INSIDE = "inside";
	public static final String WALL = "wall";
	public static final String OBJECT = "object";

	private final int[][] outsideMap;
	private final int[][] insideMap;
	private final int[][] wallMap;
	private final int[][] objectMap;
	private final int[][] ladderMap;

	private int[][] collisionMap;

	public MapGame() {
		outsideMap = DataLoader.getInstance().getOutsideMap();
		insideMap = DataLoader.getInstance().getInsideMap();
		objectMap = DataLoader.getInstance().getObjectMap();
		ladderMap = DataLoader.getInstance().getLadderMap();
		wallMap = DataLoader.getInstance().getWallMap();

		collisionMap = DataLoader.getInstance().getCollisionMap();
	}

	private void drawMap(Graphics2D g2, String name, int[][] Map) {
		if (name == "") {
			for (int i = 0; i < Map.length; ++i) {
				for (int j = 0; j < Map[0].length; ++j) {
					drawCollision(g2, i, j);
				}
			}
			return;
		}

		for (int i = 0; i < Map.length; ++i) {
			for (int j = 0; j < Map[0].length; ++j) {
				drawTileset(g2, name, Map, i, j);
			}
		}
	}

	public void draw(Graphics2D g2) {
//		 drawMap(g2, "", collisionMap);
		drawMap(g2, OUTSIDE, outsideMap);
		drawMap(g2, INSIDE, insideMap);
		drawMap(g2, WALL, wallMap);
		drawMap(g2, OBJECT, ladderMap);
		drawMap(g2, OBJECT, objectMap);
	}

	public void drawTileset(Graphics2D g2, String name, int[][] Map, int x, int y) {
		if (Map[x][y] == -1) {
			return;
		}
		g2.drawImage(DataLoader.getInstance().getFrameImage(name + Map[x][y]).getImage(), y * GameWorld.TILESIZE,
				x * GameWorld.TILESIZE, null);
	}

	public void drawCollision(Graphics2D g2, int i, int j) {

		switch (collisionMap[i][j]) {
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

	public CollisionResult haveCollisionWithLand(Rectangle rect, Object object) {
		int posX1 = (rect.x) / GameWorld.TILESIZE;
		int posX2 = (rect.x + rect.width) / GameWorld.TILESIZE;
		int posY = (rect.y + rect.height) / GameWorld.TILESIZE;
		
		posX1 = Math.max(0, posX1);
		posX2 = Math.min(getCollisionMap()[0].length - 1, posX2);

		CollisionResult collisionRect = new CollisionResult();
		Rectangle tileRect;
		int tile;

		for (int y = posY; y < getCollisionMap().length; ++y) {
			for (int x = posX1; x <= posX2; ++x) {

				tileRect = new Rectangle(x * GameWorld.TILESIZE, y * GameWorld.TILESIZE, GameWorld.TILESIZE,
						GameWorld.TILESIZE);

				tile = getCollisionMap()[y][x];
				if (rect.intersects(tileRect)) {

					switch (tile) {
					case TRANSPORT_LEFT_TILE:
						collisionRect.setCollisionRect(tileRect);
						collisionRect.setCollisionWithTile(TRANSPORT_LEFT_TILE);
						return collisionRect;

					case WALL_TILE:
						collisionRect.setCollisionRect(tileRect);
						collisionRect.setCollisionWithTile(WALL_TILE);
						break;

					case PLATFORM_TILE:
						collisionRect.setCollisionRect(tileRect);
						collisionRect.setCollisionWithTile(PLATFORM_TILE);
						break;

					case DEATH_TILE:
						collisionRect.setCollisionRect(tileRect);
						collisionRect.setCollisionWithTile(DEATH_TILE);
						return collisionRect;
					}
				}
			}
		}
		return collisionRect;
	}

	public CollisionResult haveCollisionWithWallLeft(Rectangle rect) {
		int posX = rect.x / GameWorld.TILESIZE;
		int posY1 = rect.y / GameWorld.TILESIZE;
		int posY2 = (rect.y + rect.height) / GameWorld.TILESIZE;
		
		posY1 = Math.max(0, posY1);
		posY2 = Math.min(getCollisionMap().length - 1, posY2);
		
		Rectangle tileRect;
		int tile;

		for (int x = posX; x >= 0; --x) {
			for (int y = posY1; y <= posY2; ++y) {
				tile = getCollisionMap()[y][posX];
				if(tile == 0) {
					continue;
				}
				
				tileRect = new Rectangle(posX * GameWorld.TILESIZE, y * GameWorld.TILESIZE, 
						GameWorld.TILESIZE, GameWorld.TILESIZE);

				if (rect.intersects(tileRect)) {
					return new CollisionResult(tileRect, tile);	
				}
			}
		}
		return new CollisionResult();
	}

	public CollisionResult haveCollisionWithWallRight(Rectangle rect) {
		int posX = (rect.x + rect.width) / GameWorld.TILESIZE;
		int posY1 = rect.y / GameWorld.TILESIZE;
		int posY2 = (rect.y + rect.height) / GameWorld.TILESIZE;
		
		posY1 = Math.max(0, posY1);
		posY2 = Math.min(getCollisionMap().length - 1, posY2);

		Rectangle tileRect;
		int tile;

		for (int x = posX; x < getCollisionMap()[0].length; ++x) {
			for (int y = posY1; y <= posY2; ++y) {
				tile = getCollisionMap()[y][posX];
				if(tile == 0) {
					continue;
				}
				
				tileRect = new Rectangle(posX * GameWorld.TILESIZE, y * GameWorld.TILESIZE, 
						GameWorld.TILESIZE, GameWorld.TILESIZE);

				if (rect.intersects(tileRect)) {
					return new CollisionResult(tileRect, tile);	
				}
				
			}
		}

		return new CollisionResult();
	}

	public CollisionResult haveCollisionWithTop(Rectangle rect) {
		int posX1 = (rect.x + 2) / GameWorld.TILESIZE;
		int posX2 = (rect.x + rect.width - 2) / GameWorld.TILESIZE;
		int posY = rect.y / GameWorld.TILESIZE;

		posX1 = Math.max(0, posX1);
		posX2 = Math.min(getCollisionMap()[0].length - 1, posX2);
		
		CollisionResult collisionRect = new CollisionResult();
		Rectangle tileRect;
		int tile;

		int y = posY;
		for (int x = posX1; x <= posX2; ++x) {

			tileRect = new Rectangle(x * GameWorld.TILESIZE, y * GameWorld.TILESIZE, GameWorld.TILESIZE,
					GameWorld.TILESIZE);

			tile = getCollisionMap()[y][x];

			if (rect.intersects(tileRect)) {
				
				if (tile == WALL_TILE) {
					return new CollisionResult(tileRect, tile);
				}

				if (tile == HAMMER_TILE) {
					return new CollisionResult(tileRect, tile);
				}
			}
		}
//		}

		return collisionRect;
	}

	public Rectangle haveCollisionWithLadder(Rectangle rect) {
		int posX1 = rect.x / GameWorld.TILESIZE;
		int posX2 = (rect.x + rect.width) / GameWorld.TILESIZE;
		int posY1 = rect.y / GameWorld.TILESIZE;
		int posY2 = (rect.y + rect.height) / GameWorld.TILESIZE;

		posX1 = Math.max(0, posX1);
		posY1 = Math.max(0, posY1);
		posX2 = Math.min(getCollisionMap()[0].length - 1, posX2);
		posY2 = Math.min(getCollisionMap().length - 1, posY2);

		for (int y = posY1; y <= posY2; y++) {
			for (int x = posX1; x <= posX2; x++) {
				int tile = getCollisionMap()[y][x];
				Rectangle rectLadder = new Rectangle(x * GameWorld.TILESIZE, y * GameWorld.TILESIZE, GameWorld.TILESIZE,
						GameWorld.TILESIZE);
				if (tile == LADDER_TILE) {
					return rectLadder;
				}
			}
		}

		return null;
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

	public int[][] getOutsideMap() {
		return outsideMap;
	}

	public int[][] getLadderMap() {
		return ladderMap;
	}

	public int[][] getCollisionMap() {
		return collisionMap;
	}

}
