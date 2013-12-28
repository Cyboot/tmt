package net.tmt.game.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.tmt.game.interfaces.Dispatcher;
import net.tmt.game.interfaces.Renderable;
import net.tmt.game.interfaces.Updateable;
import net.tmt.gfx.Graphics;
import net.tmt.gui.AchievmentOverlay;
import net.tmt.gui.DummyGui;
import net.tmt.gui.EconomyGui;
import net.tmt.gui.Gui;
import net.tmt.gui.MissionOverlay;
import net.tmt.gui.MoneyLevelOverlay;
import net.tmt.gui.SimulatorGui;
import net.tmt.gui.elements.Label;

public class GuiManager implements Updateable, Renderable, Dispatcher {
	private static GuiManager	instance;

	private List<Gui>			guiList		= new ArrayList<>();
	private List<Gui>			overlayGui	= new ArrayList<>();
	private Gui					activeGui;

	private Map<String, Object>	guiValue	= new HashMap<>();

	private Label				tooltip;

	public static GuiManager init() {
		instance = new GuiManager();

		instance.overlayGui.add(new MissionOverlay());
		instance.overlayGui.add(new MoneyLevelOverlay());
		instance.overlayGui.add(new AchievmentOverlay());
		instance.guiList.add(new SimulatorGui());
		instance.guiList.add(new EconomyGui());
		instance.guiList.add(new DummyGui());

		return instance;
	}

	@Override
	public void update(final double delta) {

		if (activeGui != null)
			activeGui.update(delta);
		for (Gui g : overlayGui)
			g.update(delta);
	}

	@Override
	public void render(final Graphics g) {
		if (activeGui != null)
			activeGui.render(g);
		for (Gui gui : overlayGui)
			gui.render(g);
		if (tooltip != null) {
			tooltip.render(g);
			tooltip = null;
		}
	}

	public void setGui(final Gui gui) {
		activeGui = gui;
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

	@Override
	public void dispatch(final String key, final Object value) {
		guiValue.put(key, value);
	}

	@Override
	public Object getValue(final String key) {
		return guiValue.get(key);
	}

	@Override
	public boolean isSet(final String key) {
		return guiValue.containsKey(key);
	}

	@Override
	public void remove(final String key) {
		guiValue.remove(key);
	}

	public void setTooltip(final Label tooltip) {
		this.tooltip = tooltip;
	}
}
