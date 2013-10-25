package net.tmt.gamestate;

import net.tmt.gfx.Graphics;
import net.tmt.gui.EconomyGui;

public class EconomyGamestate extends AbstractGamestate {

	@Override
	public void update(final double delta) {

	}

	@Override
	public void render(final Graphics g) {
		guiManager.setGui(EconomyGui.class);
	}
}
