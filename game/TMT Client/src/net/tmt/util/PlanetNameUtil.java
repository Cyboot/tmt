package net.tmt.util;

import java.util.ArrayList;
import java.util.List;

public class PlanetNameUtil {
	private static List<String>	planetNames	= new ArrayList<>();

	public static String getPlanetName(final int id) {
		return planetNames.get(Math.abs(id) % planetNames.size());
	}

	public static void init() {
		String readFromFile = FilereaderUtil.readFromFile("res/misc/moons.txt");

		for (String str : readFromFile.split("\n"))
			planetNames.add(str);
	}
}
