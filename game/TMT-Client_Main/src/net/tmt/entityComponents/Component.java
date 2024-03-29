package net.tmt.entityComponents;

import net.tmt.entity.Entity2D;
import net.tmt.gfx.Graphics;
import net.tmt.util.Vector2d;

public abstract class Component {
	protected Entity2D			owner;
	protected Vector2d			pos;
	public static final String	ROTATION_ANGLE_LOOK	= "ROTATION_ANGLE_LOOK";

	public void update(final ComponentDispatcher caller, final double delta) {
	}

	public void render(final ComponentDispatcher caller, final Graphics g) {
	}

	/**
	 * Dispatch all values for the first time<br>
	 * <b>Warning</b> call <i>super.initialDispatch()</i> for correct behaviour
	 * 
	 * @param caller
	 */
	public void initialDispatch(final ComponentDispatcher caller) {
		owner = caller.getOwner();
		pos = owner.getPos();
	}
}
