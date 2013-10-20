package net.tmt.map;

import java.util.ArrayList;
import java.util.List;

import net.tmt.entity.ControlledSpaceShip;
import net.tmt.game.GameEngine;
import net.tmt.game.Renderable;
import net.tmt.game.Updateable;
import net.tmt.gfx.Graphics;
import net.tmt.util.RandumUtil;
import net.tmt.util.Vector2d;

import org.lwjgl.util.Color;

public class World implements Renderable, Updateable {
	private static final double	RATIO	= 2.5;
	private double				MOVE_DIFF_WIDTH;
	private double				MOVE_DIFF_HEIGHT;
	private double				MOVE_MAX_WIDTH;
	private double				MOVE_MAX_HEIGTH;

	private static World		instance;

	private Vector2d			tmp		= new Vector2d();
	private Vector2d			offset	= new Vector2d();

	// DEBUG:
	private List<Vector2d>		stars	= new ArrayList<>();

	private ControlledSpaceShip	player;

	public World() {
		// TODO: quick & dirty: Worldoffset
		MOVE_MAX_WIDTH = (GameEngine.WIDTH / 2) * 1 / 50.;
		MOVE_DIFF_WIDTH = (GameEngine.WIDTH / 2 - MOVE_MAX_WIDTH) * RATIO;

		MOVE_MAX_HEIGTH = (GameEngine.HEIGHT / 2) * 1 / 50.;
		MOVE_DIFF_HEIGHT = (GameEngine.HEIGHT / 2 - MOVE_MAX_HEIGTH) * RATIO;

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
		centerAroundShip();
	}

	private void centerAroundShip() {
		double dx = getOffsetCentered().x - player.getPos().x;
		double dy = getOffsetCentered().y - player.getPos().y;

		if (dx > MOVE_MAX_WIDTH || dx < -MOVE_MAX_WIDTH) {
			double dxInner = Math.abs(dx) - MOVE_MAX_WIDTH;
			double factor = dxInner / MOVE_DIFF_WIDTH;
			factor = Math.pow(factor, 3);

			if (dx > 0)
				offset.x -= factor * dxInner;
			else
				offset.x += factor * dxInner;
		}

		if (dy > MOVE_MAX_HEIGTH || dy < -MOVE_MAX_HEIGTH) {
			double dyInner = Math.abs(dy) - MOVE_MAX_HEIGTH;
			double factor = dyInner / MOVE_DIFF_HEIGHT;
			factor = Math.pow(factor, 3);

			if (dy > 0)
				offset.y -= factor * dyInner;
			else
				offset.y += factor * dyInner;
		}
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

	private Vector2d getOffsetCentered() {
		tmp.x = offset.x + GameEngine.WIDTH / 2;
		tmp.y = offset.y + GameEngine.HEIGHT / 2;
		return tmp;
	}

	public static void init() {
		instance = new World();
	}

	public static World getInstance() {
		return instance;
	}

	public void setPlayer(final ControlledSpaceShip ship) {
		this.player = ship;
	}
}
