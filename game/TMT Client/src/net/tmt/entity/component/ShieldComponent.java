package net.tmt.entity.component;

import net.tmt.gfx.Graphics;
import net.tmt.gfx.Sprite;

public class ShieldComponent extends Component {
	public static final int		COLOR_BLUE		= 0;
	public static final int		COLOR_YELLOW	= 1;

	public static final String	SET_ACTIVE		= "SET_ACTIVE";

	private Sprite				sprite;
	private boolean				isActive		= false;


	public ShieldComponent(final int shieldSize, final int colorKey) {
		switch (colorKey) {
		case COLOR_BLUE:
			this.sprite = new Sprite("shield_blue_256", shieldSize, shieldSize);
			break;
		case COLOR_YELLOW:
			this.sprite = new Sprite("shield_yellow_256", shieldSize, shieldSize);
			break;
		}
	}

	@Override
	public void update(final ComponentDispatcher caller, final double delta) {
		if (caller.isSet(SET_ACTIVE))
			isActive = (boolean) caller.getValue(SET_ACTIVE);
	}

	@Override
	public void render(final ComponentDispatcher caller, final Graphics g) {
		if (!isActive)
			return;

		super.render(caller, g);

		if (caller.isSet(ROTATION_ANGLE))
			sprite.setRotation((double) caller.getValue(ROTATION_ANGLE));

		if (sprite != null)
			g.drawSprite(pos, sprite);

	}
}
