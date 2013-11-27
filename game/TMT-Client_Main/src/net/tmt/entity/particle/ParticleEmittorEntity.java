package net.tmt.entity.particle;

import net.tmt.entity.Entity2D;
import net.tmt.game.manager.EntityManager;
import net.tmt.util.CountdownTimer;
import net.tmt.util.RandomUtil;
import net.tmt.util.Vector2d;

import org.lwjgl.util.Color;

/**
 * Debugging class for now
 * 
 * @author Tim Schmiedl (Cyboot)
 */
public class ParticleEmittorEntity extends Entity2D {
	private CountdownTimer	timerParticle	= new CountdownTimer(0.5);


	public ParticleEmittorEntity(final Vector2d pos) {
		super(pos);
	}


	@Override
	public void update(final EntityManager caller, final double delta) {
		super.update(caller, delta);

		timerParticle.setIntervall(0.25);
		if (timerParticle.isTimeUp(delta)) {
			spawnSmoke(caller);
		}
	}

	private void spawnSmoke(final EntityManager caller) {
		double speed = 15;
		double lifetime = 3;
		double rotationSpeed = RandomUtil.doubleRange(30, 90);

		Entity2D particle = new Smoke(pos.copy(), lifetime, speed, 0, rotationSpeed);

		caller.addEntity(particle);
	}

	@SuppressWarnings("unused")
	private void spawnGlow(final EntityManager caller) {
		double speed = 50;
		double lifetime = 10;
		double glowTimer = 0.13;

		Color primColor = new Color(255, 0, 0, 150);
		Color secColor = new Color(255, 0, 0, 100);
		Entity2D particle = new Glow(pos.copy(), 32, lifetime, speed, 0, glowTimer, primColor, secColor);

		caller.addEntity(particle);
	}

	@SuppressWarnings("unused")
	private void spawnSpark(final EntityManager caller) {
		double speed = RandomUtil.doubleRange(25, 50);
		double rotation = RandomUtil.doubleRange(0, 360);
		double size = 4;
		double lifetime = 1;
		Color color = new Color(RandomUtil.intRange(200, 255), RandomUtil.intRange(100, 200), 50, 255);

		Entity2D particle = new Spark(pos.copy(), size, lifetime, color, speed, rotation);

		caller.addEntity(particle);
	}
}
