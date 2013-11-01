package net.tmt.entity.component;

public class RotateComponent extends Component {
	public static final String	IS_ROTATE_LEFT	= "IS_ROTATE_LEFT";
	public static final String	IS_ROTATE_RIGHT	= "IS_ROTATE_RIGHT";
	public static final String	ROTATION_SPEED	= "ROTATION_SPEED";

	private double				rotationSpeed;
	private double				rotationAngle;

	public RotateComponent(final double rotationAngle, final double rotationSpeed) {
		this.rotationAngle = rotationAngle;
		this.rotationSpeed = rotationSpeed;
	}

	@Override
	public void update(final ComponentDispatcher caller, final double delta) {
		if (caller.isSet(ROTATION_ANGLE)) {
			rotationAngle = (double) caller.getValue(ROTATION_ANGLE);
		}


		if (caller.isSet(ROTATION_SPEED)) {
			rotationSpeed = (double) caller.getValue(ROTATION_SPEED);
		}
		if (caller.isSet(IS_ROTATE_LEFT)) {
			rotationAngle -= rotationSpeed * delta;
		}
		if (caller.isSet(IS_ROTATE_RIGHT)) {
			rotationAngle += rotationSpeed * delta;
		}
		rotationAngle %= 360;

		caller.dispatch(ROTATION_ANGLE, rotationAngle);
	}

	@Override
	public void initialDispatch(final ComponentDispatcher caller) {
		super.initialDispatch(caller);

		caller.dispatch(ROTATION_ANGLE, rotationAngle);
		caller.dispatch(ROTATION_SPEED, rotationSpeed);
	}
}
