package net.tmt.gui.elements;

import net.tmt.game.interfaces.Renderable;
import net.tmt.game.interfaces.Updateable;
import net.tmt.gfx.Graphics;
import net.tmt.util.Vector2d;

import org.lwjgl.util.Color;

public abstract class GuiElement implements Renderable, Updateable {

	protected Vector2d	pos;
	protected double	height;
	protected double	width;

	protected Color		borderColor		= new Color(Color.GREY);
	protected Color		backgroundColor	= new Color(Color.DKGREY);
	protected Color		foregroundColor	= new Color(Color.WHITE);

	public GuiElement(final Vector2d pos, final double width, final double height) {
		this.width = width;
		this.height = height;
		setPos(pos);
	}

	@Override
	public void render(final Graphics g) {
		g.setColor(backgroundColor);
		g.fillRect(pos.x, pos.y, width, height);
		g.setColor(borderColor);
		g.drawRect(pos.x, pos.y, width, height);
		g.setColor(foregroundColor);
	}

	protected void setPos(final Vector2d pos) {
		this.pos = pos;
	}

	public Vector2d getPos() {
		return pos;
	}
}
