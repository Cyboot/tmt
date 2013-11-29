package net.tmt.entity.statics.area;

import net.tmt.gfx.Graphics;
import net.tmt.gfx.Sprite;
import net.tmt.global.mission.Mission;
import net.tmt.global.mission.MissionManager;
import net.tmt.util.Vector2d;

public class MissionAreaOffer extends Area {
	private Mission	mission;

	public MissionAreaOffer(final Vector2d pos, final Mission mission, final double radius) {
		super(pos, radius);

		mission.setOfferArea(this);
		this.mission = mission;
		setSprite(new Sprite("mission_circle_offer", (int) radius * 2, (int) radius * 2).setAlpha(50));
	}

	@Override
	public void render(final Graphics g) {
		// don't render if player is aready inside a mission
		if (MissionManager.getInstance().isMissionActive())
			return;

		super.render(g);
	}

	@Override
	protected void onCollide() {
		// disable if player is already inside a mission
		if (MissionManager.getInstance().isMissionActive())
			return;

		MissionManager.getInstance().offerMission(mission);
	}
}
