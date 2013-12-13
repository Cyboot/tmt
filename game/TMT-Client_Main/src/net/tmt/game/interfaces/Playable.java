package net.tmt.game.interfaces;

public interface Playable {
	/**
	 * disable all controls. So the player-entities will ignore all key bindings
	 */
	public void disableControls();

	/**
	 * enable controls (again).
	 */
	public void enableControls();
}
