package com.cyberpunk.Screen;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JComponent;

import com.cyberpunk.StartGame.GamePanel;

public class CharacterSelectionScreen {
	private boolean isChoosingCharacter;
	
	public CharacterSelectionScreen() {
		isChoosingCharacter = true;
	}
	
	
	public boolean getIsChoosingCharacter() {
		return isChoosingCharacter;
	}
	
	public final void AddEventTo(JComponent jComponent) {
		jComponent.addKeyListener(new KeyAdapter() {
		    @Override
		    public void keyPressed(KeyEvent e) {
		        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
		            isChoosingCharacter = false;
		        }
		    }
		});
		jComponent.setFocusable(true);
		jComponent.requestFocusInWindow();
	}

	public void Update() {
		// TODO Auto-generated method stub
		
	}

	public void draw(Graphics2D g2) {
		g2.setColor(Color.BLACK);
		g2.fillRect(0, 0, GamePanel.MAP_WIDTH, GamePanel.MAP_HEIGHT);
		
		g2.setColor(Color.WHITE);
		Font bigFont = new Font("Arial", Font.PLAIN, 40);
        g2.setFont(bigFont);
		g2.drawString("Nhấn Enter", StartScreen.LOGO_X + 200, StartScreen.LOGO_Y + 120);
	}
	
	
}
