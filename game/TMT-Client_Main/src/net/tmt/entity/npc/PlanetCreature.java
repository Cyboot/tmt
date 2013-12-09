package net.tmt.entity.npc;

import net.tmt.entity.Entity2D;
import net.tmt.game.factory.ComponentFactory;
import net.tmt.gfx.Sprite;
import net.tmt.util.Vector2d;

public class PlanetCreature extends Entity2D {

	private double			health;
	private double			rotationSpeed	= 30;
	private static Sprite	MISSING_SPRITE	= new Sprite("missingno");

	public PlanetCreature(final Vector2d pos, final double health) {
		super(pos);
		this.health = health;
		ComponentFactory.addDefaultMove(this, 0, 0, rotationSpeed);
		ComponentFactory.addDefaultCollision(this, 10, this.health);
		setSprite(MISSING_SPRITE);
	}

}
