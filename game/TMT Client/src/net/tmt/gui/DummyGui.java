package net.tmt.gui;

import net.tmt.gfx.Graphics;
import net.tmt.gfx.Sprite;
import net.tmt.gui.elements.Button;
import net.tmt.gui.elements.CompositeElement;
import net.tmt.gui.elements.MoveCompositeElement;
import net.tmt.util.Vector2d;

import org.lwjgl.util.Color;

public class DummyGui extends Gui {
	private CompositeElement	compElement;

	public DummyGui() {
		compElement = new MoveCompositeElement(new Vector2d(200, 200), 400, 200);
		compElement.setBackgroundColor(new Color(128, 128, 128, 100));

		compElement.addGuiElement(new Button(new Vector2d(0, 0), 64, 32));
		compElement.addGuiElement(new Button(new Vector2d(0, 32 * 1 + 8 * 1), 64, 32));
		compElement.addGuiElement(new Button(new Vector2d(0, 32 * 2 + 8 * 2), 64, 32));
		compElement.addGuiElement(new Button(new Vector2d(0, 32 * 3 + 8 * 3), 64, 32));

		compElement.addGuiElement(new Button(new Vector2d(100, 0), new Sprite("ship_blue")));
		compElement.addGuiElement(new Button(new Vector2d(100, 100), new Sprite("ship_red")));
		compElement.addGuiElement(new Button(new Vector2d(100, 200), new Sprite("ship_green")));
	}

	@Override
	public void update(final double delta) {
		compElement.update(delta);
	}

	@Override
	public void render(final Graphics g) {
		compElement.render(g);
	}
}
