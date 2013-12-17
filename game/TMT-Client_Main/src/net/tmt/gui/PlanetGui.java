package net.tmt.gui;

import net.tmt.gfx.Graphics;
import net.tmt.gfx.Graphics.Fonts;
import net.tmt.gui.view.BagView;
import net.tmt.util.ColorUtil;

public class PlanetGui extends Gui {

	private BagView	bagView	= new BagView();

	@Override
	public void update(final double delta) {
		gameStateToolbar.update(delta);
		bagView.update(delta);
	}

	@Override
	public void render(final Graphics g) {
		g.setColor(ColorUtil.GUI_CYAN);
		g.setFont(Fonts.get(14));
		renderMap(g);
		g.setColor(ColorUtil.GUI_CYAN);
		renderInfoWindow(g);
		gameStateToolbar.render(g);
		bagView.render(g);
	}

	private void renderMap(final Graphics g) {
		final double topY = height * 0.71;
		final double heightTab = 25;
		final double widthTab = 50;
		final double padding = 4;
		//
		// g.onGui().drawRect(0 * widthTab, topY, widthTab, heightTab);
		// g.onGui().drawText(0 * widthTab + padding, topY + padding, "Map");
		//
		// g.onGui().drawRect(1 * widthTab, topY, widthTab, heightTab);
		// g.onGui().drawText(1 * widthTab + padding, topY + padding, "Chat");
		// g.onGui().drawRect(0, topY + heightTab, width * 0.25, height - topY -
		// heightTab);

	}

	private void renderInfoWindow(final Graphics g) {
		g.onGui().drawRect(width * 0.75, height * 0.75, width * 0.25, height * 0.25);

		if (guiManager.isSet(GUI_HOVER)) {
			String text = (String) guiManager.getValue(GUI_HOVER);
			g.onGui().drawText(width * 0.75 + 5, height * 0.75, text);
		} else {
			g.onGui().drawText(width * 0.75 + 5, height * 0.75, "InfoView");
		}
		guiManager.remove(GUI_HOVER);
	}
}