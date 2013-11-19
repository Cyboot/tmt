package net.tmt.entity.component.other;

import java.util.ArrayList;
import java.util.List;

import net.tmt.entity.component.ComponentDispatcher;
import net.tmt.gfx.Sprite;
import net.tmt.util.CountdownTimer;

public class AnimatedRenderComponent extends RenderComponent {

	private List<Sprite>	sprites	= new ArrayList<Sprite>();
	private Sprite			pauseFrame;
	private int				currSpriteIndex;
	private CountdownTimer	timer;
	private boolean			paused	= false;

	public AnimatedRenderComponent(final List<Sprite> sprites, final double looptime) {
		setSprite(sprites.get(0));
		this.sprites = sprites;
		this.timer = new CountdownTimer(looptime);
		this.currSpriteIndex = 0;
	}

	public void pauseAnimation() {
		paused = true;
		if (pauseFrame != null)
			setSprite(pauseFrame);
	}

	public void resumeAnimation() {
		paused = false;
	}

	public void setPauseFrame(final Sprite pauseFrame) {
		this.pauseFrame = pauseFrame;
	}

	public void setAnimationFrames(final List<Sprite> sprites) {
		this.sprites = sprites;
	}

	@Override
	public void update(final ComponentDispatcher caller, final double delta) {
		super.update(caller, delta);
		if (timer.isTimeleft(delta) && !paused) {
			setSprite(sprites.get(currSpriteIndex));
			currSpriteIndex = (currSpriteIndex + 1) % sprites.size();
		}
	}

}
