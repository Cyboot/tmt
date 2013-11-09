package net.tmt.gamestate;

import net.tmt.gfx.Graphics;
import net.tmt.gui.PlanetGui;
import net.tmt.map.World;

public class PlanetGamestate extends AbstractGamestate {
	private World	world;

	public PlanetGamestate() {
		world = World.getInstance();
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

}
