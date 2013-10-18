package net.tmt.gamestate;

import net.tmt.gfx.Graphics;
import net.tmt.gfx.Sprite;
import net.tmt.util.Vector2d;

public class DummyGamestate extends AbstractGamestate {
	private Sprite	sprite;

	public DummyGamestate() {
		sprite = new Sprite("ship_green");
	}

	@Override
	public void update(final double delta) {
		sprite.rotate(delta * 36);
	}

	@Override
	public void render(final Graphics g) {
		g.drawSprite(new Vector2d(100, 100), sprite);

		g.drawSprite(new Vector2d(500, 100), new Sprite("ship_64"));
	}

}
