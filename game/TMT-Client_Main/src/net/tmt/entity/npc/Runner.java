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

public class Runner extends PlanetCreature {

	@SuppressWarnings("rawtypes")
	private List<Class>				hunts				= new ArrayList<Class>();
	// TODO: loop through entities and react to hunts in update method
	private double					normalSpeed			= 15;
	private Hero					hero;
	private final double			ROTATION_SPEED		= 280;
	private double					sensePreyDistance	= 300;
	private Vector2d				runTo				= new Vector2d();
	private CountdownTimer			changeDir			= new CountdownTimer(15);
	private AnimatedRenderComponent	aniRenCom;
	private List<Sprite>			walkingSprites		= new ArrayList<Sprite>();
	private List<Sprite>			runningSprites		= new ArrayList<Sprite>();

	public Runner(final Vector2d pos, final double health, final Hero hero) {
		super(pos, health);
		this.hero = hero;
		hunts.add(Hero.class);
		removeAllComponents();
		walkingSprites.add(new Sprite("runner_walking_0"));
		walkingSprites.add(new Sprite("runner_walking_1"));
		runningSprites.add(new Sprite("runner_running_0"));
		runningSprites.add(new Sprite("runner_running_1"));
		aniRenCom = new AnimatedRenderComponent(walkingSprites, 1);
		addComponent(aniRenCom);
		ComponentFactory.addDefaultMove(this, 0, normalSpeed, ROTATION_SPEED);
		addComponent(new Move2TargetComponent(1));
	}

	@Override
	public void update(final EntityManager caller, final World world, final double delta) {
		huntingBehaviour(delta);

		super.update(caller, world, delta);
	}

	private void huntingBehaviour(final double delta) {
		if (pos.distanceTo(hero.getPos()) < sensePreyDistance) {
			runTo.set(hero.getPos());
			// runningAnimation();
			dispatchValue(MoveComponent.SPEED, normalSpeed * 10);
		} else {
			if (changeDir.isTimeUp(delta)) {
				Vector2d.tmp1.set(pos);
				Vector2d.tmp2.set(hero.getPos());
				Vector2d.tmp2.rotate(Math.toRadians(RandomUtil.doubleRange(0, 360)));
				Vector2d.tmp1.add(Vector2d.tmp2);
				runTo.set(Vector2d.tmp1);
			}
			// walkingAnimation();
			dispatchValue(MoveComponent.SPEED, normalSpeed);
		}

		if (runTo != null)
			dispatchValue(Move2TargetComponent.SET_TARGET, runTo);
	}

	private void runningAnimation() {
		aniRenCom.setAnimationFrames(runningSprites);
		aniRenCom.setLoopTime(1);
	}

	private void walkingAnimation() {
		aniRenCom.setAnimationFrames(walkingSprites);
		aniRenCom.setLoopTime(1);
	}

}
