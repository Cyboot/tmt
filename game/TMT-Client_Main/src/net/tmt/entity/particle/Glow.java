package net.tmt.entity.particle;

import net.tmt.entityComponents.animation.GlowComponent;
import net.tmt.entityComponents.other.RenderComponent;
import net.tmt.game.manager.EntityManager;
import net.tmt.gfx.Sprite;
import net.tmt.map.World;
import net.tmt.util.Vector2d;

import org.lwjgl.util.Color;

public class Glow extends Particle {
	private static Sprite	sprite;

	private double			size;

	public Glow(final Vector2d pos, final double size, final double lifetime, final double speed,
			final double rotation, final double glowTimer, final Color primColor, final Color secColor) {
		super(pos, lifetime, speed, rotation);

		this.size = size;

		if (sprite == null)
			sprite = new Sprite("glow_32");

		setSprite(sprite);

		addComponent(new GlowComponent(primColor, secColor, glowTimer));
	}

	@Override
	public void update(final EntityManager caller, World world, final double delta) {
		super.update(caller, world, delta);
		sprite.setHeight(size);
		sprite.setWidth(size);
		sprite.setBlendColor((Color) getValue(RenderComponent.BLEND_COLOR));
	}
}
