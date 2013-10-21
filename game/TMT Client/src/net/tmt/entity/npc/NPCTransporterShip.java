package net.tmt.entity.npc;

import net.tmt.gfx.Sprite;
import net.tmt.util.Vector2d;

public class NPCTransporterShip extends NPCSpaceShip {
	public static final int		TYPE_1			= 1;
	public static final int		TYPE_2			= 2;

	private static final double	ROTATION_SPEED	= 30;
	private static final double	SPEED			= 50;

	public NPCTransporterShip(final Vector2d pos, final int type) {
		super(pos, SPEED, ROTATION_SPEED);

		switch (type) {
		case TYPE_1:
			setSprite(new Sprite("ship_transporter1"));
			break;
		case TYPE_2:
			setSprite(new Sprite("ship_transporter2"));
			break;
		}
	}

}
