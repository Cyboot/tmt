package net.tmt.entityComponents.move;

import net.tmt.entityComponents.Component;
import net.tmt.entityComponents.ComponentDispatcher;

public class RotateComponent extends Component {
	public static final String	IS_ROTATE_LEFT		= "IS_ROTATE_LEFT";
	public static final String	IS_ROTATE_RIGHT		= "IS_ROTATE_RIGHT";
	public static final String	FAST_ROTATE			= "FAST_ROTATE";
	public static final String	ROTATION_SPEED		= "ROTATION_SPEED";

	public static String		ROTATION_ANGLE_MOVE	= "ROTATION_ANGLE_REAL_MOVE";

	private double				rotationSpeed;
	private double				rotationAngleMove;
	private double				rotationAngleLook;

	public RotateComponent(final double rotationAngle, final double rotationSpeed) {
		this.rotationAngleMove = rotationAngle;
		this.rotationAngleLook = rotationAngle;
		this.rotationSpeed = rotationSpeed;
	}

	@Override
	public void update(final ComponentDispatcher caller, final double delta) {
		if (caller.isSet(ROTATION_SPEED)) {
			rotationSpeed = (double) caller.getValue(ROTATION_SPEED);
		}

		// rotate left or right
		boolean fast = caller.isSet(FAST_ROTATE);
		if (caller.isSet(IS_ROTATE_LEFT) && (boolean) caller.getValue(IS_ROTATE_LEFT)) {
			rotate(-1, fast, delta);
		}
		if (caller.isSet(IS_ROTATE_RIGHT) && (boolean) caller.getValue(IS_ROTATE_RIGHT)) {
			rotate(1, fast, delta);
		}

		rotationAngleMove = (rotationAngleMove + 360) % 360;
		rotationAngleLook = (rotationAngleLook + 360) % 360;

		caller.dispatch(ROTATION_ANGLE_LOOK, rotationAngleLook);
		caller.dispatch(ROTATION_ANGLE_MOVE, rotationAngleMove);
	}

	private void rotate(final int neg, final boolean fast, final double delta) {
		double factor = 1;
		if (fast) {
			factor = 0.2;
			rotationAngleLook += neg * rotationSpeed * 3 * delta;
			rotationAngleMove += neg * rotationSpeed * factor * delta;
		} else {
			rotationAngleLook += neg * rotationSpeed * factor * delta;
			rotationAngleMove += neg * rotationSpeed * factor * delta;
		}
	}

	@Override
	public void initialDispatch(final ComponentDispatcher caller) {
		super.initialDispatch(caller);

		caller.dispatch(ROTATION_ANGLE_MOVE, rotationAngleMove);
		caller.dispatch(ROTATION_SPEED, rotationSpeed);
	}
}
