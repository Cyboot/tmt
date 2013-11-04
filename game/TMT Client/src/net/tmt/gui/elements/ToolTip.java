package net.tmt.gui.elements;

import net.tmt.gfx.Graphics;
import net.tmt.util.Vector2d;

public class ToolTip extends LeafElement {

	private String		text;
	private String[]	textBlock;


	public ToolTip(final Vector2d pos, final String text) {
		super(pos, 0, 0);
		this.text = text;
		setupBorder();

	}

	private void setupBorder() {

		int txtLength = text.length();

		if (txtLength < 31) {
			width = txtLength * 7;
			height = 12;
			textBlock = new String[] { text };
		} else {
			int rows = (text.length() / 30) + 1;
			width = 210;
			height = rows * 12;
			textBlock = new String[rows];
			for (int i = 0; i < rows - 1; i++) {
				textBlock[i] = text.substring(i * rows, (i * rows) + 30);
			}
			textBlock[textBlock.length - 1] = text.substring((rows - 1) * 30);
		}


	}

	@Override
	public void render(final Graphics g) {
		super.render(g);
		g.setFont(Graphics.Fonts.font_12);
		for (int i = 0; i < textBlock.length; i++)
			g.drawText(pos.x, pos.y + 12 * i, textBlock[i]);

	}


}
