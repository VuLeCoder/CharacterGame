package com.cyberpunk.StartGame;

import java.awt.event.KeyEvent;

import com.cyberpunk.Object.GameWorld;

public class InputManager {
	
	private GameWorld gameWorld;	
	
	public InputManager(GameWorld gameWorld) {
		this.gameWorld = gameWorld;
	}

	public void processKeyPressed(int keyCode) {

		switch (keyCode) {
				
			case KeyEvent.VK_DOWN:
				break;
				
			case KeyEvent.VK_LEFT:
				break;
				
			case KeyEvent.VK_RIGHT:
				break;
			
			case KeyEvent.VK_UP:
				break;
				
			case KeyEvent.VK_COMMA:
				break;
				
			case KeyEvent.VK_PERIOD:
				break;
				
			case KeyEvent.VK_ENTER:
                break;
		}
	}
	
	public void processKeyReleased(int keyCode) {

		switch (keyCode) {
				
			case KeyEvent.VK_DOWN:
				break;
				
			case KeyEvent.VK_LEFT:
				break;
				
			case KeyEvent.VK_RIGHT:
				break;
				
			case KeyEvent.VK_UP:
				break;
				
			case KeyEvent.VK_COMMA:
				break;
			
			case KeyEvent.VK_ENTER:
				break;
		}
	}
}
