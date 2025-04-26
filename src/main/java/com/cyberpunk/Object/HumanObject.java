package com.cyberpunk.Object;

import java.awt.Rectangle;

import com.cyberpunk.Effect.Animation;
import com.cyberpunk.StartGame.GameFrame;

public abstract class HumanObject extends Object{
	public static final int HUMAN_HEIGHT = 34;
	public static final int HUMAN_WIDTH = 21;
	public static final float HUMAN_WEIGHT = 0.1f;
	public static final float HUMAN_RUN_SPEED = 3;
	public static final float HUMAN_WALK_SPEED = 2f;
	public static final long TIME_TO_CHANGE_DROP_STATE = 300000000L;
	
	private boolean isDrop = false;
	private boolean isSitting = false;
	private boolean isOnLadder, isClimbing;
	private boolean isDoubleJumping = false;
	private boolean isSingleJumping = false;
	private boolean isLanding;
	private boolean isOnGround;
	private boolean isPhasing;
	
	private long noBeHurtDuration = 500000000L;
	private long noBeHurtStart;
	private long beginTime = 0;
	
	private Animation hurtForwardAnim, hurtBackAnim;
	
	public HumanObject(float x, float y, GameWorld gameWorld) {
		super(x, y, 100, 10, HUMAN_WIDTH, HUMAN_HEIGHT, gameWorld);
		
		isOnLadder = false;
		isSitting = false;
		isClimbing = false;
		isDoubleJumping = false;
		isSingleJumping = false;
		
		//set temp direction, team type
		if(x > GameFrame.SCREEN_WIDTH / 2) {
			 setDirection(LEFT_DIR);
			 setTeamType(P2_TEAM);
		} else {
			 setDirection(RIGHT_DIR);
			 setTeamType(P1_TEAM);
		}
		
		beginTime = System.nanoTime();
		setMass(HUMAN_WEIGHT);
	}

	public boolean isSitting() {
		return isSitting;
	}

	public void setSitting(boolean isSitting) {
		this.isSitting = isSitting;
	}
	
	public boolean getIsOnLadder() {
		return isOnLadder;
	}
	
	public void setOnLadder(boolean status) {
		isOnLadder = status;
		if(status) {
			setSpeedY(0);
		}
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
		return hurtForwardAnim;
	}

	public void setHurtForwardAni(Animation hurtForwardAni) {
		this.hurtForwardAnim = hurtForwardAni;
	}

	public Animation getHurtBackAni() {
		return hurtBackAnim;
	}

	public void setHurtBackAni(Animation hurtBackAni) {
		this.hurtBackAnim = hurtBackAni;
	}
	
	public boolean getIsDrop() {
		return isDrop;
	}

	public void setIsDrop(boolean isDrop) {
		this.isDrop = isDrop;
	}

	public abstract void jump();
	public abstract void run();
	public abstract void stopRun();
	public abstract void sitDown();
	public abstract void standUp();
	public abstract void attack();
	public abstract void stopAttack();

	public void startDrop(long time) {
		if(!getIsDrop()) {
			setSpeedY(getSpeedY() + 1);
		}
		
	    this.isDrop = true;
	    this.beginTime = time;
//	    updateDropState();
	}

	public void updateDropState(long currentTime) {
	    if (isDrop && currentTime - beginTime >= TIME_TO_CHANGE_DROP_STATE) {
	        this.isDrop = false;
	    }
	}
	
	public void climbUp() {
		isClimbing = true;
		setSpeedY(-2);
	}
	
	public void climbDown() {
		isClimbing = true;
		setSpeedY(2);
	}
	
	public void stopClimb() {
		isClimbing = false;
		setSpeedY(0);
	}
	
	public static final int x = 0;
	public static final int y = 0;
	public static final int z = 0;
	public static final int t = 0;
	
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
		updateDropState(System.nanoTime());
		
		if(getGameWorld().getMapGame().checkCollisionWithLadder(movingHitbox())) {
			if(!isOnLadder) {
				setOnLadder(true);
			}
		} else {
			setOnLadder(false);
		}
		
			
		setPosX(getPosX() + getSpeedX());
		setPosY(getPosY() + getSpeedY());
		Rectangle boundForCollisionWithMapFuture, hitBox;
		
		
		boundForCollisionWithMapFuture = movingHitbox();
//		boundForCollisionWithMapFuture.x += (getSpeedX()* Object.LEFT_DIR > 0 ? getSpeedX() * Object.LEFT_DIR : -1);
		hitBox = getGameWorld().getMapGame().haveCollisionWithWallLeft(boundForCollisionWithMapFuture);
		if (hitBox != null) {
			if(getSpeedX() * Object.LEFT_DIR > 0) {
				setPosX(hitBox.x + hitBox.width + getWidth() / 2 );
			}
		}
		
		boundForCollisionWithMapFuture = movingHitbox();
//		boundForCollisionWithMapFuture.x += (getSpeedX() != 0 ? getSpeedX() * Object.RIGHT_DIR : 1);
		hitBox = getGameWorld().getMapGame().haveCollisionWithWallRight(boundForCollisionWithMapFuture);
		if (hitBox != null) {
			if(getSpeedX() * Object.RIGHT_DIR > 0) {
				setPosX(hitBox.x - getWidth() / 2 - 1);
			}
		}
		
		boundForCollisionWithMapFuture = movingHitbox();
		boundForCollisionWithMapFuture.y += (getSpeedY() != 0 ? getSpeedY() : 2);
		hitBox = getGameWorld().getMapGame().haveCollisionWithLand(boundForCollisionWithMapFuture, this);
		if (hitBox == null) {
			if(!getIsOnLadder()) {
				setSpeedY(getSpeedY() + getMass());
			}
		} else {
			if(getSpeedY() > 0) {
				setPosY(hitBox.y - getHeight() / 2);
				setSpeedY(0);
			}
		}
		
		
		
		
		
		
		
		
//		switch (getState()) {
//		case ALIVE:
//			//code for checking collision with object on map here
//			
//			break;
//		
//		case BEHURT:
//			setState(NOBEHURT);
//			isPhasing = true;
//			noBeHurtStart = System.nanoTime();
//			if(getHealth() <= 0) setState(DEATH);
//			break;
//			
//		case NOBEHURT:
//			//code for running
//			if(System.nanoTime() - noBeHurtStart > noBeHurtDuration) setState(ALIVE);
////			run();
//			break;
//		
//		case DEATH:
//			//build death animation here
//			break;
//		default:
//			break;
//		}
//		
//		if(getState() == ALIVE || getState() == NOBEHURT) {
//			//code for checking collision around character
//			setPosY(getPosY() + getSpeedY());
//			setSpeedY(getSpeedY() + getMass());
//			if(getPosY() >= 500) {
//				setSpeedY(0);
//				setPosY(500);
//				isOnGround = true;
//				isDoubleJumping = false;
//				isSingleJumping = false;
//				isLanding = false;
//			} else {
//				isOnGround = false;
//				setPosY(getPosY() + getSpeedY());
//				setSpeedY(getSpeedY() + getMass());
//			}
//		}
//		
//		if(getSpeedY() > 0) isLanding = true;
////		System.out.println(getSpeedX() + " " + getSpeedY());
////		System.out.println(isSingleJumping + " " + isDoubleJumping + " " + isOnGround);
//		setPosX(getPosX() + getSpeedX());
	}
	
}