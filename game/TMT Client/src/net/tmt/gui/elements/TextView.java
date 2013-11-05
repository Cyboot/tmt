package net.tmt.gui.elements;

import net.tmt.gfx.Graphics;
import net.tmt.util.Vector2d;

import org.newdawn.slick.TrueTypeFont;

public class TextView extends GuiElement {

	protected String		text;
	protected String[]		textBlock;
	protected int			maxLineLength;
	protected TrueTypeFont	font;


	public TextView(final Vector2d pos, final double width, final double height) {
		super(pos, width, height);
		// defaults
		font = Graphics.Fonts.font_12;
		maxLineLength = 50;
	}


	protected void setupTextBlock() {
		int txtLength = text.length();

		if (txtLength <= maxLineLength) {
			width = txtLength * font.getWidth("M");
			height = font.getHeight();
			textBlock = new String[] { text };
		} else {
			int rows = (text.length() / maxLineLength) + 1;
			width = maxLineLength * font.getWidth("M");
			height = rows * font.getHeight();
			textBlock = new String[rows];
			for (int i = 0; i < rows - 1; i++) {
				textBlock[i] = text.substring(i * rows, (i * rows) + maxLineLength);
			}
			textBlock[textBlock.length - 1] = text.substring((rows - 1) * maxLineLength);
		}
	}

	@Override
	public void render(final Graphics g) {
		super.render(g);
		g.setFont(Graphics.Fonts.font_12);
		for (int i = 0; i < textBlock.length; i++)
			g.onGui().drawText(pos.x, pos.y + font.getHeight() * i, textBlock[i]);
	}

	public void setMaxLineLength(final int maxLineLength) {
		this.maxLineLength = maxLineLength;
	}


	public void setText(final String text) {
		this.text = text;
		setupTextBlock();
	}
}
