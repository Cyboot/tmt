package net.tmt.game;

import static org.lwjgl.opengl.GL11.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.tmt.gamestate.AbstractGamestate;
import net.tmt.gamestate.SpaceGamestate;
import net.tmt.gfx.Graphics;
import net.tmt.gfx.Sprite;
import net.tmt.map.World;
import net.tmt.util.ConfigUtil;
import net.tmt.util.DebugUtil;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

public class GameEngine {
	private final FPS				fps			= new FPS();
	private List<AbstractGamestate>	gamestates	= new ArrayList<>();
	private Graphics				graphics;

	public static int				WIDTH;
	public static int				HEIGHT;

	public void start() throws LWJGLException {
		initConfig();
		initGL();
		initNonGL();

		while (!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			fps.update();

			int delta = fps.getDelta();
			Display.setTitle("Delta: " + delta);

			if (Math.abs(delta) < 100) {
				update(delta / 1000.);
				render();
			}

			Display.update();
			Display.sync(100);
		}

	}

	private void initConfig() {
		try {
			ConfigUtil.init("res/cfg/global.cfg"); // init global configs
			ConfigUtil.init("res/cfg/local.cfg"); // override with local configs
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		WIDTH = ConfigUtil.getInt("display.width");
		HEIGHT = ConfigUtil.getInt("display.height");
	}

	private void update(final double delta) {
		for (AbstractGamestate a : gamestates)
			a.update(delta);
	}

	private void initNonGL() {
		try {
			Sprite.init();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		World.init();
		Graphics.init();
		graphics = Graphics.getInstance();

		gamestates.add(SpaceGamestate.getInstance());

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

		for (AbstractGamestate a : gamestates)
			a.render(graphics);
	}

	private static class FPS {
		private long	lastFrame;
		private int		delta;

		private long getTime() {
			return (Sys.getTime() * 1000) / Sys.getTimerResolution();
		}

		public void update() {
			long time = getTime();
			delta = (int) (time - lastFrame);
			lastFrame = time;
		}

		public int getDelta() {
			return delta;
		}
	}

}
