package com.cyberpunk.Object;

import java.awt.Rectangle;

import com.cyberpunk.Effect.Animation;
import com.cyberpunk.StartGame.GameFrame;

public abstract class HumanObject extends Object{
	private boolean isSitting;
	private boolean isClimbing;
	private boolean isDoubleJumping;
	private boolean isSingleJumping;
	private boolean isLanding;
	private boolean isOnGround;
	private boolean isPhasing;
	
	private long noBeHurtDuration = 500000000L;
	private long noBeHurtStart;
	
	private Animation hurtForwardAni, hurtBackAni;
	
	public HumanObject(float x, float y, GameWorld gameWorld) {
		super(x, y, 100, 10, 48, 48, gameWorld);
		
		//set temp direction, team type
		if(x > GameFrame.SCREEN_WIDTH/2) {
			 setDirection(LEFT_DIR);
			 setTeamType(P2_TEAM);
		} else {
			 setDirection(RIGHT_DIR);
			 setTeamType(P1_TEAM);
		}
		
		setMass(0.1f);
	}

	public boolean isSitting() {
		return isSitting;
	}

	public void setSitting(boolean isSitting) {
		this.isSitting = isSitting;
	}

	public boolean isClimbing() {
		return isClimbing;
	}

	public void setClimbing(boolean isClimbing) {
		this.isClimbing = isClimbing;
	}

	public boolean isDoubleJumping() {
		return isDoubleJumping;
	}

	public void setDoubleJumping(boolean isDoubleJumping) {
		this.isDoubleJumping = isDoubleJumping;
	}

	public boolean isSingleJumping() {
		return isSingleJumping;
	}

	public void setSingleJumping(boolean isSingleJumping) {
		this.isSingleJumping = isSingleJumping;
	}

	public boolean isLanding() {
		return isLanding;
	}

	public void setLanding(boolean isLanding) {
		this.isLanding = isLanding;
	}

	public boolean isOnGround() {
		return isOnGround;
	}

	public void setOnGround(boolean isOnGround) {
		this.isOnGround = isOnGround;
	}

	public boolean isPhasing() {
		return isPhasing;
	}

	public void setPhasing(boolean isPhasing) {
		this.isPhasing = isPhasing;
	}

	public long getNoBeHurtDuration() {
		return noBeHurtDuration;
	}

	public void setNoBeHurtDuration(int noBeHurtDuration) {
		this.noBeHurtDuration = noBeHurtDuration;
	}

	public long getNoBeHurtStart() {
		return noBeHurtStart;
	}

	public void setNoBeHurtStart(int noBeHurtStart) {
		this.noBeHurtStart = noBeHurtStart;
	}

	public Animation getHurtForwardAni() {
		return hurtForwardAni;
	}

	public void setHurtForwardAni(Animation hurtForwardAni) {
		this.hurtForwardAni = hurtForwardAni;
	}

	public Animation getHurtBackAni() {
		return hurtBackAni;
	}

	public void setHurtBackAni(Animation hurtBackAni) {
		this.hurtBackAni = hurtBackAni;
	}
	
	public abstract void jump();
	public abstract void run();
	public abstract void stopRun();
	public abstract void sitDown();
	public abstract void standUp();
	public abstract void attack();
	public abstract void stopAttack();

	@Override
	public Rectangle attackHitbox() {
		Rectangle rect = movingHitbox();

		if(isSitting) {
			rect.x = (int) getPosX() - 12;
			rect.y = (int) getPosY() - 18;
			rect.width = 24;
			rect.height = 36;
		} else {
			rect.x = (int) getPosX() - 12;
			rect.y = (int) getPosY() - 24;
			rect.width = 24;
			rect.height = 48;
		}
		
		return rect;
	}

	@Override
	public void beHurt(int damageGet) {
		
		setHealth(getHealth() - damageGet);
		setState(BEHURT);
	}

	@Override
	public void Update() {
		switch (getState()) {
		case ALIVE:
			//code for checking collision with object on map here
			
			//code for running
//			run();
			break;
		
		case BEHURT:
			setState(NOBEHURT);
			isPhasing = true;
			noBeHurtStart = System.nanoTime();
			if(getHealth() <= 0) setState(DEATH);
			break;
			
		case NOBEHURT:
			//code for running
			if(System.nanoTime() - noBeHurtStart > noBeHurtDuration) setState(ALIVE);
//			run();
			break;
		
		case DEATH:
			//build death animation here
			break;
		default:
			break;
		}
		
		if(getState() == ALIVE || getState() == NOBEHURT) {
			//code for checking collision around character
			setPosY(getPosY() + getSpeedY());
			setSpeedY(getSpeedY() + getMass());
			if(getPosY() >= 500) {
				setSpeedY(0);
				setPosY(500);
				isOnGround = true;
				isDoubleJumping = false;
				isSingleJumping = false;
				isLanding = false;
			} else {
				isOnGround = false;
				setPosY(getPosY() + getSpeedY());
				setSpeedY(getSpeedY() + getMass());
			}
		}
		
		if(getSpeedY() > 0) isLanding = true;
//		System.out.println(getSpeedX() + " " + getSpeedY());
//		System.out.println(isSingleJumping + " " + isDoubleJumping + " " + isOnGround);
		setPosX(getPosX() + getSpeedX());
	}
	
}
