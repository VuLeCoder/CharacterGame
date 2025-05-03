package com.cyberpunk.Object;

import java.awt.Graphics2D;
import java.time.Duration;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class AnimatedObjectManager {
	public static int SERIAL_NUMBER = 0;
	
	private static final int MAX_NUMBER_BOX = 10;
	private static final long TIME_TO_NEXT_DROP = Duration.ofSeconds(3).toNanos();

	protected List<AnimatedObject> animatedObjects;
	protected List<AnimatedObject> platforms;
	private final GameWorld gameWorld;
	
	private int numberOfBox = 0;
	private long beginTime;

	public AnimatedObjectManager(GameWorld gameWorld) {
		animatedObjects = new LinkedList<>();
		platforms = new LinkedList<>();
		this.gameWorld = gameWorld;
		
		numberOfBox = 0;
		beginTime = 0;
	}
	
	public GameWorld getGameWorld() {
		return gameWorld;
	}

	public void addObject(AnimatedObject animatedObject) {
		if(animatedObject.getId() == AnimatedObject.ID_PLATFORM) {
			platforms.add(animatedObject);
			return;
		}
		
		animatedObjects.add(animatedObject);
	}
	
	public void UpdateObjects() {
		for(AnimatedObject platform : platforms) {
			platform.Update();
		}
		
		Iterator<AnimatedObject> iter = animatedObjects.iterator();
		while (iter.hasNext()) {
			AnimatedObject obj = iter.next();
		    
		    if (obj.getHealth() < -10) {
		    	if(obj.getId() == AnimatedObject.ID_BOX) {
		    		numberOfBox--;
		    	}
		    	
		        iter.remove();
		        continue;
		    }
		    obj.Update();
		}
		
	}

	public void draw(Graphics2D g2) {
		
		for(int i=0; i<platforms.size(); ++i) {
			platforms.get(i).draw(g2);
		}
		
		for(int i=animatedObjects.size() - 1; i>=0; --i) {
			animatedObjects.get(i).draw(g2);
		}
	}
	
	public void dropBox(float x, float y, long time) {
		if(numberOfBox >= MAX_NUMBER_BOX) {
			return;
		}
		
		if(time - beginTime < TIME_TO_NEXT_DROP) {
			return;
		}
		
		numberOfBox++;
		beginTime = time;
		addObject(new AnimatedObject(x * GameWorld.TILESIZE, y * GameWorld.TILESIZE, gameWorld, AnimatedObject.ID_BOX));
	}
}
