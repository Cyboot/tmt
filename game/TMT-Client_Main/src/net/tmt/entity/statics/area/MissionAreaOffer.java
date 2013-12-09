package net.tmt.entity.statics.area;

import net.tmt.entity.component.animation.GlowComponent;
import net.tmt.game.manager.EntityManager;
import net.tmt.gfx.Graphics;
import net.tmt.gfx.Sprite;
import net.tmt.global.mission.Mission;
import net.tmt.global.mission.MissionManager;
import net.tmt.map.World;
import net.tmt.util.ColorUtil;
import net.tmt.util.Vector2d;

import org.lwjgl.util.Color;

public class MissionAreaOffer extends Area {
	private Mission	mission;
	private boolean	isActive;

	public MissionAreaOffer(final Vector2d pos, final Mission mission, final double radius, final boolean showArea) {
		super(pos, radius);

		mission.setOfferArea(this);
		this.mission = mission;

		if (showArea) {
			if (radius > 128)
				setSprite(new Sprite("mission_offer_512", (int) radius * 2, (int) radius * 2));
			else
				setSprite(new Sprite("mission_offer_128", (int) radius * 2, (int) radius * 2));
			addComponent(new GlowComponent((Color) ColorUtil.WHITE_ALPHA_100, (Color) ColorUtil.WHITE_ALPHA_50, 1.5));
		}
	}


	public MissionAreaOffer(final Vector2d pos, final Mission mission, final double radius) {
		this(pos, mission, radius, true);
	}

	@Override
	public void update(final EntityManager caller, final World world, final double delta) {
		super.update(caller, world, delta);


		// disable if player is already inside a mission
		isActive = !MissionManager.getInstance().isMissionActive();
	}

	@Override
	public void render(final Graphics g) {
		if (!isActive)
			return;

		super.render(g);
	}

	@Override
	protected void onCollide() {
		if (!isActive)
			return;

		MissionManager.getInstance().offerMission(mission);
	}
}
