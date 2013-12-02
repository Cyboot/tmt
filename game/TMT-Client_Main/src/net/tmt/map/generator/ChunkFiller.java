package net.tmt.map.generator;

import net.tmt.entity.Asteroid;
import net.tmt.entity.statics.Planet;
import net.tmt.game.manager.EntityManager;
import net.tmt.map.Chunk;
import net.tmt.map.Terrain;
import net.tmt.util.RandomUtil;
import net.tmt.util.Vector2d;

class ChunkFiller {

	public static void fillPlanet(final Chunk chunk, final EntityManager entityManager) {
		Vector2d pos = chunk.getCoord().center2pos(chunk.size);

		int seed = chunk.getCoord().hashCode();
		RandomUtil.setSeed(seed);
		int chance = RandomUtil.intRange(0, 6);

		Terrain terrain = null;
		switch (chance) {
		case 0:
			terrain = Terrain.PLANET_DESERT;
			break;
		case 1:
			terrain = Terrain.PLANET_FOREST;
			break;
		case 2:
			terrain = Terrain.PLANET_GRASS;
			break;
		case 3:
			terrain = Terrain.PLANET_MOUNTAIN;
			break;
		case 4:
			terrain = Terrain.PLANET_SNOW;
			break;
		case 5:
			terrain = Terrain.PLANET_SWAMP;
			break;
		case 6:
			terrain = Terrain.PLANET_WATER;
			break;
		}

		Planet p = new Planet(pos, terrain, 300);
		entityManager.addEntity(p, EntityManager.LAYER_1_BACK);
	}

	public static void fillAsteroid(final Chunk chunk, final EntityManager entityManager) {
		Vector2d pos = chunk.getCoord().center2pos(chunk.size);

		int seed = chunk.getCoord().hashCode();
		RandomUtil.setSeed(seed);
		for (int i = 0; i < 30; i++) {
			double deltaX = RandomUtil.doubleRange(-256, 256);
			double deltaY = RandomUtil.doubleRange(-256, 256);

			entityManager.addEntity(new Asteroid(pos.copy().add(deltaX, deltaY)), EntityManager.LAYER_2_MEDIUM);
		}
	}

	public static void fillDefault(final Chunk chunk, final EntityManager entityManager) {

		// add stars (not used now, because of terrain image)

		// final int DISTANCE = 200;
		// Vector2d v = chunk.coord.center2pos(chunk.size);
		// int fromX = (int) Math.rint(v.x);
		// int toX = fromX + chunk.size;
		// int fromY = (int) Math.rint(v.y);
		// int toY = fromY + chunk.size;
		// for (int x = fromX; x < toX; x += DISTANCE) {
		// for (int y = fromY; y < toY; y += DISTANCE) {
		// double vx = x + RandomUtil.doubleRange(-DISTANCE / 2, DISTANCE / 2);
		// double vy = y + RandomUtil.doubleRange(-DISTANCE / 2, DISTANCE / 2);
		//
		// chunk.addStaticEntity(new Star(new Vector2d(vx, vy)));
		// }
		// }
	}
}
