package com.cyberpunk.Object;

import java.awt.Graphics2D;
import java.awt.Rectangle;

//public abstract class CharacterObject extends GameObject{
//	private boolean isJumping = false;
//	private boolean isSitting = false;
//	private boolean isLanding = true;
//	private boolean isClimbing = false;
//
//	public Character(float posX, float posY, float width, float height, float mass, int blood, GameWorld gameWorld) {
//		super(posX, posY, width, height, mass, blood, gameWorld);
//		setState(ALIVE);
//	}
//
//	public boolean getIsJumping() {
//		return isJumping;
//	}
//
//	public boolean getIsSitting() {
//		return isSitting;
//	}
//
//	public boolean getIsLanding() {
//		return isLanding;
//	}
//	
//	public boolean getIsClimbing() {
//		return isClimbing;
//	}
//
//	public void setIsJumping(boolean isJumping) {
//		this.isJumping = isJumping;
//	}
//
//	public void setIsSitting(boolean isSitting) {
//		this.isSitting = isSitting;
//	}
//
//	public void setIsLanding(boolean isLanding) {
//		this.isLanding = isLanding;
//	}
//	
//	public void setIsClimbing(boolean isClimbing) {
//		this.isClimbing = isClimbing;
//	}
//	
//	public abstract void punch();
//	
//	public abstract void shoot();
//	
//	public abstract void walk();
//	
//	public abstract void run();
//	
//	public abstract void jump();
//	
//	public abstract void climb();
//	
//	public abstract void sitDown();
//	
//	public abstract void standUp();
//	
//	public abstract void stopRun();
//	
//	
//	@Override
//	public void Update() {
//		super.Update();
//		
//	}
//
//}


public abstract class CharacterObject extends GameObject {

	public static final int TEAM_1 = 1;
	public static final int TEAM_2 = 2;
	
	public static final int LEFT_DIR = -1;
	public static final int RIGHT_DIR = 1;
	
	public static final int ALIVE = 0;
	public static final int BEHURT = 1;
	public static final int FEY = 2;
	public static final int DEATH = 3;
	public static final int NOBEHURT = 4;
	
	private int teamType;
	private int state = ALIVE;
	private float width;
	private float height;
	private float mass;
	private float speedX;
	private float speedY;
	private int blood;
	private int damage;
	private int direction;
	
	private float posX, posY;
	private GameWorld gameWorld;
	
	public CharacterObject(float posX, float posY, float width, float height, float mass, int blood, GameWorld gameWorld) {
		super(posX, posY, gameWorld);
		
		this.width = width;
		this.height = height;
		this.mass = mass;
		this.blood = blood;
		direction = RIGHT_DIR;
	}

	public float getPosX() {
		return posX;
	}

	public float getPosY() {
		return posY;
	}

	public GameWorld getGameWorld() {
		return gameWorld;
	}
	
	public int getState() {
		return state;
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}

	public float getMass() {
		return mass;
	}

	public float getSpeedX() {
		return speedX;
	}

	public float getSpeedY() {
		return speedY;
	}

	public int getTeamType() {
		return teamType;
	}
	
	public int getBlood() {
		return blood;
	}
	
	public int getDamage() {
		return damage;
	}

	public int getDirection() {
		return direction;
	}
	
	public void setPosX(float posX) {
		this.posX = posX;
	}

	public void setPosY(float posY) {
		this.posY = posY;
	}
	
	public void setState(int state) {
		this.state = state;
	}
	
	public void setSpeedX(float speedX) {
		this.speedX = speedX;
	}

	public void setSpeedY(float speedY) {
		this.speedY = speedY;
	}

	public void setTeamType(int teamType) {
		this.teamType = teamType;
	}
	
	public void setBlood(int blood) {
		if(blood < 0) {
			blood = 0;
		}
		this.blood = blood;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}
	
	public void Update() {
		
	}
	
	public Rectangle getBoundForCollisionWithMap() {
		Rectangle bound = new Rectangle();
		bound.x = (int) (getPosX() - (getWidth() / 2));
		bound.y = (int) (getPosY() - (getHeight() / 2));
		bound.width = (int) getWidth();
		bound.height = (int) getHeight();
		return bound;
	}
	
	public void beHurt(int damageReceived) {
		setBlood(getBlood() - damageReceived);
		state = BEHURT;
		hurtingCallback(); 
	}
	
//	public void drawBoundForCollisionWithMap(Graphics2D g2) {
//		Rectangle rect = getBoundForCollisionWithMap();
//		g2.setColor(Color.BLUE);
//		g2.drawRect(rect.x - (int)getGameWorld().camera.getPosX(),
//					rect.y - (int)getGameWorld().camera.getPosY(),
//					rect.width, rect.height);
//	}
	
	public abstract void draw(Graphics2D g2);
	
//	public abstract void attack();
	
	public void hurtingCallback() {}
}
