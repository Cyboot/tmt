package net.tmt.entity.component.other;

import java.util.ArrayList;
import java.util.List;

import net.tmt.entity.component.ComponentDispatcher;
import net.tmt.gfx.Sprite;
import net.tmt.util.CountdownTimer;

public class AnimatedRenderComponent extends RenderComponent {

	private List<Sprite>	sprites	= new ArrayList<Sprite>();
	private int				currSpriteIndex;
	private CountdownTimer	timer;

	public AnimatedRenderComponent(final List<Sprite> sprites, final double looptime) {
		this.sprites = sprites;
		this.timer = new CountdownTimer(looptime);
		this.currSpriteIndex = 0;
	}

	@Override
	public void update(final ComponentDispatcher caller, final double delta) {
		super.update(caller, delta);
		if (timer.isTimeleft(delta)) {
			setSprite(sprites.get(currSpriteIndex));
			currSpriteIndex = (currSpriteIndex + 1) % sprites.size();
		}
	}

}
