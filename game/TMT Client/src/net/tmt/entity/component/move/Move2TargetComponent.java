package net.tmt.entity.component.move;

import net.tmt.entity.component.Component;
import net.tmt.entity.component.ComponentDispatcher;
import net.tmt.util.Vector2d;

public class Move2TargetComponent extends Component {
	public static final String	SET_TARGET		= "SET_TARGET";
	public static final String	TARGET_REACHED	= "TARGET_REACHED";

	private static double		MIN_ANGLE		= 5;

	private double				minDistance		= 32;
	private Vector2d			target_diff		= new Vector2d();
	private Vector2d			target;
	private double				rotationSpeed;
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

		if (caller.isSet(ROTATION_ANGLE)) {
			rotation = (double) caller.getValue(ROTATION_ANGLE);
		}

		// reached target
		if (target != null && target.distanceTo(pos) < minDistance) {
			caller.dispatch(TARGET_REACHED, true);
		} else {
			caller.dispatch(TARGET_REACHED, false);
		}


		// Rotation to target is bigget than Min_Angle --> rotateToTarget
		if (Math.abs(angleToTarget) > MIN_ANGLE) {
			int neg = angleToTarget > 180 ? -1 : 1;

			rotation += neg * rotationSpeed * delta;
			caller.dispatch(ROTATION_ANGLE, rotation);
		}
	}

	private Vector2d getTargetDiff() {
		target_diff.set(target);
		return target_diff.sub(pos);
	}

	@Override
	public void initialDispatch(final ComponentDispatcher caller) {
		super.initialDispatch(caller);
		rotationSpeed = (double) caller.getValue(RotateComponent.ROTATION_SPEED);
	}
}
