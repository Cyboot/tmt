package net.tmt.game;

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

	public abstract Particle getDefault(final Vector2d pos);

	public abstract Particle get(final Vector2d pos);

	public abstract void reset();


	public static class Glows {

	}

	public static class Smokes {

	}

	public static class Solids {

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
			Particle particle = new Spark(pos, size, lifetime, color, speed, rotation);

			return particle;
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
			Particle particle = new Spark(pos, DEFAULT_SIZE, DEFAULT_LIFETIME, DEFAULT_COLOR, DEFAULT_SPEED,
					DEFAULT_ROTATION);
			return particle;
		}

	}
}
