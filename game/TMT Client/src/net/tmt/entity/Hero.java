package net.tmt.entity;

import java.util.ArrayList;
import java.util.List;

import net.tmt.entity.component.move.MoveComponent;
import net.tmt.entity.component.move.RotateComponent;
import net.tmt.entity.component.other.AnimatedRenderComponent;
import net.tmt.game.Controls;
import net.tmt.game.factory.ComponentFactory;
import net.tmt.game.manager.EntityManager;
import net.tmt.gfx.Sprite;
import net.tmt.util.Vector2d;

public class Hero extends Entity2D {

	private double					SPEED			= 128;
	private double					ROTATION_SPEED	= 180;
	private AnimatedRenderComponent	aniRenCom;

	public Hero(final Vector2d pos) {
		super(pos);
		removeAllComponents();
		List<Sprite> aniSprites = new ArrayList<Sprite>();
		aniSprites.add(new Sprite("hero_walk_0"));
		aniSprites.add(new Sprite("hero_walk_1"));
		aniRenCom = new AnimatedRenderComponent(aniSprites, 0.2);
		aniRenCom.setPauseFrame(new Sprite("hero_stand"));
		addComponent(aniRenCom);
		ComponentFactory.addDefaultMove(this, 0, 0, ROTATION_SPEED);
	}

	@Override
	public void update(final EntityManager caller, final double delta) {
		// FIXME: MoveComponent's speed is always 0?
		double s = ((double) getValue(MoveComponent.SPEED));;

		boolean movingF = true;
		boolean movingB = true;

		if (Controls.pressed(Controls.HERO_UP))
			dispatchValue(MoveComponent.SPEED, SPEED);
		else
			movingF = false;
		if (Controls.pressed(Controls.HERO_DOWN))
			dispatchValue(MoveComponent.SPEED, -SPEED);
		else
			movingB = false;
		if (Controls.pressed(Controls.HERO_LEFT))
			dispatchValue(RotateComponent.IS_ROTATE_LEFT, true);
		if (Controls.pressed(Controls.HERO_RIGHT))
			dispatchValue(RotateComponent.IS_ROTATE_RIGHT, true);

		if (movingF || movingB)
			aniRenCom.resumeAnimation();
		else
			aniRenCom.pauseAnimation();

		super.update(caller, delta);
	}

}
