package com.cyberpunk.StartGame;

import java.util.Stack;

import com.cyberpunk.Object.BaseCharacter;
import com.cyberpunk.Object.HumanObject;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Map;

public class InputManager {
    private final BaseCharacter player;
    private Stack<Integer> movingDir;
    private final Map<Integer, String> keyBindings;

    public InputManager(BaseCharacter player, Map<Integer, String> keyController) {
        this.player = player;
        
        movingDir = new Stack<>();
        keyBindings = keyController;
    }

    public void register(JComponent component) {
        for (Map.Entry<Integer, String> entry : keyBindings.entrySet()) {
            int action = entry.getKey();
            String keyName = entry.getValue();

            // pressed
            bindKey(component, "pressed " + keyName, () -> keyPressed(action));
            
            // released
            bindKey(component, "released " + keyName, () -> keyReleased(action));
        }
    }
    
    private void bindKey(JComponent comp, String keyStrokeStr, Runnable action) {
        KeyStroke ks = KeyStroke.getKeyStroke(keyStrokeStr);
        String actionKey = keyStrokeStr;

        comp.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(ks, actionKey);
        comp.getActionMap().put(actionKey, new AbstractAction() {
			private static final long serialVersionUID = 1L;
			
			@Override
            public void actionPerformed(ActionEvent e) {
                action.run();
            }
        });
    }

    private void keyPressed(int key) {
        switch(key) {
        	case KeyConfig.UP:
        		player.jump();
//				if(player.isClimbing()) player.climb(-1.5f);
        		break;
        		
        	case KeyConfig.LEFT:
        		if(!movingDir.contains(KeyConfig.LEFT)) {
        			movingDir.push(KeyConfig.LEFT);
        		}
				UpdateMoving();
        		break;
        		
        	case KeyConfig.RIGHT:
        		if(!movingDir.contains(KeyConfig.RIGHT)) {
        			movingDir.push(KeyConfig.RIGHT);
        		}
				UpdateMoving();
        		break;
        		
        	case KeyConfig.DOWN:
        		player.sitDown();
        		break;
        		
        	case KeyConfig.ATTACK:
        		player.attack();
        		break;
        }
    }

    private void keyReleased(int key) {
    	switch(key) {
	    	case KeyConfig.UP:
	    		break;
	    		
	    	case KeyConfig.LEFT:
	    		movingDir.remove((Integer)KeyConfig.LEFT);
				UpdateMoving();
	    		break;
	    		
	    	case KeyConfig.RIGHT:
	    		movingDir.remove((Integer)KeyConfig.RIGHT);
				UpdateMoving();
	    		break;
	    		
	    	case KeyConfig.DOWN:
	    		break;
	    		
	    	case KeyConfig.ATTACK:
	    		break;
	    }
    }

    public void UpdateMoving() {
		if(movingDir.isEmpty()) {
			player.stopRun();
		} else {
			if(movingDir.peek() == HumanObject.LEFT_DIR) {
				player.setDirection(HumanObject.LEFT_DIR);
			} else {
				player.setDirection(HumanObject.RIGHT_DIR);
			}
			player.run();
		}
		
//		if(atttack) gameWorld.baseCharacter.attack();
	}

    // Cho phép đổi phím nếu muốn (ví dụ từ menu cài đặt)
    public void setKey(String action, String newKeyName) {
//        keyBindings.put(action, newKeyName.toUpperCase());
    }
}
