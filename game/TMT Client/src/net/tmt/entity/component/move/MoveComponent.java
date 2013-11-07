package net.tmt.entity.component.move;

import net.tmt.entity.component.Component;
import net.tmt.entity.component.ComponentDispatcher;
import net.tmt.util.Vector2d;


public class MoveComponent extends Component {
	public static final String	SPEED	= "SPEED";
	public static final String	DIR		= "DIR";

	private double				rotationAngle;

	private double				speed;
	private Vector2d			dir		= new Vector2d();

	public MoveComponent(final double speed, final double rotationAngle) {
		this(speed);
		this.rotationAngle = rotationAngle;

		this.dir = Vector2d.fromAngle(Math.toRadians(rotationAngle));
	}

	public MoveComponent(final double speed) {
		this.speed = speed;
	}

	@Override
	public void update(final ComponentDispatcher caller, final double delta) {
		super.update(caller, delta);

		if (caller.isSet(SPEED)) {
			speed = (double) caller.getValue(SPEED);
		}
		if (caller.isSet(RotateComponent.ROTATION_ANGLE_FAST)) {
			rotationAngle = (double) caller.getValue(RotateComponent.ROTATION_ANGLE_FAST);
		}

		dir.x = Math.sin(Math.toRadians(rotationAngle)) * speed * delta;
		dir.y = -Math.cos(Math.toRadians(rotationAngle)) * speed * delta;

		caller.dispatch(DIR, dir);
		pos.add(dir);
	}

	@Override
	public void initialDispatch(final ComponentDispatcher caller) {
		super.initialDispatch(caller);
		caller.dispatch(SPEED, speed);
		caller.dispatch(DIR, dir);
	}
}
