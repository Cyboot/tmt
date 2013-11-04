package net.tmt.gui.elements;

import net.tmt.gfx.Graphics;
import net.tmt.util.Vector2d;

import org.lwjgl.util.Color;

public class ToolTip extends LeafElement {

	public ToolTip(final Vector2d pos, final Color borderColor, final double height, final double width) {
		super(pos, borderColor, height, width);
	}

	@Override
	public void render(final Graphics g) {

		g.setColor(borderColor);
		g.fillRect(pos.x, pos.y, width, height);

		g.setColor(Color.BLACK);
		g.drawText(pos.x + 10, pos.y, "Tooltip");


	}


}
