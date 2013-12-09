package net.tmt.gamestate;

import net.tmt.entity.npc.Satellite;
import net.tmt.game.Controls;
import net.tmt.gfx.Graphics;
import net.tmt.global.Money;
import net.tmt.global.RPLevel;
import net.tmt.gui.DummyGui;
import net.tmt.util.RandomUtil;
import net.tmt.util.Vector2d;

import org.lwjgl.util.Color;

public class DummyGamestate extends AbstractGamestate {
	private static DummyGamestate	instance;


	private DummyGamestate() {
		super(null);
		Vector2d center = new Vector2d(500, 300);


		entityManager.addEntity(new Satellite(RandomUtil.doubleRange(0, 2 * Math.PI), center, 100));
		entityManager.addEntity(new Satellite(RandomUtil.doubleRange(0, 2 * Math.PI), center, 200));
		entityManager.addEntity(new Satellite(RandomUtil.doubleRange(0, 2 * Math.PI), center, 250));

		onResume(-1);
	}

	@Override
	public void update(final double delta) {
		entityManager.update(world, delta);

		if (Controls.pressed(Controls.DEBUG_KEY_3)) {
			RPLevel.addRP((int) (delta * 80000));
		}
		if (Controls.pressed(Controls.DEBUG_KEY_4)) {
			Money.addMoney((long) (delta * 81231));
		}
	}

	@Override
	public void render(final Graphics g) {
		super.render(g);

		entityManager.render(g);

		g.setColor(Color.YELLOW);
		g.fillCircle(500, 300, 5);

		guiManager.setGui(DummyGui.class);
	}

	public static DummyGamestate getInstance() {
		if (instance == null)
			instance = new DummyGamestate();

		return instance;
	}
}
