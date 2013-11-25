package net.tmt.global.stats;

import java.util.HashMap;
import java.util.Map;

public class Stats {
	private static Map<String, Object>	statisticsMap	= new HashMap<>();

	public static void add(final String key, final double value) {
		double current = (double) statisticsMap.get(key);
		statisticsMap.put(key, current + value);
	}

	public static void add(final String key, final int value) {
		int current = (int) statisticsMap.get(key);
		statisticsMap.put(key, current + value);
	}

	public static void init() {
		Space.init();
	}

	public static final class Space {
		public static final String	SHOOTS_FIRED		= "SHOOTS_FIRED";
		public static final String	DISTANCE_TRAVELLED	= "DISTANCE_TRAVELLED";

		public static void init() {
			statisticsMap.put(SHOOTS_FIRED, 0);
			statisticsMap.put(DISTANCE_TRAVELLED, 0.);
		}
	}
}
