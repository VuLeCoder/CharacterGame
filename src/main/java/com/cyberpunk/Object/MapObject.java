package com.cyberpunk.Object;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.time.Duration;

import com.cyberpunk.Effect.Animation;
import com.cyberpunk.Effect.DataLoader;
import com.cyberpunk.StartGame.KeyConfig;

// nếu còn tầm 75% máu sẽ chuyển hoạt họa

public class MapObject extends Object {

	public static final int ID_PLATFORM = -2;
	public static final int[] IS_PLATFORM_CLOSE = { 0, 1, 2, 6, 7 };

	public static final int[] ID_TRANSPORT_LEFT = { -3, -4, -5 }, ID_TRANSPORT_RIGHT = { -6, -7, -8 };

	public static final int ID_ENTRY_LEFT = -9, ID_ENTRY_RIGHT = -10;
	public static final int[] IS_ENTRY_CLOSE = { 0, 1, 2, 6, 7 };
	public static final int ENTRY_HEIGHT = 2;

	public static final int ID_HAMER = -11;
	public static final int HAMMER_HEIGHT = 3;
	public static final int[] IS_NOT_HAMMER_BLOCK_1 = { 1, 8 };
	public static final int[] IS_NOT_HAMMER_BLOCK_2 = { 0, 9, 10 };

	public static final int ID_BOX = 20, ID_BARREL = 10;

	public static final float TRANSPORT_SPEED = 0.75f; // 0.75f;
	public static final int HAMMER_HEALTH = 1;
	public static final float HAMMER_DAMAGE = 50;
	public static final int ENTRY_HEALTH = 1;
	public static final int BOX_HEALTH = 20;
	public static final int BARREL_HEALTH = 1;
//	public static final int LOCKER_HEALTH = 1;

	public static final float BOX_WEIGTH = 0.05f;

	private final long timeNoBeHurt = Duration.ofSeconds(1).toNanos();
	private long startTimeNoBeHurt;

	private final Animation animation;
	private int id, maxHealth;
	private String name;

	public MapObject(float posX, float posY, GameWorld gameWorld, int id) {
		super(posX, posY, 10, 0, GameWorld.TILESIZE, GameWorld.TILESIZE, gameWorld);
		setTeamType(MAP_TEAM);

		this.id = id;
		setProperties();

		if (name.endsWith("_right")) {
			name = name.substring(0, name.length() - "_right".length()) + "_left";
			animation = DataLoader.getInstance().getAnimation(name);
			animation.flipAllImage();
		} else {
			animation = DataLoader.getInstance().getAnimation(name);

			if (id > 0) {
				animation.setRepeated(false);
			}
		}

		setPosX(posX + getWidth() / 2);
		setPosY(posY + getHeight() / 2);
		setWidth(animation.getCurrentFrameImages().getImageWidth());
		setHeight(animation.getCurrentFrameImages().getImageHeight());
	}
	
	public int getId() {
		return id;
	}

	private void setProperties() {

		for (int i = 0; i < ID_TRANSPORT_LEFT.length; i++) {
			if (id == ID_TRANSPORT_LEFT[i]) {
				name = "transport" + (i + 1) + "_left";
				return;
			} else if (id == ID_TRANSPORT_RIGHT[i]) {
				name = "transport" + (i + 1) + "_right";
				return;
			}
		}

		switch (id) {
		case ID_BOX:
			name = "object" + id;
			maxHealth = BOX_HEALTH;
			setHealth(BOX_HEALTH);
			setMass(0.05f);
			break;

		case ID_BARREL:
		case ID_BARREL + 1:
			name = "object" + id;
			maxHealth = BARREL_HEALTH;
			setHealth(BARREL_HEALTH);
			break;

		case ID_PLATFORM:
			name = "platform";
			break;

		case ID_ENTRY_LEFT:
			name = "entry_left";
			setHeight(ENTRY_HEIGHT * GameWorld.TILESIZE);
			setHealth(ENTRY_HEALTH);
			break;

		case ID_ENTRY_RIGHT:
			name = "entry_right";
			setHeight(ENTRY_HEIGHT * GameWorld.TILESIZE);
			setHealth(ENTRY_HEALTH);
			break;

		case ID_HAMER:
			name = "hammer_";
			name += ObjectManager.SERIAL_NUMBER + 1;
			ObjectManager.SERIAL_NUMBER = (ObjectManager.SERIAL_NUMBER + 1) % 2;

			setHeight(HAMMER_HEIGHT * GameWorld.TILESIZE);
			setHealth(HAMMER_HEALTH);
			setDamage(HAMMER_DAMAGE);
			break;
		}
	}

	public void Update() {

		if (System.nanoTime() - startTimeNoBeHurt >= timeNoBeHurt) {
			setState(ALIVE);
		}

		if (id >= 0) {
			if (getHealth() <= 0) {
				beHurt(1);
			}

			setPosX(getPosX() + getSpeedX());
			setPosY(getPosY() + getSpeedY());
			
			CollisionResult bottomCollisionObject = getGameWorld().getObjectManager().BottomCollisionWithObject(this);
			
			UpdateCollisionWithMapLand(bottomCollisionObject);

			UpdateCollisionWithMapTop(bottomCollisionObject);
			
			UpdateCollisionWithMapLeftRight(bottomCollisionObject);

			return;
		}

		animation.Update(System.nanoTime());

		int id_frame = Character.getNumericValue(animation.getCurrentFrameImages().getName()
				.charAt(animation.getCurrentFrameImages().getName().length() - 1));
		int x = (int) (getPosY() - getHeight() / 2) / GameWorld.TILESIZE;
		int y = (int) ((getPosX() - getWidth() / 2) / GameWorld.TILESIZE);

		switch (id) {
		case ID_PLATFORM:
			getGameWorld().getMapGame().setCollisionMap(x, y, 0);
			for (int i : IS_PLATFORM_CLOSE) {
				if (id_frame == i) {
					getGameWorld().getMapGame().setCollisionMap(x, y, MapGame.WALL_TILE);
					break;
				}
			}
			break;

		case ID_ENTRY_LEFT:
		case ID_ENTRY_RIGHT:
			getGameWorld().getMapGame().setCollisionMap(x, y, 0);
			getGameWorld().getMapGame().setCollisionMap(x + 1, y, 0);
			for (int i : IS_ENTRY_CLOSE) {
				if (id_frame == i) {
					getGameWorld().getMapGame().setCollisionMap(x, y, MapGame.WALL_TILE);
					getGameWorld().getMapGame().setCollisionMap(x + 1, y, MapGame.WALL_TILE);
					break;
				}
			}
			break;

		case ID_HAMER:
			getGameWorld().getMapGame().setCollisionMap(x + 1, y, MapGame.HAMMER_TILE);
			getGameWorld().getMapGame().setCollisionMap(x + 2, y, MapGame.HAMMER_TILE);

			for (int i : IS_NOT_HAMMER_BLOCK_1) {
				if (id_frame == i) {
					getGameWorld().getMapGame().setCollisionMap(x + 2, y, 0);
					break;
				}
			}

			for (int i : IS_NOT_HAMMER_BLOCK_2) {
				if (id_frame == i) {
					getGameWorld().getMapGame().setCollisionMap(x + 1, y, 0);
					getGameWorld().getMapGame().setCollisionMap(x + 2, y, 0);
					break;
				}
			}
			break;
		}

	}
	
	private void UpdateCollisionWithMapLand(CollisionResult collisionObject) {
		Rectangle boundForCollisionWithMapFuture;
		CollisionResult hitBox;

		// Va chạm với đất
		if (isOnTransportLeft()) {
			setSpeedX(getSpeedX() - MapObject.TRANSPORT_SPEED * Object.LEFT_DIR);
			setOnTransportLeft(false);
		}

		boundForCollisionWithMapFuture = movingHitbox();
		boundForCollisionWithMapFuture.y += (getSpeedY() != 0 ? getSpeedY() : 2);
		hitBox = getGameWorld().getMapGame().haveCollisionWithLand(boundForCollisionWithMapFuture);

		switch (hitBox.getCollisionWithTile()) {
			case MapGame.TRANSPORT_LEFT_TILE:
				setOnTransportLeft(true);
				setSpeedX(getSpeedX() + MapObject.TRANSPORT_SPEED * Object.LEFT_DIR);

			case MapGame.WALL_TILE:
			case MapGame.PLATFORM_TILE:
				setPosY(hitBox.getCollisionRect().y - getHeight() / 2);
				setSpeedY(0);
				break;

			case MapGame.DEATH_TILE:
				setHealth(-1000);
				break;

			default:
				if(collisionObject != null && collisionObject.getCollisionWithTile() == KeyConfig.DOWN) {
					if(getSpeedY() > 0) {
						setSpeedY(0);
						setPosY(collisionObject.getObjectCollisionWith().getPosY() - (collisionObject.getObjectCollisionWith().getHeight() + getHeight()) / 2 + 1);
					}
					break;
				}
				
				setSpeedY(getSpeedY() + getMass());
				break;
		}
	}

	private void UpdateCollisionWithMapTop(CollisionResult collisionObject) {
		CollisionResult hitBox;
//		Rectangle boundForCollisionWithMapFuture = movingHitbox();
		
		hitBox = getGameWorld().getMapGame().haveCollisionWithTop(movingHitbox());
		if(hitBox.getCollisionWithTile() == MapGame.HAMMER_TILE) {
			beHurt(20);
		}
	}
	
	private void UpdateCollisionWithMapLeftRight(CollisionResult collisionObject) {
		CollisionResult hitBox;
//		Rectangle boundForCollisionWithMapFuture = movingHitbox();
		
		hitBox = getGameWorld().getMapGame().haveCollisionWithWallLeft(movingHitbox());
		switch (hitBox.getCollisionWithTile()) {
			case MapGame.WALL_TILE:
			case MapGame.TRANSPORT_LEFT_TILE:
				setSpeedX(0);
				setPosX(hitBox.getCollisionRect().x + hitBox.getCollisionRect().width + getWidth() / 2);
				break;

			case MapGame.HAMMER_TILE:
				setPosX(getPosX() - getSpeedX());
				break;
		}

		hitBox = getGameWorld().getMapGame().haveCollisionWithWallRight(movingHitbox());
		switch (hitBox.getCollisionWithTile()) {
			case MapGame.WALL_TILE:
			case MapGame.TRANSPORT_LEFT_TILE:
				setSpeedX(0);
				setPosX(hitBox.getCollisionRect().x - getWidth() / 2 - 1);
				break;

			case MapGame.HAMMER_TILE:
				setPosX(getPosX() - getSpeedX());
				break;
		}
	}
	
	public void draw(Graphics2D g2) {

//		Rectangle rect = movingHitbox();
//		int posX1 = rect.x / GameWorld.TILESIZE;
//	    int posX2 = (rect.x + rect.width - 1) / GameWorld.TILESIZE;
//	    int posY1 = rect.y / GameWorld.TILESIZE;
//	    int posY2 = (rect.y + rect.height - 1) / GameWorld.TILESIZE;
//
//	    posX1 = Math.max(0, posX1);
//	    posY1 = Math.max(0, posY1);
//	    posX2 = Math.min(getGameWorld().getMapGame().getWallMap()[0].length - 1, posX2);
//	    posY2 = Math.min(getGameWorld().getMapGame().getWallMap().length - 1, posY2);
//	    
//	    for(int x=posX1; x<=posX2; ++x) {
//	    	for(int y=posY1; y<=posY2; ++y) {
//	    		getGameWorld().getMapGame().drawTileset(g2, MapGame.OUTSIDE, 	getGameWorld().getMapGame().getOutsideMap(),	y, x);
//	    		getGameWorld().getMapGame().drawTileset(g2, MapGame.INSIDE,		getGameWorld().getMapGame().getInsideMap(),		y, x);
//	    		getGameWorld().getMapGame().drawTileset(g2, MapGame.WALL, 		getGameWorld().getMapGame().getWallMap(),		y, x);
//	    		getGameWorld().getMapGame().drawTileset(g2, MapGame.OBJECT, 	getGameWorld().getMapGame().getLadderMap(), 	y, x);
//	    		getGameWorld().getMapGame().drawTileset(g2, MapGame.OBJECT, 	getGameWorld().getMapGame().getObjectMap(), 	y, x);
//	    	}
//	    }

		if (id >= 0) {
			drawMovingHitbox(g2);
		}

		animation.draw((int) getPosX(), (int) getPosY(), g2);
	}

	@Override
	public void beHurt(float damageGet) {
		if (getState() == NOBEHURT) {
			return;
		}
		setState(NOBEHURT);
		startTimeNoBeHurt = System.nanoTime();

		int magicNumber = 5;

		if (id >= 0) {

			int deltaY = animation.getCurrentFrameImages().getImageHeight();
			setHealth(getHealth() - damageGet);

			if (getHealth() <= 0) {
				animation.setCurrentFrame(2);
				deltaY -= animation.getCurrentFrameImages().getImageHeight() + magicNumber;
				if (deltaY >= magicNumber) {
					setPosY(getPosY() + deltaY);
				}

			} else if (getHealth() <= 0.75 * maxHealth) {
				animation.setCurrentFrame(1);
				deltaY -= animation.getCurrentFrameImages().getImageHeight();
				setPosY(getPosY() + deltaY);

			} else {
				animation.setCurrentFrame(0);
			}
			setWidth(animation.getCurrentFrameImages().getImageWidth());
			setHeight(animation.getCurrentFrameImages().getImageHeight());

			return;
		}

		setHealth(getHealth() - 2);
	}

	@Override
	public Rectangle attackHitbox() {
		return null;
	}

}