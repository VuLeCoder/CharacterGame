package com.cyberpunk.StartGame;

import java.awt.event.KeyEvent;
import java.util.Stack;

import com.cyberpunk.Object.GameWorld;

public class InputManager {
	
	private GameWorld gameWorld;	
	private Stack<Integer> movingDir = new Stack<>();
	private final int LEFT = -1;
	private final int RIGHT = 1;
	
	private boolean atttack;
	
	public InputManager(GameWorld gameWorld) {
		this.gameWorld = gameWorld;
	}

	public void processKeyPressed(int keyCode) {

		switch (keyCode) {
				
			case KeyEvent.VK_S:
			case KeyEvent.VK_DOWN:
				gameWorld.baseCharacter.sitDown();
				if(gameWorld.baseCharacter.isClimbing()) gameWorld.baseCharacter.climb(1.5f);
				break;
			
			case KeyEvent.VK_A:
			case KeyEvent.VK_LEFT:
				
				if(!movingDir.contains(LEFT)) movingDir.push(LEFT);
				UpdateMoving();
				break;
			
			case KeyEvent.VK_D:	
			case KeyEvent.VK_RIGHT:
				
				if(!movingDir.contains(RIGHT)) movingDir.push(RIGHT);
				UpdateMoving();
				break;
			
			case KeyEvent.VK_W:
			case KeyEvent.VK_SPACE:
			case KeyEvent.VK_UP:
				gameWorld.baseCharacter.jump();
				if(gameWorld.baseCharacter.isClimbing()) gameWorld.baseCharacter.climb(-1.5f);
				break;
				
			case KeyEvent.VK_COMMA:
				break;
				
			case KeyEvent.VK_PERIOD:
				break;
				
			case KeyEvent.VK_J:
				gameWorld.baseCharacter.attack();
				atttack = true;
                break;
                
			case KeyEvent.VK_H:
				gameWorld.baseCharacter.beHurt(90);
                break;
                
			case KeyEvent.VK_C:
				gameWorld.baseCharacter.climb(100);
				break;
				
			case KeyEvent.VK_G:
				gameWorld.baseCharacter.beHeal(90);
				break;
		}
	}
	
	public void processKeyReleased(int keyCode) {

		switch (keyCode) {
		
			case KeyEvent.VK_S:
			case KeyEvent.VK_DOWN:
				gameWorld.baseCharacter.standUp();
				if(gameWorld.baseCharacter.isClimbing()) gameWorld.baseCharacter.climb(0);
				UpdateMoving();
				break;
				
			case KeyEvent.VK_A:
			case KeyEvent.VK_LEFT:
//				gameWorld.baseCharacter.setSpeedX(0);
				movingDir.remove((Integer) LEFT);
				UpdateMoving();
				break;
				
			case KeyEvent.VK_D:
			case KeyEvent.VK_RIGHT:
//				gameWorld.baseCharacter.setSpeedX(0);
				movingDir.remove((Integer) RIGHT);
				UpdateMoving();
				break;
			
			case KeyEvent.VK_W:
			case KeyEvent.VK_SPACE:
			case KeyEvent.VK_UP:
				if(gameWorld.baseCharacter.isClimbing()) gameWorld.baseCharacter.climb(0);
				break;
				
			case KeyEvent.VK_COMMA:
				break;
			
			case KeyEvent.VK_J:
				gameWorld.baseCharacter.stopAttack();
				atttack = false;
				break;
		}
	}
	
	public void UpdateMoving() {
		if(movingDir.isEmpty()) {
			gameWorld.baseCharacter.setSpeedX(0);
		} else {
			int dir = movingDir.peek();
			if(dir == LEFT) {
				gameWorld.baseCharacter.setDirection(gameWorld.baseCharacter.LEFT_DIR);
				gameWorld.baseCharacter.run();
			} else {
				gameWorld.baseCharacter.setDirection(gameWorld.baseCharacter.RIGHT_DIR);
				gameWorld.baseCharacter.run();
			}
		}
		
		if(atttack) gameWorld.baseCharacter.attack();
	}
	
}

//package com.cyberpunk.StartGame;
//
//import java.awt.event.KeyEvent;
//
//import com.cyberpunk.Object.GameWorld;
//import com.cyberpunk.Object.HumanObject;
//
//public class InputManager {
//	
//	private GameWorld gameWorld;	
//	
//	public InputManager(GameWorld gameWorld) {
//		this.gameWorld = gameWorld;
//	}
//
//	public void processKeyPressed(int keyCode) {
//
//		switch (keyCode) {
//				
//			case KeyEvent.VK_DOWN:
//				if(gameWorld.baseCharacter.getIsOnLadder()) {
//					gameWorld.baseCharacter.climbDown();
//				} else {
//					gameWorld.baseCharacter.startDrop(System.nanoTime());
//				}
//				break;
//				
//			case KeyEvent.VK_LEFT:
//				gameWorld.baseCharacter.setDirection(HumanObject.LEFT_DIR);
//				gameWorld.baseCharacter.run();
//				break;
//				
//			case KeyEvent.VK_RIGHT:
//				gameWorld.baseCharacter.setDirection(HumanObject.RIGHT_DIR);
//				gameWorld.baseCharacter.run();
//				break;
//			
//			case KeyEvent.VK_UP:
//				if(!gameWorld.baseCharacter.getIsOnLadder()) {
//					gameWorld.baseCharacter.jump();
//				} else {
//					gameWorld.baseCharacter.climbUp();
//				}
//				break;
////				
////			case KeyEvent.VK_COMMA:
////				break;
////				
////			case KeyEvent.VK_PERIOD:
////				break;
////				
////			case KeyEvent.VK_ENTER:
////                break;
//		}
//	}
//	
//	public void processKeyReleased(int keyCode) {
//
//		switch (keyCode) {
//				
//			case KeyEvent.VK_DOWN:
//				if(gameWorld.baseCharacter.getIsOnLadder()) {
//					gameWorld.baseCharacter.stopClimb();
//				}
//				break;
//				
//			case KeyEvent.VK_LEFT:
//				gameWorld.baseCharacter.stopRun();
//				break;
//				
//			case KeyEvent.VK_RIGHT:
//				gameWorld.baseCharacter.stopRun();
//				break;
//				
//			case KeyEvent.VK_UP:
//				if(gameWorld.baseCharacter.getIsOnLadder()) {
//					gameWorld.baseCharacter.stopClimb();
//				}
//				break;
////				
////			case KeyEvent.VK_COMMA:
////				break;
////			
////			case KeyEvent.VK_ENTER:
////				break;
//		}
//	}
//}