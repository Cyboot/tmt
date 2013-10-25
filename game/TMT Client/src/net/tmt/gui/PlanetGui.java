package net.tmt.gui;

import net.tmt.game.GameEngine;
import net.tmt.gfx.Graphics;

import org.lwjgl.util.Color;

public class PlanetGui extends Gui {
	private static PlanetGui	instance	= new PlanetGui();

	@Override
	public void update(final double delta) {

	}

	@Override
	public void render(final Graphics g) {
		int width = GameEngine.WIDTH;
		int height = GameEngine.HEIGHT;

		g.setColor(Color.ORANGE);

		// ressource
		g.setLineWidth(2);
		g.onGui().drawRect(0, 0, width * 0.3f, height * 0.05f);

		// minimap
		g.onGui().drawRect(width * 0.85f, 0, width * 0.15f, height * 0.25f);

		// sidebar
		g.onGui().drawRect(0, height * 0.15f, width * 0.04f, height * 0.5f);

		// missions
		g.onGui().drawRect(width * 0.85f, height * 0.3f, width * 0.15f, height * 0.4f);

		// log/chat
		g.onGui().drawRect(0, height * 0.71, width * 0.05f, height * 0.04f);
		g.onGui().drawRect(0, height * 0.75, width * 0.25f, height * 0.25f);

		// weapons
		g.onGui().drawRect(width * 0.3f, height * 0.75f, width * 0.07f, height * 0.1f);
		g.onGui().drawRect(width * 0.3f, height * 0.90f, width * 0.07f, height * 0.1f);

		// states of ship
		g.onGui().drawRect(width * 0.4f, height * 0.75f, width * 0.2f, height * 0.03f);
		g.onGui().drawRect(width * 0.4f, height * 0.8f, width * 0.2f, height * 0.03f);
		g.onGui().drawRect(width * 0.4f, height * 0.85f, width * 0.2f, height * 0.03f);
		g.onGui().drawRect(width * 0.4f, height * 0.90f, width * 0.2f, height * 0.03f);
		g.onGui().drawRect(width * 0.4f, height * 0.95f, width * 0.2f, height * 0.03f);

		// information window
		g.onGui().drawRect(width * 0.75f, height * 0.75f, width * 0.25f, height * 0.25f);
	}

	public static PlanetGui getInstance() {
		return instance;
	}
}
