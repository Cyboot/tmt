package net.tmt.entity.weapons;

import net.tmt.entity.Entity2D;
import net.tmt.entityComponents.collision.CollisionComponent;
import net.tmt.entityComponents.move.MoveComponent;
import net.tmt.entityComponents.other.PickUpComponent;
import net.tmt.game.Controls;
import net.tmt.game.interfaces.UserableByHolder;
import net.tmt.game.manager.EntityManager;
import net.tmt.gfx.Graphics;
import net.tmt.gfx.Sprite;
import net.tmt.map.World;
import net.tmt.util.CountdownTimer;
import net.tmt.util.Vector2d;

import org.lwjgl.util.Color;

public class Weapon extends Entity2D implements UserableByHolder {

	CountdownTimer	timerShoot			= new CountdownTimer(0.2);

	boolean			isAction1Performed	= false;
	boolean			isAction2Performed	= false;
	boolean			isAction3Performed	= false;

	boolean			isSpriteChanged		= false;

	public Weapon(final Vector2d pos) {
		super(pos);
		addComponent(new CollisionComponent(10));
		addComponent(new PickUpComponent(new Vector2d(3, -15), false));
		setSprite(new Sprite("ak47"));
	}

	@Override
	public void update(final EntityManager caller, final World world, final double delta) {
		super.update(caller, world, delta);


		if (isAction1Performed && !Controls.pressed(Controls.MOUSE_LEFT)) {
			caller.addEntity(new LaserShoot(pos.copy(), (double) getValue(MoveComponent.ROTATION_ANGLE_LOOK),
					Color.ORANGE, (Entity2D) getValue(PickUpComponent.ITEM_HOLDER)));
			setSprite(new Sprite("ak47_fire"));
			isSpriteChanged = true;
			isAction1Performed = false;
		}
		if (timerShoot.isTimeUp(delta) && isAction2Performed) {
			caller.addEntity(new LaserShoot(pos.copy(), (double) getValue(MoveComponent.ROTATION_ANGLE_LOOK),
					Color.CYAN, (Entity2D) getValue(PickUpComponent.ITEM_HOLDER)));
			setSprite(new Sprite("ak47_fire"));
			isSpriteChanged = true;
			isAction2Performed = false;
		}

		if (isAction3Performed) {
			setSprite(new Sprite("ak47_fire"));
			isSpriteChanged = true;
			isAction3Performed = false;
		}


	}

	@Override
	public void render(final Graphics g) {
		super.render(g);
		if (isSpriteChanged) {
			setSprite(new Sprite("ak47"));
			isSpriteChanged = false;
		}
	}

	@Override
	public void action1() {
		isAction1Performed = true;
	}

	@Override
	public void action2() {
		isAction2Performed = true;

	}

	@Override
	public void action3() {
		isAction3Performed = true;

	}
}
