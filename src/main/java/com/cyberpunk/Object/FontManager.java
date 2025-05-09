package com.cyberpunk.Object;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.InputStream;

public class FontManager {
	private static Font customFont;

	static {
		try {
			InputStream is = FontManager.class.getResourceAsStream("/fonts/CyberpunkCraftpixPixel.otf");
			
			customFont = Font.createFont(Font.TRUETYPE_FONT, is);
			GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(customFont);
		} catch (Exception e) {
			e.printStackTrace();
			// fallback font nếu load thất bại
			customFont = new Font("Arial", Font.PLAIN, 24);
		}
	}

	public static Font getFont(float size) {
		return customFont.deriveFont(size);
	}
}

