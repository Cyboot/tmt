package net.tmt.entity.npc;

import net.tmt.entity.Entity2D;
import net.tmt.entityComponents.move.MoveCircleComponent;
import net.tmt.gfx.Sprite;
import net.tmt.util.Vector2d;

public class Satellite extends Entity2D {
	private double	speed	= 300;

	public Satellite(final double angle, final Vector2d center, final double radius) {
		super(new Vector2d());

		setSprite(new Sprite("satellite_64"));
		addComponent(new MoveCircleComponent(angle, center, radius, speed));
	}
}
