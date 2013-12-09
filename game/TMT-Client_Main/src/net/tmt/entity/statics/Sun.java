package net.tmt.entity.statics;

import net.tmt.entity.Entity2D;
import net.tmt.game.manager.EntityManager;
import net.tmt.gfx.Sprite;
import net.tmt.map.World;
import net.tmt.util.Vector2d;

public class Sun extends Entity2D {

	public Sun(final Vector2d pos) {
		super(pos);

		setSprite(new Sprite("sun_default"));
	}

	@Override
	public void update(final EntityManager caller, final World world, final double delta) {
	}

}