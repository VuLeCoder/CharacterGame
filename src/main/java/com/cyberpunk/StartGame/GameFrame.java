package com.cyberpunk.StartGame;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;

import javax.swing.JFrame;

import com.cyberpunk.Effect.DataLoader;

public class GameFrame extends JFrame{
	private static final long serialVersionUID = 1L;

    public static final int SCREEN_WIDTH = 32 * 40 + 18;
    public static final int SCREEN_HEIGHT = 32 * 20 + 45 + GamePanel.posY;

    public GameFrame() {
        Toolkit toolkit = this.getToolkit();
        Dimension dimension = toolkit.getScreenSize();
        this.setBounds((dimension.width - SCREEN_WIDTH)/2,
                        (dimension.height - SCREEN_HEIGHT)/2,
                        SCREEN_WIDTH, SCREEN_HEIGHT);
        
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        try {
			DataLoader.getInstance().LoadData();
		} catch (IOException e) {
			e.printStackTrace();
		}

        GamePanel gamePanel = new GamePanel();
        add(gamePanel);
        this.addKeyListener(gamePanel);
        gamePanel.startGame();
    }
}
