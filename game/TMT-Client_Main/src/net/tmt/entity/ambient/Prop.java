package net.tmt.entity.ambient;

import net.tmt.entity.Entity2D;
import net.tmt.entityComponents.move.RotateComponent;
import net.tmt.game.factory.ComponentFactory;
import net.tmt.game.manager.EntityManager;
import net.tmt.gfx.Sprite;
import net.tmt.map.World;
import net.tmt.util.RandomUtil;
import net.tmt.util.Vector2d;

/**
 * generic class for all kind of noninteractive Entities
 * 
 * @author Tim Schmiedl (Cyboot)
 */
public class Prop extends Entity2D {
	public static enum Type {
		SPACEROCK, SUN, NEBULA, DEBRIS;
	}

	private boolean	rotateLeft;

	private Prop(final Vector2d pos) {
		super(pos);
	}

	private void addCollision() {
		ComponentFactory.addDefaultCollision(this, getSprite().getWidth() / 2, 50);
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


	public static Entity2D createPropGeneric(final Vector2d pos, final Sprite sprite) {
		Prop result = new Prop(pos);
		result.setSprite(sprite);
		return result;
	}

	public static Entity2D createProp(final Type type, final Vector2d pos) {
		Prop result = new Prop(pos);

		switch (type) {
		case SPACEROCK:
			result.setSprite(new Sprite("spacerock_64"));
			result.addCollision();
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
		default:
			break;
		}

		return result;
	}
}
