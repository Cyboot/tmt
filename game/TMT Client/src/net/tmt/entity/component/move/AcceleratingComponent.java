package net.tmt.entity.component.move;

import net.tmt.entity.component.Component;
import net.tmt.entity.component.ComponentDispatcher;


public class AcceleratingComponent extends Component {
	public static final String	IS_ACCELERATING		= "IS_ACCELERATING";
	public static final String	IS_DEACCELERATING	= "IS_DEACCELERATING";

	public static final String	ACCL_FACTOR			= "ACCL_FACTOR";

	private static final double	MIN_SPEED			= 10;
	private static final double	MAX_SPEED			= 750;

	private double				accl				= 0;
	private double				friction			= 0;
	private double				speed;

	public AcceleratingComponent(final double accl, final double friction) {
		this.accl = accl;
		this.friction = friction;
	}

	@Override
	public void update(final ComponentDispatcher caller, final double delta) {
		if (caller.isSet(ACCL_FACTOR))
			accl = (double) caller.getValue(ACCL_FACTOR);

		if (caller.isSet(IS_ACCELERATING)) {
			speed += accl * delta;
		} else if ((caller.isSet(RotateComponent.IS_ROTATE_LEFT) || caller.isSet(RotateComponent.IS_ROTATE_RIGHT))
				&& speed < 50) {
			double rotationSpeed = (double) caller.getValue(RotateComponent.ROTATION_SPEED);
			speed += accl * rotationSpeed / 30 * delta;
		} else {
			if (caller.isSet(IS_DEACCELERATING))
				speed -= accl * delta;

			speed *= 1 - friction * delta;
			if (Math.abs(speed) < MIN_SPEED) {
				speed = 0;
			}
		}

		if (speed > MAX_SPEED)
			speed = MAX_SPEED;


		caller.dispatch(MoveComponent.SPEED, speed);
	}
}
