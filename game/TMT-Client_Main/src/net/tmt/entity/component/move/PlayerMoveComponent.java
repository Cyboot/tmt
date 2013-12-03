package net.tmt.entity.component.move;

import net.tmt.entity.component.Component;
import net.tmt.entity.component.ComponentDispatcher;
import net.tmt.util.Vector2d;

public class PlayerMoveComponent extends Component {
	public static final String	IS_ACCELERATING	= "IS_ACCELERATING";

	public static final String	ACCL_FACTOR		= "ACCL_FACTOR";

	private static final double	MIN_SPEED		= 15;
	private static final double	MAX_SPEED		= 550;

	private Vector2d			dir				= new Vector2d(10, 0);

	private double				friction		= 0.4;

	@Override
	public void update(final ComponentDispatcher caller, final double delta) {
		pos.add(dir.x * delta, dir.y * delta);

		if (caller.isSet(IS_ACCELERATING)) {
			double lookAngle = (double) caller.getValue(ROTATION_ANGLE_LOOK);
			double lookAngleRad = Math.toRadians(lookAngle);

			double accl = 450;

			Vector2d tmpDir = Vector2d.tmp1;
			tmpDir.x = Math.sin(lookAngleRad) * delta * accl;
			tmpDir.y = -Math.cos(lookAngleRad) * delta * accl;

			dir.add(tmpDir);

			// check Max speed
			if (dir.length() > MAX_SPEED)
				dir.normalize().multiply(MAX_SPEED);
		} else {
			dir.multiply(1 - friction * delta);

			// to slow --> stand still
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
