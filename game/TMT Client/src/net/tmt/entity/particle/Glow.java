package net.tmt.entity.particle;

import net.tmt.entity.component.GlowComponent;
import net.tmt.entity.component.RenderComponent;
import net.tmt.game.manager.EntityManager;
import net.tmt.gfx.Graphics;
import net.tmt.gfx.Sprite;
import net.tmt.util.Vector2d;

import org.lwjgl.util.Color;

public class Glow extends Particle {
	private static Sprite	sprite;

	public Glow(final Vector2d pos, final double lifetime, final double speed, final double roation,
			final double glowTimer, final Color primColor, final Color secColor) {
		super(pos, lifetime, speed, roation);

		if (sprite == null)
			sprite = new Sprite("glow_32");

		addComponent(new GlowComponent(primColor, secColor, glowTimer));
	}

	@Override
	public void update(final EntityManager caller, final double delta) {
		super.update(caller, delta);
	}

	@Override
	public void render(final Graphics g) {
		sprite.setBlendColor((Color) getValue(RenderComponent.BLEND_COLOR));

		g.drawSprite(pos, sprite);
	}
}
