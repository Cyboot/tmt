package net.tmt.gui.elements;

import net.tmt.gfx.Graphics;
import net.tmt.util.Vector2d;

public class ToolTip extends LeafElement {

	private String	text;

	public ToolTip(final Vector2d pos, final String text) {
		super(pos, 0, 0);
		this.text = text;
	}

	@Override
	public void render(final Graphics g) {
		super.render(g);
		g.drawText(pos.x + 10, pos.y, text);
	}


}
