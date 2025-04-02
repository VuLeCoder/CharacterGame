package com.game.GameObjects;

public class Camera extends GameObject{
	private float widthView;
	private float heightView;
	
	private boolean isLocked = false;
	
	public Camera(float x, float y, float widthView, float heightView, GameWorld gameWorld) {
		super(x, y, gameWorld);
		this.widthView = widthView;
		this.heightView = heightView;
	}
	
	public void lock() {
		isLocked = true;
	}
	
	public void unlock() {
		isLocked = false;
	}

	public float getWidthView() {
		return widthView;
	}

	public float getHeightView() {
		return heightView;
	}

	public boolean isLocked() {
		return isLocked;
	}

	public void setWidthView(float widthView) {
		this.widthView = widthView;
	}

	public void setHeightView(float heightView) {
		this.heightView = heightView;
	}

	public void setLocked(boolean isLocked) {
		this.isLocked = isLocked;
	}
	
	@Override
	public void Update() {
		if(isLocked) {
			return;
		}
		
		Megaman mainCharacter = getGameWorld().megaMan;
		
		if(mainCharacter.getPosX() - getPosX() > 400) {
			setPosX(mainCharacter.getPosX() - 400);
		}
		
		if(mainCharacter.getPosX() - getPosX() < 200) {
			setPosX(mainCharacter.getPosX() - 200);
		}
		
		if(mainCharacter.getPosY() - getPosY() > 400) {
			setPosY(mainCharacter.getPosY() - 400);
		} else if(mainCharacter.getPosY() - getPosY() < 250) {
			setPosY(mainCharacter.getPosY() - 250);
		}
	}
	
	
	
}
