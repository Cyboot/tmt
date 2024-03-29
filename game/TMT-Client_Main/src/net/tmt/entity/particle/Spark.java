package net.tmt.entity.particle;

import net.tmt.entityComponents.move.RotateComponent;
import net.tmt.game.manager.EntityManager;
import net.tmt.gfx.Graphics;
import net.tmt.gfx.Sprite;
import net.tmt.map.World;
import net.tmt.util.RandomUtil;
import net.tmt.util.Vector2d;

import org.lwjgl.util.Color;

public class Spark extends Particle {
	private static Sprite	sprite;

	private Color			color;

	private double			size;

	public Spark(final Vector2d pos, final double size, final double lifetime, final Color color, final double speed,
			final double roation) {
		super(pos, lifetime, speed, roation);

		if (sprite == null)
			sprite = new Sprite("spark_8");

		this.size = size;
		this.color = color;
	}

	@Override
	public void update(final EntityManager caller, World world, final double delta) {
		super.update(caller, world, delta);

		// a little random direction change to make it appear more natural
		if (Math.random() > 0.85)
			dispatchValue(RotateComponent.ROTATION_ANGLE_MOVE, (double) getValue(RotateComponent.ROTATION_ANGLE_MOVE)
					+ RandomUtil.intRange(-20, 20));
	}

	@Override
	public void render(final Graphics g) {
		sprite.setBlendColor(color);
		sprite.setHeight(size);
		sprite.setWidth(size);

		g.drawSprite(pos, sprite);
	}
}
