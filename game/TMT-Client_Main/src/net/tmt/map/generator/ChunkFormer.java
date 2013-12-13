package net.tmt.map.generator;

import net.tmt.entity.ambient.Prop;
import net.tmt.entity.ambient.Prop.Type;
import net.tmt.game.manager.EntityManager;
import net.tmt.map.Chunk;
import net.tmt.map.Coordinate;
import net.tmt.map.PlanetChunk;
import net.tmt.map.PlanetMap;
import net.tmt.map.WorldMap;
import net.tmt.util.RandomUtil;
import net.tmt.util.Vector2d;

class ChunkFormer {

	public static Chunk formChunk(final Coordinate coord, final WorldMap map, final EntityManager entityManager) {
		if (map instanceof PlanetMap)
			return formPlanetChunk(coord, map, entityManager);
		else {
			return null;
			// throw new IllegalArgumentException("Maptype is not known");
		}
	}

	private static PlanetChunk formPlanetChunk(final Coordinate coord, final WorldMap map,
			final EntityManager entityManager) {
		RandomUtil.setSeed(coord.hashCode() ^ map.hashCode());

		PlanetChunk chunk = null;
		chunk = new PlanetChunk(coord, map.getBaseTerrain(), map.getChunkSize());

		addTrees(chunk, entityManager, map.getChunkSize());
		addDecals(chunk, entityManager, map.getChunkSize());

		return chunk;
	}

	private static void addDecals(final PlanetChunk chunk, final EntityManager entityManager, final int chunkSize) {
		Vector2d centerPos = chunk.getCoord().center2pos(chunkSize);
		for (int i = 0; i < RandomUtil.intRange(0, 2); i++) {

			Vector2d pos = centerPos.copy();
			pos.x += RandomUtil.doubleRange(-chunkSize / 2, chunkSize / 2);
			pos.y += RandomUtil.doubleRange(-chunkSize / 2, chunkSize / 2);

			Prop decal = Prop.createProp(Type.DECAL, pos);
			entityManager.addEntity(decal, EntityManager.LAYER_1_BACK);
		}
	}

	private static void addTrees(final Chunk chunk, final EntityManager entityManager, final int chunkSize) {
		Vector2d centerPos = chunk.getCoord().center2pos(chunkSize);
		for (int i = 0; i < RandomUtil.intRange(0, 2); i++) {

			Vector2d pos = centerPos.copy();
			pos.x += RandomUtil.doubleRange(-chunkSize / 2, chunkSize / 2);
			pos.y += RandomUtil.doubleRange(-chunkSize / 2, chunkSize / 2);

			Prop tree = Prop.createProp(Type.TREE, pos);
			entityManager.addEntity(tree);
		}
	}

}
