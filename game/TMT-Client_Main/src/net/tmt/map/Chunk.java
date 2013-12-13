package net.tmt.map;

import net.tmt.game.interfaces.Renderable;
import net.tmt.gfx.Graphics;
import net.tmt.util.DebugUtil;
import net.tmt.util.Vector2d;

import org.lwjgl.util.Color;

public class Chunk implements Renderable {
	public Terrain		terrain;
	public int			size;
	private Coordinate	coord;

	private boolean		empty;

	public Chunk(final Coordinate coord, final Terrain terrain, final int size) {
		this.coord = new Coordinate(coord.x, coord.y);
		this.terrain = terrain;
		this.size = size;
	}


	@Override
	public void render(final Graphics g) {
		g.drawSprite(Vector2d.tmp1.set(getCoord().x * size, getCoord().y * size), terrain.getSprite());

		if (DebugUtil.renderCollision) {
			switch (terrain) {
			case SPACE_ASTEROID:
				g.setColor(Color.GREY);
				break;
			case SPACE_SUN:
				g.setColor(Color.YELLOW);
				break;
			case SPACE_BLACKHOLE:
				g.setColor(Color.DKGREY);
				break;
			case SPACE_DEBRIS:
				g.setColor(Color.ORANGE);
				break;
			case SPACE_MINEFIELD:
				g.setColor(Color.RED);
				break;
			case SPACE_NEBULA:
				g.setColor(Color.GREEN);
				break;
			default:
				g.setColor(Color.YELLOW);
				break;
			}
			g.drawRect(coord.x * size + 4, coord.y * size + 4, size - 8, size - 8);
		}
	}

	public Coordinate getCoord() {
		return coord;
	}

	public Terrain getTerrain() {
		return terrain;
	}

	public boolean freeToBuild() {
		return empty;
	}

	public void setEmpty(final boolean empty) {
		this.empty = empty;
	}
}
