package net.tmt.gui;

import net.tmt.game.GameEngine;
import net.tmt.gfx.Graphics;

import org.lwjgl.util.Color;

public class SimulatorGui extends Gui {

	@Override
	public void update(final double delta) {

	}

	@Override
	public void render(final Graphics g) {
		int width = GameEngine.WIDTH;
		int height = GameEngine.HEIGHT;

		g.setColor(Color.YELLOW);

		// circle
		g.onGui().drawCircle(width / 2, height / 2, 100);
		g.onGui().drawCircle(width / 2, height / 2, 200);

		g.onGui().fillCircle(width / 2, height / 2, 4);

		// left
		g.onGui().fillRect(width / 8, height / 2 - height / 64, width / 8, height / 128);
		g.onGui().fillRect(width / 8, height / 2 - height / 64 + 30, width / 16, height / 128);
		g.onGui().fillRect(width / 8, height / 2 - height / 64 + 60, width / 16, height / 128);
		g.onGui().fillRect(width / 8, height / 2 - height / 64 + 90, width / 16, height / 128);
		g.onGui().fillRect(width / 8, height / 2 - height / 64 + 120, width / 16, height / 128);

		// right
		g.onGui().fillRect(width / 8 * 6, height / 2 - height / 64, width / 8, height / 128);
	}
}
