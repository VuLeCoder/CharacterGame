package com.cyberpunk.Object;

import java.awt.Graphics2D;

import com.cyberpunk.Effect.DataLoader;

public class MapGame {

	public static final String OUTSIDE	= "outside";
	public static final String INSIDE 	= "inside";
	public static final String WALL		= "wall";
	public static final String OBJECT	= "object";
	
	private final int[][] outsideMap;
	private final int[][] insideMap;
	private final int[][] wallMap;
	private final int[][] objectMap;
	private final int[][] ladderMap;

	public MapGame() {
		outsideMap	=	DataLoader.getInstance().getOutsideMap();
		insideMap 	= 	DataLoader.getInstance().getInsideMap();
		objectMap 	= 	DataLoader.getInstance().getObjectMap();
		ladderMap 	= 	DataLoader.getInstance().getLadderMap();
		wallMap 	= 	DataLoader.getInstance().getWallMap();
	}
	
	private void drawMap(Graphics2D g2, String name, int[][] Map) {
		for(int i=0; i<Map.length; ++i) {
			for(int j=0; j<Map[0].length; ++j) {
				
				drawTileset(g2, name, Map, i, j);
				
											
//				if(Map[i][j] == -1) {
//					continue;
//				}
//				g2.drawImage(DataLoader.getInstance().getFrameImage(name + Map[i][j]).getImage(),
//                    		j * GameWorld.TILESIZE, i * GameWorld.TILESIZE, null);
			}
		}
	}
	
	public void draw(Graphics2D g2) {
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

	public int[][] getInsideMap() {
		return insideMap;
	}
}
