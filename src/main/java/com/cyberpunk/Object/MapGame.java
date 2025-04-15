package com.cyberpunk.Object;

import java.awt.Graphics2D;

import com.cyberpunk.Effect.DataLoader;

public class MapGame {
	
	private int[][] outsideMap;
	private int[][] insideMap;
	private int[][] wallMap;
	private int[][] objectMap;
	private int[][] ladderMap;

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
											
				if(Map[i][j] == -1) {
					continue;
				}
				g2.drawImage(DataLoader.getInstance().getFrameImage(name + Map[i][j]).getImage(),
                    		j * GameWorld.TILESIZE, i * GameWorld.TILESIZE, null);
			}
		}
	}
	
	public void draw(Graphics2D g2) {
		drawMap(g2, "outside",	outsideMap);
		drawMap(g2, "inside",	insideMap);
		drawMap(g2, "wall",		wallMap);
		drawMap(g2, "object",	ladderMap);
		drawMap(g2, "object",	objectMap);
	}
}
