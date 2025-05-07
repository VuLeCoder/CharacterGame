package com.cyberpunk.Object;

import java.awt.Rectangle;

public class CollisionResult { 
	private Object object;
	private Rectangle rect;
    private int tile;
    
    public CollisionResult() {
    	this.object = null;
    	this.rect = null;
    	this.tile = 0;
    }

    public CollisionResult(Rectangle rect, int tile) {
        this.object = null;
    	this.rect = rect;
        this.tile = tile;
    }
    
    public CollisionResult(Object object, int direction) {
    	this.object = object;
    	this.rect = object.movingHitbox();
    	this.tile = direction;
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

	public Object getObjectCollisionWith() {
		return object;
	}

	public void setCollisionWithObject(Object object) {
		this.object = object;
	}
}
