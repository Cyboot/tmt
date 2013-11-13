package net.tmt.gui.elements;

import net.tmt.util.Vector2d;

public class ToolTip extends TextView {


	public ToolTip(final Vector2d pos, final String text) {
		super(pos, 0, 0);

		width = font.getWidth(text);

		setText(text);
	}

}
