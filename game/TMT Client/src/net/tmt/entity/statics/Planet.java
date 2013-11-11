package net.tmt.entity.statics;

import net.tmt.entity.Entity2D;
import net.tmt.gfx.Graphics;
import net.tmt.map.World;
import net.tmt.map.WorldMap;
import net.tmt.util.Vector2d;

import org.lwjgl.util.Color;

public class Planet extends Entity2D {

	private static int	currID	= 0;
	private int			planetId;
	private int			radius;
	private int			baseTerrain;

	public Planet(final Vector2d pos, final int baseTerrain) {
		super(pos);
		planetId = currID;
		Planet.currID++;
		radius = 300;
		this.baseTerrain = baseTerrain;
	}

	public int getPlanetId() {
		return planetId;
	}

	public void landingCheck(final World world, final Vector2d playerPos) {
		if (playerPos.distanceTo(getPos()) < getRadius()) {
			world.changeToPlanetGameState(planetId);
		}
	}

	@Override
	public void render(final Graphics g) {
		// DEBUG: debug planet color
		switch (baseTerrain) {
		case WorldMap.TERRAIN_GRASS:
			g.setColor(Color.GREEN);
			break;
		case WorldMap.TERRAIN_WATER:
			g.setColor(Color.BLUE);
			break;
		case WorldMap.TERRAIN_DESERT:
			g.setColor(Color.YELLOW);
			break;
		case WorldMap.TERRAIN_SNOW:
			g.setColor(Color.WHITE);
			break;
		case WorldMap.TERRAIN_FOREST:
			g.setColor(Color.DKGREY);
			break;
		case WorldMap.TERRAIN_SWAMP:
			g.setColor(Color.PURPLE);
			break;
		}
		g.drawCircle(pos.x, pos.y, radius);
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(final int radius) {
		this.radius = radius;
	}

}
