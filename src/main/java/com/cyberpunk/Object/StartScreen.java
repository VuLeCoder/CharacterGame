package com.cyberpunk.Object;

import java.awt.Color;
import java.awt.Font;
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
		// Hoạt cảnh phía sau
		for(int i=0; i<numberFrame; ++i) {
			screenAnimation[i].draw(i * tileWidth + tileWidth / 2, tileHeight / 2, g2);
		}
		
		// Phần bảng game
		g2.drawImage(logoImage, LOGO_X, LOGO_Y, null);
		g2.setColor(Color.WHITE);
//		Font bigFont = new Font("Arial", Font.PLAIN, 30);
        g2.setFont(FontManager.getFont(30f));
		g2.drawString("CYBERPUNK SUPER GAME", StartScreen.LOGO_X + 200, StartScreen.LOGO_Y + 120);
		
		Font smallFont = new Font("Arial", Font.PLAIN, 24);
        g2.setFont(FontManager.getFont(24f));
		g2.drawString("Thanks For Playing", StartScreen.LOGO_X + 250, StartScreen.LOGO_Y + 260);
		
		// Nút bấm
		g2.drawImage(buttonImage, BUTTON_X, BUTTON_Y, null);
	}
}
