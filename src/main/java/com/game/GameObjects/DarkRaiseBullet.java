package com.game.GameObjects;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.game.Effect.Animation;
import com.game.Effect.CacheDataLoader;

public class DarkRaiseBullet extends Bullet {

	private Animation forwardBulletAnim, backBulletAnim;

	public DarkRaiseBullet(float x, float y, GameWorld gameWorld) {
		super(x, y, 30, 30, 1.0f, 10, gameWorld);
		forwardBulletAnim = CacheDataLoader.getInstance().getAnimation("darkraisebullet");
		backBulletAnim = CacheDataLoader.getInstance().getAnimation("darkraisebullet");
		backBulletAnim.flipAllImage();
	}

	@Override
	public Rectangle getBoundForCollisionWithEnemy() {
		return getBoundForCollisionWithMap();
	}

	@Override
	public void draw(Graphics2D g2) {
		if (getSpeedX() > 0) {
			forwardBulletAnim.Update(System.nanoTime());
			forwardBulletAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()),
					(int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
		} else {
			backBulletAnim.Update(System.nanoTime());
			backBulletAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()),
					(int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
		}
	}

	@Override
	public void Update() {
		super.Update();
	}

	@Override
	public void attack() {}
}