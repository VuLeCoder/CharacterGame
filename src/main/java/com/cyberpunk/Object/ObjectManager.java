package com.cyberpunk.Object;

import java.awt.Graphics2D;
import java.time.Duration;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ObjectManager {
	private final GameWorld gameWorld;

	// cài đặt cho hoạt ảnh Hamer
	public static int SERIAL_NUMBER = 0;

	// Cho hộp rơi
	private static final int MAX_NUMBER_BOX = 10;
	private static final long TIME_TO_NEXT_DROP = Duration.ofSeconds(3).toNanos();

//	protected List<AnimatedObject> animatedObjects;
	protected List<AnimatedObject> mapObject;
	protected List<Object> entity;

	private int numberOfBox = 0;
	private long startTimeToDropBox;

	public ObjectManager(GameWorld gameWorld) {
		this.gameWorld = gameWorld;

		mapObject = new LinkedList<>();
		entity = new LinkedList<>();

		numberOfBox = 0;
		startTimeToDropBox = 0;
	}

	public GameWorld getGameWorld() {
		return gameWorld;
	}

	public void addObject(Object object) {
		if (object instanceof AnimatedObject) {
			AnimatedObject animatedObject = (AnimatedObject) object;

			if (animatedObject.getId() < -1) {
				mapObject.add(animatedObject);
				return;
			}
		}

		entity.add(object);
	}

	public void UpdateObjects() {
		for (AnimatedObject mapObject : mapObject) {
			mapObject.Update();
		}

		
		Iterator<Object> iter = entity.iterator();
		while (iter.hasNext()) {
			Object obj = iter.next();

			if (obj instanceof AnimatedObject && obj.getHealth() < -10) {
				numberOfBox--;

				iter.remove();
				continue;
			}

			obj.Update();
		}
	}

	public void draw(Graphics2D g2) {

		for (int i = 0; i < mapObject.size(); ++i) {
			mapObject.get(i).draw(g2);
		}

		for (int i = entity.size() - 1; i >= 0; --i) {
			entity.get(i).draw(g2);
		}
	}

	public void dropBox(float x, float y, long time) {
		if (numberOfBox >= MAX_NUMBER_BOX) {
			return;
		}

		if (time - startTimeToDropBox < TIME_TO_NEXT_DROP) {
			return;
		}

		numberOfBox++;
		startTimeToDropBox = time;
		addObject(new AnimatedObject(x * GameWorld.TILESIZE, y * GameWorld.TILESIZE, gameWorld, AnimatedObject.ID_BOX));
	}
}
