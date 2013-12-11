package net.tmt.entity.npc;

import java.util.ArrayList;
import java.util.List;

import net.tmt.entity.Hero;
import net.tmt.entity.component.move.Move2TargetComponent;
import net.tmt.entity.component.move.MoveComponent;
import net.tmt.entity.component.other.AnimatedRenderComponent;
import net.tmt.game.factory.ComponentFactory;
import net.tmt.game.manager.EntityManager;
import net.tmt.gfx.Sprite;
import net.tmt.map.World;
import net.tmt.util.CountdownTimer;
import net.tmt.util.RandomUtil;
import net.tmt.util.Vector2d;

public class SpaceBug extends PlanetCreature {

	@SuppressWarnings("rawtypes")
	private List<Class>		afraidOf		= new ArrayList<Class>();
	// TODO: loop through entities and react to afraidOfs in update method
	private Vector2d		fleeTo			= new Vector2d();
	private double			saveDistance	= 500;
	private Hero			hero;
	private final double	ROTATION_SPEED	= 80;
	private CountdownTimer	changeDir		= new CountdownTimer(5);
	private double			normalSpeed		= 10;

	public SpaceBug(final Vector2d pos, final double health, final Hero hero) {
		super(pos, health);
		this.hero = hero;
		afraidOf.add(Hero.class);
		removeAllComponents();
		List<Sprite> aniSprites = new ArrayList<Sprite>();
		aniSprites.add(new Sprite("spacebug_0"));
		aniSprites.add(new Sprite("spacebug_1"));
		addComponent(new AnimatedRenderComponent(aniSprites, 0.5));
		ComponentFactory.addDefaultMove(this, 0, normalSpeed, ROTATION_SPEED);
		addComponent(new Move2TargetComponent(1));
		ComponentFactory.addDefaultCollision(SpaceBug.class, this, 20, 25);
	}

	@Override
	public void update(final EntityManager caller, final World world, final double delta) {
		fleeingBehaviour(delta);

		super.update(caller, world, delta);
	}

	private void fleeingBehaviour(final double delta) {
		if (pos.distanceTo(hero.getPos()) < saveDistance) {
			Vector2d.tmp1.set(pos);
			Vector2d.tmp1.sub(hero.getPos());
			Vector2d.tmp2.set(pos);
			Vector2d.tmp2.add(Vector2d.tmp1);
			fleeTo.set(Vector2d.tmp2);
			dispatchValue(MoveComponent.SPEED, normalSpeed * 3);
		} else {
			if (changeDir.isTimeUp(delta)) {
				Vector2d.tmp1.set(pos);
				Vector2d.tmp2.set(hero.getPos());
				Vector2d.tmp2.rotate(Math.toRadians(RandomUtil.doubleRange(0, 360)));
				Vector2d.tmp1.add(Vector2d.tmp2);
				fleeTo.set(Vector2d.tmp1);
			}
			dispatchValue(MoveComponent.SPEED, normalSpeed);
		}

		if (fleeTo != null)
			dispatchValue(Move2TargetComponent.SET_TARGET, fleeTo);
	}
}
