package net.tmt.gui.elements;

import net.tmt.game.interfaces.Renderable;
import net.tmt.game.interfaces.Updateable;
import net.tmt.gfx.Graphics;
import net.tmt.util.ColorUtil;
import net.tmt.util.Vector2d;

import org.lwjgl.util.Color;

public abstract class GuiElement implements Renderable, Updateable {
	public static final double	DEFAULT_PADDING	= 4;

	private String				title;
	protected Vector2d			pos;
	protected double			height;
	protected double			width;

	protected Color				borderColor		= new Color(Color.GREY);
	protected Color				backgroundColor	= new Color(Color.DKGREY);
	protected Color				foregroundColor	= new Color(Color.WHITE);


	public GuiElement(final Vector2d pos, final double height, final double width, final String title) {
		this.title = title;
		this.pos = pos;
		this.height = height;
		this.width = width;

		setBackgroundColor(getBackgroundColor());
		setBorderColor(getBorderColor());
	}

	public GuiElement(final Vector2d pos, final double width, final double height) {
		this.width = width;
		this.height = height;
		setPos(pos);
	}

	@Override
	public void update(final double delta) {
	}

	@Override
	public void render(final Graphics g) {
		g.onGui().setColor(backgroundColor);
		g.onGui().fillRect(pos.x, pos.y, width, height);
		g.onGui().setColor(borderColor);
		g.onGui().drawRect(pos.x, pos.y, width, height);
		g.onGui().setColor(foregroundColor);
	}

	public void setPos(final Vector2d pos) {
		this.pos = pos;
	}

	public Vector2d getPos() {
		return pos;
	}

	public Color getBorderColor() {
		return borderColor;
	}

	public void setBorderColor(final Color borderColor) {
		this.borderColor = borderColor;
	}

	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(final Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public Color getForegroundColor() {
		return foregroundColor;
	}

	public void setForegroundColor(final Color foregroundColor) {
		this.foregroundColor = foregroundColor;
	}

	public double getWidth() {
		return width;
	}

	public double getHeight() {
		return height;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public GuiElement noBackground() {
		setBackgroundColor((Color) ColorUtil.TRANSPARENT);
		setBorderColor((Color) ColorUtil.TRANSPARENT);
		return this;
	}

	@Override
	public String toString() {
		String str = getClass().getSimpleName() + " \"" + title + "\"";
		return str;
	}
}
