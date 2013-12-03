package net.tmt.entity.particle;

import net.tmt.entity.component.Component;
import net.tmt.entity.component.animation.GlowComponent;
import net.tmt.entity.component.other.RenderComponent;
import net.tmt.game.manager.EntityManager;
import net.tmt.gfx.Graphics;
import net.tmt.gfx.Sprite;
import net.tmt.map.World;
import net.tmt.util.RandomUtil;
import net.tmt.util.Vector2d;

import org.lwjgl.util.Color;

public class Smoke extends Particle {
	private static final Color	PRIM_COLOR	= new Color(255, 255, 255, 255);
	private static final Color	SEC_COLOR	= new Color(200, 200, 200, 200);

	private static Sprite		sprite;

	private int					reverseRotation;
	private double				rotationSpeed;

	public Smoke(final Vector2d pos, final double lifetime, final double speed, final double roation,
			final double rotationSpeed) {
		super(pos, lifetime, speed, roation);
		this.rotationSpeed = rotationSpeed;

		if (sprite == null)
			sprite = new Sprite("smoke_64", 32, 32);

		addComponent(new GlowComponent(PRIM_COLOR, SEC_COLOR, 3));
		dispatchValue(Component.ROTATION_ANGLE_LOOK, RandomUtil.doubleRange(0, 360));

		reverseRotation = Math.random() > 0.5 ? 1 : -1;
	}

	@Override
	public void update(final EntityManager caller, World world, final double delta) {
		super.update(caller, world, delta);

		dispatchValue(Component.ROTATION_ANGLE_LOOK, (double) getValue(Component.ROTATION_ANGLE_LOOK) + rotationSpeed * delta
				* reverseRotation);
	}

	@Override
	public void render(final Graphics g) {
		sprite.setBlendColor((Color) getValue(RenderComponent.BLEND_COLOR));
		sprite.setRotation((double) getValue(Component.ROTATION_ANGLE_LOOK));

		g.drawSprite(pos, sprite);
	}
}
