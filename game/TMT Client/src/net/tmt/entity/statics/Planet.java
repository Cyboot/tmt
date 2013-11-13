package net.tmt.entity.statics;

import net.tmt.entity.Entity2D;
import net.tmt.game.manager.MissionManager;
import net.tmt.gfx.Graphics;
import net.tmt.map.Terrain;
import net.tmt.map.World;
import net.tmt.mission.Mission;
import net.tmt.mission.PlanetMission;
import net.tmt.util.Vector2d;

import org.lwjgl.util.Color;

public class Planet extends Entity2D {
	private static int	currID	= 0;

	private Mission		mission;
	private int			planetId;
	private int			radius;
	private Terrain		baseTerrain;

	public Planet(final Vector2d pos, final Terrain baseTerrain, final int radius) {
		super(pos);
		planetId = currID++;
		this.radius = radius;
		this.baseTerrain = baseTerrain;
		mission = new PlanetMission(this);

		setColor();
	}

	private void setColor() {

	}

	public int getPlanetId() {
		return planetId;
	}

	public void landingCheck(final Vector2d playerPos) {
		if (World.getActiveWorld().getPlayer().getPos().distanceTo(pos) < getRadius()) {
			MissionManager.getInstance().offerMission(mission);
		}
	}

	@Override
	public void render(final Graphics g) {
		switch (baseTerrain) {
		case PLANET_GRASS:
			g.setColor(Color.GREEN);
			break;
		case PLANET_WATER:
			g.setColor(Color.BLUE);
			break;
		case PLANET_DESERT:
			g.setColor(Color.YELLOW);
			break;
		case PLANET_SNOW:
			g.setColor(Color.WHITE);
			break;
		case PLANET_FOREST:
			g.setColor(Color.DKGREY);
			break;
		case PLANET_SWAMP:
			g.setColor(Color.PURPLE);
			break;
		case PLANET_MOUNTAIN:
			g.setColor(Color.ORANGE);
			break;
		default:
			break;
		}
		g.drawSprite(pos, baseTerrain.getSprite());
		g.drawCircle(pos.x, pos.y, radius);
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(final int radius) {
		this.radius = radius;
	}

	public Terrain getBaseTerrain() {
		return baseTerrain;
	}

	@Override
	public String toString() {
		return super.toString() + "[terrain=" + baseTerrain.name() + "]";
	}
}
