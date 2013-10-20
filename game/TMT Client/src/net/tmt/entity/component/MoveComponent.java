package net.tmt.entity.component;

import net.tmt.util.Vector2d;


public class MoveComponent extends Component {
	public static String		IS_ACCELERATING		= "IS_ACCELERATING";
	public static String		IS_DEACCELERATING	= "IS_DEACCELERATING";
	public static String		IS_ROTATE_LEFT		= "IS_ROTATE_LEFT";
	public static String		IS_ROTATE_RIGHT		= "IS_ROTATE_RIGHT";
	public static String		SPEED				= "SPEED";

	private static final double	ROTATION_SPEED		= 180;

	private double				rotationAngle		= 0;
	private double				accl				= 0;
	private double				friction			= 0;
	private double				speed;
	private Vector2d			dir					= new Vector2d();
	private Vector2d			pos					= new Vector2d();

	@Override
	public void update(final ComponentDispatcher caller, final double delta) {
		super.update(caller, delta);

		if (caller.isSet(IS_ACCELERATING)) {
			speed += accl;
		}
		if (caller.isSet(IS_DEACCELERATING)) {
			speed -= accl;
		}
		speed *= 1 - friction * delta;
		if (caller.isSet(SPEED)) {
			speed = (double) caller.getValue(SPEED);
		}


		dir.x = Math.sin(Math.toRadians(rotationAngle)) * speed * delta;
		dir.y = -Math.cos(Math.toRadians(rotationAngle)) * speed * delta;

		if (caller.isSet(IS_ROTATE_LEFT)) {
			rotationAngle -= ROTATION_SPEED * delta;
		}
		if (caller.isSet(IS_ROTATE_RIGHT)) {
			rotationAngle += ROTATION_SPEED * delta;
		}

		caller.dispatch(ROTATION_ANGLE, rotationAngle);
		pos.add(dir);
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
