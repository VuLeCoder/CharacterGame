package com.cyberpunk.Object;

import java.awt.Rectangle;

import com.cyberpunk.Effect.Animation;
import com.cyberpunk.StartGame.GamePanel;
import com.cyberpunk.StartGame.KeyConfig;

public abstract class HumanObject extends Object{
	// Cấu hình nhân vật
	public static final float HEATLH_POINT = 100f;
	public static final float JUMP_STRENGTH = -3.8f;
	public static final int HUMAN_HEIGHT = 34;
	public static final int HUMAN_WIDTH = 21;
	public static final float HUMAN_WEIGHT = 0.2f;
	public static final float HUMAN_RUN_SPEED = 2.5f;
	public static final float HUMAN_WALK_SPEED = 2f;
//	public static final float HUMAN_FORCE = 2f;
	
	public static final long TIME_TO_CHANGE_DROP_STATE = 80000000L;
//	public static final long TIME_TO_CHANGE_DROP_STATE = 100000000L;
//	public static final long TIME_TO_CHANGE_DROP_STATE = 150000000L;
//	public static final long TIME_TO_CHANGE_DROP_STATE = 200000000L;
//	public static final long TIME_TO_CHANGE_DROP_STATE = 320000000L;
	private boolean isDrop = false;
	private long startDropTime = 0;
	
	public static final long DOUBLE_CLICK_THRESHOLD = 250000000L;
	private boolean isSitting = false;
	private long startSittingTime = 0;
	
	private boolean isRunning;
	private boolean isOnLadder, isClimbing;
	private boolean isDoubleJumping = false;
	private boolean isSingleJumping = false;
	private boolean isLanding;
	private boolean isOnGround;
	private boolean isPhasing;
	
	private long noBeHurtDuration = 500000000L;
	private long noBeHurtStart;
	
	private Animation hurtForwardAnim, hurtBackAnim;
	
	public HumanObject(float x, float y, GameWorld gameWorld) {
		super(x, y, 100, 10, HUMAN_WIDTH, HUMAN_HEIGHT, gameWorld);
		
		isRunning = false;
		isOnLadder = false;
		isSitting = false;
		isClimbing = false;
		isDoubleJumping = false;
		isSingleJumping = false;
		
		//set temp direction, team type
		if(x > GamePanel.MAP_WIDTH / 2) {
			 setDirection(LEFT_DIR);
			 setTeamType(P2_TEAM);
		} else {
			 setDirection(RIGHT_DIR);
			 setTeamType(P1_TEAM);
		}
		
		startDropTime = System.nanoTime();
		setMass(HUMAN_WEIGHT);
	}
	
	public boolean getIsRunning() {
		return isRunning;
	}
	
	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

	public boolean isSitting() {
		return isSitting;
	}

	public void setSitting(boolean isSitting) {
		this.isSitting = isSitting;
	}
	
	public long getStartSittingTime() {
		return startSittingTime;
	}

	public void setStartSittingTime(long startSittingTime) {
		this.startSittingTime = startSittingTime;
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
		if(isOnGround) {
			setSingleJumping(false);
			setDoubleJumping(false);
		}
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
	public abstract void sitDown(long time);
	public abstract void standUp();
	public abstract void attack();
	public abstract void stopAttack();
	public abstract void beHeal(float healedGet);

	public void startDrop(long time) {
		if(!getIsDrop()) {
			setSpeedY(getSpeedY() + 1);
		}
		
	    this.isDrop = true;
	    this.startDropTime = time;
	}

	public void updateDropState(long currentTime) {
	    if (isDrop && currentTime - startDropTime >= TIME_TO_CHANGE_DROP_STATE) {
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
		if(isDrop)
		System.out.println(isDrop);
		
//		UpdateCollisionWithObject();
		
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
		
//		CollisionResult bottomCollisionWithObject = getGameWorld().getObjectManager().BottomCollisionWithObject(this);
//		CollisionResult leftRightCollisionWithObject = getGameWorld().getObjectManager().LeftRightCollisionWithObject(this);
		CollisionResult bottomCollisionWithObject = null;
		CollisionResult leftRightCollisionWithObject = null;
//		System.out.println(collisionWithObject.getCollisionWithTile() == KeyConfig.DOWN);
		
		UpdateColiisionWithMapLeftRight(leftRightCollisionWithObject);
		UpdateColiisionWithMapTop();
		UpdateColiisionWithMapLand(bottomCollisionWithObject);
		
	}
	
	private void UpdateColiisionWithMapLeftRight(CollisionResult collisionObject) {
		Rectangle boundForCollisionWithMapFuture;
		CollisionResult hitBox;
		
		boundForCollisionWithMapFuture = movingHitbox();
		boundForCollisionWithMapFuture.x -= 1;
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
				
			case MapGame.HAMMER_TILE:
				setPosX(getPosX() - getSpeedX());
				break;
		}
		
		boundForCollisionWithMapFuture = movingHitbox();
		boundForCollisionWithMapFuture.x += 1;
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
				
			case MapGame.HAMMER_TILE:
				setPosX(getPosX() - getSpeedX());
				break;
		}
	}
	
	private void UpdateColiisionWithMapTop() {
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
	
	private void UpdateColiisionWithMapLand(CollisionResult collisionObject) {
		setOnGround(false);
		
		if (isOnTransportLeft()) {
			setSpeedX(getSpeedX() - MapObject.TRANSPORT_SPEED * Object.LEFT_DIR);
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
				setSpeedX(getSpeedX() + MapObject.TRANSPORT_SPEED * Object.LEFT_DIR);

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
				setOnGround(true);
				break;

			case MapGame.DEATH_TILE:
				setState(DEATH);
				setHealth(-1000);
				break;

			default:
				if(collisionObject != null && collisionObject.getCollisionWithTile() == KeyConfig.DOWN) {
					setOnGround(true);
					if(getSpeedY() > 0) {
						setSpeedY(0);
						setPosY(collisionObject.getObjectCollisionWith().getPosY() - (collisionObject.getObjectCollisionWith().getHeight() + getHeight()) / 2 + 1);
					}
					break;
				}
				
				if(isClimbing()) {
					break;
				}
				
				setSpeedY(getSpeedY() + getMass());
				break;
		}
	}
}
