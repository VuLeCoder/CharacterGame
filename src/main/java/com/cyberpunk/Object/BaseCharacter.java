package com.cyberpunk.Object;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.cyberpunk.Effect.Animation;
import com.cyberpunk.Effect.DataLoader;

public class BaseCharacter extends HumanObject{
	public String name;
	
	public static final float JUMP_STRENG = -3.0f;
	
	public boolean isRunning;
	private boolean isAttacking;
	private boolean isFirstAttack, isSecondAttack, isThirdAttack;
	
	//sau sẽ đưa về 1 attackAnimation
	private Animation attack1ForwardAnim, attack1BackAnim, attack2ForwardAnim, attack2BackAnim;
	private Animation attack3ForwardAnim, attack3BackAnim;
	
	private Animation deathForwardAnim, deathBackAnim;
	
	// Hmmm, cầm súng vẫn đung đưa
	private Animation idleForwardAnim, idleBackAnim, idleSkillForwardAnim, idleSkillBackAnim;
	
	private Animation climdAnim, djumpForwardAnim, djumpBackAnim;
	
	private Animation beHurtForwardAnim, beHurtBackAnim; 
	private Animation jumpForwardAnim, jumpBackAnim, jumpSkillForwardAnim, jumpSkillBackAnim;
	
	// Hmm có khi không cần
	private Animation punchForwardAnim, punchBackAnim;
	
	private Animation walkSkillForwardAnim, walkSkillBackAnim;
	private Animation runForwardAnim, runBackAnim;
	
	private Animation runAttackForwardAnim, runAttackBackAnim, runSkillForwardAnim, runSkillBackAnim;
	
	private Animation sitdownSkillForwardAnim, sitdownSkillBackAnim;
	
	private Animation handForwardAnim, handBackAnim;

	public BaseCharacter(float x, float y, String name, GameWorld gameWorld) {
		super(x, y, gameWorld);
		this.name = name;
		isRunning = false;

		attack1ForwardAnim = DataLoader.getInstance().getAnimation(name + "attack1");
		attack1BackAnim = DataLoader.getInstance().getAnimation(name + "attack1");
		attack1BackAnim.flipAllImage();
		
		setHeight(attack1ForwardAnim.getCurrentFrameImages().getImageHeight());
		setWidth(attack1ForwardAnim.getCurrentFrameImages().getImageWidth());
		
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
		setSpeedY(JUMP_STRENG);
		
//		if(isOnGround()) {
//			setSingleJumping(false);
//			setDoubleJumping(false);
//		}
//		
//		if(!isSingleJumping() && !isDoubleJumping()) {
//			setSingleJumping(true);
//			setSpeedY(-jumpStrength);
//			
//			jumpForwardAnim.reset();
//			jumpBackAnim.reset();
//			jumpSkillForwardAnim.reset();
//			jumpSkillBackAnim.reset();
//			djumpForwardAnim.reset();
//			djumpBackAnim.reset();
//
//			jumpForwardAnim.setIgnoreFrame(3);
//			jumpBackAnim.setIgnoreFrame(3);
//			jumpSkillForwardAnim.setIgnoreFrame(3);
//			jumpSkillBackAnim.setIgnoreFrame(3);
//			djumpForwardAnim.setIgnoreFrame(5);
//			djumpBackAnim.setIgnoreFrame(5);
//			
//		} else if(!isDoubleJumping()) {
//			setSingleJumping(false);
//			setDoubleJumping(true);
//			setSpeedY(-jumpStrength);
//		}
	}

	@Override
	public void run() {
		if(!isSitting() && !isRunning) {
			isRunning = true;
			setSpeedX(getSpeedX() + HUMAN_RUN_SPEED * getDirection());
		}
	}

	@Override
	public void stopRun() {
		if(isRunning) {
		isRunning = false;
		setSpeedX(getSpeedX() - HUMAN_RUN_SPEED * getDirection());
		
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
		isSecondAttack = false;
		isThirdAttack = false;
	}
	
	@Override
	public void Update() {
		super.Update();
		attack1ForwardAnim.Update(System.nanoTime());
	}
	
	@Override
	public void draw(Graphics2D g2) {
		Rectangle rect = movingHitbox();
		g2.setColor(Color.BLUE);
		g2.drawRect(rect.x, rect.y, rect.width, rect.height);
		attack1ForwardAnim.draw((int)getPosX(), (int)getPosY(), g2);
	}
	
}









