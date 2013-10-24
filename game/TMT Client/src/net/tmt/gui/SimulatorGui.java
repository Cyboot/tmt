package net.tmt.gui;

import net.tmt.game.GameEngine;
import net.tmt.gfx.Graphics;

import org.lwjgl.util.Color;

public class SimulatorGui extends Gui {
	private static SimulatorGui	instance	= new SimulatorGui();


	public static SimulatorGui getInstance() {
		return instance;
	}

	@Override
	public void update(final double delta) {

	}

	@Override
	public void render(final Graphics g) {
		int width = GameEngine.WIDTH;
		int height = GameEngine.HEIGHT;

		g.setColor(Color.YELLOW);

		g.onGui().drawCircle(width / 2, height / 2, 100);
		g.onGui().drawCircle(width / 2, height / 2, 200);
	}

}
