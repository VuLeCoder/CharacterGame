package com.cyberpunk.Effect;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;

import javax.imageio.ImageIO;

public class DataLoader {
	private static DataLoader instance = null;
	
	//data/map/wall/wallTileset.png
	private final String wallFramefile = "data/map/wall/wallTileset.txt";
	private final String wallMapfile = "data/map/wall/wallMap.txt";
	
	//data/map/inside/insideTileset.png
	private final String insideFramefile = "data/map/inside/insideTileset.txt";
	private final String insideMapfile = "data/map/inside/insideMap.txt";
	
	//data/map/outside/outsideTileset.png
	private final String outsideFramefile = "data/map/outside/outsideTileset.txt";
	private final String outsideMapfile = "data/map/outside/outsideMap.txt";
	
	//data/map/object/objectTileset.png
	private final String objectFramefile = "data/map/object/objectTileset.txt";
	private final String objectMapfile = "data/map/object/objectMap.txt";
	private final String ladderMapfile = "data/map/object/ladderMap.txt";
	
	//data/map/animatedObject/animatedObjectTileset.png
	private final String animatedFramefile = "data/map/animatedObject/animatedObjectTileset.txt";
	private final String animatedMapfile = "data/map/animatedObject/animatedMap.txt";
	
	private final String collisionMapfile = "data/map/collisionMap.txt";
	
	private final String animationObject = "data/map/animatedObject/animationObject.txt";
	
	private final String bikerFramefile = "data/character/biker/frame.txt";
	private final String bikerObject = "data/character/biker/animation.txt";
	
	
	private Hashtable<String, FrameImage> frameImages = null;
	private Hashtable<String, Animation> animations = null;
	
	private int[][] collisionMap;
	private int[][] wallMap;
	private int[][] insideMap;
	private int[][] outsideMap;
	private int[][] objectMap;
	private int[][] ladderMap;
	private int[][] animatedMap;
	
	private DataLoader() {}
	
	public static DataLoader getInstance() {
		if(instance == null) {
			instance = new DataLoader();
		}
		return instance;
	}
	
	private void LoadFrame(String fileName) throws IOException {
		if(frameImages == null) {
			frameImages = new Hashtable<String, FrameImage>();
		}
		
		FileReader fr = new FileReader(fileName);
		BufferedReader br = new BufferedReader(fr);
		
		String line = null;
		
		if(br.readLine() == null) {
			System.out.println("No data");
			br.close();
			throw new IOException();
			
		} else {
			fr = new FileReader(fileName);
			br = new BufferedReader(fr);
			
			while((line = br.readLine()).equals("")) {}
			
			int n = Integer.parseInt(line);
			for(int i=0; i<n; ++i) {
				FrameImage frame = new FrameImage();
				while((line = br.readLine()).equals("")) {}
				frame.setName(line);
				
				while((line = br.readLine()).equals("")) {}
				String[] str = line.split(" ");
				String path = str[1];
				
				while((line = br.readLine()).equals("")) {}
				str = line.split(" ");
				int x = Integer.parseInt(str[1]);
				
				while((line = br.readLine()).equals("")) {}
				str = line.split(" ");
				int y = Integer.parseInt(str[1]);
				
				while((line = br.readLine()).equals("")) {}
				str = line.split(" ");
				int w = Integer.parseInt(str[1]);
				
				while((line = br.readLine()).equals("")) {}
				str = line.split(" ");
				int h = Integer.parseInt(str[1]);
				
				BufferedImage imageData = ImageIO.read(new File(path));
				BufferedImage image = imageData.getSubimage(x, y, w, h);
				frame.setImage(image);
				
				instance.frameImages.put(frame.getName(), frame);
			}
		}
		
		br.close();
	}
	
	private void LoadAnimation(String fileName) throws IOException {
		if(instance.animations == null) {
			instance.animations = new Hashtable<String, Animation>();
		}
		
		FileReader fr = new FileReader(fileName);
		BufferedReader br = new BufferedReader(fr);
		
		String line = null;
		if(br.readLine() == null) {
			System.out.println("No data");
			br.close();
			throw new IOException();
			
		} else {
			fr = new FileReader(fileName);
			br = new BufferedReader(fr);
			
			while((line = br.readLine()).equals("")) {}
			
			int n = Integer.parseInt(line);
			for(int i=0; i<n; ++i) {
				Animation animation = new Animation();
				while((line = br.readLine()).equals("")) {}
				animation.setName(line);
//				System.out.println(line);
				
				while((line = br.readLine()).equals("")) {}
				String[] str = line.split(" ");
				for(int j=0; j<str.length; j+=2) {
//					System.out.println(str[j] + " " + str[j + 1]);
					animation.add(getFrameImage(str[j]), Double.parseDouble(str[j + 1]));
				}
				instance.animations.put(animation.getName(), animation);
			}
		}
		
		br.close();
	}
	
	private void LoadCollisionMap() throws IOException{
		FileReader fr = new FileReader(collisionMapfile);
		BufferedReader br = new BufferedReader(fr);
		
		String line = br.readLine();
		int numberOfRows = Integer.parseInt(line);
		line = br.readLine();
		int numberOfColumns = Integer.parseInt(line);
		
		instance.collisionMap = new int[numberOfRows][numberOfColumns];
		for(int i=0; i<numberOfRows; ++i) {
			line = br.readLine();
			String[] str = line.split(" ");
			for(int j=0; j<numberOfColumns; ++j) {
				instance.collisionMap[i][j] = Integer.parseInt(str[j]);
			}
		}		
		br.close();
	}
	
	private int[][] LoadMap(String fileMap) throws IOException {
		FileReader fr = new FileReader(fileMap);
		BufferedReader br = new BufferedReader(fr);
		
		String line = br.readLine();
		int numberOfRows = Integer.parseInt(line);
		line = br.readLine();
		int numberOfCols = Integer.parseInt(line);
		
		int[][] map = new int[numberOfRows][numberOfCols];
	    for (int i = 0; i < numberOfRows; ++i) {
	        String[] str = br.readLine().trim().split("\\s+");
	        for (int j = 0; j < numberOfCols; ++j) {
	            map[i][j] = Integer.parseInt(str[j]);
	        }
	    }
	    		
		br.close();
		return map;
	}
	
	public FrameImage getFrameImage(String name) {
		return new FrameImage(instance.frameImages.get(name)); 
	}
	
	public Animation getAnimation(String name) {
//		for (String key : instance.animations.keySet()) {
//            System.out.println("Key: " + key);
//        }
		return new Animation(instance.animations.get(name));
	}
	
	public int[][] getCollisionMap() {
		return collisionMap;
	}

	public int[][] getWallMap() {
		return wallMap;
	}

	public int[][] getInsideMap() {
		return insideMap;
	}

	public int[][] getOutsideMap() {
		return outsideMap;
	}
	
	public int[][] getObjectMap() {
		return objectMap;
	}
	
	public int[][] getLadderMap() {
		return ladderMap;
	}

	public int[][] getAnimatedMap() {
		return animatedMap;
	}

	public void LoadData() throws IOException {
		LoadFrame(wallFramefile);
		LoadFrame(insideFramefile);
		LoadFrame(outsideFramefile);
		LoadFrame(objectFramefile);
		LoadFrame(animatedFramefile);
		LoadFrame(bikerFramefile);
		
		instance.insideMap = LoadMap(insideMapfile);
		instance.outsideMap = LoadMap(outsideMapfile);
		instance.wallMap = LoadMap(wallMapfile);
		instance.objectMap = LoadMap(objectMapfile);
		instance.ladderMap = LoadMap(ladderMapfile);
		instance.animatedMap = LoadMap(animatedMapfile);
		
//		LoadCollisionMap();
		LoadAnimation(animationObject);
		LoadAnimation(bikerObject);
	}
	
}