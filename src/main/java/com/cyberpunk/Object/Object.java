package com.cyberpunk.Object;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public abstract class Object {
	private GameWorld gameWorld;
	
	public static final int ALIVE = 0;
	public static final int BEHURT = 1;
	public static final int DEATH = 2;
//	public static final int FEY = 3;
	public static final int NOBEHURT = 4;
	
	public static final int LEFT_DIR = -1;
	public static final int RIGHT_DIR = 1;
	
	public static final int MAP_TEAM = 0;
	public static final int P1_TEAM = 1;
	public static final int P2_TEAM = 2;
	
	private float posX;
	private float posY;
	
	private int state = ALIVE;
	private float health;
	private float damage;
	private int teamType;
	
	private int width;
	private int height;
	private float mass;
	private float speedX = 0;
	private float speedY;
	
	private int direction;
	private boolean isOnTransportLeft;
	
	public Object(float posX, float posY, int health, int damage, int width, int height, GameWorld gameWorld) {
		this.posX = posX;
		this.posY = posY;
		this.health = health;
		this.damage = damage;
		this.width = width;
		this.height = height;
		this.gameWorld = gameWorld;
		
		isOnTransportLeft = false;
	}
	
	public GameWorld getGameWorld() {
		return gameWorld;
	}
	
	public float getPosX() {
		return posX;
	}

	public void setPosX(float posX) {
		this.posX = posX;
	}

	public float getPosY() {
		return posY;
	}

	public void setPosY(float posY) {
		this.posY = posY;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public float getHealth() {
		return health;
	}

	public void setHealth(Float health) {
		this.health = health;
	}

	public float getDamage() {
		return damage;
	}

	public void setDamage(Float damage) {
		this.damage = damage;
	}

	public int getTeamType() {
		return teamType;
	}

	public void setTeamType(int teamType) {
		this.teamType = teamType;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public float getMass() {
		return mass;
	}

	public void setMass(float mass) {
		this.mass = mass;
	}

	public float getSpeedX() {
		return speedX;
	}

	public void setSpeedX(float speedX) {
		this.speedX = speedX;
	}

	public float getSpeedY() {
		return speedY;
	}

	public void setSpeedY(float speedY) {
		this.speedY = speedY; //jump strength
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}
//	public abstract void attack();
	public boolean isOnTransportLeft() {
 		return isOnTransportLeft;
 	}
 
 	public void setOnTransportLeft(boolean isOnTransportLeft) {
 		this.isOnTransportLeft = isOnTransportLeft;
 	}

	public Rectangle movingHitbox() {
		Rectangle hitbox = new Rectangle();
		hitbox.x = (int) getPosX() - getWidth()/2;
		hitbox.y = (int) getPosY() - getHeight()/2;
		hitbox.width = getWidth();
		hitbox.height = getHeight();	
		
//		if(getDirection() == LEFT_DIR && hitbox.x < getPosX() + getWidth()/2) hitbox.x += getWidth();

		return hitbox;
	}
	
	public void drawMovingHitbox(Graphics2D g2) {
		Rectangle rect = movingHitbox();
		g2.setColor(Color.red);
		g2.drawRect(rect.x, rect.y, rect.width, rect.height);
	}
	
	public void drawAttackHitbox(Graphics2D g2) {
		Rectangle rect = attackHitbox();
		g2.setColor(Color.black);
		g2.drawRect(rect.x, rect.y, rect.width, rect.height);
		
		
	}
	
	public abstract Rectangle attackHitbox();
	public abstract void beHurt(int damageGet);
	public abstract void draw(Graphics2D g2);
	public abstract void Update();
	
}
