package com.cyberpunk.Object;

import java.awt.Color;
import java.awt.Graphics2D;

import com.cyberpunk.StartGame.GamePanel;

public class Camera {
	private static final float OFFSET_X = 175;
	private static final float OFFSET_Y = 150;
	public static final float SCREEN_RATIO = (GamePanel.MAP_WIDTH) / (GamePanel.MAP_HEIGHT); // 2.0
//	private static final float MIN_HEIGTH = 224;
//	private static final float MIN_WIDTH = MIN_HEIGTH * SCREEN_RATIO;
	
	// Hệ số làm mượt (càng gần 0 thì càng chậm, càng mượt)
	public static final float SMOOTH_FACTOR = 0.07f;
	
	private float posX, posY;
	private float widthView, heightView;
	private final BaseCharacter P1, P2;
	
	public Camera(float x, float y, float width, float height, GameWorld gameWorld) {
		this.posX = x;
		this.posY = y;
		
		this.widthView = width;
		this.heightView = height;
		
		P1 = gameWorld.getP1();
		P2 = gameWorld.getP2();
	}
	
	public void setPosX(float x) {
		this.posX = x;
	}
	
	public void setPosY(float y) {
		this.posY = y;
	}
	
	public float getPosX() {
		return posX;
	}
	
	public float getPosY() {
		return posY;
	}
	
	public float getHeightView() {
		return heightView;
	}
	
	public float getWidthView() {
		return widthView;
	}
	
	public void Update() {
		if(P1.getState() == Object.DEATH && P2.getState() == Object.DEATH) {
			return;
		}
		
		float minX, maxX, minY, maxY;

		if (P1.getState() == Object.DEATH) {
		    minX = maxX = P2.getPosX();
		    minY = maxY = P2.getPosY();
		} else if (P2.getState() == Object.DEATH) {
		    minX = maxX = P1.getPosX();
		    minY = maxY = P1.getPosY();
		} else {
		    minX = Math.min(P1.getPosX(), P2.getPosX());
		    maxX = Math.max(P1.getPosX(), P2.getPosX());
		    minY = Math.min(P1.getPosY(), P2.getPosY());
		    maxY = Math.max(P1.getPosY(), P2.getPosY());
		}
		
		float offsetLeftX = Math.min(minX, OFFSET_X);
		float offsetRightX = Math.min(((GamePanel.MAP_WIDTH) - maxX), OFFSET_X);
		
		setPosX(minX - offsetLeftX);
		widthView = maxX + offsetRightX - getPosX();
		
		float offsetUpY = Math.min(minY, OFFSET_Y);
		float offsetDownY = Math.min(((GamePanel.MAP_HEIGHT) - maxY), OFFSET_Y);
		
		setPosY(minY - offsetUpY);
		heightView = maxY + offsetDownY - getPosY();
		
		// code here
		
		float currentRatio = widthView / heightView;

		if (currentRatio > SCREEN_RATIO) {
		    float newHeight = widthView / SCREEN_RATIO;
		    float diff = newHeight - heightView;
		    setPosY(getPosY() - diff / 2); // camera cần dãn đều 2 bên
		    heightView = newHeight;
		    
		} else if (currentRatio < SCREEN_RATIO) {
		    float newWidth = heightView * SCREEN_RATIO;
		    float diff = newWidth - widthView;
		    setPosX(getPosX() - diff / 2); // camera cần dãn đều 2 bên
		    widthView = newWidth;
		}
		
//		if(getPosX() + widthView > GamePanel.MAP_WIDTH) {
//			setPosX(GamePanel.MAP_WIDTH - widthView);
//		}
//		
		if(getPosY() + heightView > GamePanel.MAP_HEIGHT) {
			setPosY(GamePanel.MAP_HEIGHT- heightView);
		}
	}
	
	public void draw(Graphics2D g2) {
		g2.setColor(Color.cyan);
		g2.drawRect((int) posX, (int)posY, (int)widthView, (int)heightView);
	}
	
	public boolean isOutOfCameraView(float x, float y) {
	    return x < getPosX() || x > getPosX() + getWidthView()
	        || y < getPosY() || y > getPosY() + getHeightView();
	}

}
