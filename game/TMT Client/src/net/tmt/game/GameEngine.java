package net.tmt.game;

import static org.lwjgl.opengl.GL11.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.tmt.gamestate.AbstractGamestate;
import net.tmt.gamestate.DummyGamestate;
import net.tmt.gfx.Graphics;
import net.tmt.gfx.Sprite;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

public class GameEngine {
	private final FPS				fps			= new FPS();
	private List<AbstractGamestate>	gamestates	= new ArrayList<>();
	private Graphics				graphics;

	public static int				WIDTH		= 800;
	public static int				HEIGHT		= 600;

	public void start() throws LWJGLException {
		initGL();
		initNonGL();

		while (!Display.isCloseRequested()) {
			fps.update();

			int delta = fps.getDelta();
			Display.setTitle("Delta: " + delta);

			update(delta / 1000.);
			render();

			Display.update();
			Display.sync(100);
		}

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
		Graphics.init();
		graphics = Graphics.getInstance();

		gamestates.add(new DummyGamestate());
	}

	private void initGL() throws LWJGLException {
		Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
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
