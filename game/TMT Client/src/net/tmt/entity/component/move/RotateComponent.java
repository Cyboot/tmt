package net.tmt.entity.component.move;

import net.tmt.entity.PlayerSpaceShip;
import net.tmt.entity.component.Component;
import net.tmt.entity.component.ComponentDispatcher;
import net.tmt.util.MathUtil;
import net.tmt.util.StringFormatter;

public class RotateComponent extends Component {
	public static final String	IS_ROTATE_LEFT		= "IS_ROTATE_LEFT";
	public static final String	IS_ROTATE_RIGHT		= "IS_ROTATE_RIGHT";
	public static final String	FAST_ROTATE			= "FAST_ROTATE";
	public static final String	ROTATION_SPEED		= "ROTATION_SPEED";

	public static final String	ROTATION_ANGLE_FAST	= "ROTATION_ANGLE_FAST";

	private double				rotationSpeed;
	private double				rotationAngleMove;
	private double				rotationAngleFast;

	public RotateComponent(final double rotationAngle, final double rotationSpeed) {
		this.rotationAngleMove = rotationAngle;
		this.rotationSpeed = rotationSpeed;
	}

	@Override
	public void update(final ComponentDispatcher caller, final double delta) {
		// if (caller.isSet(ROTATION_ANGLE_MOVE)) {
		// rotationAngleMove = (double) caller.getValue(ROTATION_ANGLE_MOVE);
		// }

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


		// adjust the fastAngle to moveAngle
		if (!caller.isSet(IS_ROTATE_LEFT) && !caller.isSet(IS_ROTATE_RIGHT))
			adjustToMoveAngle(delta);

		rotationAngleMove = (rotationAngleMove + 360) % 360;
		rotationAngleFast = (rotationAngleFast + 360) % 360;

		// if (fastRotateEnable)
		caller.dispatch(ROTATION_ANGLE_FAST, rotationAngleMove);
		caller.dispatch(ROTATION_ANGLE_MOVE, rotationAngleFast);
	}

	private void rotate(final int neg, final boolean fast, final double delta) {
		double factor = 1;
		if (fast) {
			factor = 0.2;
			rotationAngleFast += neg * rotationSpeed * 3 * delta;
			rotationAngleMove += neg * rotationSpeed * factor * delta;
		} else {
			rotationAngleFast += neg * rotationSpeed * factor * delta;
			rotationAngleMove += neg * rotationSpeed * factor * delta;
		}
	}

	private void adjustToMoveAngle(final double delta) {
		double diff = rotationAngleFast - rotationAngleMove;
		int neg = MathUtil.nearestAngle(rotationAngleMove, rotationAngleFast);

		if (owner instanceof PlayerSpaceShip) {
			System.out.println(StringFormatter.format(diff));
			// if (neg == 1)
			// System.out.println("->");
			// else
			// System.out.println("<-");
		}
		rotationAngleMove += neg * rotationSpeed * delta;
	}

	@Override
	public void initialDispatch(final ComponentDispatcher caller) {
		super.initialDispatch(caller);

		caller.dispatch(ROTATION_ANGLE_MOVE, rotationAngleMove);
		caller.dispatch(ROTATION_SPEED, rotationSpeed);
	}
}
