package com.cyberpunk.StartGame;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;

import javax.swing.JFrame;

import com.cyberpunk.Effect.DataLoader;

public class GameFrame extends JFrame{
	private static final long serialVersionUID = 1L;

	private static final int PADDING_WIDTH = 18;
	private static final int PADDING_HEIGHT = 45;
	
    public static final int SCREEN_WIDTH = GamePanel.MAP_WIDTH + PADDING_WIDTH;
    public static final int SCREEN_HEIGHT = GamePanel.MAP_HEIGHT + PADDING_HEIGHT;

    public GameFrame() {
    	setScreenSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        try {
			DataLoader.getInstance().LoadData();
		} catch (IOException e) {
			e.printStackTrace();
		}

        GamePanel gamePanel = new GamePanel(this);
        add(gamePanel);
        gamePanel.startRunning();
    }

    public void setScreenSize(int newWidth, int newHeight) {
    	Toolkit toolkit = this.getToolkit();
    	Dimension dimension = toolkit.getScreenSize();
    	this.setBounds((dimension.width - newWidth)/2,
    			(dimension.height - newHeight)/2,
    			newWidth, newHeight);
    }
}
