package net.tmt.gamestate;

import net.tmt.entity.SpaceShip;
import net.tmt.gfx.Graphics;
import net.tmt.gfx.Sprite;
import net.tmt.util.Vector2d;

public class DummyGamestate extends AbstractGamestate {
	private Sprite		sprite_ship1;
	private Sprite		sprite_ship2;
	private Sprite		sprite_ship3;
	private Sprite		sprite_ship4;
	private Sprite		sprite_ship5;
	private SpaceShip	ship	= new SpaceShip();

	public DummyGamestate() {
		sprite_ship1 = new Sprite("ship_back_64");
		sprite_ship2 = new Sprite("ship_double_64");
		sprite_ship3 = new Sprite("ship_round_64");
		sprite_ship4 = new Sprite("ship_ends_64");
		sprite_ship5 = new Sprite("ship_cyclon_64");
	}

	@Override
	public void update(final double delta) {
		sprite_ship1.rotate(delta * 36);
		sprite_ship2.rotate(delta * 36);
		sprite_ship3.rotate(delta * 36);
		sprite_ship4.rotate(delta * 36);
		sprite_ship5.rotate(delta * 36);

		ship.update(delta);
	}

	@Override
	public void render(final Graphics g) {
		ship.render(g);

		g.drawSprite(new Vector2d(100, 100), sprite_ship1);
		g.drawSprite(new Vector2d(200, 100), sprite_ship2);
		g.drawSprite(new Vector2d(300, 100), sprite_ship3);
		g.drawSprite(new Vector2d(400, 100), sprite_ship4);
		g.drawSprite(new Vector2d(500, 100), sprite_ship5);
	}

}
