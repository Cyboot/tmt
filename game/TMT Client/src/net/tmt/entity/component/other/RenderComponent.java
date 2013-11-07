package net.tmt.entity.component.other;

import net.tmt.entity.component.Component;
import net.tmt.entity.component.ComponentDispatcher;
import net.tmt.entity.component.move.RotateComponent;
import net.tmt.gfx.Graphics;
import net.tmt.gfx.Sprite;
import net.tmt.util.Vector2d;

import org.lwjgl.util.Color;

public class RenderComponent extends Component {
	public static final String	BLEND_COLOR	= "BLEND_COLOR";

	private Sprite				sprite;

	public RenderComponent() {
	}


	public RenderComponent(final Sprite sprite) {
		this.sprite = sprite;
	}


	@Override
	public void render(final ComponentDispatcher caller, final Graphics g) {
		super.render(caller, g);

		if (caller.isSet(RotateComponent.ROTATION_ANGLE_MOVE)) {
			sprite.setRotation((double) caller.getValue(RotateComponent.ROTATION_ANGLE_MOVE));
		}

		if (sprite != null) {
			if (caller.isSet(BLEND_COLOR))
				sprite.setBlendColor((Color) caller.getValue(BLEND_COLOR));

			if (caller.isSet(ExtraOffsetComponent.TRANSLATE_POS))
				g.drawSprite((Vector2d) caller.getValue(ExtraOffsetComponent.TRANSLATE_POS), sprite);
			else
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
