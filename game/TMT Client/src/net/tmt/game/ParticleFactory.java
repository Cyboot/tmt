package net.tmt.game;

import net.tmt.entity.particle.Glow;
import net.tmt.entity.particle.Particle;
import net.tmt.entity.particle.Spark;
import net.tmt.util.Vector2d;

import org.lwjgl.util.Color;

/**
 * Factory class to create Particle in an easy way. <br>
 * Use subclasses to create <i>Glows, Smokes, Solids</i> and <i>Sparks</i>
 * 
 * @author Tim Schmiedl (Cyboot)
 */
public abstract class ParticleFactory {
	public final static SparksClass	Sparks	= new SparksClass();
	public final static GlowsClass	Glows	= new GlowsClass();
	public static final SmokesClass	Smokes	= new SmokesClass();
	public static final SolidsClass	Solids	= new SolidsClass();

	public abstract Particle getDefault(final Vector2d pos);

	public abstract Particle get(final Vector2d pos);

	public abstract void reset();


	public static class GlowsClass extends ParticleFactory {
		public double				size;
		public double				lifetime;
		public double				speed;
		public double				rotation;
		public double				glowTimer;
		public Color				primColor;
		public Color				secColor;

		private final static double	DEFAULT_SIZE		= 4;
		private final static double	DEFAULT_LIFETIME	= Double.MAX_VALUE;
		private final static double	DEFAULT_SPEED		= 0;
		private final static double	DEFAULT_ROTATION	= 0;
		private static final double	DEFAULT_GLOW_TIMER	= 1;
		private static final Color	DEFAULT_PRIM_COLOR	= (Color) Color.RED;
		private static final Color	DEFAULT_SEC_COLOR	= (Color) Color.GREEN;

		@Override
		public Particle getDefault(final Vector2d pos) {
			return new Glow(pos, DEFAULT_SIZE, DEFAULT_LIFETIME, DEFAULT_SPEED, DEFAULT_ROTATION, DEFAULT_GLOW_TIMER,
					DEFAULT_PRIM_COLOR, DEFAULT_SEC_COLOR);
		}

		@Override
		public Particle get(final Vector2d pos) {
			return new Glow(pos, size, lifetime, speed, rotation, glowTimer, primColor, secColor);
		}

		@Override
		public void reset() {
			size = DEFAULT_SIZE;
			lifetime = DEFAULT_LIFETIME;
			speed = DEFAULT_SPEED;
			rotation = DEFAULT_ROTATION;
			glowTimer = DEFAULT_GLOW_TIMER;
			primColor = DEFAULT_PRIM_COLOR;
			secColor = DEFAULT_SEC_COLOR;
		}

	}

	public static class SmokesClass extends ParticleFactory {
		// TODO: ParticleFactory (Smoke)

		@Override
		public Particle getDefault(final Vector2d pos) {
			return null;
		}

		@Override
		public Particle get(final Vector2d pos) {
			return null;
		}

		@Override
		public void reset() {
		}

	}

	public static class SolidsClass extends ParticleFactory {
		// TODO: ParticleFactory (Solid)

		@Override
		public Particle getDefault(final Vector2d pos) {
			return null;
		}

		@Override
		public Particle get(final Vector2d pos) {
			return null;
		}

		@Override
		public void reset() {

		}
	}

	public static class SparksClass extends ParticleFactory {
		public double				size;
		public double				lifetime;
		public Color				color;
		public double				speed;
		public double				rotation;

		private final static double	DEFAULT_SIZE		= 4;
		private final static double	DEFAULT_LIFETIME	= 1;
		private final static Color	DEFAULT_COLOR		= (Color) Color.ORANGE;
		private final static double	DEFAULT_SPEED		= 0;
		private final static double	DEFAULT_ROTATION	= 0;


		/**
		 * returns an Particle
		 * 
		 * @param pos
		 * @return
		 */
		@Override
		public Particle get(final Vector2d pos) {
			return new Spark(pos, size, lifetime, color, speed, rotation);
		}

		@Override
		public void reset() {
			size = DEFAULT_SIZE;
			lifetime = DEFAULT_LIFETIME;
			color = DEFAULT_COLOR;
			speed = DEFAULT_SPEED;
			rotation = DEFAULT_ROTATION;
		}

		@Override
		public Particle getDefault(final Vector2d pos) {
			return new Spark(pos, DEFAULT_SIZE, DEFAULT_LIFETIME, DEFAULT_COLOR, DEFAULT_SPEED, DEFAULT_ROTATION);
		}

	}
}
