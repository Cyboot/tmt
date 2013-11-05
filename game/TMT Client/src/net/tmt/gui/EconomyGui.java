package net.tmt.gui;

import net.tmt.gfx.Graphics;

public class EconomyGui extends Gui {

	@Override
	public void update(final double delta) {
		gameStateToolbar.update(delta);
	}

	@Override
	public void render(final Graphics g) {
		gameStateToolbar.render(g);
	}
}
