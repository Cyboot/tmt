package net.tmt.gamestate;

import net.tmt.entity.Hero;
import net.tmt.entity.pickups.BackPack;
import net.tmt.entity.pickups.GlowingStuff;
import net.tmt.entity.statics.Planet;
import net.tmt.game.GameEngine;
import net.tmt.gfx.Graphics;
import net.tmt.gui.PlanetGui;
import net.tmt.map.PlanetMap;
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
	}

	@Override
	public void update(final double delta) {
		world.update(delta);

		entityManager.update(delta);
	}

	@Override
	public void render(final Graphics g) {
		super.render(g);
		guiManager.setGui(PlanetGui.class);
		world.render(g);

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
