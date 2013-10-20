package net.tmt.gamestate;

import java.util.ArrayList;
import java.util.List;

import net.tmt.entity.ControlledSpaceShip;
import net.tmt.entity.Entity2D;
import net.tmt.entity.npc.NPCSpaceShip;
import net.tmt.gfx.Graphics;
import net.tmt.map.World;
import net.tmt.util.Vector2d;

public class SpaceGamestate extends AbstractGamestate {
	private static SpaceGamestate	instance	= new SpaceGamestate();

	private ControlledSpaceShip		ship		= new ControlledSpaceShip();

	private World					world		= World.getInstance();
	private List<Entity2D>			entities	= new ArrayList<>();

	private SpaceGamestate() {
		entities.add(new NPCSpaceShip(new Vector2d(), 30));
		world.setPlayer(ship);
	}

	@Override
	public void update(final double delta) {
		world.update(delta);

		for (Entity2D e : entities)
			e.update(delta);

		ship.update(delta);
	}

	@Override
	public void render(final Graphics g) {
		world.render(g);

		for (Entity2D e : entities)
			e.render(g);

		ship.render(g);
	}

	public ControlledSpaceShip getShip() {
		return ship;
	}

	public static SpaceGamestate getInstance() {
		return instance;
	}
}
