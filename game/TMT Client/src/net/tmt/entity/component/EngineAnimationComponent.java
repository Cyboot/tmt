package net.tmt.entity.component;

import static net.tmt.game.ParticleFactory.*;
import net.tmt.entity.particle.Particle;
import net.tmt.gfx.Graphics;
import net.tmt.util.CountdownTimer;
import net.tmt.util.Vector2d;

import org.lwjgl.util.Color;

public class EngineAnimationComponent extends Component {
	public static final String	ENGINE_1	= "ENGINE_1";
	public static final String	ENGINE_2	= "ENGINE_2";
	public static final String	ENGINE_3	= "ENGINE_3";

	private Particle			mainGlow;
	private Color				color		= new Color(200, 100, 0, 255);
	private Color				colorSec	= new Color(255, 100, 0, 150);
	private CountdownTimer		timerSpawn	= new CountdownTimer(0.02);

	private Vector2d			offset;
	private boolean				spawnSparks;
	private String				engineNr;
	private double				glowsize;

	public EngineAnimationComponent(final Vector2d offset, final boolean sparks, final double glowSize,
			final String engineNr) {
		this.offset = offset;
		this.spawnSparks = sparks;
		this.glowsize = glowSize;
		this.engineNr = engineNr;
	}

	@Override
	public void update(final ComponentDispatcher caller, final double delta) {
		if (mainGlow == null)
			createMainGlow(caller);
		mainGlow.dispatchValue(ROTATION_ANGLE, caller.getValue(ROTATION_ANGLE));
		mainGlow.update(caller.getEntityManager(), delta);

		if (caller.isSet(engineNr) && !(boolean) caller.getValue(engineNr))
			return;


		if (timerSpawn.isTimeleft(delta) && spawnSparks) {
			double speed = (double) caller.getValue(MoveComponent.SPEED);
			double rotation = (double) caller.getValue(ROTATION_ANGLE);
			Vector2d dir = ((Vector2d) caller.getValue(MoveComponent.DIR)).copy().normalize();

			Sparks.reset();
			Sparks.color = color;
			Sparks.size = 3;
			Sparks.lifetime = 0.5;
			Sparks.rotation = rotation;
			Sparks.speed = speed * 0.7;
			Particle particle = Sparks.get(pos.copy().add(-dir.x * offset.x, -dir.y * offset.y * 2 / 3));

			caller.getEntityManager().addEntity(particle);
		}
	}

	private void createMainGlow(final ComponentDispatcher caller) {
		Glows.reset();
		Glows.glowTimer = 0.8;
		Glows.size = glowsize;
		Glows.primColor = color;
		Glows.secColor = colorSec;


		mainGlow = Glows.get(pos);
		mainGlow.addComponent(new ExtraOffsetComponent(offset.copy()));
	}

	@Override
	public void render(final ComponentDispatcher caller, final Graphics g) {
		if (caller.isSet(engineNr) && !(boolean) caller.getValue(engineNr))
			return;

		// FIXME: quick & very dirty: how to set Rotation_angle to other
		// entities and avoid it to be overridden by other Components
		mainGlow.dispatchValue(ROTATION_ANGLE, caller.getValue(ROTATION_ANGLE));
		mainGlow.render(g);
	}
}
