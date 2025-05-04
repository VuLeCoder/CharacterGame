package com.cyberpunk.Object;

import java.awt.Rectangle;

public class CollisionResult {
	private Rectangle rect;
    private int tile;
    
    public CollisionResult() {
    	this.rect = null;
    	this.tile = 0;
    }

    public CollisionResult(Rectangle rect, int tile) {
        this.rect = rect;
        this.tile = tile;
    }
    
    public int getCollisionWithTile() {
    	return tile;
    }

	public Rectangle getCollisionRect() {
		return rect;
	}

	public void setCollisionRect(Rectangle rect) {
		this.rect = rect;
	}

	public void setCollisionWithTile(int tile) {
		this.tile = tile;
	}
}
