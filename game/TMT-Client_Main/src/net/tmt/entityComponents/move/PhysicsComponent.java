package net.tmt.entityComponents.move;

import net.tmt.entityComponents.Component;
import net.tmt.entityComponents.ComponentDispatcher;
import net.tmt.game.manager.CollisionManager;
import net.tmt.gfx.Graphics;
import net.tmt.util.DebugUtil;
import net.tmt.util.Vector2d;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.lwjgl.util.Color;

public class PhysicsComponent extends Component {
	public static final String	DISABLE						= "DISABLE";
	public static final String	PHYS_ACCELERATING			= "PHYS_ACCELERATING";
	public static final String	PHYS_ACCELERATING_REVERSE	= "PHYS_ACCELERATING_REVERSE";
	public static final String	PHYS_ACCL_FACTOR			= "PHYS_ACCL_FACTOR";

	private Body				body;

	private Vec2				acclVector					= new Vec2();
	private float				accl;
	private float				acclFactor;
	private float				speed;
	private float				maxSpeed;
	private boolean				isSensor					= false;

	private PhysicsComponent() {
	}

	@Override
	public void update(final ComponentDispatcher caller, final double delta) {
		// a sensors position must be set to the entities position, not the
		// other way round (like default case)
		if (isSensor || caller.isSet(DISABLE)) {
			body.setTransform(pos.toVec2(), body.getAngle());
		} else
			pos.setFromVec2(body.getPosition());

		if (caller.isSet(DISABLE))
			return;

		if (caller.isSet(PHYS_ACCL_FACTOR)) {
			acclFactor = (float) ((double) caller.getValue(PHYS_ACCL_FACTOR));
		} else
			acclFactor = 1;

		if (caller.isSet(PHYS_ACCELERATING) || caller.isSet(PHYS_ACCELERATING_REVERSE)) {
			handleAccl(caller, 0, delta);
		}

		if (caller.isSet(RotateComponent.IS_ROTATE_LEFT)) {
			body.applyAngularImpulse(-accl / 50);
		}
		if (caller.isSet(RotateComponent.IS_ROTATE_RIGHT)) {
			body.applyAngularImpulse(accl / 50);
		}

		caller.dispatch(ROTATION_ANGLE_LOOK, Math.toDegrees(body.getAngle()));
		caller.dispatch(RotateComponent.ROTATION_ANGLE_MOVE, Math.toDegrees(body.getAngle()));

		// check MIN/MAX speed
		checkSpeed();

		caller.dispatch(MoveComponent.SPEED, (double) speed);
	}

	@Override
	public void render(final ComponentDispatcher caller, final Graphics g) {
		if (DebugUtil.renderCollision) {
			g.setColor(Color.YELLOW);

			Shape shape = body.getFixtureList().getShape();
			if (shape instanceof CircleShape) {
				g.drawCircle(pos.x, pos.y, shape.getRadius() * CollisionManager.PIXEL_PER_METER);
			}

			if (shape instanceof PolygonShape) {
				Vec2[] vertices = ((PolygonShape) shape).getVertices();

				net.tmt.gfx.Shape renderShape = new net.tmt.gfx.Shape();
				int index = 0;
				for (Vec2 vec2 : vertices) {
					if (index++ > 3)
						break;
					renderShape.addPoint(Vector2d.fromVec2(vec2));
				}
				renderShape.setRotation(Math.toDegrees(body.getAngle()));

				g.drawShape(pos, renderShape);
			}
		}
	}

	private void checkSpeed() {
		Vec2 velocity = body.getLinearVelocity();
		speed = velocity.length();

		// check MaxSpeed
		if (speed > maxSpeed * acclFactor) {
			velocity.normalize();
			velocity.mulLocal(maxSpeed * acclFactor);
		}

		body.setLinearVelocity(velocity);
		speed = velocity.length();
	}

	private void handleAccl(final ComponentDispatcher caller, final double rotation, final double delta) {
		int neg = caller.isSet(PHYS_ACCELERATING_REVERSE) ? -1 : 1;
		double lookAngleRad = body.getAngle() + rotation;

		acclVector.x = (float) (Math.sin(lookAngleRad) * neg);
		acclVector.y = (float) (-Math.cos(lookAngleRad) * neg);
		acclVector.mulLocal(accl * acclFactor);

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

		private CollisionManager	collisionsManager;

		private Vec2				physPos;
		private Shape				shape;
		private BodyDef				bodyDef;
		private FixtureDef			fixtureDef;

		private PhysicsComponent	result;

		public Builder(final CollisionManager collisionsManager, final Vector2d pos) {
			this.collisionsManager = collisionsManager;
			physPos = pos.toVec2();

			// default values
			result = new PhysicsComponent();
			result.accl = DEFAULT_ACCL;
			result.maxSpeed = DEFAULT_MAX_SPEED;

			// default BodyDef
			bodyDef = new BodyDef();
			bodyDef.position = physPos;
			bodyDef.type = BodyType.DYNAMIC;
			bodyDef.angularDamping = 1f;
			bodyDef.linearDamping = 0.5f;

			// default FixtureDef
			fixtureDef = new FixtureDef();
			fixtureDef.density = 1;
			fixtureDef.restitution = 0.1f;
		}

		/**
		 * Rectangular Collisionshape, centered around position
		 * 
		 * @param height
		 * @param width
		 * @return
		 */
		public Builder rectShape(final float width, final float height) {
			shape = new PolygonShape();
			((PolygonShape) shape).setAsBox(width / 2, height / 2);
			return this;
		}

		/**
		 * set the CollisionShape to the given shape
		 * 
		 * @param shape
		 * @return
		 */
		public Builder setShape(final Shape shape) {
			this.shape = shape;
			return this;
		}

		/**
		 * Cicle Collisionshape, centered around position
		 * 
		 * @param radius
		 * @return
		 */
		public Builder circleShape(final float radius) {
			shape = new CircleShape();
			shape.setRadius(radius);
			return this;
		}

		public Builder makeSensor(final int category) {
			result.isSensor = true;
			fixtureDef.isSensor = true;
			bodyDef.type = BodyType.KINEMATIC;
			return collideWith(category);
		}

		public Builder setCategory(final int category) {
			fixtureDef.filter.categoryBits = category;
			return this;
		}

		public Builder collideWith(final int categories) {
			fixtureDef.filter.maskBits = categories;
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

		public Builder friction(final float friction) {
			bodyDef.linearDamping = friction;
			bodyDef.angularDamping = friction * 2;
			return this;
		}

		public Builder density(final float density) {
			fixtureDef.density = density;
			return this;
		}

		public PhysicsComponent create() {
			World physWorld = collisionsManager.getWorld();

			fixtureDef.shape = shape;
			result.body = physWorld.createBody(bodyDef);
			result.body.createFixture(fixtureDef);

			return result;
		}
	}
}
