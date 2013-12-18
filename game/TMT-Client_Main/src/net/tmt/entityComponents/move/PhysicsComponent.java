package net.tmt.entityComponents.move;

import net.tmt.entityComponents.Component;
import net.tmt.entityComponents.ComponentDispatcher;
import net.tmt.game.manager.CollisionsManager;
import net.tmt.gfx.Graphics;
import net.tmt.util.DebugUtil;
import net.tmt.util.Vector2d;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.lwjgl.util.Color;

public class PhysicsComponent extends Component {
	public static final String	PHYS_ACCELERATING			= "PHYS_ACCELERATING";
	public static final String	PHYS_ACCELERATING_REVERSE	= "PHYS_ACCELERATING_REVERSE";
	public static final String	PHYS_ACCL_FACTOR			= "PHYS_ACCL_FACTOR";

	private Body				body;

	private Vec2				acclVector					= new Vec2();
	private float				accl;
	private float				speed;
	private float				maxSpeed;
	private float				minSpeed;

	private PhysicsComponent() {
	}

	@Override
	public void update(final ComponentDispatcher caller, final double delta) {
		if (caller.isSet(PHYS_ACCELERATING) || caller.isSet(PHYS_ACCELERATING_REVERSE)) {
			handleAccl(caller, 0, delta);
		}

		if (caller.isSet(RotateComponent.IS_ROTATE_LEFT)) {
			body.applyAngularImpulse(-.5f);
		}
		if (caller.isSet(RotateComponent.IS_ROTATE_RIGHT)) {
			body.applyAngularImpulse(.5f);
		}

		body.setAngularDamping(10);

		caller.dispatch(ROTATION_ANGLE_LOOK, Math.toDegrees(body.getAngle()));
		caller.dispatch(RotateComponent.ROTATION_ANGLE_MOVE, Math.toDegrees(body.getAngle()));

		checkSpeed();

		caller.dispatch(MoveComponent.SPEED, (double) speed);
	}

	@Override
	public void render(final ComponentDispatcher caller, final Graphics g) {
		if (DebugUtil.renderCollision) {
			g.setColor(Color.YELLOW);

			Shape shape = body.getFixtureList().getShape();
			if (shape instanceof CircleShape) {
				g.drawCircle(pos.x, pos.y, shape.getRadius() * CollisionsManager.PIXEL_PER_METER);
			}
		}
	}

	private void checkSpeed() {
		Vec2 velocity = body.getLinearVelocity();
		speed = velocity.length();

		// check MaxSpeed
		if (speed > maxSpeed) {
			velocity.normalize();
			velocity.mulLocal(maxSpeed);
		}

		// check MinSpeed
		if (speed < minSpeed) {
			velocity.set(0, 0);
		}

		body.setLinearVelocity(velocity);
		speed = velocity.length();
	}

	private void handleAccl(final ComponentDispatcher caller, final double rotation, final double delta) {
		int neg = caller.isSet(PHYS_ACCELERATING_REVERSE) ? -1 : 1;
		double lookAngleRad = Math.toRadians((double) caller.getValue(ROTATION_ANGLE_LOOK) + rotation);

		acclVector.x = (float) (Math.sin(lookAngleRad) * neg * accl);
		acclVector.y = (float) (-Math.cos(lookAngleRad) * neg * accl);
		acclVector.mulLocal(accl);

		body.applyForceToCenter(acclVector);
	}

	@Override
	public void initialDispatch(final ComponentDispatcher caller) {
		super.initialDispatch(caller);

		// set owner (Entity2D) as UserData
		body.setUserData(owner);

		caller.dispatch(ROTATION_ANGLE_LOOK, 0);
		caller.dispatch(MoveComponent.SPEED, (double) speed);
	}

	public static class Builder {
		private static float		DEFAULT_ACCL		= 10;	// delta m/s
		private static float		DEFAULT_MAX_SPEED	= 50;	// m/s
		private static final float	MIN_SPEED			= 0.1f;

		private CollisionsManager	collisionsManager;

		private Vec2				physPos;
		private Shape				shape;
		private BodyDef				bodyDef;
		private FixtureDef			fixtureDef;

		private PhysicsComponent	result;

		public Builder(final CollisionsManager collisionsManager, final Vector2d pos) {
			this.collisionsManager = collisionsManager;
			physPos = pos.toVec2();

			// default values
			result = new PhysicsComponent();
			result.accl = DEFAULT_ACCL;
			result.maxSpeed = DEFAULT_MAX_SPEED;
			result.minSpeed = MIN_SPEED;

			// default BodyDef
			bodyDef = new BodyDef();
			bodyDef.position = physPos;
			bodyDef.type = BodyType.DYNAMIC;
			bodyDef.angularDamping = 0.5f;
			bodyDef.linearDamping = 0.5f;

			// default FixtureDef
			fixtureDef = new FixtureDef();
			fixtureDef.density = 1;
			fixtureDef.restitution = 0.1f;
		}

		public Builder circleShape(final float radius) {
			shape = new CircleShape();
			shape.setRadius(radius);
			return this;
		}

		public Builder makeStatic() {
			bodyDef.type = BodyType.STATIC;
			return this;
		}

		public Builder accl(final float accl) {
			result.accl = accl;
			return this;
		}

		public Builder maxSpeed(final float maxSpeed) {
			result.maxSpeed = maxSpeed;
			return this;
		}

		public Builder minSpeed(final float minSpeed) {
			result.minSpeed = minSpeed;
			return this;
		}

		public PhysicsComponent create() {
			World physWorld = collisionsManager.getWorld();

			fixtureDef.shape = shape;
			result.body = physWorld.createBody(bodyDef);
			result.body.createFixture(fixtureDef);

			return result;
		}

		public Builder friction(final float friction) {
			bodyDef.linearDamping = friction;
			return this;
		}

		public Builder density(final float density) {
			fixtureDef.density = density;
			return this;
		}
	}
}
