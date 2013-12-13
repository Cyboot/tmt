package net.tmt.entityComponents.other;

import net.tmt.entityComponents.Component;
import net.tmt.entityComponents.ComponentDispatcher;
import net.tmt.game.Controls;
import net.tmt.game.manager.GuiManager;
import net.tmt.gfx.Graphics;
import net.tmt.gui.Gui;
import net.tmt.map.World;
import net.tmt.util.Vector2d;

public class OnClickComponent extends Component {

	private boolean	isClicked	= false;

	@Override
	public void update(final ComponentDispatcher caller, final double delta) {
		Vector2d mouse2d = Vector2d.tmp1.set(Controls.mouseX(), Controls.mouseY());

		World world = caller.getWorld();

		Vector2d shipAbsolut2d = world.getVectorOnScreen(pos);

		// bedinung genauer einstellen. radius abhänging von bildgröße
		if (mouse2d.distanceTo(shipAbsolut2d) < 32 && Controls.wasReleased(Controls.MOUSE_LEFT)) {
			isClicked = true;
			String info = owner.toString();
			GuiManager guiManager = GuiManager.getInstance();
			guiManager.dispatch(Gui.GUI_CLICKED, info);
		}
	}

	@Override
	public void render(final ComponentDispatcher caller, final Graphics g) {
	}
}
