package net.tmt.entity.npc;

import net.tmt.gfx.Sprite;
import net.tmt.util.RandomUtil;
import net.tmt.util.Vector2d;

import org.lwjgl.util.Color;

public class NPCCargoShip extends NPCSpaceShip {

	private static final double	ROTATION_SPEED	= 80;
	private static final double	SPEED			= 100;

	public NPCCargoShip(final Vector2d pos) {
		super(pos, SPEED, ROTATION_SPEED);

		// random Blendcolor
		Color blendColor = new Color(RandomUtil.intRange(175, 255), RandomUtil.intRange(175, 255), RandomUtil.intRange(
				175, 255), 255);

		setSprite(new Sprite("ship_ends_64", 32, 32).setBlendColor(blendColor));

	}

}
