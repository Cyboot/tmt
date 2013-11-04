package net.tmt.gui;

import net.tmt.gfx.Graphics;
import net.tmt.gui.elements.Button;
import net.tmt.gui.elements.GuiElement;
import net.tmt.gui.elements.LeafElement;
import net.tmt.util.Vector2d;

public class DummyGui extends Gui {

	private GuiElement	guiElement;
	private Button		button;


	public DummyGui() {
		guiElement = new LeafElement(new Vector2d(50, 50), 100, 100);
		button = new Button(new Vector2d(200, 200), 64, 32);
	}

	@Override
	public void update(final double delta) {
		Vector2d offset = new Vector2d();

		button.update(offset, delta);
		guiElement.update(offset, delta);
	}

	@Override
	public void render(final Graphics g) {
		guiElement.render(g);
		button.render(g);
	}
}
