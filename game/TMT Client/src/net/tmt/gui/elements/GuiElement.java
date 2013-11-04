package net.tmt.gui.elements;

import net.tmt.game.interfaces.Renderable;
import net.tmt.gfx.Graphics;
import net.tmt.util.Vector2d;

import org.lwjgl.util.Color;

public abstract class GuiElement implements Renderable {

	protected Vector2d	pos;
	protected Vector2d	offset;
	protected double	height;
	protected double	width;

	protected Color		borderColor		= new Color(Color.GREY);
	protected Color		backgroundColor	= new Color(Color.DKGREY);
	protected Color		foregroundColor	= new Color(Color.WHITE);

	public GuiElement(final Vector2d pos, final double width, final double height) {
		this.pos = pos;
		this.width = width;
		this.height = height;
	}

	public void update(final Vector2d offset, final double delta) {
		this.offset = offset;

	}

	@Override
	public void render(final Graphics g) {
		g.setColor(backgroundColor);
		g.fillRect(pos.x, pos.y, width, height);
		g.setColor(borderColor);
		g.drawRect(pos.x, pos.y, width, height);
		g.setColor(foregroundColor);
	}
}
