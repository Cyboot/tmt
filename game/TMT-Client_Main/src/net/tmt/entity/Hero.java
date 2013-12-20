package net.tmt.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.tmt.entity.pickups.BackPack;
import net.tmt.entityComponents.move.MoveComponent;
import net.tmt.entityComponents.move.PhysicsComponent;
import net.tmt.entityComponents.move.PhysicsComponent.Builder;
import net.tmt.entityComponents.move.RotateComponent;
import net.tmt.entityComponents.other.AnimatedRenderComponent;
import net.tmt.entityComponents.other.PickUpComponent;
import net.tmt.game.Controls;
import net.tmt.game.interfaces.Playable;
import net.tmt.game.interfaces.UserableByHolder;
import net.tmt.game.manager.CollisionManager;
import net.tmt.game.manager.EntityManager;
import net.tmt.gfx.Graphics;
import net.tmt.map.World;
import net.tmt.util.CountdownTimer;
import net.tmt.util.MathUtil;
import net.tmt.util.SpriteAnimation;
import net.tmt.util.Vector2d;

public class Hero extends Entity2D implements Playable {

	private AnimatedRenderComponent	aniRenCom;
	private boolean					sprinting			= false;
	private boolean					catchingBreath		= false;
	private Entity2D				holding;
	private List<Entity2D>			wearing				= new ArrayList<Entity2D>();
	private CountdownTimer			sprintingTimer		= CountdownTimer.createManualResetTimer(5);
	private CountdownTimer			catchBreathTimer	= CountdownTimer.createManualResetTimer(15);
	private CountdownTimer			uppackThrowTimer	= CountdownTimer.createManualResetTimer(0.2);
	private boolean					isControlled		= true;


	public Hero(final CollisionManager collisionsManager, final Vector2d pos) {
		super(pos);
		catchBreathTimer.setTimer(-1);
		removeAllComponents();
		Map<String, SpriteAnimation> aniMap = new HashMap<String, SpriteAnimation>();
		SpriteAnimation walk = new SpriteAnimation(new String[] { "hero_walk_0", "hero_walk_1" }, 0.25);
		SpriteAnimation walk_hold = new SpriteAnimation(new String[] { "hero_walk_hold_0", "hero_walk_hold_1" }, 0.25);
		SpriteAnimation stand = new SpriteAnimation(new String[] { "hero_stand" }, Float.MAX_VALUE);
		SpriteAnimation stand_hold = new SpriteAnimation(new String[] { "hero_stand_hold" }, Float.MAX_VALUE);
		aniMap.put("walk", walk);
		aniMap.put("walk_hold", walk_hold);
		aniMap.put("stand", stand);
		aniMap.put("stand_hold", stand_hold);
		aniRenCom = new AnimatedRenderComponent(aniMap, "stand");
		addComponent(aniRenCom);

		// addComponent(new RotateComponent(0, ROTATION_SPEED));
		Builder builder = new Builder(collisionsManager, pos);
		builder.circleShape(MathUtil.toBox2d(12));
		builder.accl(25).maxSpeed(10).friction(5).density(0.1f).setCategory(CollisionManager.CATEGORY_PLAYABLE);

		PhysicsComponent physicsComponent = builder.create();
		addComponent(physicsComponent);
	}

	@Override
	public void update(final EntityManager caller, final World world, final double delta) {
		if (isControlled) {
			checkControls(delta);
		} else {
			// disable physics if not controlled
			dispatchValue(PhysicsComponent.DISABLE, true);
		}
		setAnimation();
		super.update(caller, world, delta);
	}

	@Override
	public void render(final Graphics g) {
		// DEBUG: don't render hero if not controlled
		if (isControlled)
			super.render(g);
	}

	private void checkControls(final double delta) {
		// dispatchValue(MoveComponent.SPEED, 0.);

		if (Controls.pressed(Controls.HERO_UP))
			dispatchValue(PhysicsComponent.PHYS_ACCELERATING, true);
		if (Controls.pressed(Controls.HERO_DOWN))
			dispatchValue(PhysicsComponent.PHYS_ACCELERATING_REVERSE, true);
		if (Controls.pressed(Controls.HERO_LEFT))
			dispatchValue(RotateComponent.IS_ROTATE_LEFT, true);
		if (Controls.pressed(Controls.HERO_RIGHT))
			dispatchValue(RotateComponent.IS_ROTATE_RIGHT, true);
		if (Controls.pressed(Controls.HERO_SPRINT)) {
			chekSprinting(delta);
			dispatchValue(PhysicsComponent.PHYS_ACCL_FACTOR, 2.);
		} else {
			notSprinting();
		}
		if (Controls.pressed(Controls.HERO_PACK))
			packHoldingItem();
		if (Controls.pressed(Controls.HERO_UNPACK))
			unpackItem(delta);

		if (Controls.pressed(Controls.MOUSE_LEFT) && !Controls.pressed(Controls.MOUSE_RIGHT)) {
			if (holding != null && holding instanceof UserableByHolder) {
				((UserableByHolder) holding).action1();
			}
		}
		if (Controls.pressed(Controls.MOUSE_RIGHT) && !Controls.pressed(Controls.MOUSE_LEFT)) {
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
		double speed = ((double) getValue(MoveComponent.SPEED));
		String ws, h;
		ws = (speed > 3 ? "walk" : "stand");
		h = (holding != null ? "_hold" : "");
		aniRenCom.changeAnimation(ws + h);
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

	public List<Entity2D> getWearing() {
		return wearing;
	}

	public boolean holdsSomething() {
		return !(holding == null);
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
