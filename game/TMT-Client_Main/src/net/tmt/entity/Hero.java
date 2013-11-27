package net.tmt.entity;

import java.util.ArrayList;
import java.util.List;

import net.tmt.entity.component.collision.CollisionComponent;
import net.tmt.entity.component.move.MoveComponent;
import net.tmt.entity.component.move.RotateComponent;
import net.tmt.entity.component.other.AnimatedRenderComponent;
import net.tmt.game.Controls;
import net.tmt.game.factory.ComponentFactory;
import net.tmt.game.interfaces.Playable;
import net.tmt.game.manager.EntityManager;
import net.tmt.gfx.Sprite;
import net.tmt.util.CountdownTimer;
import net.tmt.util.Vector2d;

public class Hero extends Entity2D implements Playable {

	private double					speed				= 128;
	private final double			ROTATION_SPEED		= 180;
	private AnimatedRenderComponent	aniRenCom;
	private boolean					sprinting			= false;
	private boolean					catchingBreath		= false;
	private boolean					holdingSomething	= false;
	private CountdownTimer			sprintingTimer		= CountdownTimer.createManualResetTimer(5);
	private CountdownTimer			catchBreathTimer	= CountdownTimer.createManualResetTimer(15);

	public Hero(final Vector2d pos) {
		super(pos);
		catchBreathTimer.setTimer(-1);
		removeAllComponents();
		List<Sprite> aniSprites = new ArrayList<Sprite>();
		aniSprites.add(new Sprite("hero_walk_0"));
		aniSprites.add(new Sprite("hero_walk_1"));
		aniRenCom = new AnimatedRenderComponent(aniSprites, 0.25);
		aniRenCom.setPauseFrame(new Sprite("hero_stand"));
		addComponent(aniRenCom);
		ComponentFactory.addDefaultMove(this, 0, 0, ROTATION_SPEED);
		addComponent(new CollisionComponent(10));
	}

	@Override
	public void update(final EntityManager caller, final double delta) {
		// FIXME: MoveComponent's speed is always 0?
		double s = ((double) getValue(MoveComponent.SPEED));;

		boolean movingF = true;
		boolean movingB = true;

		if (Controls.pressed(Controls.HERO_UP))
			dispatchValue(MoveComponent.SPEED, speed);
		else
			movingF = false;
		if (Controls.pressed(Controls.HERO_DOWN))
			dispatchValue(MoveComponent.SPEED, -speed);
		else
			movingB = false;
		if (Controls.pressed(Controls.HERO_LEFT))
			dispatchValue(RotateComponent.IS_ROTATE_LEFT, true);
		if (Controls.pressed(Controls.HERO_RIGHT))
			dispatchValue(RotateComponent.IS_ROTATE_RIGHT, true);
		if (Controls.pressed(Controls.HERO_SPRINT)) {
			chekSprinting(delta);
		} else {
			notSprinting();
		}
		speed = (sprinting ? 256 : 128);

		checkHolding();

		if (movingF || movingB)
			aniRenCom.resumeAnimation();
		else
			aniRenCom.pauseAnimation();

		super.update(caller, delta);
	}

	private void chekSprinting(final double delta) {
		if (!sprinting) {
			sprintingTimer.setTimer(5);
		}
		if (!sprintingTimer.isTimeUp(delta) && catchBreathTimer.isTimeUp(delta)) {
			sprinting = true;
		} else {
			if (!catchingBreath) {
				catchBreathTimer.setTimer(20);
				catchingBreath = true;
			}
			notSprinting();
		}
		if (catchingBreath && catchBreathTimer.isTimeUp(delta)) {
			catchingBreath = false;
			catchBreathTimer.setTimer(-1);
			sprintingTimer.reset();
		}
	}

	private void notSprinting() {
		sprinting = false;
	}

	public void setHoldingSth(final boolean h) {
		holdingSomething = h;
	}

	private void checkHolding() {
		if (holdingSomething) {
			List<Sprite> aniSprites = new ArrayList<Sprite>();
			aniSprites.add(new Sprite("hero_walk_hold_0"));
			aniSprites.add(new Sprite("hero_walk_hold_1"));
			aniRenCom.setAnimationFrames(aniSprites);
			aniRenCom.setPauseFrame(new Sprite("hero_stand_hold"));
		} else {
			List<Sprite> aniSprites = new ArrayList<Sprite>();
			aniSprites.add(new Sprite("hero_walk_0"));
			aniSprites.add(new Sprite("hero_walk_1"));
			aniRenCom.setAnimationFrames(aniSprites);
			aniRenCom.setPauseFrame(new Sprite("hero_stand"));
		}
	}

}
