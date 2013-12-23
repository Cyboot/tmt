package net.tmt.entity.ambient;

import net.tmt.entity.Entity2D;
import net.tmt.entityComponents.move.PhysicsComponent.Builder;
import net.tmt.entityComponents.move.RotateComponent;
import net.tmt.game.factory.ComponentFactory;
import net.tmt.game.manager.CollisionManager;
import net.tmt.game.manager.EntityManager;
import net.tmt.gfx.Sprite;
import net.tmt.map.World;
import net.tmt.util.MathUtil;
import net.tmt.util.RandomUtil;
import net.tmt.util.Vector2d;

/**
 * generic class for all kind of noninteractive Entities
 * 
 * @author Tim Schmiedl (Cyboot)
 */
public class Prop extends Entity2D {
	public static enum Type {
		SPACEROCK, SUN, NEBULA, DEBRIS, TREE, DECAL, ANNO_BUILDING, BUILDING_1;
	}

	private boolean	rotateLeft;

	private Prop(final Vector2d pos) {
		super(pos);
	}

	private void addStaticCollisionRect(final CollisionManager collisionsManager, final float width,
			final float height, final float density) {
		Builder builder = new Builder(collisionsManager, pos, false);
		builder.rectShape(width, height).density(density).makeStatic();
		addComponent(builder.create());
	}

	private void addStaticCollisionCircle(final CollisionManager collisionsManager, final float radius,
			final float density) {
		Builder builder = new Builder(collisionsManager, pos, false);
		builder.circleShape(radius).density(density).makeStatic();
		addComponent(builder.create());
	}

	private void addMove(final double rotation, final double speed, final double rotationSpeed, final boolean rotateLeft) {
		this.rotateLeft = rotateLeft;
		ComponentFactory.addDefaultMove(this, rotation, speed, rotationSpeed);
	}

	@Override
	public void update(final EntityManager caller, final World world, final double delta) {
		if (rotateLeft)
			dispatchValue(RotateComponent.IS_ROTATE_LEFT, true);
		else
			dispatchValue(RotateComponent.IS_ROTATE_RIGHT, true);

		super.update(caller, world, delta);
	}


	public static Prop createPropGeneric(final Vector2d pos, final Sprite sprite) {
		Prop result = new Prop(pos);
		result.setSprite(sprite);
		return result;
	}


	public static Prop createProp(final Type type, final Vector2d pos, final CollisionManager collMng) {
		Prop result = new Prop(pos);

		int size = 0;
		double rotation = 0;
		switch (type) {
		case SPACEROCK:
			result.setSprite(new Sprite("spacerock_64"));
			result.addStaticCollisionCircle(collMng, MathUtil.toBox2d(64 / 2), 2);
			result.addMove(0, 10, RandomUtil.intRange(1, 20), RandomUtil.randBoolean());
			break;
		case SUN:
			result.setSprite(new Sprite("sun_default"));
			break;
		case NEBULA:
			result.setSprite(new Sprite("nebula_default"));
			break;
		case DEBRIS:
			result.setSprite(new Sprite("debris_default"));
			break;
		case ANNO_BUILDING:
			result.setSprite(new Sprite("anno_building"));
			break;
		case BUILDING_1:
			result.addStaticCollisionRect(collMng, MathUtil.toBox2d(512), MathUtil.toBox2d(512), 2);
			result.setSprite(new Sprite("building_1").setRotation(rotation));
			break;
		case TREE:
			size = RandomUtil.intRange(32, 128);
			result.addStaticCollisionCircle(collMng, MathUtil.toBox2d(size / 2), 2);
			result.setSprite(new Sprite("tree_3", size, size));
			break;
		case DECAL:
			size = RandomUtil.intRange(32, 128);
			rotation = RandomUtil.doubleRange(0, 360);

			String sprite = "";
			switch (RandomUtil.intRange(0, 4)) {
			case 0:
				sprite = "decal_mountain_1";
				break;
			case 1:
				sprite = "decal_mountain_2";
				break;
			case 2:
				sprite = "decal_sand_1";
				break;
			case 3:
				sprite = "decal_swamp_1";
				break;
			case 4:
				sprite = "decal_swamp_2";
				break;
			}
			result.setSprite(new Sprite(sprite, size, size).setRotation(rotation));
			break;
		default:
			break;
		}

		return result;
	}
}
