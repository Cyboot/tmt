package net.tmt.gui;

import net.tmt.gfx.Graphics;

public class DummyGui extends Gui {
	private static final DummyGui	instance	= new DummyGui();

	@Override
	public void update(final double delta) {

	}

	@Override
	public void render(final Graphics g) {

	}

	public static DummyGui getInstance() {
		return instance;
	}
}
