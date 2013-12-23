package net.tmt.entityComponents.move;

import net.tmt.entityComponents.ComponentDispatcher;
import net.tmt.util.CountdownTimer;
import net.tmt.util.MathUtil;
import net.tmt.util.Vector2d;

import org.jbox2d.common.Vec2;

/**
 * Physicscomponent where the Move-direction is linked to the rotation, usefull
 * for vehicles on Planet that cannot move sideways (cars, trains, people etc.)
 * 
 * @author Tim Schmiedl (Cyboot)
 * 
 */
class PhysicsComponentFixedRotation extends PhysicsComponent {
	private CountdownTimer	timerLastRotation	= CountdownTimer.createManualResetTimer(1);

	protected float			rotationSpeed;													// in
																							// radians/s

	@Override
	public void update(final ComponentDispatcher caller, final double delta) {
		super.update(caller, delta);
		adjustMoveToRotation();

		adjustFriction(caller, delta);
	}

	private void adjustFriction(final ComponentDispatcher caller, final double delta) {
		if (caller.isSet(RotateComponent.IS_ROTATE_LEFT))
			body.setAngularVelocity(-rotationSpeed);
		if (caller.isSet(RotateComponent.IS_ROTATE_RIGHT))
			body.setAngularVelocity(rotationSpeed);

		if (caller.isSet(RotateComponent.IS_ROTATE_LEFT) || caller.isSet(RotateComponent.IS_ROTATE_RIGHT))
			timerLastRotation.reset();

		if (timerLastRotation.isTimeUp(delta)) {
			body.setAngularDamping(frictionRotation);
		} else {
			body.setAngularDamping(10);
		}
	}

	private void adjustMoveToRotation() {
		float speed = body.getLinearVelocity().length();
		float rotAngle = (float) (body.getAngle() % (Math.PI * 2));
		float moveAngle = (float) Vector2d.fromVec2(body.getLinearVelocity()).getRotation();

		float angle = (float) (MathUtil.angleDiff(moveAngle, rotAngle) < Math.PI / 2 ? rotAngle : rotAngle + Math.PI);
		Vec2 vec2rot = new Vec2((float) Math.sin(angle), (float) (-Math.cos(angle)));

		body.setLinearVelocity(vec2rot.mulLocal(speed));
	}
}
