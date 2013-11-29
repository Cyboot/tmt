package net.tmt.entity.statics.area;

import net.tmt.gfx.Graphics;
import net.tmt.gfx.Sprite;
import net.tmt.global.mission.MissionDispatcher;
import net.tmt.util.Vector2d;

import org.lwjgl.util.Color;

public class MissionAreaAction extends Area {
	private static final int	RADIUS		= 128;

	private boolean				isActive	= true;
	private boolean				isVisible	= true;
	private int					areaId		= 0;

	public MissionAreaAction(final Vector2d pos) {
		this(pos, 0);
	}

	public MissionAreaAction(final Vector2d pos, final int areaId) {
		super(pos, RADIUS);
		this.areaId = areaId;

		setSprite(new Sprite("mission_circle", RADIUS * 2, RADIUS * 2).setBlendColor((Color) Color.PURPLE));
	}

	@Override
	public void render(final Graphics g) {
		if (isVisible) {
			super.render(g);
		}
	}

	@Override
	protected void onCollide() {
		if (isActive && isAlive())
			MissionDispatcher.dispatch(this, "" + areaId);
	}

	public void setActive(final boolean active) {
		this.isActive = active;
	}

	public int getAreaId() {
		return areaId;
	}

	public void setVisible(final boolean isVisible) {
		this.isVisible = isVisible;
	}

	public void setColor(final Color color) {
		getSprite().setBlendColor(color);
	}
}
