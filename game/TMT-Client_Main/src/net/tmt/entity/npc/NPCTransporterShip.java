package net.tmt.entity.npc;

import net.tmt.entity.component.other.ShieldComponent;
import net.tmt.game.manager.EntityManager;
import net.tmt.gfx.Sprite;
import net.tmt.map.World;
import net.tmt.util.CountdownTimer;
import net.tmt.util.RandomUtil;
import net.tmt.util.Vector2d;

public class NPCTransporterShip extends NPCSpaceShip {
	public static final int		TYPE_1			= 1;
	public static final int		TYPE_2			= 2;

	private static final double	ROTATION_SPEED	= 30;
	private static final double	SPEED			= 50;

	private boolean				shieldToggle	= RandomUtil.randBoolean();
	private CountdownTimer		timerShield		= new CountdownTimer(RandomUtil.doubleRange(5, 10),
														RandomUtil.doubleRange(0, 10));

	public NPCTransporterShip(final Vector2d pos, final int type) {
		super(pos, SPEED, ROTATION_SPEED, 128);

		switch (type) {
		case TYPE_1:
			setSprite(new Sprite("ship_transporter1"));
			break;
		case TYPE_2:
			setSprite(new Sprite("ship_transporter2"));
			break;
		}
		addComponent(new ShieldComponent(320, ShieldComponent.COLOR_BLUE));
	}

	@Override
	public void update(final EntityManager caller, World world, final double delta) {
		if (timerShield.isTimeUp(delta)) {
			shieldToggle = !shieldToggle;
		}

		dispatchValue(ShieldComponent.SET_ACTIVE, shieldToggle);


		super.update(caller, world, delta);
	}
}
