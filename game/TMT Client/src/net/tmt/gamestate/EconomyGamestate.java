package net.tmt.gamestate;

import net.tmt.gfx.Graphics;
import net.tmt.gui.EconomyGui;

public class EconomyGamestate extends AbstractGamestate {
	private static EconomyGamestate	instance	= new EconomyGamestate();

	private InputState				inputState	= InputState.NEUTRAL;

	@Override
	public void update(final double delta) {
		switch (inputState) {
		case NEUTRAL:
			// do nothing special here (yet)
			break;
		case CONSTRUCTING:
			// TODO #27 on Leftclick --> create the Building
			break;
		case BULIDING_SELECTED:
			// TODO #27 tell gui to display building information
			break;
		}
	}

	@Override
	public void render(final Graphics g) {
		guiManager.setGui(EconomyGui.class);

		switch (inputState) {
		case NEUTRAL:
			// nothing special
			break;
		case CONSTRUCTING:
			// TODO #27 display the currently constructing building at mouse
			// position
			break;
		case BULIDING_SELECTED:
			// display building information (already done in update() )
			break;
		}
	}

	public InputState getInputState() {
		return inputState;
	}

	public void setInputState(final InputState inputState) {
		this.inputState = inputState;
	}

	public static EconomyGamestate getInstance() {
		return instance;
	}

	public static enum InputState {
		/** neutral */
		NEUTRAL,

		/** currently constructing a building */
		CONSTRUCTING,

		/** infos about building etc. */
		BULIDING_SELECTED
	}
}
