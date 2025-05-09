package com.cyberpunk.Object;

import java.awt.Rectangle;

import com.cyberpunk.Effect.Animation;
import com.cyberpunk.StartGame.KeyConfig;

public abstract class HumanObject extends Object {
	// Cấu hình nhân vật
	public static final float HEALTH_POINT = 100f;
	public static final float JUMP_STRENGTH = -3.8f;
	public static final int HUMAN_HEIGHT = 34;
	public static final int HUMAN_WIDTH = 21;
	public static final float HUMAN_WEIGHT = 0.2f;
	public static final float HUMAN_RUN_SPEED = 2.5f;
	public static final float HUMAN_WALK_SPEED = 2f;
//	public static final float HUMAN_FORCE = 2f;

	public static final float FALL_SPEED = 6.3f;
	public static final float FALL_DAMAGE = 15;

	public static final long TIME_TO_CHANGE_DROP_STATE = 80000000L;
	private boolean isDrop = false;
	private long startDropTime = 0;

	public static final long DOUBLE_CLICK_THRESHOLD = 250000000L;
	private boolean isSitting = false;
	private long lastSittingTime = 0;
	
	
	public static final long KNOCKDOWN_DURATION = 750000000L;
	private static final float BIG_DAMAGE = 15;
	private long timeStartKnockDown;

	private boolean isRunning;
	private boolean isOnLadder, isClimbing;
	private boolean isDoubleJumping = false;
	private boolean isSingleJumping = false;
	private boolean isLanding;
	private boolean isOnGround;
	private boolean isPhasing;

//	private long NOBEHURT_DURATION = 500000000L; //500ms
	private static final long NOBEHURT_DURATION = 1000000000L; // 1s
//	private long NOBEHURT_DURATION = 2000000000L; //2s
	private long noBeHurtStart;

	private int preDirection = 0;

	private Animation hurtForwardAnim, hurtBackAnim;

	public HumanObject(float x, float y, GameWorld gameWorld) {
		super(x, y, 100, 10, HUMAN_WIDTH, HUMAN_HEIGHT, gameWorld);

		isRunning = false;
		isOnLadder = false;
		isSitting = false;
		isClimbing = false;
		isDoubleJumping = false;
		isSingleJumping = false;

		// set temp direction, team type
//		if(x > GamePanel.MAP_WIDTH / 2) {
//			 setDirection(LEFT_DIR);
//			 setTeamType(P2_TEAM);
//		} else {
//			 setDirection(RIGHT_DIR);
//			 setTeamType(P1_TEAM);
//		}

		startDropTime = System.nanoTime();
		setMass(HUMAN_WEIGHT);
	}

	private long getTimeStartKnockDown() {
		return timeStartKnockDown;
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

	public long getLastSittingTime() {
		return lastSittingTime;
	}

	public void setLastSittingTime(long lastSittingTime) {
		this.lastSittingTime = lastSittingTime;
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
		if (isOnGround) {
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

	public long getNoBeHurtStart() {
		return noBeHurtStart;
	}

	public void setNoBeHurtStart(long l) {
		this.noBeHurtStart = l;
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

	public void setPreDrection(int dir) {
		preDirection = dir;
	}

	public int getPreDirection() {
		return preDirection;
	}
	
	public void startKnockdownTimer() {
		this.timeStartKnockDown = System.nanoTime();
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
		if (getIsDrop()) {
			return;
		}
		setIsDrop(true);
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
	public void beHurt(float damageGet) {
		if (getState() == NOBEHURT || getState() == KNOCKDOWN || damageGet <= 0) {
			return;
		}

		// sửa dame nhận
		float currHealth = Math.max(-1, getHealth() - damageGet);
		setHealth(currHealth);

		if (getHealth() <= 0) {
			setState(DEATH);
			return;
		}
		
		if (damageGet >= BIG_DAMAGE) {
			setState(KNOCKDOWN);
			startKnockdownTimer();
			return;
		}

		setState(BEHURT);

	}

	private static final int MARGIN = 2;

	@Override
	public void Update() {
//		if (getHealth() <= 0) {
//			setState(DEATH);
//		}
		
		updateDropState(System.nanoTime());

		Rectangle boundForCollisionWithLadder = getGameWorld().getMapGame().haveCollisionWithLadder(movingHitbox());
		if (boundForCollisionWithLadder != null) {
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

// Va chạm với object
//		CollisionResult bottomCollisionWithObject = getGameWorld().getObjectManager().BottomCollisionWithObject(this);
//		CollisionResult leftRightCollisionWithObject = getGameWorld().getObjectManager().LeftRightCollisionWithObject(this);
		CollisionResult bottomCollisionWithObject = null;
		CollisionResult topCollisionWithObject = null;
		CollisionResult leftCollisionWithObject = null;
		CollisionResult rightCollisionWithObject = null;

// Code update va chạm lại :>
		Rectangle boundForCollisionWithMapFuture;
		;

		boundForCollisionWithMapFuture = movingHitbox();
		boundForCollisionWithMapFuture.x += MARGIN * LEFT_DIR; // Math.max(getSpeedX() * LEFT_DIR, 1) * LEFT_DIR;
		CollisionResult hitBoxLeft = getGameWorld().getMapGame()
				.haveCollisionWithWallLeft(boundForCollisionWithMapFuture);

		boundForCollisionWithMapFuture = movingHitbox();
		boundForCollisionWithMapFuture.x += MARGIN * RIGHT_DIR; // Math.max(getSpeedX() * RIGHT_DIR, 1) * RIGHT_DIR;
		CollisionResult hitBoxRight = getGameWorld().getMapGame()
				.haveCollisionWithWallRight(boundForCollisionWithMapFuture);

		boundForCollisionWithMapFuture = movingHitbox();
		boundForCollisionWithMapFuture.y += Math.min(getSpeedY(), -1);
		CollisionResult hitBoxTop = getGameWorld().getMapGame().haveCollisionWithTop(boundForCollisionWithMapFuture);

		boundForCollisionWithMapFuture = movingHitbox();
		boundForCollisionWithMapFuture.y += Math.max(getSpeedY(), 1);
		CollisionResult hitBoxLand = getGameWorld().getMapGame().haveCollisionWithLand(boundForCollisionWithMapFuture);

		UpdateColiisionWithMapLeft(hitBoxLeft, leftCollisionWithObject);
		UpdateColiisionWithMapRight(hitBoxRight, rightCollisionWithObject);
		UpdateColiisionWithMapTop(hitBoxTop, topCollisionWithObject);
		UpdateColiisionWithMapLand(hitBoxLand, bottomCollisionWithObject);
		
//		boundForCollisionWithMapFuture = movingHitbox();
//		hitBoxLeft = getGameWorld().getMapGame().haveCollisionWithWallLeft(boundForCollisionWithMapFuture);
//		hitBoxRight = getGameWorld().getMapGame().haveCollisionWithWallRight(boundForCollisionWithMapFuture);
//		UpdateColiisionWithPlatformTile(hitBoxLeft, hitBoxRight);

		switch (getState()) {
		case FALL:
			if(isOnGround()) {
				beHurt(FALL_DAMAGE);
				setState(KNOCKDOWN);
			}
			stopRun();
			break;
			
		case KNOCKDOWN:
			if (System.nanoTime() - getTimeStartKnockDown() >= KNOCKDOWN_DURATION) {
				if(getHealth() > 0) {
					setState(NOBEHURT);
			        setNoBeHurtStart(System.nanoTime());
				}
		    }
			stopRun();
			break;

		case NOBEHURT:
			// code for running
			if (System.nanoTime() - getNoBeHurtStart() > NOBEHURT_DURATION) {
				setState(ALIVE);
				isPhasing = false;
			}
			break;

		case ALIVE:
			if(getSpeedY() >= FALL_SPEED) {
				setState(FALL);
				break;
			}
			
			UpdateColiisionWithMapForALIVE(hitBoxLeft, hitBoxRight, hitBoxTop, hitBoxLand);
			break;

		case BEHURT:
			setState(NOBEHURT);
			isPhasing = true;
			setNoBeHurtStart(System.nanoTime());
			
			if (getHealth() <= 0) {
				setState(DEATH);
			}

			break;

		case DEATH:
			// build death animation here
			stopRun();
			setClimbing(false);
			setIsDrop(true);
			break;

		default:
			break;
		}

	}
	
	private void UpdateColiisionWithMapForALIVE(CollisionResult left, CollisionResult right, CollisionResult top,
			CollisionResult land) {
		// Left ------------------------------------ Left
		// ----------------------------------------------
		switch (left.getCollisionWithTile()) {
			case MapGame.HAMMER_TILE:
				if (getSpeedX() * Object.LEFT_DIR > 0) {
					setPosX(left.getCollisionRect().x + left.getCollisionRect().width + getWidth() / 2 + 2);
				}
				break;
		}

		// Right -------------------------------------- Right
		// ---------------------------------------
		switch (right.getCollisionWithTile()) {

			case MapGame.HAMMER_TILE:
				if (getSpeedX() * Object.RIGHT_DIR > 0) {
					setPosX(right.getCollisionRect().x - getWidth() / 2 - 2);
				}
				break;
		}

		// Top -------------------------------------- Top
		// ----------------------------------------------
		switch (top.getCollisionWithTile()) {
			case MapGame.HAMMER_TILE:
				beHurt((int) MapObject.HAMMER_DAMAGE);
				break;
		}

		// Land -------------------------------------- Land
		// ----------------------------------------------

	}

	private void UpdateColiisionWithMapLeft(CollisionResult hitBox, CollisionResult collisionObject) {
		switch (hitBox.getCollisionWithTile()) {
			case MapGame.WALL_TILE:
			case MapGame.TRANSPORT_LEFT_TILE:
				if (getSpeedX() * Object.LEFT_DIR > 0) {
					setPosX(hitBox.getCollisionRect().x + hitBox.getCollisionRect().width + getWidth() / 2 + MARGIN);
				}
				break;
	
			case MapGame.PLATFORM_TILE:
				startDrop(System.nanoTime());
				break;
	
	//		case MapGame.HAMMER_TILE:
//				setPosX(getPosX() - getSpeedX());
	//			break;
		}
	}

	private void UpdateColiisionWithMapRight(CollisionResult hitBox, CollisionResult collisionObject) {
		switch (hitBox.getCollisionWithTile()) {
			case MapGame.WALL_TILE:
			case MapGame.TRANSPORT_LEFT_TILE:
				if (getSpeedX() * Object.RIGHT_DIR > 0) {
					setPosX(hitBox.getCollisionRect().x - getWidth() / 2 - MARGIN);
				}
				break;
	
			case MapGame.PLATFORM_TILE:
				startDrop(System.nanoTime());
				break;
			}
	}

	private void UpdateColiisionWithMapTop(CollisionResult hitBox, CollisionResult collisionObject) {
		switch (hitBox.getCollisionWithTile()) {
			case MapGame.WALL_TILE:
				setPosY(hitBox.getCollisionRect().y + 3 * getHeight() / 2);
				setSpeedY(0);
				break;
	
	//			case MapGame.HAMMER_TILE:
	//				beHurt((int)MapObject.HAMMER_DAMAGE);
	//				break;
			}
	}

	private void UpdateColiisionWithMapLand(CollisionResult hitBox, CollisionResult collisionObject) {
		setOnGround(false);

		if (isOnTransportLeft()) {
			setSpeedX(getSpeedX() - MapObject.TRANSPORT_SPEED * Object.LEFT_DIR);
			setOnTransportLeft(false);
		}

		switch (hitBox.getCollisionWithTile()) {
			case MapGame.TRANSPORT_LEFT_TILE:
				if (isClimbing()) {
					break;
				}
				setOnTransportLeft(true);
				setSpeedX(getSpeedX() + MapObject.TRANSPORT_SPEED * Object.LEFT_DIR);
	
			case MapGame.PLATFORM_TILE:
				if (getIsDrop()) {
					setSpeedY(getSpeedY() + getMass());
					break;
				}
	
			case MapGame.WALL_TILE:
				if (getSpeedY() > 0) {
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
				if (collisionObject != null && collisionObject.getCollisionWithTile() == KeyConfig.DOWN) {
					setOnGround(true);
					if (getSpeedY() > 0) {
						setSpeedY(0);
						setPosY(collisionObject.getObjectCollisionWith().getPosY()
								- (collisionObject.getObjectCollisionWith().getHeight() + getHeight()) / 2 + 1);
					}
					break;
				}
	
				if (isClimbing()) {
					break;
				}
	
				setSpeedY(getSpeedY() + getMass());
				break;
		}
	}
}
