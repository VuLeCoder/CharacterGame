package com.cyberpunk.Object;

import java.awt.Graphics2D;

import com.cyberpunk.Effect.Animation;
import com.cyberpunk.Effect.DataLoader;

public class BaseCharacter extends HumanObject{
	public String name;
	
	private float jumpStrength = 5.0f;
	
	private boolean isAttacking;
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
		deathBackAnim = DataLoader.getInstance().getAnimation(name + "death");
		deathBackAnim.flipAllImage();
		
		idleForwardAnim = DataLoader.getInstance().getAnimation(name + "idle");
		idleBackAnim = DataLoader.getInstance().getAnimation(name + "idle");
		idleBackAnim.flipAllImage();
		
		idleSkillForwardAnim = DataLoader.getInstance().getAnimation(name + "idleskill");
		idleSkillBackAnim = DataLoader.getInstance().getAnimation(name + "idleskill");
		idleSkillBackAnim.flipAllImage();
		
		djumpForwardAnim = DataLoader.getInstance().getAnimation(name + "doublejump");
		djumpForwardAnim.setRepeated(false); //jump once, wait until landing
		djumpForwardAnim.setIgnoreFrame(3); //til landing, wait until on ground
		djumpBackAnim = DataLoader.getInstance().getAnimation(name + "doublejump");
		djumpBackAnim.setRepeated(false); //same above
		djumpBackAnim.setIgnoreFrame(3); //same above
		djumpBackAnim.flipAllImage();
		
		jumpForwardAnim = DataLoader.getInstance().getAnimation(name + "jump");
		jumpForwardAnim.setRepeated(false); //jump once, wait until landing
		jumpForwardAnim.setIgnoreFrame(3); //til landing, wait until on ground
		jumpBackAnim = DataLoader.getInstance().getAnimation(name + "jump");
		jumpBackAnim.setRepeated(false); //same above
		jumpBackAnim.setIgnoreFrame(3); //same above
		jumpBackAnim.flipAllImage();
		
		jumpSkillForwardAnim = DataLoader.getInstance().getAnimation(name + "jumpskill");
		jumpSkillForwardAnim.setRepeated(false); //jump once, wait until landing
		jumpSkillForwardAnim.setIgnoreFrame(3); //til landing, wait until on ground
		jumpSkillBackAnim = DataLoader.getInstance().getAnimation(name + "jumpskill");
		jumpSkillBackAnim.setRepeated(false); //same above
		jumpSkillBackAnim.setIgnoreFrame(3); //same above
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
		
		if(isOnGround()) {
			setSingleJumping(false);
			setDoubleJumping(false);
		}
		
		if(!isSingleJumping() && !isDoubleJumping()) {
			setSingleJumping(true);
			setSpeedY(-jumpStrength);
			
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
			
		} else if(!isDoubleJumping()) {
			setSingleJumping(false);
			setDoubleJumping(true);
			setSpeedY(-jumpStrength);
		}
	}

	@Override
	public void run() {
		if(!isSitting()) {
			if(getDirection() == LEFT_DIR) setSpeedX(-5);
			else setSpeedX(5);
		}
	}

	@Override
	public void stopRun() {
		setSpeedX(0);
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

	@Override
	public void sitDown() {
		if(!isSingleJumping() && !isDoubleJumping() && !isLanding() && !isSitting()) {
			setSitting(true);
			sitdownSkillForwardAnim.reset();
			sitdownSkillBackAnim.reset();
			sitdownSkillForwardAnim.setIgnoreFrame(3);
			sitdownSkillBackAnim.setIgnoreFrame(3);
		}
		
	}

	@Override
	public void standUp() {
		idleForwardAnim.reset();
		idleBackAnim.reset();
		sitdownSkillForwardAnim.unIgnoreFrame(3);
		sitdownSkillBackAnim.unIgnoreFrame(3);
		
	}

	@Override
	public void attack() {
		
		isAttacking = true;
		if(!isFirstAttack && !isSecondAttack && !isThirdAttack && !isSitting() && !isDoubleJumping()) {
			isFirstAttack = true;
//			attack1ForwardAnim.reset();
//			attack1BackAnim.reset();
			attack2ForwardAnim.reset();
			attack2BackAnim.reset();
			attack3ForwardAnim.reset();
			attack3BackAnim.reset();
		} else if(!isSecondAttack && (attack1ForwardAnim.getCurrentFrame() == 3 || attack1BackAnim.getCurrentFrame() == 3)) {
			isSecondAttack = true;
			isFirstAttack = false;
			attack2ForwardAnim.setCurrentFrame(attack1ForwardAnim.getCurrentFrame());
			attack2BackAnim.setCurrentFrame(attack1BackAnim.getCurrentFrame());
			attack1ForwardAnim.reset();
			attack1BackAnim.reset();
		} else if(!isThirdAttack && (attack2ForwardAnim.isLastFrame() || attack2BackAnim.isLastFrame())) {
			isSecondAttack = false;
			isThirdAttack = true;
		} else if(attack3ForwardAnim.isLastFrame() || attack3BackAnim.isLastFrame()) {
			isThirdAttack = false;
//			isFirstAttack = false;
		}
		
		if(isFirstAttack) {
			
		} else if(isSecondAttack) {
			
		}
			
	
	}
	
	@Override
	public void stopAttack() {
//		isAttacking = false;
		
		isSecondAttack = false;
		isThirdAttack = false;
	}
	
	@Override
	public void Update() {
		super.Update();
		
		if(isLanding()) {
			if(getPosY() >= 498) {
				jumpForwardAnim.unIgnoreFrame(3);
				jumpBackAnim.unIgnoreFrame(3);
				jumpSkillForwardAnim.unIgnoreFrame(3);
				jumpSkillBackAnim.unIgnoreFrame(3);
				djumpForwardAnim.unIgnoreFrame(5);
				djumpBackAnim.unIgnoreFrame(5);
			}
			
		}
		
		if(sitdownSkillBackAnim.isLastFrame() || sitdownSkillForwardAnim.isLastFrame()) {
			setSitting(false);
		}
		
		if(jumpForwardAnim.getCurrentFrame() > jumpBackAnim.getCurrentFrame())
		  jumpBackAnim.setCurrentFrame(jumpForwardAnim.getCurrentFrame());
		else jumpForwardAnim.setCurrentFrame(jumpBackAnim.getCurrentFrame());
		
		if(sitdownSkillForwardAnim.getCurrentFrame() > sitdownSkillBackAnim.getCurrentFrame())
			  sitdownSkillBackAnim.setCurrentFrame(sitdownSkillForwardAnim.getCurrentFrame());
			else sitdownSkillForwardAnim.setCurrentFrame(sitdownSkillBackAnim.getCurrentFrame());
		
		if(attack1ForwardAnim.isLastFrame() || attack1BackAnim.isLastFrame()) {
			isAttacking = false;
			isFirstAttack = false;
		}
		System.out.println(attack1ForwardAnim.getCurrentFrame());
	}
	
	@Override
	public void draw(Graphics2D g2) {
		
		switch (getState()) {
		case ALIVE:
		case NOBEHURT:
			if(isSitting()) {
				
				if(getDirection() == RIGHT_DIR) {
					sitdownSkillForwardAnim.Update(System.nanoTime());
					sitdownSkillForwardAnim.draw((int) getPosX(),(int)  getPosY(), g2);
				} else {
					sitdownSkillBackAnim.Update(System.nanoTime());
					sitdownSkillBackAnim.draw((int) getPosX(),(int)  getPosY(), g2);
				}
			} else if(isSingleJumping()) {
				
				if(getDirection() == RIGHT_DIR) {
					jumpForwardAnim.Update(System.nanoTime());
					jumpForwardAnim.draw((int) getPosX(), (int) getPosY(), g2);
				} else {
					jumpBackAnim.Update(System.nanoTime());
					jumpBackAnim.draw((int) getPosX(), (int) getPosY(), g2);
				}
			} else if(isDoubleJumping()) {
				if(getDirection() == RIGHT_DIR) {
					djumpForwardAnim.Update(System.nanoTime());
					djumpForwardAnim.draw((int) getPosX(), (int) getPosY(), g2);
				} else {
					djumpBackAnim.Update(System.nanoTime());
					djumpBackAnim.draw((int) getPosX(), (int) getPosY(), g2);
				}
			} else if(isLanding()) {
				jumpForwardAnim.setCurrentFrame(2);
				jumpBackAnim.setCurrentFrame(2);
				if(getDirection() == RIGHT_DIR) 
					jumpForwardAnim.draw((int) getPosX(), (int) getPosY(), g2);
				else jumpBackAnim.draw((int) getPosX(), (int) getPosY(), g2);
				
			} else if(isAttacking) {
				if(isFirstAttack) {
					if(getDirection() == RIGHT_DIR) {
						attack1ForwardAnim.Update(System.nanoTime());
						attack1ForwardAnim.draw((int) getPosX(), (int) getPosY(), g2);
					} else {
						attack1BackAnim.Update(System.nanoTime());
						attack1BackAnim.draw((int) getPosX(), (int) getPosY(), g2);
					}
				} else if(isSecondAttack) {
					if(getDirection() == RIGHT_DIR) {
						attack2ForwardAnim.Update(System.nanoTime());
						attack2ForwardAnim.draw((int) getPosX(), (int) getPosY(), g2);
					} else {
						attack2BackAnim.Update(System.nanoTime());
						attack2BackAnim.draw((int) getPosX(), (int) getPosY(), g2);
					}
				} else if(isThirdAttack) {
					if(getDirection() == RIGHT_DIR) {
						attack3ForwardAnim.Update(System.nanoTime());
						attack3ForwardAnim.draw((int) getPosX(), (int) getPosY(), g2);
					} else {
						attack3BackAnim.Update(System.nanoTime());
						attack3BackAnim.draw((int) getPosX(), (int) getPosY(), g2);
					}
				}
			
			} else if(isOnGround()) {
				
				if(getSpeedX() > 0) {
					runForwardAnim.Update(System.nanoTime());
					runForwardAnim.draw((int) getPosX(), (int) getPosY(), g2);
				} else if(getSpeedX() < 0) {
					runBackAnim.Update(System.nanoTime());
					runBackAnim.draw((int) getPosX(), (int) getPosY(), g2);
				} else {
					if(getDirection() == RIGHT_DIR) {
						idleForwardAnim.Update(System.nanoTime());
						idleForwardAnim.draw((int) getPosX(), (int) getPosY(), g2);
					} else {
						idleBackAnim.Update(System.nanoTime());
						idleBackAnim.draw((int) getPosX(), (int) getPosY(), g2);
					}
				}
			}
			
			break;

		default:
			break;
		}
		
		drawAttackHitbox(g2);
		drawMovingHitbox(g2);
	}
	
}
