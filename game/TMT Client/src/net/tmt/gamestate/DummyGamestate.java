package net.tmt.gamestate;

import net.tmt.gfx.Graphics;
import net.tmt.gfx.Sprite;
import net.tmt.gui.DummyGui;
import net.tmt.util.Vector2d;

import org.lwjgl.util.Color;

public class DummyGamestate extends AbstractGamestate {
	private Sprite	sprite_ship1;
	private Sprite	sprite_ship2;
	private Sprite	sprite_ship3;
	private Sprite	sprite_ship4;
	private Sprite	sprite_ship5;

	public DummyGamestate() {
		sprite_ship1 = new Sprite("ship_double_64");
		sprite_ship2 = new Sprite("ship_back_64");
		sprite_ship3 = new Sprite("ship_round_64");
		sprite_ship4 = new Sprite("ship_ends_64");
		sprite_ship5 = new Sprite("ship_cyclon_64");

		onResume(-1);
	}

	@Override
	public void update(final double delta) {
		sprite_ship1.rotate(delta * 36);
		sprite_ship2.rotate(delta * 36);
		sprite_ship3.rotate(delta * 36);
		sprite_ship4.rotate(delta * 36);
		sprite_ship5.rotate(delta * 36);
	}

	@Override
	public void render(final Graphics g) {
		guiManager.setGui(DummyGui.class);

		sprite_ship1.setBlendColor(new Color(255, 175, 175, 255));
		sprite_ship2.setBlendColor(new Color(175, 255, 175, 255));
		sprite_ship3.setBlendColor(new Color(175, 175, 255, 255));
		sprite_ship4.setBlendColor(new Color(255, 255, 175, 255));
		sprite_ship5.setBlendColor(new Color(175, 255, 255, 255));

		g.onGui().drawSprite(new Vector2d(100, 100), sprite_ship1);
		g.onGui().drawSprite(new Vector2d(200, 100), sprite_ship2);
		g.onGui().drawSprite(new Vector2d(300, 100), sprite_ship3);
		g.onGui().drawSprite(new Vector2d(400, 100), sprite_ship4);
		g.onGui().drawSprite(new Vector2d(500, 100), sprite_ship5);

	}

	@Override
	public void requestMap() {
	}
}
