package net.tmt.gui;

import net.tmt.gfx.Graphics;
import net.tmt.gui.elements.GuiElement;
import net.tmt.gui.elements.LeafElement;
import net.tmt.util.Vector2d;

import org.lwjgl.util.Color;

public class DummyGui extends Gui {

	private GuiElement	guiElement;


	public DummyGui() {
		guiElement = new LeafElement(new Vector2d(50, 50), (Color) Color.ORANGE, 100, 100);
	}

	@Override
	public void update(final double delta) {
		guiElement.update(new Vector2d(0, 0), delta);
	}

	@Override
	public void render(final Graphics g) {
		guiElement.render(g);
	}
}
