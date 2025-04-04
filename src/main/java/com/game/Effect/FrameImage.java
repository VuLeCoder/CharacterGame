package com.game.Effect;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class FrameImage {
	
	private String name;
	private BufferedImage image;
	
	public FrameImage() {
		this.name = null;
		this.image = null;
	}
	
	public FrameImage(String name, BufferedImage image) {
		this.name = name;
		this.image = image;
	}

	public FrameImage(FrameImage frameImage) {
		image = new BufferedImage(frameImage.getImageWidth(), 
									frameImage.getImageHeight(),
									frameImage.getImage().getType());
		
		Graphics g = image.getGraphics();
		g.drawImage(frameImage.getImage(), 0, 0, null);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}
	
	public int getImageWidth() {
		return image.getWidth();
	}
	
	public int getImageHeight() {
		return image.getHeight();
	}
	
	public void draw(Graphics2D g2, int x, int y) {
		g2.drawImage(image, x - getImageWidth() / 2, 
					x - getImageHeight() / 2, null);
	}
}
