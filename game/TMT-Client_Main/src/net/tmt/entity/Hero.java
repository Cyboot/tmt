package net.tmt.entity;

import java.util.ArrayList;
import java.util.List;

import net.tmt.entity.pickups.BackPack;
import net.tmt.entityComponents.move.MoveComponent;
import net.tmt.entityComponents.move.RotateComponent;
import net.tmt.entityComponents.other.AnimatedRenderComponent;
import net.tmt.entityComponents.other.PickUpComponent;
import net.tmt.game.Controls;
import net.tmt.game.factory.ComponentFactory;
import net.tmt.game.interfaces.Playable;
import net.tmt.game.interfaces.UserableByHolder;
import net.tmt.game.manager.EntityManager;
import net.tmt.game.manager.ZoomManager;
import net.tmt.gfx.Graphics;
import net.tmt.gfx.Sprite;
import net.tmt.map.World;
import net.tmt.util.CountdownTimer;
import net.tmt.util.Vector2d;

public class Hero extends Entity2D implements Playable {

	private double					speed				= 128;
	private final double			ROTATION_SPEED		= 180;
	private AnimatedRenderComponent	aniRenCom;
	private boolean					sprinting			= false;
	private boolean					catchingBreath		= false;
	private Entity2D				holding;
	private List<Entity2D>			wearing				= new ArrayList<Entity2D>();
	private CountdownTimer			sprintingTimer		= CountdownTimer.createManualResetTimer(5);
	private CountdownTimer			catchBreathTimer	= CountdownTimer.createManualResetTimer(15);
	private CountdownTimer			uppackThrowTimer	= CountdownTimer.createManualResetTimer(0.2);
	private boolean					isControlled		= true;


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
		ComponentFactory.addDefaultCollision(this, 10, 999999);
	}

	@Override
	public void update(final EntityManager caller, final World world, final double delta) {
		if (isControlled) {
			checkControls(delta);
			checkHolding();
		}
		setAnimation();
		super.update(caller, world, delta);

		if (isControlled)
			ZoomManager.setFreeZoomBySpeed((double) getValue(MoveComponent.SPEED));
	}

	@Override
	public void render(final Graphics g) {
		// DEBUG: don't render hero if not controlled
		if (isControlled)
			super.render(g);
	}

	private void checkControls(final double delta) {
		dispatchValue(MoveComponent.SPEED, 0.);

		if (Controls.pressed(Controls.HERO_UP))
			dispatchValue(MoveComponent.SPEED, speed);

		if (Controls.pressed(Controls.HERO_DOWN))
			dispatchValue(MoveComponent.SPEED, -speed);


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
		if (Controls.pressed(Controls.HERO_PACK))
			packHoldingItem();
		if (Controls.pressed(Controls.HERO_UNPACK))
			unpackItem(delta);


		if (Controls.pressed(Controls.MOUSE_RIGHT)) {
			if (holding != null && holding instanceof UserableByHolder) {
				((UserableByHolder) holding).action2();
			}
		}

		if (Controls.pressed(Controls.MOUSE_MIDDLE)) {
			if (holding != null && holding instanceof UserableByHolder) {
				((UserableByHolder) holding).action3();
			}
		}

	}

	private void packHoldingItem() {
		if (holding == null)
			return;
		BackPack bp = null;
		for (Entity2D e : wearing) {
			if (e instanceof BackPack)
				bp = (BackPack) e;
		}
		if (bp == null || bp.isFull())
			return;
		bp.packItem(holding);
		holding.getComponent(PickUpComponent.class).getPacked();
		holding = null;
	}


	private void unpackItem(final double delta) {
		if (holding != null) {
			if (uppackThrowTimer.isTimeUp(delta))
				throwHoldingItem();
			return;
		}
		BackPack bp = null;
		for (Entity2D e : wearing) {
			if (e instanceof BackPack)
				bp = (BackPack) e;
		}
		if (bp == null || bp.getItemCount() < 1)
			return;
		holding = bp.unPackNext();
		holding.getComponent(PickUpComponent.class).getUnPacked();
		uppackThrowTimer.reset();
	}

	private void throwHoldingItem() {
		holding.getComponent(PickUpComponent.class).getThrown();
		holding = null;
	}

	private void setAnimation() {
		double s = ((double) getValue(MoveComponent.SPEED));

		if (s != 0)
			aniRenCom.resumeAnimation();
		else
			aniRenCom.pauseAnimation();
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

	public void hold(final Entity2D e) {
		holding = e;

	}

	public void wear(final Entity2D e) {
		wearing.add(e);
	}

	public boolean holdsSomething() {
		return !(holding == null);
	}

	private void checkHolding() {
		if (holding != null) {
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

	@Override
	public void disableControls() {
		isControlled = false;
	}

	@Override
	public void enableControls() {
		isControlled = true;
	}

}
