package net.tmt.map;

import net.tmt.entity.Entity2D;
import net.tmt.gfx.Graphics;
import net.tmt.util.Vector2d;

import org.lwjgl.util.Color;

// TODO: hook up planet entries with game states and move planet classes into entity package
public class Planet extends Entity2D {

	private static int	currID	= 0;
	private int			id;
	private PlanetMap	map;

	public Planet(final Vector2d pos, final int baseTerrain) {
		super(pos);
		id = currID;
		Planet.currID++;
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
		case 200:
			g.setColor(Color.GREEN);
			break;
		case 201:
			g.setColor(Color.BLUE);
			break;
		case 202:
			g.setColor(Color.YELLOW);
			break;
		case 203:
			g.setColor(Color.WHITE);
			break;
		case 204:
			g.setColor(Color.DKGREY);
			break;
		case 205:
			g.setColor(Color.PURPLE);
			break;
		}
		g.drawCircle(pos.x, pos.y, 300);
	}

}
