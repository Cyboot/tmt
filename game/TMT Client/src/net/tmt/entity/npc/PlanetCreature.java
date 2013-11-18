package net.tmt.entity.npc;

import net.tmt.entity.Entity2D;
import net.tmt.util.Vector2d;

public class PlanetCreature extends Entity2D {

	// TODO: maybe create a healthcomponent
	private double	health;

	public PlanetCreature(final Vector2d pos, final double health) {
		super(pos);
		this.health = health;
	}

}
