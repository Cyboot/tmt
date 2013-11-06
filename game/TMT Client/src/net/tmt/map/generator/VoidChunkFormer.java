package net.tmt.map.generator;

import net.tmt.entity.statics.Planet;
import net.tmt.map.Chunk;
import net.tmt.util.Vector2d;

public class VoidChunkFormer {

	public static void fill(final Chunk c) {
		if (Math.random() > 0.6) {
			Vector2d pos = c.coord.center2pos(c.size);
			// DEBUG planet terrain constants rage from 200 - 205
			int baseTerrain = 200 + ((int) Math.rint(Math.random() * 5));
			c.addStaticEntity(new Planet(pos, baseTerrain));
		}
	}
}
