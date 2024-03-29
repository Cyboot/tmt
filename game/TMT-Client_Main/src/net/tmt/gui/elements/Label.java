package net.tmt.gui.elements;

import net.tmt.util.Vector2d;

import org.newdawn.slick.TrueTypeFont;

public class Label extends TextView {
	private static final int	PADDING	= 2;

	public Label(final Vector2d pos, final String text, final boolean hasBorder) {
		super(pos, 0, 0, PADDING);

		setText(text);

		if (!hasBorder) {
			noBackground();
		}
	}

	@Override
	public void setText(final String text) {
		width = font.getWidth(text) + PADDING;
		height = font.getHeight() + PADDING;
		super.setText(text);
	}

	public Label(final Vector2d pos, final String text) {
		this(pos, text, false);
	}

	public Label(final Vector2d pos, final String text, final TrueTypeFont font) {
		this(pos, text, false);
		setFont(font);
	}

	public static Label createTooltip(final Vector2d pos, final String text) {
		return new Label(pos, text, true);
	}
}
