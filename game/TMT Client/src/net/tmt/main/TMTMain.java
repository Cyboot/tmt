package net.tmt.main;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import net.tmt.game.GameEngine;

import org.lwjgl.LWJGLException;

public class TMTMain {
	public static void main(final String[] args) throws LWJGLException {
		checkOS();
		new GameEngine().start();
		// MapController mc = MapController.getInstance();
		// SpaceMap sm = mc.getSpaceMap(new Vector2d(0, 0));
		// sm = mc.getSpaceMap(new Vector2d(300, 300));
		// sm = mc.getSpaceMap(new Vector2d(600, 600));
		// sm = mc.getSpaceMap(new Vector2d(-1000, -1000));
		// sm = mc.getSpaceMap(new Vector2d(600, 900));
		// sm = mc.getSpaceMap(new Vector2d(900, 900));
		// sm = mc.getSpaceMap(new Vector2d(900, 900));
		// sm.debugPrint();
	}

	/**
	 * check and finds the natives for the running OS
	 */
	private static void checkOS() {
		/*
		 * Set lwjgl library path so that LWJGL finds the natives depending on
		 * the OS.
		 */
		String osName = System.getProperty("os.name");
		// Get .jar dir. new File(".") and property "user.dir" will not work if
		// .jar is called from
		// a different directory, e.g. java -jar /someOtherDirectory/myApp.jar
		String nativeDir = "";
		try {
			nativeDir = new File(TMTMain.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParent();
		} catch (URISyntaxException uriEx) {
			try {
				// Try to resort to current dir. May still fail later due to bad
				// start dir.
				uriEx.printStackTrace();
				nativeDir = new File(".").getCanonicalPath();
			} catch (IOException ioEx) {
				// Completely failed
				ioEx.printStackTrace();
				System.out.println("Failed to locate native library directory. " + "Error:\n" + ioEx.toString());
				System.exit(-1);
			}
		}
		// Append library subdir
		nativeDir += File.separator + "lib" + File.separator + "lwjgl" + File.separator + "native" + File.separator;
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
			try {
				System.in.read();
			} catch (IOException ex) {
				try {
					Thread.sleep(5000);
				} catch (InterruptedException ex1) {
					ex1.printStackTrace();
				}
			}
			System.exit(-1);
		}

		System.setProperty("org.lwjgl.librarypath", nativeDir);
	}
}
