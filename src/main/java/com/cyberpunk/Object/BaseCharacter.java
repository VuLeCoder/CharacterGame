package com.cyberpunk.Object;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Collections;

import javax.imageio.ImageIO;
import javax.xml.stream.XMLInputFactory;

import com.cyberpunk.Effect.Animation;
import com.cyberpunk.Effect.DataLoader;

public class BaseCharacter extends HumanObject {
	public String name;

	private int previousDirX; // Chưa dùng
	private int previousDirY = 1; // để cho hoạt ảnh trèo đúng

	private float previousHealth = getHealth();

//	private BufferedImage healthBarFull, healthBarDrain, healthBarBlank, healthBarHeal;
//	private BufferedImage healthBarFull_sub, healthBarDrain_sub, healthBarHeal_sub;

//	private boolean isRunning = false;

	private int hurtDisplay = 0;

	private boolean isGetDamage;

	private boolean isAttacking, isLastFrameReached;
	private boolean isFirstAttack, isSecondAttack, isThirdAttack;

	private Animation attack1ForwardAnim, attack1BackAnim, attack2ForwardAnim, attack2BackAnim;
	private Animation attack3ForwardAnim, attack3BackAnim, deathForwardAnim, deathBackAnim;
	private Animation idleForwardAnim, idleBackAnim, idleSkillForwardAnim, idleSkillBackAnim;
	private Animation climdAnim, djumpForwardAnim, djumpBackAnim;
	private Animation beHurtForwardAnim, beHurtBackAnim;
	private Animation jumpForwardAnim, jumpBackAnim, jumpSkillForwardAnim, jumpSkillBackAnim;
	private Animation punchForwardAnim, punchBackAnim, runForwardAnim, runBackAnim;
	private Animation runAttackForwardAnim, runAttackBackAnim, runSkillForwardAnim, runSkillBackAnim;
	private Animation sitdownSkillForwardAnim, sitdownSkillBackAnim, walkSkillForwardAnim, walkSkillBackAnim;
	private Animation handForwardAnim, handBackAnim;

	public BaseCharacter(float x, float y, String name, GameWorld gameWorld) {
		super(x, y, gameWorld);
		this.name = name;
		this.previousDirX = getDirection();

//		try {
//			healthBarFull = ImageIO.read(new File("data/gui/health_bar_full.png"));
//			healthBarDrain = ImageIO.read(new File("data/gui/health_bar_drain1.png"));
//			healthBarBlank = ImageIO.read(new File("data/gui/health_bar_blank.png"));
//			healthBarHeal = ImageIO.read(new File("data/gui/health_bar_heal.png"));
//
//		} catch (Exception e) {
//			// TODO: handle exception
//		}

		attack1ForwardAnim = DataLoader.getInstance().getAnimation(name + "attack1");
		attack1BackAnim = DataLoader.getInstance().getAnimation(name + "attack1");
		attack1BackAnim.flipAllImage();

		attack2ForwardAnim = DataLoader.getInstance().getAnimation(name + "attack2");
		attack2BackAnim = DataLoader.getInstance().getAnimation(name + "attack2");
		attack2BackAnim.flipAllImage();

		attack3ForwardAnim = DataLoader.getInstance().getAnimation(name + "attack3");
		attack3BackAnim = DataLoader.getInstance().getAnimation(name + "attack3");
		attack3BackAnim.flipAllImage();

		deathForwardAnim = DataLoader.getInstance().getAnimation(name + "death");
		deathForwardAnim.setRepeated(false);
		deathBackAnim = DataLoader.getInstance().getAnimation(name + "death");
		deathBackAnim.setRepeated(false);
		deathBackAnim.flipAllImage();

		idleForwardAnim = DataLoader.getInstance().getAnimation(name + "idle");
		idleBackAnim = DataLoader.getInstance().getAnimation(name + "idle");
		idleBackAnim.flipAllImage();

		idleSkillForwardAnim = DataLoader.getInstance().getAnimation(name + "idleskill");
		idleSkillBackAnim = DataLoader.getInstance().getAnimation(name + "idleskill");
		idleSkillBackAnim.flipAllImage();

		djumpForwardAnim = DataLoader.getInstance().getAnimation(name + "doublejump");
		djumpForwardAnim.setRepeated(false); // jump once, wait until landing
		djumpForwardAnim.setIgnoreFrame(3); // til landing, wait until on ground
		djumpBackAnim = DataLoader.getInstance().getAnimation(name + "doublejump");
		djumpBackAnim.setRepeated(false); // same above
		djumpBackAnim.setIgnoreFrame(3); // same above
		djumpBackAnim.flipAllImage();

		jumpForwardAnim = DataLoader.getInstance().getAnimation(name + "jump");
		jumpForwardAnim.setRepeated(false); // jump once, wait until landing
		jumpForwardAnim.setIgnoreFrame(3); // til landing, wait until on ground
		jumpBackAnim = DataLoader.getInstance().getAnimation(name + "jump");
		jumpBackAnim.setRepeated(false); // same above
		jumpBackAnim.setIgnoreFrame(3); // same above
		jumpBackAnim.flipAllImage();

		jumpSkillForwardAnim = DataLoader.getInstance().getAnimation(name + "jumpskill");
		jumpSkillForwardAnim.setRepeated(false); // jump once, wait until landing
		jumpSkillForwardAnim.setIgnoreFrame(3); // til landing, wait until on ground
		jumpSkillBackAnim = DataLoader.getInstance().getAnimation(name + "jumpskill");
		jumpSkillBackAnim.setRepeated(false); // same above
		jumpSkillBackAnim.setIgnoreFrame(3); // same above
		jumpSkillBackAnim.flipAllImage();

		punchForwardAnim = DataLoader.getInstance().getAnimation(name + "punch");
		punchBackAnim = DataLoader.getInstance().getAnimation(name + "punch");
		punchBackAnim.flipAllImage();

		runForwardAnim = DataLoader.getInstance().getAnimation(name + "run");
		runBackAnim = DataLoader.getInstance().getAnimation(name + "run");
		runBackAnim.flipAllImage();

		runAttackForwardAnim = DataLoader.getInstance().getAnimation(name + "runattack");
		runAttackBackAnim = DataLoader.getInstance().getAnimation(name + "runattack");
		runAttackBackAnim.flipAllImage();

		runSkillForwardAnim = DataLoader.getInstance().getAnimation(name + "runskill");
		runSkillBackAnim = DataLoader.getInstance().getAnimation(name + "runskill");
		runSkillBackAnim.flipAllImage();

		sitdownSkillForwardAnim = DataLoader.getInstance().getAnimation(name + "sitdownskill");
		sitdownSkillForwardAnim.setRepeated(false);
		sitdownSkillBackAnim = DataLoader.getInstance().getAnimation(name + "sitdownskill");
		sitdownSkillBackAnim.setRepeated(false);
		sitdownSkillBackAnim.flipAllImage();

		walkSkillForwardAnim = DataLoader.getInstance().getAnimation(name + "walkskill");
		walkSkillBackAnim = DataLoader.getInstance().getAnimation(name + "walkskill");
		walkSkillBackAnim.flipAllImage();

		handForwardAnim = DataLoader.getInstance().getAnimation(name + "hand");
		handBackAnim = DataLoader.getInstance().getAnimation(name + "hand");
		handBackAnim.flipAllImage();

		beHurtForwardAnim = DataLoader.getInstance().getAnimation(name + "hurt");
		beHurtBackAnim = DataLoader.getInstance().getAnimation(name + "hurt");
		beHurtBackAnim.flipAllImage();

		climdAnim = DataLoader.getInstance().getAnimation(name + "climb");
	}

	@Override
	public void jump() {

//		setSpeedY(-jumpStrength);
//		setRunning(false);
		setSitting(false);

		if (isClimbing()) {
			return;
		}

		if (getSpeedY() > 0 && getIsOnLadder()) {
			setClimbing(true);
			return;
		}

		if (!isSingleJumping() && !isDoubleJumping()) {
			setSingleJumping(true);
			setSpeedY(JUMP_STRENGTH);

			jumpForwardAnim.reset();
			jumpBackAnim.reset();
			jumpSkillForwardAnim.reset();
			jumpSkillBackAnim.reset();
			djumpForwardAnim.reset();
			djumpBackAnim.reset();

			jumpForwardAnim.setIgnoreFrame(3);
			jumpBackAnim.setIgnoreFrame(3);
			jumpSkillForwardAnim.setIgnoreFrame(3);
			jumpSkillBackAnim.setIgnoreFrame(3);
			djumpForwardAnim.setIgnoreFrame(5);
			djumpBackAnim.setIgnoreFrame(5);

		} else if (!isDoubleJumping() && (jumpForwardAnim.getCurrentFrame() == 2 || jumpBackAnim.getCurrentFrame() == 2 )) {
			setSingleJumping(false);
			setDoubleJumping(true);
			setSpeedY(JUMP_STRENGTH);
		}
	}

	@Override
	public void run() {
		if (!isSitting() && !getIsRunning()) {
			setRunning(true);
			setSpeedX(getSpeedX() + HUMAN_RUN_SPEED * getDirection());
		}
	}

	@Override
	public void stopRun() {
		if (getIsRunning()) {
			setRunning(false);
			setSpeedX((Math.abs(getSpeedX()) - Math.abs(HUMAN_RUN_SPEED)) * getDirection());

			runForwardAnim.reset();
			runBackAnim.reset();
			runAttackForwardAnim.reset();
			runAttackBackAnim.reset();
			runSkillForwardAnim.reset();
			runSkillBackAnim.reset();

			runForwardAnim.setCurrentFrame(1);
			runBackAnim.setCurrentFrame(1);
			runAttackForwardAnim.setCurrentFrame(1);
			runAttackBackAnim.setCurrentFrame(1);
			runSkillForwardAnim.setCurrentFrame(1);
			runSkillBackAnim.setCurrentFrame(1);
		}
	}

	@Override
	public void sitDown(long time) {

		if (isClimbing()) {
			return;
		}

		if (getSpeedY() > 0 && getIsOnLadder()) {
			setClimbing(true);
			return;
		}

		if (isSingleJumping() || isDoubleJumping() || isLanding()) {
			return;
		}

		if (time - getStartSittingTime() > DOUBLE_CLICK_THRESHOLD) {
			if (!isSitting()) {
				setSitting(true);

				sitdownSkillForwardAnim.reset();
				sitdownSkillBackAnim.reset();
				sitdownSkillForwardAnim.setIgnoreFrame(3);
				sitdownSkillBackAnim.setIgnoreFrame(3);
			}
		} else {
			startDrop(time);
		}
		setStartSittingTime(time);
	}

	@Override
	public void standUp() {
		idleForwardAnim.reset();
		idleBackAnim.reset();
		sitdownSkillForwardAnim.unIgnoreFrame(3);
		sitdownSkillBackAnim.unIgnoreFrame(3);

	}

//	@Override
//	public void climb(float speed) {
//		if (speed == 100) {
//			if (isClimbing() == false) {
//				setClimbing(true);
//
//				float ladderX = (int) getPosX() / GameWorld.TILESIZE;
//				setPosX(ladderX * GameWorld.TILESIZE + getWidth() / 2);
//				setSpeedY(0);
//			} else
//				setClimbing(false);
//		} else
//			setSpeedY(speed);
//	}

	@Override
	public void attack() {

		if (getIsRunning()) {
			setSpeedX(0.7f * getDirection());
			setRunning(false);
		}

		isAttacking = true;
		if (!isFirstAttack && !isSecondAttack && !isThirdAttack && !isSitting() && !isDoubleJumping()) {
			isFirstAttack = true;

			attack2ForwardAnim.reset();
			attack3ForwardAnim.reset();
			attack3BackAnim.reset();

		} else if (!isSecondAttack
				&& (attack1ForwardAnim.getCurrentFrame() >= 3 || attack1BackAnim.getCurrentFrame() >= 3)) {
			isSecondAttack = true;
			isFirstAttack = false;
			attack2ForwardAnim.setCurrentFrame(attack1ForwardAnim.getCurrentFrame() + 1);
			attack2BackAnim.setCurrentFrame(attack1BackAnim.getCurrentFrame() + 1);
			attack1ForwardAnim.reset();
			attack1BackAnim.reset();

		} else if (!isThirdAttack && (attack2ForwardAnim.isLastFrame() || attack2BackAnim.isLastFrame())) {
			isSecondAttack = false;
			isThirdAttack = true;
			isLastFrameReached = false;

		} else if (attack3ForwardAnim.isLastFrame() || attack3BackAnim.isLastFrame()) {
			if (isLastFrameReached) {
				isThirdAttack = false;
				isAttacking = false;
			} else
				isLastFrameReached = true;
		}
	}

	@Override
	public void stopAttack() {

//		setSpeedX(0);
		if (!isFirstAttack) {
			isAttacking = false;
		}

		isSecondAttack = false;
		isThirdAttack = false;

		attack2ForwardAnim.reset();
		attack2BackAnim.reset();
		attack3ForwardAnim.reset();
		attack3BackAnim.reset();

	}

	@Override
	public void beHurt(int damageGet) {
		super.beHurt(damageGet);

		isGetDamage = true;
	}

	@Override
	public void beHeal(float healedGet) {
		float health = Math.min(100, getHealth() + healedGet);
		setHealth(health);

	}

//	public void drawHealthBar(Graphics2D g2) {
//
//		g2.drawImage(healthBarBlank, 0, 0, 262, 14 * 2, null);
//
//		if (previousHealth >= getHealth()) { // draw health drained
//
//			if (previousHealth > getHealth())
//				previousHealth -= 1;
//
//			if (previousHealth > 0) {
//				healthBarDrain_sub = healthBarDrain.getSubimage(0, 0, (int) (1.31 * previousHealth), 14);
//				g2.drawImage(healthBarDrain_sub, 0, 0, (int) (previousHealth * 1.31) * 2, 14 * 2, null);
//			}
//
//			if (getHealth() > 0) {
//				healthBarFull_sub = healthBarFull.getSubimage(0, 0, (int) (1.31 * getHealth()), 14);
//				g2.drawImage(healthBarFull_sub, 0, 0, (int) (getHealth() * 2.62), 14 * 2, null);
//			}
//		} else if (previousHealth < getHealth()) { // draw health healed
//			previousHealth += 1;
//
//			healthBarHeal_sub = healthBarHeal.getSubimage(0, 0, (int) (1.31 * getHealth()), 14);
//			g2.drawImage(healthBarHeal_sub, 0, 0, (int) (1.31 * getHealth()) * 2, 14 * 2, null);
//
//			healthBarFull_sub = healthBarFull.getSubimage(0, 0, (int) (1.31 * previousHealth), 14);
//			g2.drawImage(healthBarFull_sub, 0, 0, (int) (1.31 * previousHealth) * 2, 14 * 2, null);
//		}
//	}

	@Override
	public void Update() {
		super.Update();

//		if(getDirection() != previousDir) {
//			 setPosX(getPosX() + 24 * getDirection());
//			 previousDir = getDirection();
//		}

//		if (isLanding()) {
//			if (getPosY() >= 498) {
//				jumpForwardAnim.unIgnoreFrame(3);
//				jumpBackAnim.unIgnoreFrame(3);
//				jumpSkillForwardAnim.unIgnoreFrame(3);
//				jumpSkillBackAnim.unIgnoreFrame(3);
//				djumpForwardAnim.unIgnoreFrame(5);
//				djumpBackAnim.unIgnoreFrame(5);
//			}
//
//		}

		if (sitdownSkillBackAnim.isLastFrame() || sitdownSkillForwardAnim.isLastFrame()) {
			setSitting(false);
		}

		if (jumpForwardAnim.getCurrentFrame() > jumpBackAnim.getCurrentFrame())
			jumpBackAnim.setCurrentFrame(jumpForwardAnim.getCurrentFrame());
		else
			jumpForwardAnim.setCurrentFrame(jumpBackAnim.getCurrentFrame());

		if (sitdownSkillForwardAnim.getCurrentFrame() > sitdownSkillBackAnim.getCurrentFrame())
			sitdownSkillBackAnim.setCurrentFrame(sitdownSkillForwardAnim.getCurrentFrame());
		else
			sitdownSkillForwardAnim.setCurrentFrame(sitdownSkillBackAnim.getCurrentFrame());

//		System.out.println(attack1ForwardAnim.getCurrentFrame() + " " + attack2ForwardAnim.getCurrentFrame() + " "
//				+ attack3ForwardAnim.getCurrentFrame() + " " + isFirstAttack + " " + isSecondAttack + " "
//				+ isThirdAttack + " " + isAttacking);

		if (attack1ForwardAnim.isLastFrame() || attack1BackAnim.isLastFrame()) {
			isAttacking = false;
			isFirstAttack = false;
			attack1ForwardAnim.reset();
			attack1BackAnim.reset();
		}

//		System.out.println(hurtDisplay + " " + getHealth() + " " + getState());
	}

	@Override
	public void draw(Graphics2D g2) {

//		drawAttackHitbox(g2);
		drawMovingHitbox(g2);
		g2.setColor(Color.black);
		g2.drawRect((int) getPosX(), (int) getPosY(), 1, 1);

//		drawHealthBar(g2);

		if (getState() == NOBEHURT) {
			if (getState() != DEATH) {

				if (hurtDisplay < 5)
					hurtDisplay++;
				else {
					hurtDisplay++;
					if (hurtDisplay > 9)
						hurtDisplay = 0;
					return;
				}
			} else
				hurtDisplay = 0;
		}

		switch (getState()) {
		case ALIVE:
		case NOBEHURT:
			if (isSitting()) {

				if (getDirection() == RIGHT_DIR) {
					sitdownSkillForwardAnim.Update(System.nanoTime());
					sitdownSkillForwardAnim.draw((int) getPosX() + 12, (int) getPosY() - 7, g2);
				} else {
					sitdownSkillBackAnim.Update(System.nanoTime());
					sitdownSkillBackAnim.draw((int) getPosX() - 8, (int) getPosY() - 7, g2);
				}
			} else if (isClimbing()) {
				if (getSpeedY() != 0) {
					if (previousDirY * getSpeedY() < 0) {
						Collections.reverse(climdAnim.getFrameImages());
						climdAnim.setCurrentFrame(climdAnim.getFrameImages().size() - 1 - climdAnim.getCurrentFrame());
						previousDirY *= -1;
					}
					climdAnim.Update(System.nanoTime());
					climdAnim.draw((int) getPosX() + 12, (int) getPosY() - 7, g2);
				} else
					climdAnim.draw((int) getPosX() + 12, (int) getPosY() - 7, g2);
			} else if (isSingleJumping()) {

				System.out.println("Nhảy 1 nè");
				if (getDirection() == RIGHT_DIR) {
					jumpForwardAnim.Update(System.nanoTime());
					jumpForwardAnim.draw((int) getPosX() + 12, (int) getPosY() - 7, g2);
				} else {
					jumpBackAnim.Update(System.nanoTime());
					jumpBackAnim.draw((int) getPosX() - 8, (int) getPosY() - 7, g2);
				}
			} else if (isDoubleJumping()) {
				
				if (getDirection() == RIGHT_DIR) {
					djumpForwardAnim.Update(System.nanoTime());
					djumpForwardAnim.draw((int) getPosX() + 12, (int) getPosY() - 7, g2);
				} else {
					djumpBackAnim.Update(System.nanoTime());
					djumpBackAnim.draw((int) getPosX() - 8, (int) getPosY() - 7, g2);
				}
			} else if (isLanding()) {
				jumpForwardAnim.setCurrentFrame(2);
				jumpBackAnim.setCurrentFrame(2);
				if (getDirection() == RIGHT_DIR)
					jumpForwardAnim.draw((int) getPosX() + 12, (int) getPosY() - 7, g2);
				else
					jumpBackAnim.draw((int) getPosX() - 8, (int) getPosY() - 7, g2);

			} else if (isAttacking) {
				if (isFirstAttack) {
					if (getDirection() == RIGHT_DIR) {
						attack1ForwardAnim.Update(System.nanoTime());
						attack1ForwardAnim.draw((int) getPosX() + 12, (int) getPosY() - 7, g2);
					} else {
						attack1BackAnim.Update(System.nanoTime());
						attack1BackAnim.draw((int) getPosX() - 8, (int) getPosY() - 7, g2);
					}
				} else if (isSecondAttack) {
					if (getDirection() == RIGHT_DIR) {
						attack2ForwardAnim.Update(System.nanoTime());
						attack2ForwardAnim.draw((int) getPosX() + 12, (int) getPosY() - 7, g2);
					} else {
						attack2BackAnim.Update(System.nanoTime());
						attack2BackAnim.draw((int) getPosX() - 8, (int) getPosY() - 7, g2);
					}
				} else if (isThirdAttack) {
					if (getDirection() == RIGHT_DIR) {
						attack3ForwardAnim.Update(System.nanoTime());
						attack3ForwardAnim.draw((int) getPosX() + 12, (int) getPosY() - 7, g2);
					} else {
						attack3BackAnim.Update(System.nanoTime());
						attack3BackAnim.draw((int) getPosX() - 8, (int) getPosY() - 7, g2);
					}
				}

			} else if (getState() == NOBEHURT) {
				if (getDirection() == RIGHT_DIR) {
					beHurtForwardAnim.Update(System.nanoTime());
					beHurtForwardAnim.draw((int) getPosX() + 12, (int) getPosY() - 7, g2);
				} else {
					beHurtBackAnim.Update(System.nanoTime());
					beHurtBackAnim.draw((int) getPosX() - 8, (int) getPosY() - 7, g2);
				}
			} else if (isOnGround()) {

				if (getSpeedX() > 0) {
					runForwardAnim.Update(System.nanoTime());
					runForwardAnim.draw((int) getPosX() + 12, (int) getPosY() - 7, g2);
				} else if (getSpeedX() < 0) {
					runBackAnim.Update(System.nanoTime());
					runBackAnim.draw((int) getPosX() - 8, (int) getPosY() - 7, g2);
				} else {
					if (getDirection() == RIGHT_DIR) {
						idleForwardAnim.Update(System.nanoTime());
						idleForwardAnim.draw((int) getPosX() + 12, (int) getPosY() - 7, g2);
					} else {
						idleBackAnim.Update(System.nanoTime());
						idleBackAnim.draw((int) getPosX() - 8, (int) getPosY() - 7, g2);
					}
				}
			} else {
				System.out.println("Ảo thật đấy");
			}

			break;

		case DEATH:
			if (getDirection() == RIGHT_DIR) {
				deathForwardAnim.Update(System.nanoTime());
				deathForwardAnim.draw((int) getPosX() + 12, (int) getPosY() - 7, g2);
			} else {
				deathBackAnim.Update(System.nanoTime());
				deathBackAnim.draw((int) getPosX() - 8, (int) getPosY() - 7, g2);
			}
			break;
		default:
			break;

		}

	}

	@Override
	public Rectangle attackHitbox() {
		// TODO Auto-generated method stub
		return null;
	}

}