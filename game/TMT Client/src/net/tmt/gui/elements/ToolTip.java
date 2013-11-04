package net.tmt.gui.elements;

import net.tmt.gfx.Graphics;
import net.tmt.util.Vector2d;

import org.lwjgl.util.Color;

public class ToolTip extends LeafElement {

	String	text;

	public ToolTip(final Vector2d pos, final String text) {
		super(pos, (Color) Color.CYAN, 0, 0); // default
		this.text = text;


	}

	@Override
	public void render(final Graphics g) {

		g.setColor(borderColor);
		g.fillRect(pos.x, pos.y, width, height);

		g.setColor(Color.BLACK);
		g.drawText(pos.x + 10, pos.y, "Tooltip");


	}


}
