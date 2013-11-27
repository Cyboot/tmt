package net.tmt.gamestate;

import net.tmt.game.Controls;
import net.tmt.gfx.Graphics;
import net.tmt.gfx.Sprite;
import net.tmt.global.Money;
import net.tmt.global.RPLevel;
import net.tmt.gui.DummyGui;
import net.tmt.util.Vector2d;

import org.lwjgl.util.Color;

public class DummyGamestate extends AbstractGamestate {
	private static DummyGamestate	instance;

	private Sprite					sprite_ship3;

	private DummyGamestate() {
		super(null);
		sprite_ship3 = new Sprite("ship_round_64");

		onResume(-1);
	}

	@Override
	public void update(final double delta) {
		sprite_ship3.rotate(delta * 36);

		if (Controls.pressed(Controls.DEBUG_KEY_3)) {
			RPLevel.addRP((int) (delta * 800));
		}
		if (Controls.pressed(Controls.DEBUG_KEY_4)) {
			Money.addMoney((long) (delta * 81231));
		}
	}

	@Override
	public void render(final Graphics g) {
		super.render(g);
		guiManager.setGui(DummyGui.class);

		sprite_ship3.setBlendColor(new Color(175, 175, 255, 255));

		g.onGui().drawSprite(new Vector2d(300, 100), sprite_ship3);

	}

	public static DummyGamestate getInstance() {
		if (instance == null)
			instance = new DummyGamestate();

		return instance;
	}
}
