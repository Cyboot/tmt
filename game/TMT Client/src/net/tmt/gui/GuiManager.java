package net.tmt.gui;

import net.tmt.game.interfaces.Renderable;
import net.tmt.game.interfaces.Updateable;
import net.tmt.gfx.Graphics;

public class GuiManager implements Updateable, Renderable {
	private static GuiManager	instance;

	private Gui					activeGui;

	public static GuiManager init() {
		instance = new GuiManager();
		return instance;
	}

	@Override
	public void update(final double delta) {
		if (activeGui != null)
			activeGui.update(delta);
	}

	@Override
	public void render(final Graphics g) {
		if (activeGui != null)
			activeGui.render(g);
	}

	public void setGui(final Gui activeGui) {
		this.activeGui = activeGui;
	}

	public static GuiManager getInstance() {
		return instance;
	}
}
