package com.cyberpunk.Object;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.cyberpunk.Effect.Animation;
import com.cyberpunk.Effect.DataLoader;
import com.cyberpunk.StartGame.GameFrame;

public class StartScreen {
	// button
	public static final int BUTTON_WIDTH = 70;
	public static final int BUTTON_HEIGHT = 70;
	public static final int BUTTON_X = (GameFrame.SCREEN_WIDTH - BUTTON_WIDTH) / 2;
	public static final int BUTTON_Y = 310;
	
	//logo
	public static int LOGO_X, LOGO_Y;
	
	
	private final int numberFrame = 107;
	private final int tileWidth = 12;
	private final int tileHeight = 640;
	private final String nameAnimation = "idleScreen_";
	
	private final Animation[] screenAnimation;
	private final BufferedImage logoImage;
	private final String buttonName = "button";
	private final BufferedImage buttonImage;
	private final String logoName = "logo";
	
	public StartScreen() {
		screenAnimation = new Animation[numberFrame]; 
		for(int i=0; i<numberFrame; ++i) {
			screenAnimation[i] = DataLoader.getInstance().getAnimation(nameAnimation + (i + 1));
		}
		
		buttonImage = DataLoader.getInstance().getFrameImage(buttonName).getImage();
		
		logoImage = DataLoader.getInstance().getFrameImage(logoName).getImage();
		LOGO_X = (GameFrame.SCREEN_WIDTH - logoImage.getWidth()) / 2;
		LOGO_Y = 10;
	}
	
	public void Update() {
		for(int i=0; i<numberFrame; ++i) {
			screenAnimation[i].Update(System.nanoTime());
		}
	}
	
	public void draw(Graphics2D g2) {
		for(int i=0; i<numberFrame; ++i) {
			screenAnimation[i].draw(i * tileWidth + tileWidth / 2, tileHeight / 2, g2);
		}
		g2.drawImage(logoImage, LOGO_X, LOGO_Y, null);
		g2.drawImage(buttonImage, BUTTON_X, BUTTON_Y, null);
		
//		g2.setColor(Color.ORANGE);
//		g2.fillRect(StartScreen.BUTTON_X, StartScreen.BUTTON_Y, StartScreen.BUTTON_WIDTH, StartScreen.BUTTON_HEIGHT);

//		g2.setColor(Color.WHITE);
//		g2.drawString("START GAME", StartScreen.BUTTON_X + 12, StartScreen.BUTTON_Y + 30);
	}
}
