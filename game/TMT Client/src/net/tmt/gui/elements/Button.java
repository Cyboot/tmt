package net.tmt.gui.elements;

import net.tmt.game.GameEngine;
import net.tmt.gfx.Graphics;
import net.tmt.util.Rectangle;
import net.tmt.util.Vector2d;

import org.lwjgl.input.Mouse;

public class Button extends LeafElement {
	private Rectangle	rect;
	private boolean		isHover;

	public Button(final Vector2d pos, final double width, final double height) {
		super(pos, width, height);
		rect = new Rectangle(pos.x, pos.y, width, height);
	}

	@Override
	public void update(final Vector2d offset, final double delta) {
		super.update(offset, delta);
		rect.setX(offset.x + pos.x);
		rect.setY(offset.y + pos.y);

		Vector2d mouse = Vector2d.tmp1.set(Mouse.getX(), GameEngine.HEIGHT - Mouse.getY());

		if (rect.contains(mouse))
			isHover = true;
		else
			isHover = false;

		if (isHover && Mouse.isButtonDown(0)) {
			// TODO: button is clicked when mouse is down and moved to button
			System.out.println("button pressed");
		}
	}

	@Override
	public void render(final Graphics g) {
		super.render(g);
	}
}
