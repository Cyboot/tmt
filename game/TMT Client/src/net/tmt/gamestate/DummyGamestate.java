package net.tmt.gamestate;

import net.tmt.entity.SpaceShip;
import net.tmt.gfx.Graphics;
import net.tmt.gfx.Sprite;

public class DummyGamestate extends AbstractGamestate {
	private Sprite		sprite;
	private SpaceShip	ship	= new SpaceShip();

	public DummyGamestate() {
		sprite = new Sprite("ship_green");
	}

	@Override
	public void update(final double delta) {
		sprite.rotate(delta * 36);
		ship.update(delta);
	}

	@Override
	public void render(final Graphics g) {
		ship.render(g);

		// g.drawSprite(new Vector2d(100, 100), sprite);
		//
		// g.drawSprite(new Vector2d(500, 100), new Sprite("ship_64"));
	}

}
