package net.tmt.entity;

import net.tmt.entity.component.move.RotateComponent;
import net.tmt.game.factory.ComponentFactory;
import net.tmt.game.manager.EntityManager;
import net.tmt.gfx.Sprite;
import net.tmt.map.World;
import net.tmt.util.RandomUtil;
import net.tmt.util.Vector2d;

public class Asteroid extends Entity2D {
	private int		size	= 64;
	private boolean	rotateLeft;

	public Asteroid(final Vector2d pos) {
		super(pos);

		setSprite(new Sprite("asteroid_64", size, size));
		ComponentFactory.addDefaultMove(this, RandomUtil.intRange(0, 359), RandomUtil.doubleRange(1, 5),
				RandomUtil.doubleRange(1, 20));
		ComponentFactory.addDefaultCollision(this, size / 2, 50);

		rotateLeft = RandomUtil.randBoolean();
	}

	@Override
	public void update(final EntityManager caller, World world, final double delta) {
		if (rotateLeft)
			dispatchValue(RotateComponent.IS_ROTATE_LEFT, true);
		else
			dispatchValue(RotateComponent.IS_ROTATE_RIGHT, true);

		super.update(caller, world, delta);
	}

}
