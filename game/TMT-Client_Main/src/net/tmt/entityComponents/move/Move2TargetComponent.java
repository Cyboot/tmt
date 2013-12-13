package net.tmt.entityComponents.move;

import net.tmt.entityComponents.Component;
import net.tmt.entityComponents.ComponentDispatcher;
import net.tmt.util.MathUtil;
import net.tmt.util.Vector2d;

public class Move2TargetComponent extends Component {
	public static final String	SET_TARGET		= "SET_TARGET";
	public static final String	TARGET_REACHED	= "TARGET_REACHED";

	private static double		MIN_ANGLE		= 5;

	private double				minDistance		= 32;
	private Vector2d			target_diff		= new Vector2d();
	private Vector2d			target;
	private double				rotation;
	private double				angleToTarget;

	public Move2TargetComponent() {
	}

	public Move2TargetComponent(final double minDistance) {
		this.minDistance = minDistance;
	}

	@Override
	public void update(final ComponentDispatcher caller, final double delta) {
		if (caller.isSet(SET_TARGET)) {
			target = (Vector2d) caller.getValue(SET_TARGET);
		}
		if (target != null)
			target_diff = getTargetDiff();

		if (target_diff != null) {
			angleToTarget = Math.toDegrees(target_diff.getRotation()) - rotation + 360;
			angleToTarget %= 360;
		}

		if (caller.isSet(ROTATION_ANGLE_LOOK)) {
			rotation = (double) caller.getValue(ROTATION_ANGLE_LOOK);
		}

		// reached target
		if (target != null && target.distanceTo(pos) < minDistance) {
			caller.dispatch(TARGET_REACHED, true);
		} else {
			caller.dispatch(TARGET_REACHED, false);
		}

		caller.dispatch(RotateComponent.IS_ROTATE_LEFT, false);
		caller.dispatch(RotateComponent.IS_ROTATE_RIGHT, false);

		// Rotation to target is bigget than Min_Angle --> rotateToTarget
		if (Math.abs(angleToTarget) > MIN_ANGLE) {


			if (MathUtil.nearestAngle(rotation, Math.toDegrees(target_diff.getRotation())) == -1)
				caller.dispatch(RotateComponent.IS_ROTATE_LEFT, true);
			else
				caller.dispatch(RotateComponent.IS_ROTATE_RIGHT, true);

			// rotation += neg * rotationSpeed * delta;
			// caller.dispatch(ROTATION_ANGLE_MOVE, rotation);
		}
	}

	private Vector2d getTargetDiff() {
		target_diff.set(target);
		return target_diff.sub(pos);
	}
}
