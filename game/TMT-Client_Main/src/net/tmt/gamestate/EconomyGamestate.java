package net.tmt.gamestate;

import net.tmt.entity.economy.Building;
import net.tmt.game.Controls;
import net.tmt.gfx.Graphics;
import net.tmt.gui.EconomyGui;
import net.tmt.map.PlanetChunk;
import net.tmt.map.PlanetMap;
import net.tmt.map.Terrain;
import net.tmt.util.Vector2d;

public class EconomyGamestate extends AbstractGamestate {
	private static EconomyGamestate	instance	= new EconomyGamestate();

	private InputState				inputState	= InputState.NEUTRAL;

	private Building				buildingToConstruct;

	private EconomyGamestate() {
		super(new PlanetMap(Terrain.PLANET_GRASS, 0));
	}

	@Override
	public void update(final double delta) {
		world.update(delta);
		entityManager.update(world, delta);

		switch (inputState) {
		case NEUTRAL:
			// do nothing special here (yet)
			break;

		case CONSTRUCTING:
			// update position to mouse curser
			double chunkSize = world.getMap().getChunkSize();
			Vector2d posOnWorld = world.getVectorOnWorld(new Vector2d(Controls.mouseX(), Controls.mouseY()));

			double x = posOnWorld.x() / (int) chunkSize * chunkSize;
			double y = posOnWorld.y() / (int) chunkSize * chunkSize;
			x += Building.SIZE / 2;
			y += Building.SIZE / 2;

			buildingToConstruct.getPos().set(x, y);

			// right click to abort constructin
			if (Controls.wasReleased(Controls.MOUSE_RIGHT)) {

				buildingToConstruct = null;
				setInputState(InputState.NEUTRAL);
				break;
			}


			// add Building to EntityManager on leftclick
			if (Controls.wasReleased(Controls.MOUSE_LEFT)) {

				// #27 maybe the Chunk to build on is of interest:
				@SuppressWarnings("unused")
				PlanetChunk chunk = (PlanetChunk) world.getMap().getChunk(buildingToConstruct.getPos());

				// do not construct building through GUI
				if (isBuildingInGUI() || isThereAlreadyBuilding()) {
					boolean success = world.addStaticEntity(buildingToConstruct);
					if (success) {
						buildingToConstruct = null;
						setInputState(InputState.NEUTRAL);
					}
				}
			}
			break;


		case BULIDING_SELECTED:
			// TODO #27 tell gui to display building information
			break;
		}
	}


	@Override
	public void render(final Graphics g) {
		super.render(g);
		guiManager.setGui(EconomyGui.class);
		world.render(g);
		entityManager.render(g);

		switch (inputState) {
		case NEUTRAL:
			// nothing special

			break;
		case CONSTRUCTING:
			// render the currently constructing Building under curser
			g.drawSprite(buildingToConstruct.getPos(), buildingToConstruct.getSprite());
			break;
		case BULIDING_SELECTED:
			// display building information (already done in update() )
			break;
		}
	}

	boolean isBuildingInGUI() {
		// TODO: how to find out the sprite-size?
		return true;
	}

	private boolean isThereAlreadyBuilding() {
		// TODO: how to find out the sprite-size?
		return true;
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

	public void setBuildingToConstruct(final Building buildingToConstruct) {
		this.buildingToConstruct = buildingToConstruct;
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
