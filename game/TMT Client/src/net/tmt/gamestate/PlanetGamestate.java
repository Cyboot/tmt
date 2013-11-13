package net.tmt.gamestate;

import net.tmt.entity.Hero;
import net.tmt.entity.statics.Planet;
import net.tmt.game.GameEngine;
import net.tmt.gfx.Graphics;
import net.tmt.gui.PlanetGui;
import net.tmt.map.PlanetMap;
import net.tmt.map.World;
import net.tmt.util.Vector2d;

public class PlanetGamestate extends AbstractGamestate {
	private Planet	planet;
	private World	world;

	public PlanetGamestate(final Planet planet) {
		this.planet = planet;
		requestMap();
	}

	@Override
	public void update(final double delta) {
		world.update(delta);
	}

	@Override
	public void render(final Graphics g) {
		guiManager.setGui(PlanetGui.class);
		world.render(g);
	}

	@Override
	public void requestMap() {
		// init World
		World.init(PlanetGamestate.class);
		World.setActiveWorld(PlanetGamestate.class);
		world = World.getActiveWorld();
		world.setMap(new PlanetMap(planet));
		world.setPlayer(new Hero(new Vector2d(GameEngine.WIDTH / 2, GameEngine.HEIGHT / 2)));
	}
}
