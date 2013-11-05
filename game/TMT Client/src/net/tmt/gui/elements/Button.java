package net.tmt.gui.elements;

import java.util.ArrayList;
import java.util.List;

import net.tmt.game.Controls;
import net.tmt.game.interfaces.ClickListener;
import net.tmt.gfx.Graphics;
import net.tmt.gfx.Sprite;
import net.tmt.util.ColorUtil;
import net.tmt.util.Rectangle;
import net.tmt.util.Vector2d;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.Color;

public class Button extends GuiElement {
	private Color				origin_backgroundColor;
	private Color				origin_borderColor;

	private Rectangle			rect;
	private boolean				isHover;
	private boolean				isMouseClicked;
	private Sprite				img;

	private List<ClickListener>	clickListener	= new ArrayList<>();

	public Button(final Vector2d pos, final Sprite img, final String title) {
		this(pos, img.getWidth(), img.getHeight(), title);
		this.img = img;
		this.img.setCentered(false);
	}

	public Button(final Vector2d pos, final Sprite img) {
		this(pos, img, "");
	}

	public Button(final Vector2d pos, final double width, final double height, final String title) {
		super(pos, width, height, title);
	}

	public Button(final Vector2d pos, final double width, final double height) {
		this(pos, width, height, "");

		setBackgroundColor(getBackgroundColor());
		setBorderColor(getBorderColor());
	}

	@Override
	public void setPos(final Vector2d pos) {
		super.setPos(pos);
		rect = new Rectangle(pos.x, pos.y, width, height);
	}

	@Override
	public void update(final double delta) {
		super.update(delta);

		Vector2d mouse = Vector2d.tmp1.set(Controls.mouseX(), Controls.mouseY());

		if (rect.contains(mouse))
			isHover = true;
		else
			isHover = false;

		boolean click = Controls.wasReleased(Controls.MOUSE_LEFT);
		Mouse.poll();

		isMouseClicked = false;
		if (isHover) {
			if (Controls.pressed(Controls.MOUSE_LEFT))
				isMouseClicked = true;

			if (click)
				onClicked();
		}

		backgroundColor.setColor(origin_backgroundColor);
		borderColor.setColor(origin_borderColor);
		if (isHover) {
			ColorUtil.brighter(backgroundColor, 1.5);
			ColorUtil.brighter(borderColor, 1.5);
		}
		if (isMouseClicked) {
			ColorUtil.brighter(backgroundColor, 1.2);
			ColorUtil.brighter(borderColor, 1.2);
		}

	}

	private void onClicked() {
		// notify all listener
		for (ClickListener cl : clickListener)
			cl.onClick(this);
	}

	public void addClickListener(final ClickListener listener) {
		clickListener.add(listener);
	}

	@Override
	public void setBackgroundColor(final Color backgroundColor) {
		super.setBackgroundColor(backgroundColor);
		origin_backgroundColor = new Color(backgroundColor);
	}

	@Override
	public void setBorderColor(final Color borderColor) {
		super.setBorderColor(borderColor);
		origin_borderColor = new Color(borderColor);
	}


	@Override
	public void render(final Graphics g) {
		super.render(g);
		if (img != null) {
			if (isMouseClicked)
				img.setBlendColor(new Color(200, 200, 200, 255));
			else if (isHover)
				img.setBlendColor(new Color(150, 150, 150, 255));
			else
				img.setBlendColor(new Color(100, 100, 100, 255));

			g.onGui().drawSprite(pos, img);
		}
	}
}
