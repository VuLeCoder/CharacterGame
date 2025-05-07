package com.cyberpunk.Screen;

import java.awt.Graphics2D;

import javax.swing.JComponent;

public class ScreenManager {
	private boolean isGameInitiated;
	private boolean isInCharacterScreen;
	
	private final JComponent jComponent;
	private final StartScreen startScreen;
	private final CharacterSelectionScreen characterSelectionScreen;

	
	public ScreenManager(JComponent jComponent) {
		this.jComponent = jComponent;

		isGameInitiated = false;
		isInCharacterScreen = false;
		
		startScreen = new StartScreen();
		characterSelectionScreen = new CharacterSelectionScreen();
		
		startScreen.addEventTo(jComponent);
	}
	
	public boolean isNotInitializedGameYet() {
		return !isGameInitiated;
	}
	
	public void Update() {
		isGameInitiated = (!startScreen.getIsStartingScreen() && !characterSelectionScreen.getIsChoosingCharacter());
		
		
		if(startScreen.getIsStartingScreen()) {
			startScreen.Update();
			return;
		}
		
		if(!isInCharacterScreen) {
			isInCharacterScreen = true;
			characterSelectionScreen.AddEventTo(jComponent);
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
