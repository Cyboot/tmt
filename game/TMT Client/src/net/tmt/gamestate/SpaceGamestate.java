package net.tmt.gamestate;

import java.util.ArrayList;
import java.util.List;

import net.tmt.entity.ControlledSpaceShip;
import net.tmt.entity.Entity2D;
import net.tmt.entity.npc.NPCCargoShip;
import net.tmt.entity.npc.NPCTransporterShip;
import net.tmt.game.GameEngine;
import net.tmt.gfx.Graphics;
import net.tmt.map.World;
import net.tmt.util.RandomUtil;
import net.tmt.util.Vector2d;

import org.lwjgl.util.Color;

public class SpaceGamestate extends AbstractGamestate {
	private static SpaceGamestate	instance		= new SpaceGamestate();

	private ControlledSpaceShip		ship			= new ControlledSpaceShip();

	private World					world			= World.getInstance();
	private List<Entity2D>			entities		= new ArrayList<>();
	private List<Entity2D>			entitiesToAdd	= new ArrayList<>();

	private SpaceGamestate() {
		entities.add(new NPCCargoShip(new Vector2d()));
		entities.add(new NPCCargoShip(new Vector2d(1000, 500)));
		entities.add(new NPCCargoShip(new Vector2d(500, 500)));

		for (int i = 0; i < 10; i++) {
			double x = RandomUtil.doubleRange(-1000, 1000);
			double y = RandomUtil.doubleRange(-1000, 1000);
			entities.add(new NPCCargoShip(new Vector2d(x, y)));
		}
		entities.add(new NPCTransporterShip(new Vector2d(800, 200), NPCTransporterShip.TYPE_1));
		entities.add(new NPCTransporterShip(new Vector2d(500, 500), NPCTransporterShip.TYPE_2));

		world.setPlayer(ship);
	}

	@Override
	public void update(final double delta) {
		world.update(delta);

		for (Entity2D e : entities)
			e.update(delta);
		entities.addAll(entitiesToAdd);
		entitiesToAdd.clear();

		ship.update(delta);

		updateGUI(delta);
	}


	@Override
	public void render(final Graphics g) {
		world.render(g);

		for (Entity2D e : entities)
			e.render(g);

		ship.render(g);

		renderGUI(g);

	}

	private void updateGUI(final double delta) {
		// TODO Auto-generated method stub

	}

	private void renderGUI(final Graphics g) {

		int width = GameEngine.WIDTH;
		int height = GameEngine.HEIGHT;

		g.setColor(Color.CYAN);

		// ressource
		g.gui().fillRect(0, 0, width * 0.3f, height * 0.05f);

		// minimap
		g.gui().fillRect(width * 0.85f, 0, width * 0.15f, height * 0.25f);

		// sidebar
		g.gui().fillRect(0, height * 0.15f, width * 0.04f, height * 0.5f);

		// missions
		g.gui().fillRect(width * 0.85f, height * 0.3f, width * 0.15f, height * 0.4f);

		// log/chat
		g.gui().fillRect(0, height * 0.71, width * 0.05f, height * 0.04f);
		g.gui().fillRect(0, height * 0.75, width * 0.25f, height * 0.25f);

		// weapons
		g.gui().fillRect(width * 0.3f, height * 0.75f, width * 0.07f, height * 0.1f);
		g.gui().fillRect(width * 0.3f, height * 0.90f, width * 0.07f, height * 0.1f);

		// states of ship
		g.gui().fillRect(width * 0.4f, height * 0.75f, width * 0.2f, height * 0.03f);
		g.gui().fillRect(width * 0.4f, height * 0.8f, width * 0.2f, height * 0.03f);
		g.gui().fillRect(width * 0.4f, height * 0.85f, width * 0.2f, height * 0.03f);
		g.gui().fillRect(width * 0.4f, height * 0.90f, width * 0.2f, height * 0.03f);
		g.gui().fillRect(width * 0.4f, height * 0.95f, width * 0.2f, height * 0.03f);

		// information window
		g.gui().fillRect(width * 0.75f, height * 0.75f, width * 0.25f, height * 0.25f);

	}

	public ControlledSpaceShip getShip() {
		return ship;
	}

	public static SpaceGamestate getInstance() {
		return instance;
	}

	public void addEntity(final Entity2D entity) {
		entitiesToAdd.add(entity);
	}
}
