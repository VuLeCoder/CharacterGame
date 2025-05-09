package com.cyberpunk.Object;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.cyberpunk.Effect.DataLoader;

public class GameUI {
	public static final int SCALE_HEALTH_BAR = 2;
	public static final int HEALTH_BAR_WIDTH = 262;
	public static final int HEALTH_BAR_HEIGHT = 28;
	public static final float DRAIN_SPEED = 0.01f;
	public static final long DRAIN_DURATION = 20000000L;

//	private long timeStartDrain = 0;

	private final static String FULL = "data/gui/health_bar_full.png";
	private final static String DRAIN = "data/gui/health_bar_drain1.png";
	private final static String BLANK = "data/gui/health_bar_blank.png";
	private final static String HEAL = "data/gui/health_bar_heal.png";

	private BufferedImage healthBarFull, healthBarDrain, healthBarBlank, healthBarHeal;
	private BufferedImage healthBarFull_sub, healthBarDrain_sub, healthBarHeal_sub;

	public GameUI() {
		// health bar
		healthBarFull = DataLoader.getInstance().getDataBufferedImage(FULL);
		healthBarDrain = DataLoader.getInstance().getDataBufferedImage(DRAIN);
		healthBarBlank = DataLoader.getInstance().getDataBufferedImage(BLANK);
		healthBarHeal = DataLoader.getInstance().getDataBufferedImage(HEAL);
	}

	public void drawHealthBar(BaseCharacter player, int x, int y, Graphics2D g2) {

		g2.drawImage(healthBarBlank, x, y, HEALTH_BAR_WIDTH, HEALTH_BAR_HEIGHT, null);

		float currHPratio = Math.max(0.01f, Math.min(1, player.getHealth() / HumanObject.HEALTH_POINT));

		healthBarDrain_sub = healthBarDrain.getSubimage(0, 0,
				(int) ((HEALTH_BAR_WIDTH / SCALE_HEALTH_BAR) * player.getPreviousHPratio()), HEALTH_BAR_HEIGHT / SCALE_HEALTH_BAR);
		g2.drawImage(healthBarDrain_sub, x, y, (int) (HEALTH_BAR_WIDTH * player.getPreviousHPratio()), HEALTH_BAR_HEIGHT, null);

		healthBarFull_sub = healthBarFull.getSubimage(0, 0, (int) ((HEALTH_BAR_WIDTH / SCALE_HEALTH_BAR) * currHPratio),
				HEALTH_BAR_HEIGHT / SCALE_HEALTH_BAR);
		g2.drawImage(healthBarFull_sub, x, y, (int) (HEALTH_BAR_WIDTH * currHPratio), HEALTH_BAR_HEIGHT, null);
	}

	public void Update(BaseCharacter player) {
		long now = System.nanoTime();
		if (now - player.getTimeStartDrain() <= DRAIN_DURATION) {
			return;
		}

		player.setTimeStartDrain(now);

		float currHPratio = Math.max(0.01f, Math.min(1, player.getHealth() / HumanObject.HEALTH_POINT));

		if (player.getPreviousHPratio() > currHPratio) {
			player.setPreviousHPratio(Math.max(currHPratio, player.getPreviousHPratio() - DRAIN_SPEED));
		} else {
			player.setPreviousHPratio(currHPratio);
		}
	}

}
