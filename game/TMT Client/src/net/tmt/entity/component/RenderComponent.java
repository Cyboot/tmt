package net.tmt.entity.component;

import net.tmt.gfx.Graphics;
import net.tmt.gfx.Sprite;
import net.tmt.util.Vector2d;

public class RenderComponent extends Component {
	private Sprite		sprite;
	private Vector2d	pos;

	@Override
	public void render(final ComponentDispatcher caller, final Graphics g) {
		super.render(caller, g);
		sprite.setRotation((double) caller.getValue(ROTATION_ANGLE));

		if (sprite != null)
			g.drawSprite(pos, sprite);

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

		public Builder pos(final Vector2d pos) {
			render.pos = pos;
			return this;
		}

		public RenderComponent build() {
			return render;
		}
	}
}
