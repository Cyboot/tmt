package net.tmt.map.generator;

import java.util.ArrayList;
import java.util.List;

import net.tmt.util.RandomUtil;
import net.tmt.util.Vector2d;

public class Test {
	private static final int		MAX_X				= 170;
	private static final int		MAX_Y				= 150;

	private static final int		MIN_DISTANCE_STARS	= 20;
	private static List<Vector2d>	stars				= new ArrayList<>();
	private static List<Vector2d>	combined			= new ArrayList<>();
	private static char[][]			map					= new char[MAX_X][MAX_Y];

	public static void main(final String[] args) {
		RandomUtil.setSeed((int) (Math.random() * 10000));
		for (int y = 0; y < MAX_Y; y++) {
			for (int x = 0; x < MAX_X; x++) {
				map[x][y] = ' ';
			}
		}

		addStars();


		// syso
		for (int y = 0; y < MAX_Y; y++) {
			StringBuilder strBuilder = new StringBuilder();
			for (int x = 0; x < MAX_X; x++) {
				strBuilder.append(map[x][y]);
			}
			System.out.println(" " + strBuilder.toString());
		}

	}

	private static void addStars() {
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
			} while (checkInvalidDistance(star, stars, MIN_DISTANCE_STARS));
			stars.add(star);
			combined.add(star);
			addPlanet(star, RandomUtil.intRange(1, 7));

			map[star.x()][star.y()] = '●';
		}

	}

	private static void addPlanet(final Vector2d star, final int count) {
		int MAX_RADIUS = 4;

		for (int i = 0; i < count; i++) {
			int x = RandomUtil.intRange(-MAX_RADIUS, MAX_RADIUS);
			int y = RandomUtil.intRange(-MAX_RADIUS, MAX_RADIUS);

			if (x == 0 && y == 0)
				continue;

			Vector2d planet = star.copy().add(x, y);

			if (planet.x() > 0 && planet.x() < MAX_X && planet.y() > 0 && planet.y() < MAX_Y) {
				map[planet.x()][planet.y()] = '◌';
				combined.add(planet);
			}
		}
	}

	private static boolean checkInvalidDistance(final Vector2d toAdd, final List<Vector2d> list,
			final double minDistance) {
		for (Vector2d v : list) {
			if (v.distanceTo(toAdd) < minDistance)
				return true;
		}

		return false;
	}
}
