package net.tmt.entity.economy;

import net.tmt.entity.Entity2D;
import net.tmt.entityComponents.other.OnClickComponent;
import net.tmt.entityComponents.other.OnHoverComponent;
import net.tmt.util.Vector2d;

public class Building extends Entity2D {

	public static final int	SIZE	= 256;

	public Building(final Vector2d pos) {
		super(pos);

		addComponent(new OnHoverComponent());
		addComponent(new OnClickComponent());
	}

}
