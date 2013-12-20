package net.tmt.entity.npc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.tmt.entity.Entity2D;
import net.tmt.entity.Hero;
import net.tmt.entity.pickups.MoneyBundle;
import net.tmt.entityComponents.move.Move2TargetComponent;
import net.tmt.entityComponents.move.MoveComponent;
import net.tmt.entityComponents.other.AnimatedRenderComponent;
import net.tmt.entityComponents.other.DropOnDeathComponent;
import net.tmt.game.factory.ComponentFactory;
import net.tmt.game.manager.EntityManager;
import net.tmt.global.mission.MissionDispatcher;
import net.tmt.map.World;
import net.tmt.util.CountdownTimer;
import net.tmt.util.RandomUtil;
import net.tmt.util.SpriteAnimation;
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
		Map<String, SpriteAnimation> aniMap = new HashMap<String, SpriteAnimation>();
		SpriteAnimation bug = new SpriteAnimation(new String[] { "spacebug_0", "spacebug_1" }, 0.5);
		aniMap.put("bug", bug);
		addComponent(new AnimatedRenderComponent(aniMap, "bug"));
		ComponentFactory.addDefaultMove(this, 0, normalSpeed, ROTATION_SPEED);
		addComponent(new Move2TargetComponent(1));
		ComponentFactory.addDefaultCollision(SpaceBug.class, this, 20, 1);
		List<Entity2D> drops = new ArrayList<Entity2D>();
		drops.add(new MoneyBundle(new Vector2d()));
		addComponent(new DropOnDeathComponent(drops));
	}

	@Override
	public void update(final EntityManager caller, final World world, final double delta) {
		fleeingBehaviour(delta);

		super.update(caller, world, delta);
	}

	@Override
	protected void onKilled() {
		super.onKilled();
		MissionDispatcher.dispatch(this, "killed");
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
