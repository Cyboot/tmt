package net.tmt.gui.elements;

import java.util.ArrayList;
import java.util.List;

import net.tmt.gfx.Graphics;
import net.tmt.util.Vector2d;

import org.newdawn.slick.TrueTypeFont;

public class TextView extends GuiElement {
	private List<String>	textBlockList	= new ArrayList<>();
	private String			text;
	private double			padding;

	protected TrueTypeFont	font			= Graphics.Fonts.get(14);


	public TextView(final Vector2d pos, final double width, final double height, final double padding) {
		super(pos, width, height);

		this.padding = padding;
	}

	public TextView(final Vector2d pos, final double width, final double height) {
		this(pos, width, height, DEFAULT_PADDING);
	}

	public void setFont(final TrueTypeFont font) {
		this.font = font;
	}

	protected void setupTextBlock() {
		textBlockList.clear();

		String[] words = text.split(" ");

		double currentX = padding;
		double currentY = padding;
		String currentLine = "";
		boolean first = true;
		for (String w : words) {
			int wordWidth = 0;

			if (first) {
				first = false;
				wordWidth = font.getWidth(w);
			} else {
				wordWidth = font.getWidth(" " + w);
			}

			// check for line end (width) + '\n' newline
			if (!w.equals("\n") && currentX + wordWidth <= width) {
				currentLine += w + " ";
				currentX += wordWidth;
			} else {
				// check for box height
				currentY += font.getHeight();
				if (currentY + font.getHeight() > height) {
					// no space for next line
					currentLine = currentLine.substring(0, Math.max(currentLine.length() - 1, 0));
					currentLine += "...";
					break;
				} else {
					textBlockList.add(currentLine);
					if (w.equals("\n"))
						currentLine = "";
					else
						currentLine = w + " ";
					currentX = wordWidth;
				}
			}
		}
		textBlockList.add(currentLine);
	}

	@Override
	public void render(final Graphics g) {
		super.render(g);
		g.setFont(font);
		for (int i = 0; i < textBlockList.size(); i++)
			g.onGui().drawText(pos.x + padding, pos.y + font.getHeight() * i + padding, textBlockList.get(i));
	}

	public void setText(final String text) {
		this.text = text;
		setupTextBlock();
	}
}
