package com.cyberpunk.Object;

import java.awt.Graphics2D;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ObjectManager {
	protected List<GameObject> gameObjects;
	private GameWorld gameWorld;

	public ObjectManager(GameWorld gameWorld) {
		gameObjects = Collections.synchronizedList(new LinkedList<GameObject>());
		this.gameWorld = gameWorld;
	}

	public GameWorld getGameWorld() {
		return gameWorld;
	}

	public void addObject(GameObject gameObject) {
		synchronized (gameObjects) {
			gameObjects.add(gameObject);
		}
	}

	public void UpdateObjects() {
		GameObject object;

		synchronized (gameObjects) {
			Iterator<GameObject> iterator = gameObjects.iterator();

			while (iterator.hasNext()) {
				object = iterator.next();

//				if (object.getState() == GameObject.DEATH) {
//					iterator.remove();
//				} else {
//					object.Update();
//				}
			}
		}
	}

	public void draw(Graphics2D g2) {
		synchronized(gameObjects) {
//			for(GameObject object : gameObjects) {
//				object.draw(g2);
//		    }
		}
	}
}
