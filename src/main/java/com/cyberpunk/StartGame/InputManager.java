package com.cyberpunk.StartGame;

import java.awt.event.KeyEvent;

import com.cyberpunk.Object.GameWorld;
import com.cyberpunk.Object.HumanObject;

public class InputManager {
	
	private GameWorld gameWorld;	
	
	public InputManager(GameWorld gameWorld) {
		this.gameWorld = gameWorld;
	}

	public void processKeyPressed(int keyCode) {

		switch (keyCode) {
				
			case KeyEvent.VK_DOWN:
				if(gameWorld.baseCharacter.getIsOnLadder()) {
					gameWorld.baseCharacter.climbDown();
				} else {
					gameWorld.baseCharacter.startDrop(System.nanoTime());
				}
				break;
				
			case KeyEvent.VK_LEFT:
				gameWorld.baseCharacter.setDirection(HumanObject.LEFT_DIR);
				gameWorld.baseCharacter.run();
				break;
				
			case KeyEvent.VK_RIGHT:
				gameWorld.baseCharacter.setDirection(HumanObject.RIGHT_DIR);
				gameWorld.baseCharacter.run();
				break;
			
			case KeyEvent.VK_UP:
				if(!gameWorld.baseCharacter.getIsOnLadder()) {
					gameWorld.baseCharacter.jump();
				} else {
					gameWorld.baseCharacter.climbUp();
				}
				break;
//				
//			case KeyEvent.VK_COMMA:
//				break;
//				
//			case KeyEvent.VK_PERIOD:
//				break;
//				
//			case KeyEvent.VK_ENTER:
//                break;
		}
	}
	
	public void processKeyReleased(int keyCode) {

		switch (keyCode) {
				
			case KeyEvent.VK_DOWN:
				if(gameWorld.baseCharacter.getIsOnLadder()) {
					gameWorld.baseCharacter.stopClimb();
				}
				break;
				
			case KeyEvent.VK_LEFT:
				gameWorld.baseCharacter.stopRun();
				break;
				
			case KeyEvent.VK_RIGHT:
				gameWorld.baseCharacter.stopRun();
				break;
				
			case KeyEvent.VK_UP:
				if(gameWorld.baseCharacter.getIsOnLadder()) {
					gameWorld.baseCharacter.stopClimb();
				}
				break;
//				
//			case KeyEvent.VK_COMMA:
//				break;
//			
//			case KeyEvent.VK_ENTER:
//				break;
		}
	}
}
