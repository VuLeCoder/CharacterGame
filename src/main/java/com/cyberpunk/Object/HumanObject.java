package com.cyberpunk.Object;

import java.awt.Rectangle;

import com.cyberpunk.Effect.Animation;
import com.cyberpunk.StartGame.GameFrame;

public abstract class HumanObject extends Object{
	public static final float JUMP_STRENGTH = -3f;
	public static final int HUMAN_HEIGHT = 34;
	public static final int HUMAN_WIDTH = 21;
	public static final float HUMAN_WEIGHT = 0.15f;
	public static final float HUMAN_RUN_SPEED = 2.5f;
	public static final float HUMAN_WALK_SPEED = 2f;
	public static final long TIME_TO_CHANGE_DROP_STATE = 400000000L;
	
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
	}

	public void updateDropState(long currentTime) {
	    if (isDrop && currentTime - beginTime >= TIME_TO_CHANGE_DROP_STATE) {
	        this.isDrop = false;
	    }
	}
	
	public void climbUp() {
		setSpeedY(-2);
	}
	
	public void climbDown() {
		setSpeedY(2);
	}
	
	public void stopClimb() {
		setSpeedY(0);
	}

	@Override
	public void beHurt(int damageGet) {
		setHealth(getHealth() - damageGet);
		setState(BEHURT);
	}

	@Override
	public void Update() {
		updateDropState(System.nanoTime());
		
		Rectangle boundForCollisionWithLadder = getGameWorld().getMapGame().haveCollisionWithLadder(movingHitbox());
		if(boundForCollisionWithLadder != null) {
			setOnLadder(true);
		} else {
			setOnLadder(false);
			setClimbing(false);
		}
		
		if (isClimbing && getSpeedY() != 0) {
		    setPosX(boundForCollisionWithLadder.x + boundForCollisionWithLadder.width / 2 + 3);
		} else {
		    setPosX(getPosX() + getSpeedX());
		}

		setPosY(getPosY() + getSpeedY());
		
		UpdateLeftRight();
		UpdateTop();
		UpdateLand();
	}
	
	private void UpdateLeftRight() {
		Rectangle boundForCollisionWithMapFuture;
		CollisionResult hitBox;
		
		boundForCollisionWithMapFuture = movingHitbox();
		boundForCollisionWithMapFuture.y -= 1;
		hitBox = getGameWorld().getMapGame().haveCollisionWithWallLeft(boundForCollisionWithMapFuture);
		switch(hitBox.getCollisionWithTile()) {
			case MapGame.WALL_TILE:
			case MapGame.TRANSPORT_LEFT_TILE:
				if(getSpeedX() * Object.LEFT_DIR > 0) {
					setPosX(hitBox.getCollisionRect().x + hitBox.getCollisionRect().width + getWidth() / 2 + 1);
				}
				break;
				
			case MapGame.PLATFORM_TILE:
				startDrop(System.nanoTime());
				break;
		}
		
		boundForCollisionWithMapFuture = movingHitbox();
		boundForCollisionWithMapFuture.y -= 1;
		hitBox = getGameWorld().getMapGame().haveCollisionWithWallRight(boundForCollisionWithMapFuture);
		switch(hitBox.getCollisionWithTile()) {
			case MapGame.WALL_TILE:
			case MapGame.TRANSPORT_LEFT_TILE:
				if(getSpeedX() * Object.RIGHT_DIR > 0) {
					setPosX(hitBox.getCollisionRect().x - getWidth() / 2 - 1);
				}
				break;
				
			case MapGame.PLATFORM_TILE:
				startDrop(System.nanoTime());
				break;
		}
	}
	
	private void UpdateTop() {
		Rectangle boundForCollisionWithMapFuture = movingHitbox();
		boundForCollisionWithMapFuture.y += (getSpeedY() != 0 ? getSpeedY() : -1);
		CollisionResult hitBox = getGameWorld().getMapGame().haveCollisionWithTop(boundForCollisionWithMapFuture);
		
		switch (hitBox.getCollisionWithTile()) {
			case MapGame.WALL_TILE:
				setPosY(hitBox.getCollisionRect().y + 3 * getHeight() / 2);
				setSpeedY(0);				
				break;
				
			case MapGame.HAMMER_TILE:
				break;
		}
	}
	
	private void UpdateLand() {
		if (isOnTransportLeft()) {
			setSpeedX(getSpeedX() - AnimatedObject.TRANSPORT_SPEED * Object.LEFT_DIR);
			setOnTransportLeft(false);
		}

		Rectangle boundForCollisionWithMapFuture = movingHitbox();
		boundForCollisionWithMapFuture.y += (getSpeedY() != 0 ? getSpeedY() : 2);
		CollisionResult hitBox = getGameWorld().getMapGame().haveCollisionWithLand(boundForCollisionWithMapFuture, this);

		switch (hitBox.getCollisionWithTile()) {
			case MapGame.TRANSPORT_LEFT_TILE:
				if(isClimbing()) {
					break;
				}
 				setOnTransportLeft(true);
				setSpeedX(getSpeedX() + AnimatedObject.TRANSPORT_SPEED * Object.LEFT_DIR);

			case MapGame.PLATFORM_TILE:
				if(getIsDrop()) {
					setSpeedY(getSpeedY() + getMass());
					break;
				}
				
			case MapGame.WALL_TILE:
				if(getSpeedY() > 0) {
					setPosY(hitBox.getCollisionRect().y - getHeight() / 2);
					setSpeedY(0);
				}
				break;

			case MapGame.DEATH_TILE:
				setPosY(hitBox.getCollisionRect().y - getHeight() / 2);
				setSpeedY(0);
				setHealth(-1000);
				break;

			default:
				if(isClimbing()) {
					break;
				}
				setSpeedY(getSpeedY() + getMass());
				break;
		}
	}
	
}