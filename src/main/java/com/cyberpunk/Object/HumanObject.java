package com.cyberpunk.Object;

import java.awt.Rectangle;

import com.cyberpunk.StartGame.GameFrame;

public abstract class HumanObject extends Object{
	public static final float HUMAN_RUN_SPEED = 5;
	public static final float HUMAN_WALK_SPEED = 2.5f;
	public static final float HUMAN_MASS = 0.2f;
	
	private boolean isRunning;
	private boolean isSitting;
	private boolean isClimbing;
	private boolean isDoubleJumping;
	private boolean isSingleJumping;
	private boolean isLanding;
	private boolean isOnGround;
	private boolean isPhasing;
	
	private long noBeHurtDuration = 1000000000L;
	private long noBeHurtStart;
	
//	private Animation hurtForwardAni, hurtBackAni;
	
	public HumanObject(float x, float y, GameWorld gameWorld) {
		super(x, y, 100, 10, 24, 34, gameWorld);
		
		//set temp direction, team type
		if(x > GameFrame.SCREEN_WIDTH/2) {
			 setDirection(LEFT_DIR);
			 setTeamType(P2_TEAM);
		} else {
			 setDirection(RIGHT_DIR);
			 setTeamType(P1_TEAM);
		}
		
		setMass(HUMAN_MASS);
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

	public boolean isRunning() {
		return isRunning;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
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
	
	public abstract void jump();
	public abstract void run();
	public abstract void stopRun();
	public abstract void climb(float speed);
	public abstract void sitDown();
	public abstract void standUp();
	public abstract void attack();
	public abstract void stopAttack();
	public abstract void beHeal(float healedGet);
	@Override
	public Rectangle attackHitbox() {
		Rectangle rect = movingHitbox();

		if(isSitting) {
			rect.x = (int) getPosX() - 12;
			rect.y = (int) getPosY() - 10;
			rect.width = 24;
			rect.height = 27;
		} else {
			rect.x = (int) getPosX() - 12;
			rect.y = (int) getPosY() - 17;
			rect.width = 24;
			rect.height = 34;
		}
		
//		if(getDirection() == LEFT_DIR && rect.x < getPosX() + getWidth()/2) rect.x += getWidth();
		
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
			if(System.nanoTime() - noBeHurtStart > noBeHurtDuration) {
				setState(ALIVE);
				isPhasing = false;
			}
//			run();
			break;
		
		case DEATH:
			//build death animation here
			break;
		default:
			break;
		}
		
		if(getState() == ALIVE || getState() == NOBEHURT || getState() == DEATH) {
			//code for checking collision around character
			if(!isClimbing) {
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
				
				setPosX(getPosX() + getSpeedX());
			}
			else {
				setPosY(getPosY() + getSpeedY());
			}
			
////			if(!isPhasing) {
//				
//				Rectangle rectRightWall = getGameWorld().getMapGame().haveCollisionWithWallRight(movingHitbox());
//				Rectangle rectLeftWall = getGameWorld().getMapGame().haveCollisionWithWallLeft(movingHitbox());
//				
//				if(rectRightWall != null)
//					setPosX(rectRightWall.x - getWidth()/2);
//				
//				if(rectLeftWall != null)
//					setPosX(rectLeftWall.x + rectLeftWall.width + getWidth()/2 + 10);
////			}
//		
//		
//			Rectangle movingHitboxFuture = movingHitbox();
//			movingHitboxFuture.y += getSpeedY() < 0 ? getSpeedY() : 2;
//			
//			SimpleEntry<Rectangle, Integer> rectLand = getGameWorld().getMapGame().haveCollisionWithLand(movingHitboxFuture);
//			Rectangle rectTop = getGameWorld().getMapGame().haveCollisionWithTop(movingHitboxFuture, this);
//			
//			if(rectLand != null) {
//				setSpeedY(0);
//				setPosY(rectLand.getKey().y - getHeight()/2);
//				isOnGround = true;
//				isDoubleJumping = false;
//				isSingleJumping = false;
//				isLanding = false;
//				
//				if(rectLand.getValue() == MapGame.TRANSPORT_LEFT_TILE) {
//					setSpeedX(getSpeedX() - AnimatedObject.TRANSPORT_SPEED);
//				}
//			} else {
//				isOnGround = false;
//				setPosY(getPosY() + getSpeedY());
//				setSpeedY(getSpeedY() + getMass());
//			}
//			
//			if(rectTop != null) {
//				setSpeedY(0);
//				setPosY(rectTop.y + rectTop.height + getHeight()/2);
//			}
//			if(getSpeedY() > 0) isLanding = true;
		}
	}
	
}