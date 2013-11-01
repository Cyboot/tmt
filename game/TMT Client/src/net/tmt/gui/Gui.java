package net.tmt.gui;

import net.tmt.game.interfaces.Renderable;
import net.tmt.game.interfaces.Updateable;
import net.tmt.game.manager.GuiManager;

public abstract class Gui implements Updateable, Renderable {
	public static final String	GUI_HOVER	= "GUI_HOVER";

	protected GuiManager		guiManager	= GuiManager.getInstance();
}
