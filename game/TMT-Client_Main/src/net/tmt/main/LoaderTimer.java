package net.tmt.main;

import java.util.HashMap;
import java.util.Map;

public class LoaderTimer {
	private static Map<String, Long>	timeMap	= new HashMap<>();

	public static void start(final String string) {
		timeMap.put(string, System.currentTimeMillis());
	}

	public static void stop(final String string) {
		Long startTime = timeMap.get(string);
		long elapsed = System.currentTimeMillis() - startTime;

		System.err.println("Loading " + string + " took " + elapsed + " ms");
		timeMap.put(string, elapsed);
	}
}
