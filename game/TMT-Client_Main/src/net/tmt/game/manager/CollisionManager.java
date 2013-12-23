package net.tmt.game.manager;

import net.tmt.entity.Entity2D;
import net.tmt.game.interfaces.Updateable;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.collision.Manifold;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;

public class CollisionManager implements Updateable {
	public static float		PIXEL_PER_METER		= 10;

	public static final int	CATEGORY_PLAYABLE	= 0x00000002;

	private World			physWorld;

	private ContactListener	listener;

	public CollisionManager() {
		physWorld = new World(new Vec2());
		listener = new ContactListener();
		physWorld.setContactListener(listener);
	}

	@Override
	public void update(final double delta) {
		physWorld.step((float) delta, 8, 3);
	}

	public World getWorld() {
		return physWorld;
	}

	private static class ContactListener implements org.jbox2d.callbacks.ContactListener {

		@Override
		public void beginContact(final Contact contact) {
			Entity2D entityA = (Entity2D) contact.getFixtureA().getBody().getUserData();
			Entity2D entityB = (Entity2D) contact.getFixtureB().getBody().getUserData();

			if (contact.getFixtureA().isSensor()) {
				entityA.triggerSensor(entityB, true);
			}
			if (contact.getFixtureB().isSensor()) {
				entityB.triggerSensor(entityA, true);
			}

		}

		@Override
		public void endContact(final Contact contact) {
			Entity2D entityA = (Entity2D) contact.getFixtureA().getBody().getUserData();
			Entity2D entityB = (Entity2D) contact.getFixtureB().getBody().getUserData();

			if (contact.getFixtureA().isSensor()) {
				entityA.triggerSensor(entityB, false);
			}
			if (contact.getFixtureB().isSensor()) {
				entityB.triggerSensor(entityA, false);
			}
		}

		@Override
		public void postSolve(final Contact contact, final ContactImpulse impulse) {
			// System.out.println(impulse.normalImpulses[0]);
		}

		@Override
		public void preSolve(final Contact contact, final Manifold oldManifold) {
		}

	}
}
