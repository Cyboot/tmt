package net.tmt.entity.component;

import static net.tmt.game.ParticleFactory.Sparks;
import net.tmt.entity.particle.Particle;
import net.tmt.util.CountdownTimer;
import net.tmt.util.Vector2d;

import org.lwjgl.util.Color;

public class JetTrailComponent extends Component {
	private static final double	DEFAULT_BOUNDARY_SPEED	= 300;
	private double				boundarySpeed;
	private boolean				showTrail;
	private CountdownTimer		timerSpawnParticle		= CountdownTimer.createManuelResetTimer(0.02);
	private double				size					= 24;

	public JetTrailComponent() {
		this(DEFAULT_BOUNDARY_SPEED);
	}

	public JetTrailComponent(final double boundarySpeed) {
		this.boundarySpeed = boundarySpeed;
	}

	@Override
	public void update(final ComponentDispatcher caller, final double delta) {
		double speed = 0;
		speed = (double) caller.getValue(MoveComponent.SPEED);

		if (speed > boundarySpeed)
			showTrail = true;
		else
			showTrail = false;

		System.out.println(speed);

		if (timerSpawnParticle.isTimeleft(delta) && showTrail) {
			Vector2d dir = ((Vector2d) caller.getValue(MoveComponent.DIR)).copy().normalize();
			Vector2d dir90 = dir.copy().normalize().rotate(Math.toRadians(90));

			Vector2d pos1 = Vector2d.tmp1.set(pos.x + dir90.x * size, pos.y + dir90.y * size);
			Vector2d pos2 = Vector2d.tmp2.set(pos.x - dir90.x * size, pos.y - dir90.y * size);
			pos1.add(-dir.x * size, -dir.y * size);
			pos2.add(-dir.x * size, -dir.y * size);


			Sparks.reset();
			Sparks.color = (Color) Color.LTGREY;

			Particle particle1 = Sparks.get(pos1.copy());
			Particle particle2 = Sparks.get(pos2.copy());

			caller.getEntityManager().addEntity(particle1);
			caller.getEntityManager().addEntity(particle2);
			timerSpawnParticle.reset();
		}
	}
}
