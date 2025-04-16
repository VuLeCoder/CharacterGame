package com.cyberpunk.Object;

import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.List;

public class AnimatedObjectManager {

	protected List<AnimatedObject> animatedObjects;
	private final GameWorld gameWorld;

	public AnimatedObjectManager(GameWorld gameWorld) {
		animatedObjects = new LinkedList<>();
		this.gameWorld = gameWorld;
	}
	
	public GameWorld getGameWorld() {
		return gameWorld;
	}

	public void addObject(AnimatedObject animatedObject) {
		animatedObjects.add(animatedObject);
	}
	
	public void UpdateObjects() {
		for(int i=0; i<animatedObjects.size(); ++i) {
			animatedObjects.get(i).Update();
		}
	}

	public void draw(Graphics2D g2) {
		for(int i=0; i<animatedObjects.size(); ++i) {
			animatedObjects.get(i).draw(g2);
		}
	}
}
