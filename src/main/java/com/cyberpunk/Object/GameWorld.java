package com.cyberpunk.Object;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

import com.cyberpunk.Effect.DataLoader;
import com.cyberpunk.Screen.ScreenManager;
import com.cyberpunk.StartGame.GamePanel;

public class GameWorld{
	public static final int TILESIZE = 32;
	private final static int pointX = 22, pointY = 14; // Điểm rơi hộp
	
	private final BufferedImage bufferedImage;

	private final ScreenManager screenManager;
	private final MapGame mapGame;
	private final int[][] animatedMap;
	
	private boolean isStartGame = false;
//	private boolean isFirstDrawMap = true;

	private final Camera camera;
	private final ObjectManager objectManager;
	private final SkillManager skillManager;
	private final GameUI gUi;
	
	private final BaseCharacter P1;
	private final BaseCharacter P2;

	public GameWorld(JComponent jComponent) {
		bufferedImage = new BufferedImage(GamePanel.MAP_WIDTH, GamePanel.MAP_HEIGHT + GamePanel.HUD_HEIGHT, BufferedImage.TYPE_INT_ARGB);
		
		screenManager = new ScreenManager(jComponent);
		
		mapGame = new MapGame();
		animatedMap = DataLoader.getInstance().getAnimatedMap();

		objectManager = new ObjectManager(this);
		skillManager = new SkillManager(this);
		gUi = new GameUI();
		
		P1 = new BaseCharacter(100, 500, "biker", this);
		P1.setTeamType(HumanObject.P1_TEAM);
		objectManager.addObject(P1);
		
		P2 = new BaseCharacter(150, 500, "biker", this);
		P2.setTeamType(HumanObject.P2_TEAM);
		objectManager.addObject(P2);
		
		camera = new Camera(0, 0, GamePanel.MAP_WIDTH, GamePanel.MAP_HEIGHT, this);

		addAllAnimatedObject();
	}
	
	public boolean isStartGame() {
		return isStartGame;
	}
	
	public void StartGame() {
		isStartGame = true;
	}
	
	public BufferedImage getBufferedImage(){
        return bufferedImage;
    }
	
	public int getAnimatedMap(int x, int y) {
		return animatedMap[x][y];
	}

	public MapGame getMapGame() {
		return mapGame;
	}
	
	public ScreenManager getScreenManager() {
		return screenManager;
	}
	
	public ObjectManager getObjectManager() {
		return objectManager;
	}
	
	public SkillManager getSkillManager() {
		return skillManager;
	}
	
	public BaseCharacter getP1() {
		return P1;
	}

	public BaseCharacter getP2() {
		return P2;
	}
	
	public Camera getCamera() {
		return camera;
	}
	
	private void addAllAnimatedObject() {
		for(int i=0; i<animatedMap.length; ++i) {
			for(int j=0; j<animatedMap[0].length; ++j) {
				if(animatedMap[i][j] == -1) {
					continue;
				}

				objectManager.addObject(new MapObject(j * GameWorld.TILESIZE, i * GameWorld.TILESIZE, this, animatedMap[i][j]));
			}
		}
	}

	public void Update() {
		
		if(screenManager.isNotInitializedGameYet()) {
			screenManager.Update();
			return;
		}
		
		gUi.Update(getP1());
		gUi.Update(getP2());		
		
		camera.Update();
		objectManager.dropBox(pointX, pointY, System.nanoTime());
		objectManager.UpdateObjects();
		skillManager.UpdateObjects();
	}
	
	float currentZoomX = 1.0f, currentZoomY = 1.0f;
	
	public void Render() {
		Graphics2D g2 = (Graphics2D) bufferedImage.getGraphics();
		if(g2 == null) {
			return;
		}
		
		if(screenManager.isNotInitializedGameYet()) {
			screenManager.draw(g2);
			return;
		}
		
		drawGameObject(g2);
		
		drawHUD();
		
	}
	
	private void drawGameObject(Graphics2D g2) {
		g2.setClip(0, 0, GamePanel.MAP_WIDTH, GamePanel.MAP_HEIGHT);
		
		g2.setColor(Color.BLACK);
//		g2.fillRect(0, 0, GamePanel.MAP_WIDTH, GamePanel.MAP_HEIGHT);
		
		float targetZoomX = GamePanel.MAP_WIDTH / camera.getWidthView();
		float targetZoomY = GamePanel.MAP_HEIGHT / camera.getHeightView();

		currentZoomX += (targetZoomX - currentZoomX) * Camera.SMOOTH_FACTOR;
		currentZoomY += (targetZoomY - currentZoomY) * Camera.SMOOTH_FACTOR;
		g2.scale(currentZoomX, currentZoomY);
		
//		if(isFirstDrawMap) {
//			mapGame.draw(g2);
//			isFirstDrawMap = false;
//		}

		g2.translate(-camera.getPosX(),- camera.getPosY());
		
		// Vẽ map
		g2.drawImage(mapGame.getCachedMapImage(), 0, 0, null);
//		g2.drawImage(mapGame.getCachedMapImage(), GamePanel.MAP_DRAW_X, -GamePanel.MAP_DRAW_Y, null);
		
		camera.draw(g2);
		objectManager.draw(g2);
		skillManager.draw(g2);	
	}

	private void drawHUD() {
		System.out.println(getP1().getHealth());
		
		Graphics2D g2 = (Graphics2D) bufferedImage.getGraphics();
		if(g2 == null) {
			return;
		}
		
		g2.setClip(0, GamePanel.MAP_HEIGHT, getBufferedImage().getWidth(), GamePanel.HUD_HEIGHT);
		g2.translate(0, 0);
		g2.scale(1, 1);
		g2.setColor(Color.black);
		g2.setFont(new Font("Arial", Font.PLAIN, 25));
		
		int posX1 = 0, posX2 = GamePanel.MAP_WIDTH - GameUI.HEALTH_BAR_WIDTH;
		int posY = GamePanel.MAP_HEIGHT + (GamePanel.HUD_HEIGHT - GameUI.HEALTH_BAR_HEIGHT) / 2; 
		
		g2.drawString("P1", posX1, posY - 10);
		gUi.drawHealthBar(getP1(), posX1, posY, g2);
		
		g2.drawString("P2", posX2, posY - 10);
		gUi.drawHealthBar(getP2(), posX2, posY, g2);
	}

}
