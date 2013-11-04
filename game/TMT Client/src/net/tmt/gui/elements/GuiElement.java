package net.tmt.gui.elements;

import net.tmt.game.interfaces.Renderable;
import net.tmt.gfx.Graphics;
import net.tmt.util.Vector2d;

import org.lwjgl.util.Color;

public abstract class GuiElement implements Renderable {

	protected Vector2d	pos;
	protected Vector2d	offset;
	protected Color		borderColor;
	protected double	height;
	protected double	width;


	public GuiElement(final Vector2d pos, final Color borderColor, final double height, final double width) {
		this.pos = pos;
		this.borderColor = borderColor;
		this.height = height;
		this.width = width;
	}

	public void update(final Vector2d offset, final double delta) {
		this.offset = offset;

	}

	@Override
	public void render(final Graphics g) {
		g.setColor(borderColor);
		g.drawRect(pos.x, pos.y, width, height);
	}

}
