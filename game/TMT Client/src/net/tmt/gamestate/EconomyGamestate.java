package net.tmt.gamestate;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import net.tmt.entity.economy.Building;
import net.tmt.gfx.Graphics;
import net.tmt.gui.EconomyGui;
import net.tmt.util.Vector2d;

import org.lwjgl.input.Mouse;

public class EconomyGamestate extends AbstractGamestate {

	public static final int			NEUTRAL				= 0;
	public static final int			CONSTRUCTING		= 1;
	public static final int			BULIDING_SELECTED	= 2;


	private static EconomyGamestate	instance			= new EconomyGamestate();
	private List<Building>			buildingList		= new ArrayList<>();

	private int						inputState			= NEUTRAL;

	@Override
	public void update(final double delta) {
		switch (inputState) {
		case NEUTRAL:
			// do nothing special here (yet)
			break;

		case CONSTRUCTING:
			// TODO #27 on Leftclick --> create the Building

			if (Mouse.isButtonDown(MouseEvent.MOUSE_CLICKED)) {
				buildingList.add(new Building(new Vector2d(Mouse.getX(), Mouse.getY())));
				// setInputState(InputState.NEUTRAL);
			}
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

		for (Building building : buildingList)
			building.render(g);
	}

	public int getInputState() {
		return inputState;
	}

	public void setInputState(final int inputState) {
		this.inputState = inputState;
	}

	public static EconomyGamestate getInstance() {
		return instance;
	}

	// public static enum InputState {
	// /** neutral */
	// NEUTRAL,
	//
	// /** currently constructing a building */
	// CONSTRUCTING,
	//
	// /** infos about building etc. */
	// BULIDING_SELECTED
	// }
}
