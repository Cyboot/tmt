package net.tmt.gamestate;

import net.tmt.gfx.Graphics;
import net.tmt.gui.PlanetGui;

public class PlanetGamestate extends AbstractGamestate {

	@Override
	public void update(final double delta) {

	}

	@Override
	public void render(final Graphics g) {
		guiManager.setGui(PlanetGui.getInstance());

	}

}
