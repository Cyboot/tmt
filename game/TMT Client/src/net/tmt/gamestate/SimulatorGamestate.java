package net.tmt.gamestate;

import net.tmt.gfx.Graphics;
import net.tmt.gui.SimulatorGui;

public class SimulatorGamestate extends AbstractGamestate {

	@Override
	public void update(final double delta) {

	}

	@Override
	public void render(final Graphics g) {
		guiManager.setGui(SimulatorGui.getInstance());
	}
}
