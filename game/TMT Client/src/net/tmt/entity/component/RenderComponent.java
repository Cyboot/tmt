package net.tmt.entity.component;

import net.tmt.gfx.Graphics;
import net.tmt.gfx.Sprite;
import net.tmt.util.Vector2d;

import org.lwjgl.util.Color;

public class RenderComponent extends Component {
	public static final String	BLEND_COLOR	= "BLEND_COLOR";

	private Sprite				sprite;

	public RenderComponent() {
	}


	public RenderComponent(final Sprite sprite, final Vector2d pos) {
		this.sprite = sprite;
		this.pos = pos;
	}


	@Override
	public void render(final ComponentDispatcher caller, final Graphics g) {
		super.render(caller, g);

		if (caller.isSet(ROTATION_ANGLE))
			sprite.setRotation((double) caller.getValue(ROTATION_ANGLE));

		if (sprite != null) {
			if (caller.isSet(BLEND_COLOR))
				sprite.setBlendColor((Color) caller.getValue(BLEND_COLOR));

			g.drawSprite(pos, sprite);
		}

	}

	public void setSprite(final Sprite sprite) {
		this.sprite = sprite;
	}

	public static class Builder {
		private RenderComponent	render;

		public Builder() {
			render = new RenderComponent();
		}

		public Builder sprite(final Sprite sprite) {
			render.sprite = sprite;
			return this;
		}

		public RenderComponent build() {
			return render;
		}
	}
}
