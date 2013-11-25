package net.tmt.main;

import java.io.File;

import net.tmt.game.GameEngine;

import org.lwjgl.LWJGLException;

public class TMTMain {
	public static void main(final String[] args) throws LWJGLException {
		checkOS();
		new GameEngine().start();
	}

	/**
	 * check and finds the natives for the running OS
	 */
	private static void checkOS() {
		String osName = System.getProperty("os.name");
		String nativeDir = "";
		if (osName.startsWith("Windows")) {
			nativeDir += "windows";
		} else if (osName.startsWith("Linux") || osName.startsWith("FreeBSD")) {
			nativeDir += "linux";
		} else if (osName.startsWith("Mac OS X")) {
			nativeDir += "macosx";
		} else if (osName.startsWith("Solaris") || osName.startsWith("SunOS")) {
			nativeDir += "solaris";
		} else {
			System.out.println("Unsupported OS: " + osName + ". Exiting.");
		}

		File nativeFile = new File("lib" + File.separator + "native" + File.separator + nativeDir);

		System.out.println("LWJGL-Path: " + nativeFile.getAbsolutePath());

		System.setProperty("org.lwjgl.librarypath", nativeFile.getAbsolutePath());
	}
}
