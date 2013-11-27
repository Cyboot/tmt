package net.tmt.game;

import static org.lwjgl.opengl.GL11.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import net.tmt.game.manager.GameManager;
import net.tmt.gamestate.AbstractGamestate;
import net.tmt.gfx.Graphics;
import net.tmt.gfx.Sprite;
import net.tmt.global.achievments.Achievments;
import net.tmt.global.stats.Stats;
import net.tmt.map.Terrain;
import net.tmt.util.ConfigUtil;
import net.tmt.util.CountdownTimer;
import net.tmt.util.DebugUtil;
import net.tmt.util.PlanetNameUtil;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Controllers;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

public class GameEngine {
	private final FPS		fps		= new FPS();
	private GameManager		gameManager;
	private Graphics		graphics;

	private CountdownTimer	timerMs	= new CountdownTimer(1);

	public static int		WIDTH;
	public static int		HEIGHT;

	public void start() throws LWJGLException {
		initConfig();
		initGL();
		initNonGL();

		while (!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			fps.updateBefore();
			DebugUtil.update();
			Controls.update();

			long delta = fps.getDelta();

			if (Math.abs(delta) < 100) {
				update(delta / 1000.);
				render();
			}
			fps.updateAfter();

			if (timerMs.isTimeUp(delta / 1000.)) {
				updateTitle();
			}

			Display.update();
			Display.sync(100);
		}

	}

	/** Updates the title of the window with gameinfos */
	private void updateTitle() {
		timerMs.reset();
		long lastMs = fps.getElapsedTime();

		String activeClass = gameManager.getActiveGamestate().getClass().getSimpleName();
		String backgroundClasses = "";
		if (!gameManager.getBackgroundGamestates().isEmpty()) {
			backgroundClasses = " (";

			for (AbstractGamestate a : gameManager.getBackgroundGamestates())
				backgroundClasses += a.getClass().getSimpleName() + " | ";

			backgroundClasses = backgroundClasses.substring(0, backgroundClasses.length() - 3) + ")";
		}

		Display.setTitle(activeClass + "    @ " + lastMs + " ms  " + backgroundClasses);
	}

	private void initConfig() {
		try {
			String cfg = "res" + File.separator + "cfg" + File.separator + "controls.cfg";
			String cfgGamepad = "res" + File.separator + "cfg" + File.separator + "controls_gamepad.cfg";
			Controls.init(cfg);
			Controls.init(cfgGamepad);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			String globalCFG = "res" + File.separator + "cfg" + File.separator + "global.cfg";
			String localCFG = "res" + File.separator + "cfg" + File.separator + "local.cfg";
			ConfigUtil.init(globalCFG); // init global configs
			ConfigUtil.init(localCFG); // override with local configs
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		WIDTH = ConfigUtil.getInt("display.width");
		HEIGHT = ConfigUtil.getInt("display.height");
	}

	private void update(final double delta) {
		gameManager.update(delta);
	}

	private void initNonGL() {
		try {
			Sprite.init();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PlanetNameUtil.init();
		Achievments.init();
		Terrain.init();
		graphics = Graphics.init();
		gameManager = GameManager.init();

		try {
			Controllers.create();
		} catch (LWJGLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Stats.init();

		try {
			DebugUtil.setUser(ConfigUtil.getString("debug.User"));
		} catch (Exception e) {
		}
	}

	private void initGL() throws LWJGLException {
		Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
		Display.setLocation(ConfigUtil.getInt("display.offsetX"), ConfigUtil.getInt("display.offsetY"));
		Display.setVSyncEnabled(true);
		Display.create();

		glEnable(GL11.GL_TEXTURE_2D);
		glEnable(GL_BLEND);

		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, WIDTH, HEIGHT, 0, -1, 1);
		glMatrixMode(GL_MODELVIEW);
		glClearColor(0, 0, 0, 0);
	}

	private void render() {
		glClear(GL_COLOR_BUFFER_BIT);

		gameManager.render(graphics);
	}

	/**
	 * calculates the Delta/FPS
	 * 
	 */
	private static class FPS {
		private long	lastFrame;
		private long	delta;
		private long	elapsedTime;

		private long getTime() {
			return (Sys.getTime() * 1000) / Sys.getTimerResolution();
		}

		/**
		 * call after update() and render()
		 */
		public void updateAfter() {
			elapsedTime = (int) (getTime() - lastFrame);
		}

		/**
		 * call immediatly in while() loop
		 */
		public void updateBefore() {
			long time = getTime();
			delta = (time - lastFrame);
			lastFrame = time;
		}

		public long getDelta() {
			return delta;
		}

		/**
		 * @return time elapsed of the update() and render()-methods
		 */
		public long getElapsedTime() {
			return elapsedTime;
		}
	}

}
