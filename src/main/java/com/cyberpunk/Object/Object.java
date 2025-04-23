package com.cyberpunk.Object;

import java.awt.Graphics2D;
import java.awt.Rectangle;

public abstract class Object {
	private final GameWorld gameWorld;
	
	public static final int ALIVE = 0;
	public static final int BEHURT = 1;
	public static final int DEATH = 2;
	public static final int NOBEHURT = 4;
	
	public static final int LEFT_DIR = -1;
	public static final int RIGHT_DIR = 1;
	
	public static final int MAP_TEAM = 0;
	public static final int P1_TEAM = 1;
	public static final int P2_TEAM = 2;
	
	private float posX;
	private float posY;
	
	private int state = ALIVE;
	private int health;
	private int damage;
	private int teamType;
	
	private int width;
	private int height;
	private float mass = 0.1f;
	private float speedX;
	private float speedY;
	
	private int direction;
	
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

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
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
		this.speedY = speedY;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public Object(GameWorld gameWorld, float posX, float posY, int health, int damage, int width, int height) {
		this.gameWorld = gameWorld;
		this.posX = posX;
		this.posY = posY;
		this.width = width;
		this.height = height;
		this.health = health;
		this.damage = damage;
	}

	public Rectangle movingHitbox() {
		Rectangle hitbox = new Rectangle();
		hitbox.x = (int) getPosX() - getWidth() / 2;
		hitbox.y = (int) getPosY() - getHeight() / 2;
//		hitbox.x = (int) getPosX();
//		hitbox.y = (int) getPosY();
		hitbox.width = getWidth();
		hitbox.height = getHeight();

		return hitbox;
	}
	
	public abstract void Update();
	public abstract void draw(Graphics2D g2);
	public abstract void beHurt(int damageGet);
}