package net.tmt.gui;

import java.util.ArrayList;
import java.util.List;

import net.tmt.game.interfaces.Renderable;
import net.tmt.game.interfaces.Updateable;
import net.tmt.gfx.Graphics;

public class GuiManager implements Updateable, Renderable {
	private static GuiManager	instance;

	private List<Gui>			guiList	= new ArrayList<>();
	private Gui					activeGui;

	public static GuiManager init() {
		instance = new GuiManager();

		instance.guiList.add(new SpaceGui());
		instance.guiList.add(new SimulatorGui());
		instance.guiList.add(new PlanetGui());
		instance.guiList.add(new EconomyGui());
		instance.guiList.add(new DummyGui());

		return instance;
	}

	@Override
	public void update(final double delta) {
		if (activeGui != null)
			activeGui.update(delta);
	}

	@Override
	public void render(final Graphics g) {
		if (activeGui != null)
			activeGui.render(g);
	}

	public void setGui(final Class<? extends Gui> gui) {
		for (Gui g : guiList) {
			if (g.getClass().equals(gui)) {
				activeGui = g;
				break;
			}
		}
	}

	public static GuiManager getInstance() {
		return instance;
	}
}
