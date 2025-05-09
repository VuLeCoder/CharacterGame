package com.cyberpunk.Object;

import java.awt.Graphics2D;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class SkillManager extends ObjectManager{
	
	protected List<BaseAttack> dameObjects;
	protected List<Bullet> bulletList;

	public SkillManager(GameWorld gameWorld) {
		super(gameWorld);
		
		dameObjects = new LinkedList<>();
		bulletList = new LinkedList<>();
	}
	
//	
//	public static final float[][] bulletSpeed = {{x, y}, {}, {}, {}, {}};
//	public static final float[] bulletGoc = {{0}, {}, {}, {}, {}};
//	
//	public Bullet(int handFrame) {
//		setSpeedX(bulletSpeed[handFrame][0]);
//		setSpeedY(bulletSpeed[handFrame][1]);
//	}
	
	public Object getCollisionWithEnemy(Object object) {
		for(int id = 0; id < getGameWorld().getObjectManager().entity.size(); id++) {
			Object objectInList = getGameWorld().getObjectManager().entity.get(id);
			
			if(object.getTeamType() != Object.MAP_TEAM && objectInList.getTeamType() != Object.MAP_TEAM 
					&& object.getTeamType() != objectInList.getTeamType()) {

				if(object.movingHitbox().intersects(objectInList.movingHitbox())) {
					return objectInList;
				}
			}
		}
		return null;
	}
	
	@Override
	public void addObject(Object object) {
		if(object instanceof BaseAttack) {
			dameObjects.add((BaseAttack)object);
			return;
		}
		
		bulletList.add((Bullet) object);
	}
	
	@Override
	public void UpdateObjects() {
		// List đạn
		Iterator<Bullet>  iterator = bulletList.iterator();
		while(iterator.hasNext()) {
			Bullet bulletInList = iterator.next();
			if(bulletInList.getState() == Object.DEATH) {
				iterator.remove();
				continue;
			}
			
			bulletInList.Update();
		}
		
		// List cận chiến
		Iterator<BaseAttack> iter = dameObjects.iterator();
		while (iter.hasNext()) {
			BaseAttack dameObj = iter.next();
			dameObj.Update();
		}
		
		removeAttack(getGameWorld().getP1());
		removeAttack(getGameWorld().getP2());
	}
	
	public void removeAttack(BaseCharacter character) {
		Iterator<BaseAttack> iter = dameObjects.iterator();
		while (iter.hasNext()) {
			BaseAttack dameObj = iter.next();
			if(dameObj.getPerpetrator().equals(character)) {
				iter.remove();
			}
		}
	}
	
	@Override
	public void draw(Graphics2D g2) {
		for (int id = dameObjects.size() - 1; id >= 0; --id) {
			dameObjects.get(id).draw(g2);
		}
		
		for(int id = bulletList.size() - 1; id >= 0; --id) {
			bulletList.get(id).draw(g2);
		}
	}

}