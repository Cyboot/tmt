package net.tmt.entity.component;

import net.tmt.gfx.Graphics;

public abstract class Component {
	public static String	ROTATION_ANGLE	= "ROTATION_ANGLE";

	public void update(final ComponentDispatcher caller, final double delta) {
	}

	public void render(final ComponentDispatcher caller, final Graphics g) {
	}

	/**
	 * Dispatch all values for the first time
	 * 
	 * @param caller
	 */
	public void dispatch(final ComponentDispatcher caller) {

	}
}
