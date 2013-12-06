package net.tmt.gamestate;

import net.tmt.entity.PlayerSpaceShip;
import net.tmt.entity.statics.SpaceStation;
import net.tmt.game.Controls;
import net.tmt.game.manager.EntityManager;
import net.tmt.gfx.Graphics;
import net.tmt.gui.SpaceGui;
import net.tmt.map.SpaceMap;
import net.tmt.util.StringFormatter;
import net.tmt.util.Vector2d;

public class SpaceGamestate extends AbstractGamestate {
	private static SpaceGamestate	instance;

	private PlayerSpaceShip			player	= new PlayerSpaceShip();

	private SpaceGamestate() {
		super(SpaceMap.getInstance());
		world.setPlayer(player);

		entityManager.addEntity(player, EntityManager.LAYER_3_FRONT);
		entityManager.addEntity(new SpaceStation(new Vector2d(-500, -500), 1));
		entityManager.addEntity(new SpaceStation(new Vector2d(-500, -800), 2));

		onResume(-1);
	}

	@Override
	public void update(final double delta) {
		world.update(delta);
		entityManager.update(world, delta);

		Graphics g = Graphics.getInstance();
		if (Controls.pressed(Controls.DEBUG_KEY_1))
			g.setScale(g.getScale() * (1 - 1 * delta));
		if (Controls.pressed(Controls.DEBUG_KEY_2))
			g.setScale(g.getScale() * (1 + 1 * delta));
		System.out.println(StringFormatter.format(g.getScale()));
	}

	@Override
	public void render(final Graphics g) {
		super.render(g);
		guiManager.setGui(SpaceGui.class);

		world.render(g);
		entityManager.render(g);
	}

	public PlayerSpaceShip getShip() {
		return player;
	}

	public static SpaceGamestate getInstance() {
		if (instance == null)
			instance = new SpaceGamestate();
		return instance;
	}
}
