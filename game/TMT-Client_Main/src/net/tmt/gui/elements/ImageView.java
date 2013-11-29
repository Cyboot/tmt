package net.tmt.gui.elements;

import net.tmt.gfx.Graphics;
import net.tmt.gfx.Sprite;
import net.tmt.util.Vector2d;

public class ImageView extends GuiElement {
	private Sprite	img;

	public ImageView(final Vector2d pos, final Sprite img) {
		super(pos, img.getWidth(), img.getHeight());
		this.img = img.setCentered(false);

		noBackground();
	}

	@Override
	public void render(final Graphics g) {
		g.onGui().drawSprite(pos, img);
	}

}
