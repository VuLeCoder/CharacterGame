package com.game.UserInterface;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;

import javax.swing.JFrame;

import com.game.Effect.CacheDataLoader;

public class GameFrame extends JFrame{
	private static final long serialVersionUID = 1L;
    
    public static final int SCREEN_WIDTH = 1000;
    public static final int SCREEN_HEIGHT = 600; 

    public GameFrame() {
        Toolkit toolkit = this.getToolkit();
        Dimension dimension = toolkit.getScreenSize();
        this.setBounds((dimension.width - SCREEN_WIDTH)/2,
                        (dimension.height - SCREEN_HEIGHT)/2,
                        SCREEN_WIDTH, SCREEN_HEIGHT);
        
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        try {
			CacheDataLoader.getInstance().LoadData();
		} catch (IOException e) {
			e.printStackTrace();
		}

        GamePanel gamePanel = new GamePanel();
        add(gamePanel);
        this.addKeyListener(gamePanel);
        gamePanel.startGame();
    }
}
