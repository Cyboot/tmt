package net.tmt.entity.npc;

import net.tmt.entity.Entity2D;
import net.tmt.entity.component.MoveComponent;
import net.tmt.gfx.Graphics;
import net.tmt.gfx.Sprite;
import net.tmt.util.Vector2d;

public class NPCSpaceShip extends Entity2D {

	public NPCSpaceShip(final Vector2d pos, final double speed) {
		super(pos);

		addComponent(new MoveComponent.Builder().pos(pos).speed(speed).dir(new Vector2d(1, 1).normalize()).build());
		setSprite(new Sprite("ship_ends_64", 32, 32));
	}

	@Override
	public void update(final double delta) {
		super.update(delta);
	}

	@Override
	public void render(final Graphics g) {
		super.render(g);
	}
}
