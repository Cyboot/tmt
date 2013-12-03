package net.tmt.entity.component.animation;

import static net.tmt.game.factory.ParticleFactory.Sparks;
import net.tmt.entity.component.Component;
import net.tmt.entity.component.ComponentDispatcher;
import net.tmt.entity.component.move.MoveComponent;
import net.tmt.entity.particle.Particle;
import net.tmt.util.CountdownTimer;
import net.tmt.util.Vector2d;

import org.lwjgl.util.Color;

public class JetTrailComponent extends Component {
	private static final double	DEFAULT_BOUNDARY_SPEED	= 250;

	private double				boundarySpeed;
	private boolean				showTrail;
	private Color				color;

	private CountdownTimer		timerSpawnParticle		= CountdownTimer.createManualResetTimer(0.02);
	private double				size					= 24;

	public JetTrailComponent() {
		this(DEFAULT_BOUNDARY_SPEED, 24);
	}

	public JetTrailComponent(final double boundarySpeed, final double size) {
		this.boundarySpeed = boundarySpeed;
		this.size = size;
		this.color = new Color(255, 255, 255, 80);
	}

	@Override
	public void update(final ComponentDispatcher caller, final double delta) {
		double speed = 0;
		speed = (double) caller.getValue(MoveComponent.SPEED);

		if (speed > boundarySpeed)
			showTrail = true;
		else
			showTrail = false;

		if (timerSpawnParticle.isTimeUp(delta) && showTrail) {
			Vector2d dir = Vector2d.fromAngle(Math.toRadians((double) (caller.getValue(ROTATION_ANGLE_LOOK))));
			// Vector2d dir = ((Vector2d)
			// caller.getValue(ROTATION_ANGLE_LOOK)).copy().normalize();
			Vector2d dir90 = dir.copy().normalize().rotate(Math.toRadians(90));

			// move to left/right of the Vertical center
			Vector2d pos1 = Vector2d.tmp1.set(pos.x + dir90.x * size, pos.y + dir90.y * size);
			Vector2d pos2 = Vector2d.tmp2.set(pos.x - dir90.x * size, pos.y - dir90.y * size);
			// move to bottom of Image
			pos1.add(-dir.x * size, -dir.y * size);
			pos2.add(-dir.x * size, -dir.y * size);

			color.setAlpha((int) (speed / DEFAULT_BOUNDARY_SPEED * 40));
			Sparks.reset();
			Sparks.color = color;

			Particle particle1 = Sparks.get(pos1.copy());
			Particle particle2 = Sparks.get(pos2.copy());

			// adds Particles
			caller.getEntityManager().addEntity(particle1);
			caller.getEntityManager().addEntity(particle2);
			timerSpawnParticle.reset();
		}
	}
}
