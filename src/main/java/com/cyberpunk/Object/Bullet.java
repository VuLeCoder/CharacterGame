package com.cyberpunk.Object;

import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Bullet extends Object{
	BaseCharacter baseCharacter;
	float dir;
	public static final int[][] speed = {{0, 7}, {5, 5}, {7, 0}, {5, -5}, {0, -7}};
	public static final float[] offSetX = {6, 15, 18, 15, 7};
	public static final float[] offSetY = {8, 3, -6, -15, -20};

	public Bullet(float posX, float posY,int dir,  int damage, BaseCharacter baseCharacter, GameWorld gameWorld) {
		super(posX, posY, 10, damage, 2, 2, gameWorld);
		this.setTeamType(baseCharacter.getTeamType());
		this.baseCharacter = baseCharacter;
		this.dir = dir;
		setPosX(posX + offSetX[dir] * baseCharacter.getDirection());
		setPosY(posY + offSetY[dir]);
		setSpeedX(speed[dir][0] * baseCharacter.getDirection());
		setSpeedY(speed[dir][1]);
	}

	@Override
	public Rectangle attackHitbox() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public boolean checkCollisionAround() {
		CollisionResult hitbox;
		
		hitbox = getGameWorld().getMapGame().haveCollisionWithLand(movingHitbox(), this);
//		System.out.println(hitbox.getCollisionWithTile());
		if(hitbox.getCollisionWithTile() != 0) return true;
		
		hitbox = getGameWorld().getMapGame().haveCollisionWithTop(movingHitbox());
//		System.out.println(hitbox.getCollisionWithTile());
		if(hitbox.getCollisionWithTile() != 0) return true;
		
		hitbox = getGameWorld().getMapGame().haveCollisionWithWallLeft(movingHitbox());
//		System.out.println(hitbox.getCollisionWithTile());
		if(hitbox.getCollisionWithTile() != 0) return true;
		
		hitbox = getGameWorld().getMapGame().haveCollisionWithWallRight(movingHitbox());
//		System.out.println(hitbox.getCollisionWithTile());	
		if(hitbox.getCollisionWithTile() != 0) return true;
		
		return false;
	}

	@Override
	public void Update() {
		
		setPosX(getPosX() + getSpeedX());
		setPosY(getPosY() + getSpeedY());
		
		Object object = getGameWorld().skillManager.getCollisionWithEnemy(this);
		if(object != null && object.getState() != NOBEHURT) {
//			System.out.println("hurt");
			object.beHurt((int) this.getDamage());
			setState(DEATH);
		}
		
		if(checkCollisionAround() && getState() != DEATH) setState(DEATH);
	}

	@Override
	public void draw(Graphics2D g2) {
		// TODO Auto-generated method stub
		g2.drawRect((int) getPosX(), (int) getPosY(), (int) getWidth(), (int) getHeight());
	}

	@Override
	public void beHurt(int damageGet) {
		// TODO Auto-generated method stub
		
	}

}
