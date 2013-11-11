package net.tmt.map.generator;

import net.tmt.entity.statics.BackgroundBody;
import net.tmt.entity.statics.Star;
import net.tmt.map.Chunk;
import net.tmt.map.World;
import net.tmt.util.RandomUtil;
import net.tmt.util.Vector2d;

public class GeneralSpaceChunkFiller {

	public static void fill(final World world, final Chunk c) {
		final int DISTANCE_BACK = 200 * 48;
		final int DISTANCE = 200;

		Vector2d v = c.coord.center2pos(c.size);
		int fromX = (int) Math.rint(v.x);
		int toX = fromX + c.size;
		int fromY = (int) Math.rint(v.y);
		int toY = fromY + c.size;

		for (int x = fromX; x < toX; x += DISTANCE_BACK) {
			for (int y = fromY; y < toY; y += DISTANCE_BACK) {
				double vx = x + RandomUtil.doubleRange(-DISTANCE_BACK / 4, DISTANCE_BACK / 4);
				double vy = y + RandomUtil.doubleRange(-DISTANCE_BACK / 4, DISTANCE_BACK / 4);

				c.addStaticEntity(new BackgroundBody(new Vector2d(vx, vy)));
			}
		}
		for (int x = fromX; x < toX; x += DISTANCE) {
			for (int y = fromY; y < toY; y += DISTANCE) {
				double vx = x + RandomUtil.doubleRange(-DISTANCE / 2, DISTANCE / 2);
				double vy = y + RandomUtil.doubleRange(-DISTANCE / 2, DISTANCE / 2);

				c.addStaticEntity(new Star(new Vector2d(vx, vy)));
			}
		}
	}
}
