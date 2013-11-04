package net.tmt.gui.elements;

import net.tmt.util.Vector2d;

public abstract class LeafElement extends GuiElement {

	public LeafElement(final Vector2d pos, final double width, final double height) {
		super(pos, width, height);
	}

	@Override
	public void update(final double delta) {
	}
}
