package com.cyberpunk.Object;

import java.awt.Graphics2D;
import java.awt.Rectangle;

public class BaseAttack extends Object{
	public BaseCharacter baseCharacter;

	public BaseAttack(int damage, BaseCharacter baseCharacter, GameWorld gameWorld) {
		super(baseCharacter.getPosX() + (baseCharacter.getDirection() == RIGHT_DIR ? 20 : -20), 
				baseCharacter.getPosY(), 10, damage, 10, 10, gameWorld);
		this.setTeamType(baseCharacter.getTeamType());
		this.baseCharacter = baseCharacter;
	}

	@Override
	public Rectangle attackHitbox() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void Update() {
		setPosX(baseCharacter.getPosX() + (baseCharacter.getDirection() == RIGHT_DIR ? 20 : -20));
		
		Object object = getGameWorld().skillManager.getCollisionWithEnemy(this);
		if(object != null && object.getState() != NOBEHURT) {
			System.out.println("hurt");
			object.beHurt((int) this.getDamage());
			setState(DEATH);
		}
	}

	@Override
	public void draw(Graphics2D g2) {
		// TODO Auto-generated method stub
		g2.drawRect((int) getPosX(), (int) getPosY(), (int) getWidth(), (int) getHeight());
		if(baseCharacter.isAttacking) {
		}
	}

	@Override
	public void beHurt(int damageGet) {
		// TODO Auto-generated method stub
		
	}

}
