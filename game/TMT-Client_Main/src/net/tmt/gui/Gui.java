package net.tmt.gui;

import net.tmt.game.GameEngine;
import net.tmt.game.interfaces.Renderable;
import net.tmt.game.interfaces.Updateable;
import net.tmt.game.manager.GuiManager;
import net.tmt.gui.view.GameStateToolbar;

public abstract class Gui implements Updateable, Renderable {
	public static final String	GUI_HOVER			= "GUI_HOVER";
	public static final String	ACHIEVMENT			= "ACHIEVMENT";
	public static final String	MISSION_OFFER		= "MISSION";
	public static final String	GUI_CLICKED			= "CLICKED";
	public static final String	DEBUG_ENTITY_COUNT	= "DEBUG_ENTITY_COUNT";

	protected int				width				= GameEngine.WIDTH;
	protected int				height				= GameEngine.HEIGHT;

	protected GuiManager		guiManager			= GuiManager.getInstance();
	protected GameStateToolbar	gameStateToolbar	= GameStateToolbar.getInstance();
}
