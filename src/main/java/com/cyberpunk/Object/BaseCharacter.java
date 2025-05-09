package com.cyberpunk.Object;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.cyberpunk.Effect.Animation;
import com.cyberpunk.Effect.DataLoader;

public class BaseCharacter extends HumanObject {
	public String name;

//	private boolean isRunning = false;

	// private int hurtDisplay = 0;
	
	private int[][] damageFrames;

//	private boolean isGetDamage;
//
//	private boolean isLastFrameReached;
//	private boolean isFirstAttack, isSecondAttack, isThirdAttack;

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

	private Animation fallForwardAnim, fallBackAnim;
	private Animation knockForwardAnim, knockBackAnim;
	
	
	public BaseCharacter(float x, float y, String name, GameWorld gameWorld) {
		super(x, y, gameWorld);
		this.name = name;
		
		if(name.equals("biker")) {
			damageFrames = BIKER_FRAME_DAMAGE;
		}

		// Animation for character action
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
		
		
		fallForwardAnim = DataLoader.getInstance().getAnimation(name + "fall");
		fallForwardAnim.setRepeated(false);
		fallBackAnim = DataLoader.getInstance().getAnimation(name + "fall");
		fallBackAnim.setRepeated(false);
		fallBackAnim.flipAllImage();
		
		knockForwardAnim = DataLoader.getInstance().getAnimation(name + "knockdown");
		knockForwardAnim.setRepeated(false);
		knockBackAnim = DataLoader.getInstance().getAnimation(name + "knockdown");
		knockBackAnim.setRepeated(false);
		knockBackAnim.flipAllImage();
	}

	
	// -----------------------------------------------------------------------------------------------------------------------------------
	// ------------------------------------------------------------ Xong nhảy ------------------------------------------------------------
	@Override
	public void jump() {
		setSitting(false);

		if (isClimbing() || getIsAttacking()) {
			return;
		}

		if (getSpeedY() > 0 && getIsOnLadder()) {
			setClimbing(true);
			return;
		}

		if (isOnGround()) {
			setSpeedY(JUMP_STRENGTH);
			setSingleJumping(true);

			jumpForwardAnim.reset();
			jumpBackAnim.reset();
			jumpForwardAnim.setIgnoreFrame(3);
			jumpBackAnim.setIgnoreFrame(3);
	        return;
	    }
		
		if (!isDoubleJumping()) {
	    	setSpeedY(JUMP_STRENGTH);
	        setDoubleJumping(true);
	        
	        djumpForwardAnim.reset();
	        djumpBackAnim.reset();
	        djumpForwardAnim.setIgnoreFrame(5);
			djumpBackAnim.setIgnoreFrame(5);
	    }
	}
	
	
	public void drawJumpStage(Graphics2D g2) {		
		if(isDoubleJumping()) {
			drawCharacterAnimation(djumpForwardAnim, djumpBackAnim, g2, 12, -8);
			return;
		}
		
		if(isSingleJumping()) {
			drawCharacterAnimation(jumpForwardAnim, jumpBackAnim, g2, 12, -8);
		}
	}
	
	
	// -----------------------------------------------------------------------------------------------------------------------------------
	// -------------------------------------------------------------- Chạy----------------------------------------------------------------
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

	
	// -----------------------------------------------------------------------------------------------------------------------------------
	// ---------------------------------------------------- Xử lý ngồi, đứng và drop -----------------------------------------------------	
	private boolean sitDownWhenRunning = false;
	
	public boolean getSitDownWhenRunning() {
		return sitDownWhenRunning;
	}
	
	@Override
	public void sitDown(long now) {
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
		
		if(getIsRunning()) {
			sitDownWhenRunning = true;
			setPreDrection(getDirection());
			stopRun();
		}

//		if (time - getStartSittingTime() > DOUBLE_CLICK_THRESHOLD) {
//			if (!isSitting()) {
//				setSitting(true);
//
//				sitdownSkillForwardAnim.reset();
//				sitdownSkillBackAnim.reset();
//				sitdownSkillForwardAnim.setIgnoreFrame(3);
//				sitdownSkillBackAnim.setIgnoreFrame(3);
//			}
//		} else {
//			startDrop(time);
//		}
//		setStartSittingTime(time);

        if (now - getLastSittingTime() <= DOUBLE_CLICK_THRESHOLD) {
        	startDrop(now);
        	setSitting(false);

            return;
        }
        
        if (!isSitting()) {
			setSitting(true);

			sitdownSkillForwardAnim.reset();
			sitdownSkillBackAnim.reset();
			sitdownSkillForwardAnim.setIgnoreFrame(3);
			sitdownSkillBackAnim.setIgnoreFrame(3);
		}
        
	}

	@Override
	public void standUp() {		
		setSitting(false);
		sitDownWhenRunning = false;
		
		idleForwardAnim.reset();
		idleBackAnim.reset();
		sitdownSkillForwardAnim.unIgnoreFrame(3);
		sitdownSkillBackAnim.unIgnoreFrame(3);
		
		setLastSittingTime(System.nanoTime());
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

	// -----------------------------------------------------------------------------------------------------------------------------------
	// -------------------------------------------------------- Xử lý tấn công -----------------------------------------------------------
	private boolean isClickButtonAttack = false;
	private boolean isAttacking = false;
	private int attackStage = 0; // 0: idle, 1: t1, 2: t2, 3: t3
	private long lastAttackTime = 0;
	private final long[] attackDurations = {0, 5 * 50000000 + 100000000, 2 * 120000000, 8 * 120000000 };
	
	// Các frame gây dame riêng của nhân vật 
	public final static int[][] BIKER_FRAME_DAMAGE = {{}, {4}, {1}, {4, 5, 6}};
	
	private boolean AtkWhenRunning = false;
	
	public boolean getAtkWhenRunning() {
		return AtkWhenRunning;
	}
	
	public void setAtkWhenRunning(boolean b) {
		AtkWhenRunning = b;
	}
/*
 * 
 * animation Attack cũ
bikerattack2
bikerattack2_0 100000000 bikerattack2_1 100000000 bikerattack2_2 100000000 bikerattack2_3 100000000 bikerattack2_4 100000000 bikerattack2_5 100000000 bikerattack2_6 100000000 bikerattack2_7 100000000 

bikerattack3
bikerattack3_0 100000000 bikerattack3_1 100000000 bikerattack3_2 100000000 bikerattack3_3 100000000 bikerattack3_4 100000000 bikerattack3_5 100000000 bikerattack3_6 100000000 bikerattack3_7 100000000 

bikerattack1 
bikerattack1_0 100000000 bikerattack1_1 100000000 bikerattack1_2 100000000 bikerattack1_3 100000000 bikerattack1_4 100000000 bikerattack1_5 100000000 
 */
	public void setClickButtonAttack(boolean b) {
		isClickButtonAttack = b;
	}
	
	public boolean getIsAttacking() {
		return isAttacking;
	}
	
	@Override
	public void attack() {
		if (isSingleJumping() || isDoubleJumping()) {
			return;
		}
		
		if(getIsRunning()) {
			AtkWhenRunning = true;
			stopRun();
		}

		isAttacking = true;
		setPreDrection(getDirection());
		attackStage = 1;
		lastAttackTime = System.nanoTime();
	}

	@Override
	public void stopAttack() {
		isAttacking = false;
		attackStage = 0;

		// reset hoạt ảnh tấn công
		attack1ForwardAnim.reset();
		attack1BackAnim.reset();
		attack2ForwardAnim.reset();
		attack2BackAnim.reset();
		attack3ForwardAnim.reset();
		attack3BackAnim.reset();
		
		if(getAtkWhenRunning()) {
			run();
		}
	}

	private void UpdateAttackStage() {
		long currentTime = System.nanoTime();
		long elapsed = currentTime - lastAttackTime;
		
		if(attackStage == 3 && elapsed <= attackDurations[attackStage]/8) {
			setPosX(getPosX() + 2 * getDirection());
		}
		
		if(attackStage == 2 && elapsed <= attackDurations[attackStage]/2) {
			setPosX(getPosX() + 1 * getDirection());
		}

		if (elapsed < attackDurations[attackStage]) {
			return;
		}
		
		lastAttackTime = currentTime;

		if (attackStage < 3) {
			attackStage++;
		} else {
			attackStage = 1;
		}
	}
	
	public void drawAttackAnimation(Graphics2D g2) {
	    Animation forwardAnim = null, backAnim = null;
	    int damage = 0;

	    switch (attackStage) {
	        case 1:
	            forwardAnim = attack1ForwardAnim;
	            backAnim = attack1BackAnim;
	            damage = 5;
	            break;
	        case 2:
	            forwardAnim = attack2ForwardAnim;
	            backAnim = attack2BackAnim;
	            damage = 5;
	            break;
	        case 3:
	            forwardAnim = attack3ForwardAnim;
	            backAnim = attack3BackAnim;
	            damage = 15;
	            break;
	        default:
	            return;
	    }

	    drawCharacterAnimation(forwardAnim, backAnim, g2, 12, -8);
	    checkAndCreateBaseAttack(forwardAnim, backAnim, damageFrames[attackStage], damage);
	    checkAndStopAttack(forwardAnim, backAnim);
	}

	private void checkAndStopAttack(Animation forward, Animation back) {
	    if (forward.isLastFrame() || back.isLastFrame()) {
	        if (!isClickButtonAttack) {
	        	stopAttack();
	        }
	    }
	}
	
	// Thêm object gây dame
	private void checkAndCreateBaseAttack(Animation forward, Animation back, int[] dmgFrames, int damage) {
	    int frameF = forward.getCurrentFrame();
	    int frameB = back.getCurrentFrame();
	    
	    for (int f : dmgFrames) {
	        if (frameF == f || frameB == f) {
	            getGameWorld().getSkillManager().addObject(new BaseAttack(damage, this, getGameWorld()));
	            break;
	        }
	    }
	}

	
	// -----------------------------------------------------------------------------------------------------------------------------------
	// ---------------------------------------------------------- Xử lý ngã --------------------------------------------------------------	
	
	private boolean hadResetFallAnim = true;
//	private boolean wasOnGround = true;
//	private long timeJustFellToGround = 0;
	
	public void onFallLand() {
		
		if(!hadResetFallAnim) {
			hadResetFallAnim = true;
			
			fallForwardAnim.reset();
			fallBackAnim.reset();
		}
	}
	
	private void drawFallStage(Graphics2D g2) {
		hadResetFallAnim = false;
		drawCharacterAnimation(fallForwardAnim, fallBackAnim, g2, 12, -8);
	}
	
	
	// -----------------------------------------------------------------------------------------------------------------------------------
	// -------------------------------------------------------- Xử lý Knockdown ----------------------------------------------------------
	private boolean hadResetKnockAnim = true;
	
	private void gettingUp() {
		if(!hadResetKnockAnim) {
			hadResetKnockAnim = true;
			
			knockForwardAnim.reset();
			knockBackAnim.reset();
		}
	}
	
	private void drawKnockStage(Graphics2D g2) {
		hadResetKnockAnim = false;
		drawCharacterAnimation(knockForwardAnim, knockBackAnim, g2, 12, -8);
	}
	
	// -----------------------------------------------------------------------------------------------------------------------------------
	// ---------------------------------------------------- Xử lý Thanh máu --------------------------------------------------------------
	private float previousHPratio = 1;
	private long timeStartDrain = 0;
	
	public float getPreviousHPratio() {
		return this.previousHPratio;
	}
	
	public void setPreviousHPratio(float ratio) {
		this.previousHPratio = ratio;
	}

	public void setTimeStartDrain(long time) {
		this.timeStartDrain = time;
	}

	public long getTimeStartDrain() {
		return this.timeStartDrain;
	}
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
	
	
	
	@Override
	public void beHurt(float damageGet) {
		super.beHurt(damageGet);
//		isGetDamage = true;
	}

	@Override
	public void beHeal(float healedGet) {
		float health = Math.min(100, getHealth() + healedGet);
		setHealth(health);

	}



	@Override
	public void Update() {
		super.Update();
		
		switch (getState()) {
			case FALL:
				break;
				
			case KNOCKDOWN:
				onFallLand();
				break;
				
			case BEHURT:
				break;
		
			case NOBEHURT:
				gettingUp();
				
			case ALIVE:
				if(getIsAttacking()) {
					UpdateAttackStage();
					return;
				}
				
				if(getSpeedY() >= FALL_SPEED) {
					setState(FALL);
				}
				
				break;
			
			case DEATH:
				stopRun();
				stopAttack();
				break;
				
			default:
				break;
		}
	}

	private void drawCharacterAnimation(Animation animForward, Animation animBack, Graphics2D g2, int offsetXForward,
			int offsetXBack) {
		long currentTime = System.nanoTime();
		animForward.Update(currentTime);
		animBack.Update(currentTime);
		
		if (getDirection() == RIGHT_DIR) {
			animForward.draw((int) getPosX() + offsetXForward, (int) getPosY() - 7, g2);
		} else {
			animBack.draw((int) getPosX() + offsetXBack, (int) getPosY() - 7, g2);
		}
	}

	@Override
	public void draw(Graphics2D g2) {

//		drawAttackHitbox(g2);
		drawMovingHitbox(g2);
		g2.setColor(Color.black);
		g2.drawRect((int) getPosX(), (int) getPosY(), 1, 1);

//		drawHealthBar(g2, 0, 0);
		
//		if (getState() == NOBEHURT && !getIsFalling()) {
//			if (getState() != DEATH) {
//
//				if (hurtDisplay < 5)
//					hurtDisplay++;
//				else {
//					hurtDisplay++;
//					if (hurtDisplay > 9)
//						hurtDisplay = 0;
//					return;
//				}
//			} else
//				hurtDisplay = 0;
//		}

		switch (getState()) {
		case FALL:
			drawFallStage(g2);
			break;
			
		case KNOCKDOWN:
			drawKnockStage(g2);
			break;
		
		case ALIVE:
		case NOBEHURT:
			if (getIsAttacking()) {				
				drawAttackAnimation(g2);
				break;
			}
			
			if (isSitting()) {
				drawCharacterAnimation(sitdownSkillForwardAnim, sitdownSkillBackAnim, g2, 12, -8);
				break;
			}

			if (isClimbing()) {
				if (getSpeedY() != 0) {
					climdAnim.Update(System.nanoTime());
				}
				climdAnim.draw((int) getPosX() + 12, (int) getPosY() - 7, g2);
				break;
			}
			
			if (getState() == NOBEHURT) {
				drawCharacterAnimation(beHurtForwardAnim, beHurtBackAnim, g2, 12, -8);
				break;
			}

			if (isSingleJumping() || isDoubleJumping()) {
				drawJumpStage(g2);
				break;
			}

//			if (isLanding()) {
//				jumpForwardAnim.setCurrentFrame(2);
//				jumpBackAnim.setCurrentFrame(2);
//				drawCharacterAnimation(jumpForwardAnim, jumpBackAnim, g2, 12, -8);
//				break;
//			}

			if (isOnGround()) {
				if(getIsRunning()) {
					if (getSpeedX() > 0) {
						runForwardAnim.Update(System.nanoTime());
						runForwardAnim.draw((int) getPosX() + 12, (int) getPosY() - 7, g2);
					} else {
						runBackAnim.Update(System.nanoTime());
						runBackAnim.draw((int) getPosX() - 8, (int) getPosY() - 7, g2);
					}
				} else {
					drawCharacterAnimation(idleForwardAnim, idleBackAnim, g2, 12, -8);
				}
				break;
			}
			
			drawCharacterAnimation(jumpForwardAnim, jumpBackAnim, g2, 12, -8);	
			break;

		case DEATH:
			drawCharacterAnimation(deathForwardAnim, deathBackAnim, g2, 12, -8);
			break;
		}
	}

	@Override
	public Rectangle attackHitbox() {
		// TODO Auto-generated method stub
		return null;
	}

}