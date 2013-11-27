package net.tmt.entity.statics.area;

import net.tmt.game.manager.MissionManager;
import net.tmt.gfx.Sprite;
import net.tmt.global.mission.Mission;
import net.tmt.util.Vector2d;

public class MissionAreaOffer extends Area {
	private Mission	mission;

	public MissionAreaOffer(final Vector2d pos, final Mission mission, final double radius) {
		super(pos, radius);

		this.mission = mission;
		setSprite(new Sprite("mission_circle_offer", (int) radius * 2, (int) radius * 2));
	}

	@Override
	protected void onCollide() {
		MissionManager.getInstance().offerMission(mission);
	}
}
