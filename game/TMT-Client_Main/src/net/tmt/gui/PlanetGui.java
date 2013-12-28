package net.tmt.gui;

import net.tmt.gfx.Graphics;
import net.tmt.gfx.Graphics.Fonts;
import net.tmt.gui.view.BagView;
import net.tmt.gui.view.MiniMapView;
import net.tmt.map.World;
import net.tmt.util.ColorUtil;

public class PlanetGui extends Gui {
	private BagView		bagView	= new BagView();
	private MiniMapView	miniMap;

	public PlanetGui(final World world) {
		miniMap = new MiniMapView(world, 20);
	}

	@Override
	public void update(final double delta) {
		gameStateToolbar.update(delta);
		bagView.update(delta);
		miniMap.update(delta);
	}

	@Override
	public void render(final Graphics g) {
		g.setFont(Fonts.get(14));
		g.setColor(ColorUtil.GUI_CYAN);
		renderInfoWindow(g);

		gameStateToolbar.render(g);
		bagView.render(g);
		miniMap.render(g);
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