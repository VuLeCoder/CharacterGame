package com.game.GameObjects;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.game.Effect.Animation;
import com.game.Effect.CacheDataLoader;

public class BlueFire extends Bullet {
	 private Animation forwardBulletAnim, backBulletAnim;
	 
	 public BlueFire(float x, float y, GameWorld gameWorld) {
		super(x, y, 60, 30, 0.1f, 10, gameWorld);
		
		forwardBulletAnim = CacheDataLoader.getInstance().getAnimation("bluefire");
		backBulletAnim = CacheDataLoader.getInstance().getAnimation("bluefire");
		backBulletAnim.flipAllImage();
	}

	@Override
	public void draw(Graphics2D g2) {
		if(getSpeedX() > 0){
            if(!forwardBulletAnim.isIgnoreFrame(0) && forwardBulletAnim.getCurrentFrame() == 3) {
                forwardBulletAnim.setIgnoreFrame(0);
                forwardBulletAnim.setIgnoreFrame(1);
                forwardBulletAnim.setIgnoreFrame(2);
            }
                
            forwardBulletAnim.Update(System.nanoTime());
            forwardBulletAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()),
           						   (int) getPosY() - (int) getGameWorld().camera.getPosY(),
            						g2);
        
		} else {
            if(!backBulletAnim.isIgnoreFrame(0) && backBulletAnim.getCurrentFrame() == 3) {
                backBulletAnim.setIgnoreFrame(0);
                backBulletAnim.setIgnoreFrame(1);
                backBulletAnim.setIgnoreFrame(2);
            }
            
            backBulletAnim.Update(System.nanoTime());
            backBulletAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()),
            					(int) getPosY() - (int) getGameWorld().camera.getPosY(),
            					g2);
        }
	}

	@Override
	public Rectangle getBoundForCollisionWithEnemy() {
		return getBoundForCollisionWithMap();
	}
	
	@Override
	public void Update() {
		if(forwardBulletAnim.isIgnoreFrame(0) || backBulletAnim.isIgnoreFrame(0) ) {
			setPosX(getPosX() + getSpeedX());
		}
		
//		ParticularObject object = getGameWorld().particularObjectManager.getCollisionWithEnemyObject(this);
//		if(object != null && object.getState() == ALIVE) {
//			setBlood(0);
//			object.setBlood(object.getBlood() - getDamage());
//			object.setState(BEHURT);
//			System.out.println("Bullet set behurt for enemy");
//		}
		
		super.Update();
	}

	@Override
	public void attack() {}
}
