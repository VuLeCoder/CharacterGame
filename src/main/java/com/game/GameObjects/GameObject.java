package com.game.GameObjects;

public abstract class GameObject {
	private float posX, posY;
	private GameWorld gameWorld;
	
	public GameObject(float posX, float posY, GameWorld gameWorld) {
		this.posX = posX;
		this.posY = posY;
		this.gameWorld = gameWorld;
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

	public void setPosX(float posX) {
		this.posX = posX;
	}

	public void setPosY(float posY) {
		this.posY = posY;
	}

	public void setGameWorld(GameWorld gameWorld) {
		this.gameWorld = gameWorld;
	}
	
	public abstract void Update();
}
