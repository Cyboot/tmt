package net.tmt.gui.elements;

import net.tmt.gfx.Graphics;
import net.tmt.util.Vector2d;

import org.newdawn.slick.TrueTypeFont;

public class TextView extends GuiElement {

	protected String		text;
	protected String[]		textBlock;
	protected TrueTypeFont	font;


	public TextView(final Vector2d pos, final double width, final double height) {
		super(pos, width, height);
		// defaults
		font = Graphics.Fonts.font_12;
	}


	protected void setupTextBlock() {
		int textWidth = font.getWidth(text);

		if (textWidth <= width) {
			height = font.getHeight();
			textBlock = new String[] { text };
		} else {
			int rows = (int) (textWidth / width) + 1;
			height = rows * font.getHeight();
			textBlock = new String[rows];
			int charsPerLine = (int) (width / font.getWidth("W"));

			for (int i = 0; i < rows; i++) {
				int maxIndex = Math.min(text.length(), (i * charsPerLine) + charsPerLine);
				textBlock[i] = text.substring(i * charsPerLine, maxIndex);
			}
		}
	}

	@Override
	public void render(final Graphics g) {
		super.render(g);
		g.setFont(Graphics.Fonts.font_12);
		for (int i = 0; i < textBlock.length; i++)
			g.onGui().drawText(pos.x, pos.y + font.getHeight() * i, textBlock[i]);
	}

	public void setText(final String text) {
		this.text = text;
		setupTextBlock();
	}
}
