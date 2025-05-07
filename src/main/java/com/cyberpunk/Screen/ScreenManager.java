package com.cyberpunk.Screen;

import java.awt.Graphics2D;

import javax.swing.JComponent;

public class ScreenManager {
	private boolean isGameInitiated;
//	private boolean isInCharacterScreen;
	
	private final StartScreen startScreen;
	private final CharacterSelectionScreen characterSelectionScreen;
	
	public ScreenManager() {
//		isInCharacterScreen = false;
		isGameInitiated = false;
		
		characterSelectionScreen = new CharacterSelectionScreen();
		startScreen = new StartScreen(characterSelectionScreen);
	}
	
	public boolean isNotInitializedGameYet() {
		return !isGameInitiated;
	}
	
	public final void startScreenAddEvent(JComponent jComponent) {
		startScreen.addEventTo(jComponent);
	}
	
	public void Update() {
		isGameInitiated = !startScreen.getIsStartingScreen() && !characterSelectionScreen.getIsChoosingCharacter();
		
		if(startScreen.getIsStartingScreen()) {
			startScreen.Update();
			return;
		}
		
		characterSelectionScreen.Update();
	}
	
	public void draw(Graphics2D g2) {
		if(startScreen.getIsStartingScreen()) {
			startScreen.draw(g2);
			return;
		}
		
		characterSelectionScreen.draw(g2);
	}
	
	
}
