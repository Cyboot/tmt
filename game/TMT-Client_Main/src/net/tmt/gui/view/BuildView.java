package net.tmt.gui.view;

import net.tmt.entity.economy.Base;
import net.tmt.entity.economy.Mine;
import net.tmt.entity.economy.Store;
import net.tmt.game.interfaces.ClickListener;
import net.tmt.game.manager.GuiManager;
import net.tmt.gamestate.EconomyGamestate;
import net.tmt.gamestate.EconomyGamestate.InputState;
import net.tmt.gfx.Graphics;
import net.tmt.gfx.Sprite;
import net.tmt.gui.Gui;
import net.tmt.gui.elements.Button;
import net.tmt.gui.elements.ContainerElement;
import net.tmt.gui.elements.GuiElement;
import net.tmt.util.Vector2d;

public class BuildView extends ContainerElement implements ClickListener {


	public BuildView(final Vector2d pos, final double width, final double height) {
		super(pos, width, height);
		addButton(new Button(new Vector2d(0, 0), new Sprite("icon_base"), "icon_base"));
		addButton(new Button(new Vector2d(64 + DEFAULT_PADDING, 0), new Sprite("icon_mine"), "icon_mine"));
		addButton(new Button(new Vector2d(128 + DEFAULT_PADDING * 2, 0), new Sprite("icon_store"), "icon_store"));
	}


	private void addButton(final Button button) {
		button.addClickListener(this);
		addGuiElement(button);
	}


	@Override
	public void onClick(final GuiElement caller) {
		EconomyGamestate game = EconomyGamestate.getInstance();

		// Buttons work only if Inputstate is neutral
		if (game.getInputState() == InputState.NEUTRAL) {
			game.setInputState(InputState.CONSTRUCTING);
			switch (caller.getTitle()) {
			case "icon_base":
				// TODO: you just can create one base in planet?
				game.setBuildingToConstruct(new Base(new Vector2d()));
				break;
			case "icon_mine":
				game.setBuildingToConstruct(new Mine(new Vector2d()));
				break;
			case "icon_store":
				game.setBuildingToConstruct(new Store(new Vector2d()));
				break;
			default:
				break;
			}
		}
	}

	@Override
	public void render(final Graphics g) {
		super.render(g);

		GuiManager guiManager = GuiManager.getInstance();

		if (guiManager.isSet(Gui.GUI_CLICKED)) {
			String text = (String) guiManager.getValue(Gui.GUI_CLICKED);
			// g.onGui().drawText(width * 0.76 + 5, height * 0.97, text);
			g.onGui().drawText(pos.x, pos.y, text);
		}
	}
}
