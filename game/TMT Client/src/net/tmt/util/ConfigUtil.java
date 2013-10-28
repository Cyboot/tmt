package net.tmt.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import net.tmt.main.TMTMain;

public class ConfigUtil {
	private static Map<String, String>	valuePairs;

	/**
	 * read config values from specified file. Can be used for multiple files,
	 * duplicated values will be overriden
	 * 
	 * @param file
	 *            config file
	 * @throws FileNotFoundException
	 */
	public static void init(final String file) throws FileNotFoundException {
		if (valuePairs == null)
			valuePairs = new HashMap<>();

		BufferedReader br = new BufferedReader(new InputStreamReader(TMTMain.class.getResourceAsStream(file)));

		String line = null;
		try {
			int lineNr = 1;
			while ((line = br.readLine()) != null) {
				// ignore comments and white lines
				if (line.startsWith("#") || line.trim().isEmpty())
					continue;

				// syntax error
				if (!line.contains(":") || line.split(":").length != 2)
					throw new RuntimeException("config file corrupted at line #" + lineNr);

				String key = line.split(":")[0].trim().toLowerCase();
				String value = line.split(":")[1].trim();

				lineNr++;
				valuePairs.put(key, value);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
			}
		}
	}

	public static String getString(final String key) {
		if (valuePairs == null)
			throw new RuntimeException("Class not initialized. Use init() before using");
		if (!valuePairs.containsKey(key.toLowerCase()))
			throw new IllegalArgumentException("config file contains no such key");

		return valuePairs.get(key.toLowerCase());
	}

	public static long getLong(final String key) {
		return Long.parseLong(getString(key));
	}

	public static int getInt(final String key) {
		return Integer.parseInt(getString(key));
	}

	public static boolean getBoolean(final String key) {
		return Boolean.parseBoolean(getString(key));
	}

	public static double getDouble(final String key) {
		return Double.parseDouble(getString(key));
	}

	public static double getFloat(final String key) {
		return Float.parseFloat(getString(key));
	}
}
