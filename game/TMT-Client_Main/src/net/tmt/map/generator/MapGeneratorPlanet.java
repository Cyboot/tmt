package net.tmt.map.generator;

import java.util.List;

import net.tmt.entity.ambient.Prop;
import net.tmt.entity.ambient.Prop.Type;
import net.tmt.game.manager.EntityManager;
import net.tmt.map.Chunk;
import net.tmt.map.Coordinate;
import net.tmt.map.PlanetMap;
import net.tmt.map.Terrain;
import net.tmt.map.WorldMap;
import net.tmt.map.generator.planet.GrassFiller;
import net.tmt.util.RandomUtil;
import net.tmt.util.Vector2d;

public class MapGeneratorPlanet {
	public static final int	MAX_X		= 50;
	public static final int	MAX_Y		= 50;

	private Terrain			baseTerrain;
	private EntityManager	entityManager;
	private WorldMap		map;

	private ChunkFiller		fillerGrass	= new GrassFiller();

	private char[][]		charMap		= new char[MAX_X][MAX_Y];

	public MapGeneratorPlanet(final Terrain baseTerrain) {
		this.baseTerrain = baseTerrain;
	}

	public void generate(final WorldMap worldMap, final EntityManager entityManager) {
		this.map = worldMap;
		this.entityManager = entityManager;

		RandomUtil.setSeed(RandomUtil.SEED);
		for (int y = 0; y < MAX_Y; y++) {
			for (int x = 0; x < MAX_X; x++) {
				charMap[x][y] = ' ';
				Coordinate coord = new Coordinate(x, y);
				map.addChunk(coord, new Chunk(coord, baseTerrain, map.getChunkSize()));
			}
		}

		generateStreets();
		generateBuildings();

		fillChunks();

		// syso
		for (int y = 0; y < MAX_Y; y++) {
			StringBuilder strBuilder = new StringBuilder();
			for (int x = 0; x < MAX_X; x++) {
				strBuilder.append(charMap[x][y]);
			}
			System.out.println(" " + strBuilder.toString());
		}
	}

	private void fillChunks() {
		for (int y = 0; y < MAX_Y; y++) {
			for (int x = 0; x < MAX_X; x++) {
				Chunk chunk = map.getChunk(new Coordinate(x, y));
				switch (chunk.getTerrain()) {
				case PLANET_GRASS:
					fillerGrass.fill(chunk, entityManager);
					break;
				default:
					break;
				}
			}
		}
	}


	private void generateBuildings() {
		int buildingCount = (MAX_X * MAX_Y) / 4;

		Coordinate coord = Coordinate.tmp0;
		for (int i = 0; i < buildingCount; i++) {
			Vector2d pos = new Vector2d(RandomUtil.intRange(0, MAX_X - 1), RandomUtil.intRange(0, MAX_Y - 1));

			if (map.getChunk(coord.set(pos.x(), pos.y())).getTerrain() == baseTerrain) {
				addChunk(pos, Terrain.PLANET_ASPHALT);

				Prop prop = Prop.createProp(Type.BUILDING_1, coord.center2pos(map.getChunkSize()),
						entityManager.getCollisionManager());
				entityManager.addEntity(prop.setPosZ(25));

				charMap[pos.x()][pos.y()] = '.';
			}
		}

	}

	public static void main(final String[] args) {
		WorldMap worldMap = new PlanetMap(null, -1);
		EntityManager entityManager = new EntityManager();

		new MapGeneratorPlanet(null).generate(worldMap, entityManager);
	}

	private void generateStreets() {
		int distanceBetween = 5;

		for (int x = 0; x < MAX_X; x += distanceBetween) {
			for (int y = 0; y < MAX_Y; y++) {
				Vector2d pos = new Vector2d(x, y);

				addChunk(pos, Terrain.PLANET_ASPHALT_STREET_90);
				charMap[pos.x()][pos.y()] = '|';
			}
		}
		for (int y = 0; y < MAX_X; y += distanceBetween) {
			for (int x = 0; x < MAX_Y; x++) {
				Vector2d pos = new Vector2d(x, y);

				if (x % distanceBetween == 0) {
					addChunk(pos, Terrain.PLANET_ASPHALT_STREET_INTERSECT);
					charMap[pos.x()][pos.y()] = '+';
				} else {
					addChunk(pos, Terrain.PLANET_ASPHALT_STREET);
					charMap[pos.x()][pos.y()] = '-';
				}
			}
		}
	}

	private void addChunk(final Vector2d pos, final Terrain terrain) {
		Coordinate coord = new Coordinate(pos.x(), pos.y());
		Chunk chunk = new Chunk(coord, terrain, map.getChunkSize());

		map.addChunk(coord, chunk);
	}

	private boolean checkInvalidDistance(final Vector2d toAdd, final List<Vector2d> list, final double minDistance) {
		for (Vector2d v : list) {
			if (v.distanceTo(toAdd) < minDistance)
				return true;
		}

		return false;
	}
}
