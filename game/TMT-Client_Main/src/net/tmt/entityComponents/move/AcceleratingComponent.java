package net.tmt.entityComponents.move;

import net.tmt.entityComponents.Component;
import net.tmt.entityComponents.ComponentDispatcher;


public class AcceleratingComponent extends Component {
	public static final String	IS_ACCELERATING		= "IS_ACCELERATING";
	public static final String	IS_DEACCELERATING	= "IS_DEACCELERATING";
	public static final String	ACCL_FACTOR			= "ACCL_FACTOR";

	private static final double	DEFAULT_MIN_SPEED	= 10;

	private boolean				reverse				= true;
	private double				accl;
	private double				friction;
	private double				speed;
	private double				maxSpeed;


	/**
	 * 
	 * @param accl
	 *            Accleration in delta units/s
	 * @param friction
	 *            friction (in percent of speed after 1 second of no
	 *            accleartion, e.g. 0.25 --> 25% speed after one second)
	 * @param maxSpeed
	 *            maximum speed
	 * @param reverse
	 *            "reverse gear", if set to true (default) deaccl at speed 0 -->
	 *            entity will move backwards
	 */
	public AcceleratingComponent(final double accl, final double friction, final double maxSpeed, final boolean reverse) {
		this.accl = accl;
		this.friction = friction;
		this.maxSpeed = maxSpeed;
		this.reverse = reverse;
	}


	@Override
	public void update(final ComponentDispatcher caller, final double delta) {
		if (caller.isSet(ACCL_FACTOR))
			accl = (double) caller.getValue(ACCL_FACTOR);

		if (caller.isSet(IS_ACCELERATING)) {
			speed += accl * delta;
		} else if (caller.isSet(IS_DEACCELERATING)) {
			speed -= accl * delta;

			if (!reverse && speed < 0)
				speed = 0;
		} else {
			speed *= 1 - friction * delta;
			if (Math.abs(speed) < DEFAULT_MIN_SPEED) {
				speed = 0;
			}
		}

		if (speed > maxSpeed)
			speed = maxSpeed;
		if (speed < -maxSpeed / 2)
			speed = -maxSpeed / 2;

		caller.dispatch(MoveComponent.SPEED, speed);
	}
}
