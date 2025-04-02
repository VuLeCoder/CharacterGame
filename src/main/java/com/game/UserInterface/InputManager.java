package com.game.UserInterface;

import java.awt.event.KeyEvent;

import com.game.GameObjects.GameWorld;
import com.game.GameObjects.ParticularObject;

public class InputManager {
	
	private GameWorld gameWorld;	
	
	public InputManager(GameWorld gameWorld) {
		this.gameWorld = gameWorld;
	}

	public void processKeyPressed(int keyCode) {

		switch (keyCode) {
				
			case KeyEvent.VK_DOWN:
				gameWorld.megaMan.dick();
				break;
				
			case KeyEvent.VK_LEFT:
				gameWorld.megaMan.setDirection(ParticularObject.LEFT_DIR);
				gameWorld.megaMan.run();
				break;
				
			case KeyEvent.VK_RIGHT:
				gameWorld.megaMan.setDirection(ParticularObject.RIGHT_DIR);
				gameWorld.megaMan.run();
				break;
			
			case KeyEvent.VK_UP:
			case KeyEvent.VK_SPACE:				
				gameWorld.megaMan.jump();
				break;
				
			case KeyEvent.VK_A:
				gameWorld.megaMan.attack();
				break;
				
			case KeyEvent.VK_ENTER:
				
                if(gameWorld.state == GameWorld.PAUSEGAME) {
                    if(gameWorld.previousState == GameWorld.GAMEPLAY) {
                    	gameWorld.switchState(GameWorld.GAMEPLAY);
                    } else {
                    	gameWorld.switchState(GameWorld.TUTORIAL);
                    }
                }
                
                if(gameWorld.state == GameWorld.TUTORIAL && gameWorld.storyTutorial >= 1) {
                    if(gameWorld.storyTutorial<=3) {
                    	gameWorld.storyTutorial++;
                    	gameWorld.currentSize = 1;
                    	gameWorld.textTutorial = gameWorld.texts1[gameWorld.storyTutorial - 1];
                    } else {
                    	gameWorld.switchState(GameWorld.GAMEPLAY);
                    }
                    
                    // for meeting boss tutorial
                    if(gameWorld.tutorialState == GameWorld.MEETFINALBOSS){
                    	gameWorld.switchState(GameWorld.GAMEPLAY);
                    }
                }
                break;
		}
	}
	
	public void processKeyReleased(int keyCode) {

		switch (keyCode) {
				
			case KeyEvent.VK_DOWN:
				gameWorld.megaMan.standUp();
				break;
				
			case KeyEvent.VK_LEFT:
				if(gameWorld.megaMan.getSpeedX() < 0) {
					gameWorld.megaMan.stopRun();
				}
				break;
				
			case KeyEvent.VK_RIGHT:
				if(gameWorld.megaMan.getSpeedX() > 0) {
					gameWorld.megaMan.stopRun();
				}
				break;
				
			case KeyEvent.VK_UP:	
			case KeyEvent.VK_SPACE:
				break;
				
			case KeyEvent.VK_A:
				break;
			
			case KeyEvent.VK_ENTER:
				break;
		}
	}
}
