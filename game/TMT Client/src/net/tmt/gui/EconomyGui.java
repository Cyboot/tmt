package net.tmt.gui;


import net.tmt.gfx.Graphics;
import net.tmt.gui.view.BuildView;
import net.tmt.util.Vector2d;

public class EconomyGui extends Gui {
	BuildView	buildView;

	public EconomyGui() {
		buildView = new BuildView(new Vector2d(980, 520), 300, 200);
	}

	@Override
	public void update(final double delta) {
		gameStateToolbar.update(delta);
		buildView.update(delta);
	}

	@Override
	public void render(final Graphics g) {
		// TODO #27 show Information if InputState == BULIDING_SELECTED
		gameStateToolbar.render(g);
		buildView.render(g);


		if (guiManager.isSet(GUI_CLICKED)) {
			String text = (String) guiManager.getValue(GUI_CLICKED);
			g.onGui().drawText(width * 0.76 + 5, height * 0.97, text);
		}
		guiManager.remove(GUI_HOVER);

	}


}
