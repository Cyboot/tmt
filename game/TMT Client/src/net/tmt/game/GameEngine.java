package net.tmt.game;

import static org.lwjgl.opengl.GL11.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import net.tmt.game.manager.GameManager;
import net.tmt.gamestate.AbstractGamestate;
import net.tmt.gfx.Graphics;
import net.tmt.gfx.Sprite;
import net.tmt.util.ConfigUtil;
import net.tmt.util.CountdownTimer;
import net.tmt.util.DebugUtil;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
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

			long delta = fps.getDelta();

			if (Math.abs(delta) < 100) {
				update(delta / 1000.);
				render();
			}
			fps.updateAfter();

			if (timerMs.isTimeleft(delta / 1000.)) {
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
			ConfigUtil.init("/cfg/global.cfg"); // init global configs
			ConfigUtil.init("/cfg/local.cfg"); // override with local configs
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
		graphics = Graphics.init();

		gameManager = GameManager.init();

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
