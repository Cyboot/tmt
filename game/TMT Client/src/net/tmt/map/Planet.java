package net.tmt.map;

import net.tmt.game.interfaces.Renderable;
import net.tmt.gfx.Graphics;
import net.tmt.util.Vector2d;

import org.lwjgl.util.Color;


public class Planet implements Renderable {

	private int			id;
	private Coordinate	coord;
	private PlanetMap	map;

	public Planet(final int i, final Coordinate c, final int baseTerrain) {
		id = i;
		coord = c;
		map = new PlanetMap(baseTerrain);
	}

	public PlanetMap getMap() {
		return map;
	}

	public int getId() {
		return id;
	}

	@Override
	public void render(final Graphics g) {
		Vector2d v = coord.center2pos(map.getChunkSize());
		g.setColor(Color.GREEN);
		g.drawCircle(v.x, v.y, 300);
	}

}
