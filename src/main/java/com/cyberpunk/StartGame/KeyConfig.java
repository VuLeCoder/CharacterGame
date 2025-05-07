package com.cyberpunk.StartGame;

import java.util.HashMap;
import java.util.Map;

import com.cyberpunk.Object.HumanObject;

public class KeyConfig {
    private Map<Integer, String> P1_KeyMap;
    private Map<Integer, String> P2_KeyMap;
    
    public static final int UP 		= 2;
    public static final int DOWN 	= -2;
    public static final int LEFT 	= HumanObject.LEFT_DIR;
    public static final int RIGHT 	= HumanObject.RIGHT_DIR;
    public static final int ATTACK 	= 3;

    public KeyConfig() {
    	// Check KeyEvent.VK_ để rõ :v
    	P1_KeyMap = new HashMap<>();
    	P1_KeyMap.put(UP,		"W");
    	P1_KeyMap.put(DOWN, 	"S");
    	P1_KeyMap.put(LEFT, 	"A");
    	P1_KeyMap.put(RIGHT, 	"D");
    	P1_KeyMap.put(ATTACK,	"1");

        P2_KeyMap = new HashMap<>();
        P2_KeyMap.put(UP,		"UP");
        P2_KeyMap.put(DOWN, 	"DOWN");
        P2_KeyMap.put(LEFT,		"LEFT");
        P2_KeyMap.put(RIGHT,	"RIGHT");
        P2_KeyMap.put(ATTACK,	"COMMA");
    }

    public void setKey(int player, int action, String keyName) {
    	if(player == 1) {
    		P1_KeyMap.put(action, keyName);
    		return;
    	}
    	
    	P2_KeyMap.put(action, keyName);
    }

	public Map<Integer, String> getP1_KeyMap() {
		return P1_KeyMap;
	}

	public Map<Integer, String> getP2_KeyMap() {
		return P2_KeyMap;
	}
}
