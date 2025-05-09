package com.cyberpunk.Object;

import java.awt.Graphics2D;

public class SkillManager extends ObjectManager{

	public SkillManager(GameWorld gameWorld) {
		super(gameWorld);
		
	}
	
	public Object getCollisionWithEnemy(Object object) {
		for(int id = 0; id < getGameWorld().getObjectManager().entity.size(); id++) {
			Object objectInList = getGameWorld().getObjectManager().entity.get(id);
			
			if(object.getTeamType() != Object.MAP_TEAM && objectInList.getTeamType() != Object.MAP_TEAM 
					&& object.getTeamType() != objectInList.getTeamType()) {
//				System.out.println("hurt");
//				System.out.println(objectInList.getClass().getSimpleName());
				if(object.movingHitbox().intersects(objectInList.movingHitbox())) {
					return objectInList;
				}
			}
		}
		return null;
	}
	
	@Override
	public void UpdateObjects() {
		for(int id = 0; id < entity.size(); id++) {
			if(entity.get(id) != null) {
				entity.get(id).Update();
			}
			Object objectInList = entity.get(id);
//			System.out.println(objectInList.getClass().getSimpleName());

			if(objectInList.getState() == Object.DEATH) {
				entity.remove(id);
			}
		}
	}
	
	@Override
	public void draw(Graphics2D g2) {
		for (int id = entity.size() - 1; id >= 0; --id) {
			if(entity.get(id) != null) {
				entity.get(id).draw(g2);
			}
			
		}
	}

}
