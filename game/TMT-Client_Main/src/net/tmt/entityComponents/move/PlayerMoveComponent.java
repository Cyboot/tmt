package net.tmt.entityComponents.move;

import net.tmt.entityComponents.Component;
import net.tmt.entityComponents.ComponentDispatcher;
import net.tmt.util.Vector2d;

public class PlayerMoveComponent extends Component {
	public static final String	IS_ACCELERATING		= "IS_ACCELERATING";
	public static final String	IS_DEACCELERATING	= "IS_DEACCELERATING";
	public static final String	ACCL_FACTOR			= "ACCL_FACTOR";

	private static final double	MIN_SPEED			= 15;

	private Vector2d			dir					= new Vector2d(10, 0);
	private double				friction			= 0.4;
	private double				accl;
	private double				maxSpeed;


	public PlayerMoveComponent(final double accl, final double friction, final double maxSpeed) {
		this.accl = accl;
		this.friction = friction;
		this.maxSpeed = maxSpeed;
	}


	@Override
	public void update(final ComponentDispatcher caller, final double delta) {
		pos.add(dir.x * delta, dir.y * delta);

		if (caller.isSet(ACCL_FACTOR)) {
			accl = (double) caller.getValue(ACCL_FACTOR);
		}

		if (caller.isSet(IS_ACCELERATING) || caller.isSet(IS_DEACCELERATING)) {
			int neg = caller.isSet(IS_DEACCELERATING) ? -1 : 1;
			double lookAngle = (double) caller.getValue(ROTATION_ANGLE_LOOK);
			double lookAngleRad = Math.toRadians(lookAngle);

			Vector2d tmpDir = Vector2d.tmp1;
			tmpDir.x = Math.sin(lookAngleRad) * delta * neg * accl;
			tmpDir.y = -Math.cos(lookAngleRad) * delta * neg * accl;

			dir.add(tmpDir);

			// check Max speed
			if (dir.length() > maxSpeed)
				dir.normalize().multiply(maxSpeed);
		} else {
			dir.multiply(1 - friction * delta);

			// too slow --> stand still
			if (dir.length() < MIN_SPEED)
				dir.set(0, 0);
		}

		caller.dispatch(MoveComponent.SPEED, dir.length());
		caller.dispatch(RotateComponent.ROTATION_ANGLE_MOVE, Math.toDegrees(dir.getRotation()));
	}

	@Override
	public void initialDispatch(final ComponentDispatcher caller) {
		super.initialDispatch(caller);
		caller.dispatch(MoveComponent.SPEED, dir.length());
	}
}
