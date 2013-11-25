package net.tmt.game.interfaces;

public interface Updateable {
	/**
	 * update Method, all logic here
	 * 
	 * @param delta
	 *            in seconds (typically 0.01 s at 100 FPS)
	 */
	public abstract void update(final double delta);
}
