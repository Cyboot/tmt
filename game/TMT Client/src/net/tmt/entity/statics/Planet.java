package net.tmt.entity.statics;

import net.tmt.entity.Entity2D;
import net.tmt.gfx.Graphics;
import net.tmt.map.PlanetMap;
import net.tmt.map.WorldMap;
import net.tmt.util.Vector2d;

import org.lwjgl.util.Color;

// TODO: hook up planet entries with game states and move planet classes into entity package
public class Planet extends Entity2D {

	private static int	currID	= 0;
	private int			id;
	private PlanetMap	map;
	private int			radius;

	public Planet(final Vector2d pos, final int baseTerrain) {
		super(pos);
		id = currID;
		Planet.currID++;
		radius = 300;
		map = new PlanetMap(baseTerrain);
	}

	public PlanetMap getMap() {
		return map;
	}

	public int getPlanetId() {
		return id;
	}

	@Override
	public void render(final Graphics g) {
		// Vector2d v = coord.center2pos(map.getChunkSize());
		// DEBUG: debug planet color
		switch (map.getBaseTerrain()) {
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
