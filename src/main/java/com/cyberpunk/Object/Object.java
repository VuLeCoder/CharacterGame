package com.cyberpunk.Object;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.cyberpunk.Effect.Animation;

public abstract class Object {
	private GameWorld gameWorld;
	
	public static final int ALIVE = 0;
	public static final int BEHURT = 1;
	public static final int DEATH = 2;
//	public static final int FEY = 3;
	public static final int NOBEHURT = 4;
	
	public static final int LEFT_DIR = 0;
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
	private int mass;
	private int speedX;
	private int speedY;
	
	private int direction;
	
	private int noBeHurtDuration;
	private int noBeHurtStart;
	
	private Animation hurtForwardAni, hurtBackAni;

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

	public int getMass() {
		return mass;
	}

	public void setMass(int mass) {
		this.mass = mass;
	}

	public int getSpeedX() {
		return speedX;
	}

	public void setSpeedX(int speedX) {
		this.speedX = speedX;
	}

	public int getSpeedY() {
		return speedY;
	}

	public void setSpeedY(int speedY) {
		this.speedY = speedY;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public int getNoBeHurtDuration() {
		return noBeHurtDuration;
	}

	public void setNoBeHurtDuration(int noBeHurtDuration) {
		this.noBeHurtDuration = noBeHurtDuration;
	}

	public int getNoBeHurtStart() {
		return noBeHurtStart;
	}

	public void setNoBeHurtStart(int noBeHurtStart) {
		this.noBeHurtStart = noBeHurtStart;
	}

	public Animation getHurtForwardAni() {
		return hurtForwardAni;
	}

	public void setHurtForwardAni(Animation hurtForwardAni) {
		this.hurtForwardAni = hurtForwardAni;
	}

	public Animation getHurtBackAni() {
		return hurtBackAni;
	}

	public void setHurtBackAni(Animation hurtBackAni) {
		this.hurtBackAni = hurtBackAni;
	}

	public Object(GameWorld gameWorld, float posX, float posY, int health, int damage, int teamType, int width,
			int height, int direction) {
		this.gameWorld = gameWorld;
		this.posX = posX;
		this.posY = posY;
		this.health = health;
		this.damage = damage;
		this.teamType = teamType;
		this.width = width;
		this.height = height;
		this.direction = direction;
	}
	
	public abstract void attack();

	public Rectangle movingHitbox() {
		Rectangle hitbox = new Rectangle();
		hitbox.x = (int) getPosX() - getWidth()/2;
		hitbox.y = (int) getPosY() - getHeight()/2;
		hitbox.width = getWidth();
		hitbox.height = getHeight();

		return hitbox;
	}
	
	public abstract void beHurt(int damageGet);
	public abstract void Update();
	public abstract void draw(Graphics2D g2);
}
