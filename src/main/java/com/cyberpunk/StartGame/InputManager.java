package com.cyberpunk.StartGame;

import java.awt.event.KeyEvent;

import com.cyberpunk.Object.GameWorld;

public class InputManager {
	
	private long time = 0;
	
	private GameWorld gameWorld;	
	
	public InputManager(GameWorld gameWorld) {
		this.gameWorld = gameWorld;
	}

	public void processKeyPressed(int keyCode) {

		switch (keyCode) {
				
			case KeyEvent.VK_S:
			case KeyEvent.VK_DOWN:
				gameWorld.baseCharacter.sitDown();
				break;
			
			case KeyEvent.VK_A:
			case KeyEvent.VK_LEFT:
//				gameWorld.baseCharacter.setSpeedX(-4);
				gameWorld.baseCharacter.setDirection(gameWorld.baseCharacter.LEFT_DIR);
				gameWorld.baseCharacter.run();
				break;
			
			case KeyEvent.VK_D:	
			case KeyEvent.VK_RIGHT:
//				gameWorld.baseCharacter.setSpeedX(4);
				gameWorld.baseCharacter.setDirection(gameWorld.baseCharacter.RIGHT_DIR);
//				System.out.println(System.nanoTime() - time);
//				time = System.nanoTime();
				gameWorld.baseCharacter.run();
				break;
			
			case KeyEvent.VK_W:
			case KeyEvent.VK_SPACE:
			case KeyEvent.VK_UP:
				gameWorld.baseCharacter.jump();
				
				break;
				
			case KeyEvent.VK_COMMA:
				break;
				
			case KeyEvent.VK_PERIOD:
				break;
				
			case KeyEvent.VK_J:
				gameWorld.baseCharacter.attack();
                break;
		}
	}
	
	public void processKeyReleased(int keyCode) {

		switch (keyCode) {
		
			case KeyEvent.VK_S:
			case KeyEvent.VK_DOWN:
				gameWorld.baseCharacter.standUp();
				break;
				
			case KeyEvent.VK_A:
			case KeyEvent.VK_LEFT:
				gameWorld.baseCharacter.setSpeedX(0);
				break;
				
			case KeyEvent.VK_D:
			case KeyEvent.VK_RIGHT:
				gameWorld.baseCharacter.setSpeedX(0);
				break;
			
			case KeyEvent.VK_W:
			case KeyEvent.VK_SPACE:
			case KeyEvent.VK_UP:
				
				break;
				
			case KeyEvent.VK_COMMA:
				break;
			
			case KeyEvent.VK_J:
				gameWorld.baseCharacter.stopAttack();
				break;
		}
	}
}
