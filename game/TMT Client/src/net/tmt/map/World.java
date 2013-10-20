package net.tmt.map;

import java.util.ArrayList;
import java.util.List;

import net.tmt.game.Renderable;
import net.tmt.game.Updateable;
import net.tmt.gfx.Graphics;
import net.tmt.util.RandumUtil;
import net.tmt.util.Vector2d;

import org.lwjgl.util.Color;

public class World implements Renderable, Updateable {
	private static World	instance	= new World();

	private Vector2d		offset		= new Vector2d();

	// DEBUG:
	private List<Vector2d>	stars		= new ArrayList<>();

	public World() {
		final int DISTANCE = 200;
		final int MAX_MAP_SIZE = 20 * 1000;

		for (int x = -MAX_MAP_SIZE; x < MAX_MAP_SIZE; x += DISTANCE) {
			for (int y = -MAX_MAP_SIZE; y < MAX_MAP_SIZE; y += DISTANCE) {
				double vx = x + RandumUtil.doubleRange(-DISTANCE / 2, DISTANCE / 2);
				double vy = y + RandumUtil.doubleRange(-DISTANCE / 2, DISTANCE / 2);

				stars.add(new Vector2d(vx, vy));
			}
		}
	}

	@Override
	public void update(final double delta) {

	}

	@Override
	public void render(final Graphics g) {
		g.setColor(Color.WHITE);
		for (Vector2d v : stars) {
			g.fillCircle(v.x, v.y, 1);
		}
	}

	public Vector2d getOffset() {
		return offset;
	}

	public static World getInstance() {
		return instance;
	}
}
