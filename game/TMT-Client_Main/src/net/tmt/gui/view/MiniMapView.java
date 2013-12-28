package net.tmt.gui.view;

import net.tmt.game.GameEngine;
import net.tmt.gfx.Graphics;
import net.tmt.gui.elements.ContainerElement;
import net.tmt.map.Chunk;
import net.tmt.map.Coordinate;
import net.tmt.map.World;
import net.tmt.map.WorldMap;
import net.tmt.util.Vector2d;

import org.lwjgl.util.Color;
import org.lwjgl.util.ReadableColor;

public class MiniMapView extends ContainerElement {
	private final static int	WIDTH	= 300;
	private final static int	HEIGHT	= 300;

	private int					chunksPerMap;

	private WorldMap			map;
	private World				world;

	public MiniMapView(final World world, final int chunksPerMap) {
		super(new Vector2d(0, GameEngine.HEIGHT - HEIGHT), WIDTH, HEIGHT);

		this.chunksPerMap = chunksPerMap;
		this.world = world;
		this.map = world.getMap();
	}

	@Override
	public void render(final Graphics g) {
		super.render(g);

		Coordinate upperLeftCoord = new Coordinate(world.getOffset(), map.getChunkSize());
		upperLeftCoord.x -= chunksPerMap / 2;
		upperLeftCoord.y -= chunksPerMap / 2;

		double chunkSizeonMiniMap = WIDTH / chunksPerMap;
		double dx = (world.getOffset().x % map.getChunkSize()) / map.getChunkSize();
		dx *= chunkSizeonMiniMap;
		double dy = (world.getOffset().y % map.getChunkSize()) / map.getChunkSize();
		dy *= chunkSizeonMiniMap;

		Coordinate coord = Coordinate.tmp0;
		for (int x = 0; x < chunksPerMap + 1; x++) {
			for (int y = 0; y < chunksPerMap + 1; y++) {
				double posX = pos.x + x * chunkSizeonMiniMap;
				double posY = pos.y + y * chunkSizeonMiniMap;
				double posWidth = chunkSizeonMiniMap;
				double posHeight = chunkSizeonMiniMap;

				coord.set(upperLeftCoord.x + x, upperLeftCoord.y + y);

				g.setColor(getColorForChunk(map.getChunk(coord)));
				g.onGui().fillRect(posX - dx, posY - dy, posWidth, posHeight);
			}
		}
		g.setColor(Color.WHITE);
		g.onGui().fillCircle(width / 2, pos.y() + height / 2, 2);
	}

	private ReadableColor getColorForChunk(final Chunk chunk) {
		if (chunk == null)
			return Color.BLACK;

		ReadableColor color = null;
		switch (chunk.getTerrain()) {
		case PLANET_ASPHALT:
			color = Color.GREY;
			break;
		case PLANET_ASPHALT_STREET:
		case PLANET_ASPHALT_STREET_90:
		case PLANET_ASPHALT_STREET_INTERSECT:
			color = Color.DKGREY;
			break;
		case PLANET_GRASS:
			color = Color.GREEN;
			break;
		case SPACE_SUN:
			color = Color.YELLOW;
			break;
		default:
			color = Color.BLACK;
			break;
		}

		return color;
	}
}
