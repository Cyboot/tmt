package net.tmt.entity.economy;

import net.tmt.entity.Entity2D;
import net.tmt.entity.component.OnClickComponent;
import net.tmt.entity.component.other.OnHoverComponent;
import net.tmt.util.Vector2d;

public class Building extends Entity2D {

	public static final int	SIZE	= 64;

	public Building(final Vector2d pos) {
		super(pos);

		addComponent(new OnHoverComponent());
		addComponent(new OnClickComponent());
	}

}
