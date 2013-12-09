package net.tmt.gamestate;

import net.tmt.entity.Hero;
import net.tmt.entity.npc.Runner;
import net.tmt.entity.npc.SpaceBug;
import net.tmt.entity.pickups.BackPack;
import net.tmt.entity.pickups.GlowingStuff;
import net.tmt.entity.statics.Planet;
import net.tmt.game.GameEngine;
import net.tmt.gfx.Graphics;
import net.tmt.gui.PlanetGui;
import net.tmt.map.PlanetMap;
import net.tmt.util.ColorUtil;
import net.tmt.util.Vector2d;

public class PlanetGamestate extends AbstractGamestate {
	private Planet	planet;

	private Hero	hero;

	public PlanetGamestate(final Planet planet) {
		super(new PlanetMap(planet));
		this.planet = planet;

		hero = new Hero(new Vector2d(GameEngine.WIDTH / 2, GameEngine.HEIGHT / 2));
		world.setPlayer(hero);
		entityManager.addEntity(hero);

		// DEBUG: debug items
		BackPack bp = new BackPack(new Vector2d(GameEngine.WIDTH / 2 - 40, GameEngine.HEIGHT / 2 - 40));
		entityManager.addEntity(bp);
		GlowingStuff gs = new GlowingStuff(new Vector2d(GameEngine.WIDTH / 2 - 140, GameEngine.HEIGHT / 2 - 140));
		entityManager.addEntity(gs);
		SpaceBug sb = new SpaceBug(new Vector2d(GameEngine.WIDTH / 2 + 140, GameEngine.HEIGHT / 2 + 140), 100, hero);
		entityManager.addEntity(sb);
		Runner rnr = new Runner(new Vector2d(GameEngine.WIDTH / 2 - 340, GameEngine.HEIGHT / 2 - 340), 100, hero);
		entityManager.addEntity(rnr);
	}

	@Override
	public void update(final double delta) {
		world.update(delta);

		entityManager.update(world, delta);
	}

	@Override
	public void render(final Graphics g) {
		super.render(g);
		guiManager.setGui(PlanetGui.class);
		world.render(g);

		// Blend color, so hero and other entities are more noticable
		g.setColor(ColorUtil.BLACK_ALPHA_50);
		g.onGui().fillRect(0, 0, GameEngine.WIDTH, GameEngine.HEIGHT);

		entityManager.render(g);
	}

	public Planet getPlanet() {
		return planet;
	}

	@Override
	public String toString() {
		return super.toString() + " [planet = " + planet + "]";
	}
}
