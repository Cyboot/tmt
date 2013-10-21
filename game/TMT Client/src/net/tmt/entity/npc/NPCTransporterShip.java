package net.tmt.entity.npc;

import net.tmt.gfx.Sprite;
import net.tmt.util.Vector2d;

public class NPCTransporterShip extends NPCSpaceShip {
	private static final double	ROTATION_SPEED	= 30;
	private static final double	SPEED			= 50;

	public NPCTransporterShip(final Vector2d pos) {
		super(pos, SPEED, ROTATION_SPEED);

		setSprite(new Sprite("ship_transporter"));
	}

}
