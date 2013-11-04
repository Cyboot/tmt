package net.tmt.gui.elements;

import net.tmt.util.Vector2d;

import org.lwjgl.util.Color;

public class LeafElement extends GuiElement {

	public LeafElement(final Vector2d pos, final Color borderColor, final double height, final double width) {
		super(pos, borderColor, height, width);
	}

	@Override
	public void update(final Vector2d offset, final double delta) {

		super.update(offset, delta);

		// borderColor.setRed(RandomUtil.intRange(0, 255));
	}
}
