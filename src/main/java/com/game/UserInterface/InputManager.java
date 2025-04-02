package com.game.UserInterface;

import java.awt.event.KeyEvent;

import com.game.GameObjects.GameWorld;
import com.game.GameObjects.Megaman;

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
				gameWorld.megaMan.setDirection(gameWorld.megaMan.LEFT_DIR);
				gameWorld.megaMan.run();
				break;
				
			case KeyEvent.VK_RIGHT:
				gameWorld.megaMan.setDirection(gameWorld.megaMan.RIGHT_DIR);
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
				
                if(gameWorld.state ==gameWorld.PAUSEGAME) {
                    if(gameWorld.previousState == gameWorld.GAMEPLAY) {
                    	gameWorld.switchState(gameWorld.GAMEPLAY);
                    } else {
                    	gameWorld.switchState(gameWorld.TUTORIAL);
                    }
                }
                
                if(gameWorld.state == gameWorld.TUTORIAL && gameWorld.storyTutorial >= 1) {
                    if(gameWorld.storyTutorial<=3) {
                    	gameWorld.storyTutorial++;
                    	gameWorld.currentSize = 1;
                    	gameWorld.textTutorial = gameWorld.texts1[gameWorld.storyTutorial - 1];
                    } else {
                    	gameWorld.switchState(gameWorld.GAMEPLAY);
                    }
                    
                    // for meeting boss tutorial
                    if(gameWorld.tutorialState == gameWorld.MEETFINALBOSS){
                    	gameWorld.switchState(gameWorld.GAMEPLAY);
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
