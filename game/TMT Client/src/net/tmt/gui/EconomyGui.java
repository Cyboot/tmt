package net.tmt.gui;

import net.tmt.gfx.Graphics;

public class EconomyGui extends Gui {
	private static EconomyGui	instance	= new EconomyGui();


	@Override
	public void update(final double delta) {

	}

	@Override
	public void render(final Graphics g) {

	}

	public static EconomyGui getInstance() {
		return instance;
	}
}
