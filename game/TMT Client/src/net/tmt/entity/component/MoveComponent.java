package net.tmt.entity.component;

import net.tmt.util.Vector2d;


public class MoveComponent extends Component {
	public static final String	IS_ACCELERATING		= "IS_ACCELERATING";
	public static final String	IS_DEACCELERATING	= "IS_DEACCELERATING";
	public static final String	IS_ROTATE_LEFT		= "IS_ROTATE_LEFT";
	public static final String	IS_ROTATE_RIGHT		= "IS_ROTATE_RIGHT";
	public static final String	SPEED				= "SPEED";
	public static final String	POSITION			= "POSITION";
	public static final String	DIR					= "DIR";
	public static final String	ROTATION_SPEED		= "ROTATION_SPEED";

	private static final double	MIN_SPEED			= 10;

	private double				rotationSpeed		= 0;
	private double				rotationAngle		= 0;
	private double				accl				= 0;
	private double				friction			= 0;
	private double				speed;
	private Vector2d			dir					= new Vector2d();
	private Vector2d			pos					= new Vector2d();

	private MoveComponent() {
	}


	public MoveComponent(final double accl, final double friction, final double speed, final Vector2d pos) {
		this.accl = accl;
		this.friction = friction;
		this.speed = speed;
		this.pos = pos;
	}


	@Override
	public void update(final ComponentDispatcher caller, final double delta) {
		super.update(caller, delta);

		if (caller.isSet(ROTATION_SPEED)) {
			rotationSpeed = (double) caller.getValue(ROTATION_SPEED);
		}
		if (caller.isSet(SPEED)) {
			speed = (double) caller.getValue(SPEED);
		}
		if (caller.isSet(IS_ACCELERATING)) {
			speed += accl;
		}
		if (caller.isSet(IS_DEACCELERATING)) {
			speed -= accl;
		}
		speed *= 1 - friction * delta;
		if (Math.abs(speed) < MIN_SPEED && !caller.isSet(IS_ACCELERATING) && !caller.isSet(IS_DEACCELERATING)) {
			speed = 0;
		}

		if (caller.isSet(ROTATION_ANGLE)) {
			rotationAngle = (double) caller.getValue(ROTATION_ANGLE);
		}


		dir.x = Math.sin(Math.toRadians(rotationAngle)) * speed * delta;
		dir.y = -Math.cos(Math.toRadians(rotationAngle)) * speed * delta;

		if (caller.isSet(IS_ROTATE_LEFT)) {
			rotationAngle -= rotationSpeed * delta;
		}
		if (caller.isSet(IS_ROTATE_RIGHT)) {
			rotationAngle += rotationSpeed * delta;
		}
		rotationAngle %= 360;


		caller.dispatch(ROTATION_ANGLE, rotationAngle);
		pos.add(dir);
	}

	@Override
	public void dispatch(final ComponentDispatcher caller) {
		if (caller.isSet(ROTATION_SPEED)) {
			rotationSpeed = (double) caller.getValue(ROTATION_SPEED);
		}

		caller.dispatch(ROTATION_ANGLE, rotationAngle);
		caller.dispatch(ROTATION_SPEED, rotationSpeed);
		caller.dispatch(POSITION, pos);
		caller.dispatch(DIR, dir);
	}

	public static class Builder {
		private MoveComponent	move;

		public Builder() {
			move = new MoveComponent();
		}

		public Builder dir(final Vector2d dir) {
			move.dir = dir;
			move.rotationAngle = Math.toDegrees(dir.getRotation());
			return this;
		}

		public Builder pos(final Vector2d pos) {
			move.pos = pos;
			return this;
		}

		public Builder speed(final double speed) {
			move.speed = speed;
			return this;
		}

		public Builder rotationSpeed(final double rotation_speed) {
			move.rotationSpeed = rotation_speed;
			return this;
		}

		public Builder accl(final double accl) {
			move.accl = accl;
			return this;
		}

		public Builder friction(final double friction) {
			move.friction = friction;
			return this;
		}

		public MoveComponent build() {
			return move;
		}
	}
}
