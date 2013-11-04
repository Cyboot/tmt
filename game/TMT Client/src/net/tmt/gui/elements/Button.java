package net.tmt.gui.elements;

import net.tmt.game.GameEngine;
import net.tmt.gfx.Graphics;
import net.tmt.gfx.Sprite;
import net.tmt.util.ColorUtil;
import net.tmt.util.Rectangle;
import net.tmt.util.Vector2d;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.Color;

public class Button extends LeafElement {
	private Color		origin_backgroundColor;
	private Color		origin_borderColor;

	private Rectangle	rect;
	private boolean		isHover;
	private boolean		isMouseDown;
	private Sprite		img;

	public Button(final Vector2d pos, final Sprite img) {
		this(pos, img.setCentered(false).getWidth(), img.getHeight());
		this.img = img;
	}

	public Button(final Vector2d pos, final double width, final double height) {
		super(pos, width, height);

		origin_backgroundColor = new Color(backgroundColor);
		origin_borderColor = new Color(borderColor);
	}

	@Override
	protected void setPos(final Vector2d pos) {
		super.setPos(pos);
		rect = new Rectangle(pos.x, pos.y, width, height);
	}

	@Override
	public void update(final double delta) {
		super.update(delta);

		Vector2d mouse = Vector2d.tmp1.set(Mouse.getX(), GameEngine.HEIGHT - Mouse.getY());

		if (rect.contains(mouse))
			isHover = true;
		else
			isHover = false;

		if (isHover && Mouse.isButtonDown(0)) {
			// TODO: button is clicked when mouse is down and moved to button
			isMouseDown = true;
		} else
			isMouseDown = false;

		backgroundColor.setColor(origin_backgroundColor);
		borderColor.setColor(origin_borderColor);
		if (isHover) {
			ColorUtil.brighter(backgroundColor, 1.5);
			ColorUtil.brighter(borderColor, 1.5);
		}
		if (isMouseDown) {
			ColorUtil.brighter(backgroundColor, 1.2);
			ColorUtil.brighter(borderColor, 1.2);
		}
	}

	@Override
	public void render(final Graphics g) {
		super.render(g);
		if (img != null) {
			if (isMouseDown)
				img.setBlendColor(new Color(200, 200, 200, 255));
			else if (isHover)
				img.setBlendColor(new Color(150, 150, 150, 255));
			else
				img.setBlendColor(new Color(100, 100, 100, 255));

			g.onGui().drawSprite(pos, img);
		}
	}
}
