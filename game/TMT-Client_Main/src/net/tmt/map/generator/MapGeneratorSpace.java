package net.tmt.map.generator;

import java.util.ArrayList;
import java.util.List;

import net.tmt.game.manager.EntityManager;
import net.tmt.map.Chunk;
import net.tmt.map.Coordinate;
import net.tmt.map.Terrain;
import net.tmt.map.WorldMap;
import net.tmt.map.generator.space.AsteroidFiller;
import net.tmt.map.generator.space.BlackholeFiller;
import net.tmt.map.generator.space.DebrisFiller;
import net.tmt.map.generator.space.MinefieldFiller;
import net.tmt.map.generator.space.NebulaFiller;
import net.tmt.map.generator.space.SunFiller;
import net.tmt.util.RandomUtil;
import net.tmt.util.Vector2d;

class MapGeneratorSpace {
	private static final int	MAX_X							= 170;
	private static final int	MAX_Y							= 150;

	private static final int	MIN_DISTANCE_BETWEEN_STARS		= 20;
	private static final int	CHUNK_OUTSIDE_STARS				= 12;

	private static final double	MIN_DISTANCE_BETWEEN_SAME_STUFF	= 10;
	private static final int	MIN_DISTANCE_BETWEEN_STUFF		= 3;

	private List<Vector2d>		stars							= new ArrayList<>();
	private List<Vector2d>		stuff							= new ArrayList<>();
	private char[][]			charMap							= new char[MAX_X][MAX_Y];

	private ChunkFiller			fillerAsteroid					= new AsteroidFiller();
	private ChunkFiller			fillerBlackhole					= new BlackholeFiller();
	private ChunkFiller			fillerDebris					= new DebrisFiller();
	private ChunkFiller			fillerMinefield					= new MinefieldFiller();
	private ChunkFiller			fillerNebula					= new NebulaFiller();
	private ChunkFiller			fillerSun						= new SunFiller();

	private WorldMap			map;
	private EntityManager		entityManager;

	public void generate(final WorldMap worldMap, final EntityManager entityManager) {
		this.map = worldMap;
		this.entityManager = entityManager;

		RandomUtil.setSeed(RandomUtil.SEED);
		for (int y = 0; y < MAX_Y; y++) {
			for (int x = 0; x < MAX_X; x++) {
				charMap[x][y] = ' ';
				Coordinate coord = new Coordinate(x, y);
				map.addChunk(coord, new Chunk(coord, Terrain.SPACE_VOID, map.getChunkSize()));
			}
		}

		generateStars();
		generateStuff(Terrain.SPACE_ASTEROID);
		generateStuff(Terrain.SPACE_DEBRIS);
		generateStuff(Terrain.SPACE_MINEFIELD);
		generateStuff(Terrain.SPACE_NEBULA);
		generateStuff(Terrain.SPACE_BLACKHOLE);

		fillChunks();


		// syso
		for (int y = 0; y < MAX_Y; y++) {
			StringBuilder strBuilder = new StringBuilder();
			for (int x = 0; x < MAX_X; x++) {
				strBuilder.append(charMap[x][y]);
			}
			// System.out.println(" " + strBuilder.toString());
		}
	}

	private void fillChunks() {
		for (int y = 0; y < MAX_Y; y++) {
			for (int x = 0; x < MAX_X; x++) {
				Chunk chunk = map.getChunk(new Coordinate(x, y));
				switch (chunk.getTerrain()) {
				case SPACE_ASTEROID:
					fillerAsteroid.fill(chunk, entityManager);
					break;
				case SPACE_BLACKHOLE:
					fillerBlackhole.fill(chunk, entityManager);
					break;
				case SPACE_DEBRIS:
					fillerDebris.fill(chunk, entityManager);
					break;
				case SPACE_MINEFIELD:
					fillerMinefield.fill(chunk, entityManager);
					break;
				case SPACE_NEBULA:
					fillerNebula.fill(chunk, entityManager);
					break;
				case SPACE_SUN:
					fillerSun.fill(chunk, entityManager);
					break;
				default:
					break;
				}
			}
		}
	}

	private void generateStuff(final Terrain terrain) {
		List<Vector2d> sameStaff = new ArrayList<>();

		Add:
		for (int i = 0; i < 100; i++) {
			Vector2d ast;
			int tries = 0;

			do {
				if (tries > 256) {
					System.out.println(terrain.name() + ": " + i);
					break Add;
				}

				ast = new Vector2d(RandomUtil.intRange(0, MAX_X - 1), RandomUtil.intRange(0, MAX_Y - 1));

				tries++;
			} while (checkInvalidDistance(ast, sameStaff, MIN_DISTANCE_BETWEEN_SAME_STUFF)
					|| checkInvalidDistance(ast, stars, CHUNK_OUTSIDE_STARS)
					|| checkInvalidDistance(ast, stuff, MIN_DISTANCE_BETWEEN_STUFF));
			addChunk(ast, Terrain.SPACE_ASTEROID);

			charMap[ast.x()][ast.y()] = terrain.name().charAt(0);
			stuff.add(ast);
			sameStaff.add(ast);
		}
	}

	private void generateStars() {
		Add:
		for (int i = 0;; i++) {

			Vector2d star;
			int tries = 0;
			do {
				if (tries > 256) {
					System.out.println("Stars: " + i);
					break Add;
				}

				star = new Vector2d(RandomUtil.intRange(0, MAX_X - 1), RandomUtil.intRange(0, MAX_Y - 1));
				tries++;
			} while (checkInvalidDistance(star, stars, MIN_DISTANCE_BETWEEN_STARS));
			addChunk(star, Terrain.SPACE_SUN);

			stars.add(star);

			charMap[star.x()][star.y()] = '●';
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
