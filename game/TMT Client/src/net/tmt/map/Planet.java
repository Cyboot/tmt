package net.tmt.map;

import net.tmt.entity.Entity2D;
import net.tmt.gfx.Graphics;
import net.tmt.util.Vector2d;

import org.lwjgl.util.Color;


public class Planet extends Entity2D {

	private int			id;
	private PlanetMap	map;

	public Planet(final int i, final Vector2d pos, final int baseTerrain) {
		super(pos);
		id = i;
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
		g.setColor(Color.GREEN);
		g.drawCircle(pos.x, pos.y, 300);
	}

}
