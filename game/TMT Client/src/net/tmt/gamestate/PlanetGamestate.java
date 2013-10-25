package net.tmt.gamestate;

import net.tmt.game.GameEngine;
import net.tmt.gfx.Graphics;
import net.tmt.gui.PlanetGui;

import org.lwjgl.util.Color;

public class PlanetGamestate extends AbstractGamestate {
	private Color	grassColor	= new Color(10, 150, 0, 255);


	@Override
	public void update(final double delta) {

	}

	@Override
	public void render(final Graphics g) {
		guiManager.setGui(PlanetGui.getInstance());

		g.setColor(grassColor);
		g.onGui().fillRect(0, 0, GameEngine.WIDTH, GameEngine.HEIGHT);
	}

}
