package net.tmt.game.manager;

import net.tmt.entity.Entity2D;
import net.tmt.game.interfaces.Updateable;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;

public class CollisionsManager implements Updateable {
	public static float	PIXEL_PER_METER	= 10;

	private World		physWorld;

	public CollisionsManager() {
		physWorld = new World(new Vec2());
	}

	@Override
	public void update(final double delta) {
		physWorld.step((float) delta, 8, 3);

		Body body = physWorld.getBodyList();
		while (body != null) {
			Entity2D entity = (Entity2D) body.getUserData();
			entity.getPos().setFromVec2(body.getPosition());

			// System.out.println("speed " + body.getLinearVelocity().length());

			body = body.getNext();
		}
	}

	public World getWorld() {
		return physWorld;
	}
}
