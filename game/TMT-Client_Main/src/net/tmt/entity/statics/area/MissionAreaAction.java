package net.tmt.entity.statics.area;

import net.tmt.gfx.Sprite;
import net.tmt.global.mission.MissionDispatcher;
import net.tmt.util.Vector2d;

public class MissionAreaAction extends Area {
	private static final int	RADIUS	= 128;

	public MissionAreaAction(final Vector2d pos) {
		super(pos, RADIUS);

		setSprite(new Sprite("mission_circle", RADIUS * 2, RADIUS * 2));
	}

	@Override
	protected void onCollide() {
		MissionDispatcher.dispatch(this);
	}
}
