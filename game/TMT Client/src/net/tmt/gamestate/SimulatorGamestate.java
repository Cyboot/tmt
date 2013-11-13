package net.tmt.gamestate;

import net.tmt.gfx.Graphics;
import net.tmt.gui.SimulatorGui;

public class SimulatorGamestate extends AbstractGamestate {
	private static SimulatorGamestate	instance;

	private SimulatorGamestate() {
	}

	@Override
	public void update(final double delta) {

	}

	@Override
	public void render(final Graphics g) {
		guiManager.setGui(SimulatorGui.class);
	}

	@Override
	public void requestMap() {
	}

	public static SimulatorGamestate getInstance() {
		if (instance == null)
			instance = new SimulatorGamestate();
		return instance;
	}
}
