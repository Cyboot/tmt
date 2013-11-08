package net.tmt.entity.component.animation;

import static net.tmt.game.factory.ParticleFactory.*;
import net.tmt.entity.component.Component;
import net.tmt.entity.component.ComponentDispatcher;
import net.tmt.entity.component.move.MoveComponent;
import net.tmt.entity.component.move.RotateComponent;
import net.tmt.entity.component.other.ExtraOffsetComponent;
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
		mainGlow.dispatchValue(ROTATION_ANGLE_LOOK, caller.getValue(ROTATION_ANGLE_LOOK));
		mainGlow.update(caller.getEntityManager(), delta);

		if (caller.isSet(engineNr) && !(boolean) caller.getValue(engineNr))
			return;


		if (timerSpawn.isTimeleft(delta) && spawnSparks) {
			double speed = (double) caller.getValue(MoveComponent.SPEED);
			double rotationMove = (double) caller.getValue(RotateComponent.ROTATION_ANGLE_MOVE);
			double rotationLook = (double) caller.getValue(RotateComponent.ROTATION_ANGLE_LOOK);

			// if ship isn't looking in the direction its moving to --> no
			// sparks
			if (Math.abs(rotationMove - rotationLook) > 45)
				return;

			Sparks.reset();
			Sparks.color = color;
			Sparks.size = 3;
			Sparks.lifetime = 0.5;
			Sparks.speed = speed * 0.6;
			Sparks.rotation = rotationMove;

			Vector2d posParticle = new Vector2d().add(pos);
			Particle particle = Sparks.get(posParticle);

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
		mainGlow.dispatchValue(ROTATION_ANGLE_LOOK, caller.getValue(ROTATION_ANGLE_LOOK));
		mainGlow.render(g);
	}
}
