package net.tmt.gui.elements;

import net.tmt.gfx.Graphics;
import net.tmt.util.Vector2d;

public class ToolTip extends LeafElement {

	public ToolTip(final Vector2d pos, final double height, final double width) {
		super(pos, width, height);
	}

	@Override
	public void render(final Graphics g) {
		super.render(g);
		g.drawText(pos.x + 10, pos.y, "Tooltip");
	}


}
