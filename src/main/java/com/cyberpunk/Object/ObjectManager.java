package com.cyberpunk.Object;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.time.Duration;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.cyberpunk.StartGame.KeyConfig;

public class ObjectManager {
	private final GameWorld gameWorld;

	// cài đặt cho hoạt ảnh Hamer
	public static int SERIAL_NUMBER = 0;

	// Cho hộp rơi
	private static final int MAX_NUMBER_BOX = 2;
	private static final long TIME_TO_NEXT_DROP = Duration.ofSeconds(3).toNanos();

//	protected List<AnimatedObject> animatedObjects;
	protected List<MapObject> mapObjects;
	protected List<Object> entity;

	private int numberOfBox = 0;
	private long startTimeToDropBox;

	public ObjectManager(GameWorld gameWorld) {
		this.gameWorld = gameWorld;

		mapObjects = new LinkedList<>();
		entity = new LinkedList<>();

		numberOfBox = 0;
		startTimeToDropBox = 0;
	}

	public GameWorld getGameWorld() {
		return gameWorld;
	}
	
	
	// chia làm 4 hàm va chạm left, top, right, bottom
	public CollisionResult LeftRightCollisionWithObject(Object object) {
		float collisionFutureW = object.getWidth(), collisionFutureH = object.getHeight();
		float objX, objY, objW, objH;
		
		float collisionFutureX = object.getPosX() - collisionFutureW / 2;
		float collisionFutureY = object.getPosY() - collisionFutureH / 2;
		
		for (int id = 0; id < entity.size(); ++id) {
			Object objectInList = entity.get(id);
			if ((object.getTeamType() == Object.P1_TEAM || object.getTeamType() == Object.P2_TEAM)
					&& (objectInList.getTeamType() == Object.P1_TEAM || objectInList.getTeamType() == Object.P2_TEAM)) {
				continue;
			}

			objW = objectInList.getWidth();
			objH = objectInList.getHeight();
			objX = objectInList.getPosX() - objW / 2;
			objY = objectInList.getPosY() - objH / 2;

			if (object.movingHitbox().intersects(objectInList.movingHitbox())
					&& objY - collisionFutureY <= Math.abs(objH - collisionFutureH)) {
				
				System.out.println(object.getSpeedX() + " " + objectInList.getIsPush());
				if(!objectInList.getIsPush()) {
					objectInList.setIsPush(true, object.getSpeedX());
//					extraSpeed = object.getSpeedX();
					objectInList.setSpeedX(objectInList.getSpeedX() + object.getSpeedX());
				}
				System.out.println("var");
				return new CollisionResult(objectInList, KeyConfig.LEFT);
			} 
		}
		
		return new CollisionResult();
	}
	
	public CollisionResult BottomCollisionWithObject(Object object) {
		float collisionFutureW = object.getWidth(), collisionFutureH = object.getHeight();
		float objX, objY, objW, objH;
		
		float collisionFutureX = object.getPosX() - collisionFutureW / 2;
		float collisionFutureY = object.getPosY() - collisionFutureH / 2;
		
		for (int id = 0; id < entity.size(); ++id) {
			Object objectInList = entity.get(id);
			if ((object.getTeamType() == Object.P1_TEAM || object.getTeamType() == Object.P2_TEAM)
					&& (objectInList.getTeamType() == Object.P1_TEAM || objectInList.getTeamType() == Object.P2_TEAM)) {
				continue;
			}

			objW = objectInList.getWidth();
			objH = objectInList.getHeight();
			objX = objectInList.getPosX() - objW / 2;
			objY = objectInList.getPosY() - objH / 2;

			// Nhân vật nhảy lên hộp
			if (object.movingHitbox().intersects(objectInList.movingHitbox())
					&& objY - collisionFutureY > Math.abs(objH - collisionFutureH)) {
				return new CollisionResult(objectInList, KeyConfig.DOWN);
			}
		}
		
		return new CollisionResult();
	}

	public void addObject(Object object) {
		if (object instanceof MapObject) {
			MapObject animatedObject = (MapObject) object;

			if (animatedObject.getId() < -1) {
				mapObjects.add(animatedObject);
				return;
			}
		}

		entity.add(object);
	}
	
	public void UpdateObjects() {
		for (MapObject mapObject : mapObjects) {
			mapObject.Update();
		}

		Iterator<Object> iter = entity.iterator();
		while (iter.hasNext()) {
			Object obj = iter.next();

			if (obj instanceof MapObject && obj.getHealth() < -10) {
				numberOfBox--;

				iter.remove();
				continue;
			}

			obj.Update();
		}
	}
	
	public void reDraw(Object object, Graphics2D g2) {
		Rectangle rect = object.movingHitbox();
		int posX1 = rect.x / GameWorld.TILESIZE - 1;
	    int posX2 = (rect.x + rect.width - 1) / GameWorld.TILESIZE + 1;
	    int posY1 = rect.y / GameWorld.TILESIZE - 1;
	    int posY2 = (rect.y + rect.height - 1) / GameWorld.TILESIZE + 1;

	    posX1 = Math.max(0, posX1);
	    posY1 = Math.max(0, posY1);
	    posX2 = Math.min(getGameWorld().getMapGame().getWallMap()[0].length - 1, posX2);
	    posY2 = Math.min(getGameWorld().getMapGame().getWallMap().length - 1, posY2);
	    
	    for(int x=posX1; x<=posX2; ++x) {
	    	for(int y=posY1; y<=posY2; ++y) {
	    		getGameWorld().getMapGame().drawTileset(g2, MapGame.OUTSIDE, 	getGameWorld().getMapGame().getOutsideMap(),	y, x);
	    		getGameWorld().getMapGame().drawTileset(g2, MapGame.INSIDE,		getGameWorld().getMapGame().getInsideMap(),		y, x);
	    		getGameWorld().getMapGame().drawTileset(g2, MapGame.WALL, 		getGameWorld().getMapGame().getWallMap(),		y, x);
	    		getGameWorld().getMapGame().drawTileset(g2, MapGame.OBJECT, 	getGameWorld().getMapGame().getLadderMap(), 	y, x);
	    		getGameWorld().getMapGame().drawTileset(g2, MapGame.OBJECT, 	getGameWorld().getMapGame().getObjectMap(), 	y, x);
	    	}
	    }
	}

	public void draw(Graphics2D g2) {
		for (int i = 0; i < mapObjects.size(); ++i) {
			reDraw(mapObjects.get(i), g2);
		}
		for (int i = entity.size() - 1; i >= 0; --i) {
			reDraw(entity.get(i), g2);
		}
		
		for (int i = 0; i < mapObjects.size(); ++i) {
			mapObjects.get(i).draw(g2);
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
		addObject(new MapObject(x * GameWorld.TILESIZE, y * GameWorld.TILESIZE, gameWorld, MapObject.ID_BOX));
	}
}
