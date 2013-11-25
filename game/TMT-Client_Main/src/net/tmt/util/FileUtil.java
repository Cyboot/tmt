package net.tmt.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class FileUtil {

	public static String readFromFile(final String file) {
		BufferedReader br;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e.getMessage());
		}
		StringBuilder str = new StringBuilder();

		String line = null;
		try {
			while ((line = br.readLine()) != null) {
				str.append(line + "\n");
			}
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}

		try {
			br.close();
		} catch (IOException e) {
		}
		return str.toString();
	}

	public static void appendToFile(final String file, final String append) {
		PrintWriter printWriter;
		try {
			printWriter = new PrintWriter(new FileWriter(file, true));
			printWriter.write(append);
			printWriter.close();
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}
	}
}
