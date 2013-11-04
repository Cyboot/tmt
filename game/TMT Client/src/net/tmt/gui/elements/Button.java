package net.tmt.gui.elements;

import net.tmt.util.Rectangle;
import net.tmt.util.Vector2d;

import org.lwjgl.util.Color;

public class Button extends LeafElement {
	private Rectangle	rect;

	public Button(final Vector2d pos, final Color borderColor, final double width, final double height) {
		super(pos, borderColor, width, height);
		rect = new Rectangle(pos.x, pos.y, width, height);
	}

	@Override
	public void update(final Vector2d offset, final double delta) {
		super.update(offset, delta);

		rect.setX(offset.x + pos.x);
		rect.setY(offset.y + pos.y);
	}
}
