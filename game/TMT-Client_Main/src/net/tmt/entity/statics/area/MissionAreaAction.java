package net.tmt.entity.statics.area;

import net.tmt.gfx.Sprite;
import net.tmt.global.mission.Mission;
import net.tmt.util.Vector2d;

public class MissionAreaAction extends Area {
	private static final int	RADIUS	= 128;
	private Mission				mission;

	public MissionAreaAction(final Vector2d pos, final Mission mission) {
		super(pos, RADIUS);

		this.mission = mission;
		setSprite(new Sprite("mission_circle", RADIUS * 2, RADIUS * 2));
	}

	@Override
	protected void onCollide() {
		mission.action(this);
	}
}
