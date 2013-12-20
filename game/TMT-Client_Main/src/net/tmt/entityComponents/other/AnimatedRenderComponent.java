package net.tmt.entityComponents.other;

import java.util.HashMap;
import java.util.Map;

import net.tmt.entityComponents.ComponentDispatcher;
import net.tmt.util.SpriteAnimation;

public class AnimatedRenderComponent extends RenderComponent {

	private Map<String, SpriteAnimation>	animationMap	= new HashMap<String, SpriteAnimation>();
	private String							currAnimation;

	public AnimatedRenderComponent(final Map<String, SpriteAnimation> animationMap, final String initialAnimation) {
		this.animationMap = animationMap;
		this.currAnimation = initialAnimation;
		setSprite(animationMap.get(currAnimation).getFrame(0));
	}

	public void pauseCurrentAnimation() {
		animationMap.get(currAnimation).pause();
	}

	public void resumeCurrentAnimation() {
		animationMap.get(currAnimation).resume();
	}

	public void setAnimationMap(final Map<String, SpriteAnimation> animationMap) {
		this.animationMap = animationMap;
	}

	public void changeAnimation(final String key) {
		currAnimation = key;
	}

	@Override
	public void update(final ComponentDispatcher caller, final double delta) {
		super.update(caller, delta);
		setSprite(animationMap.get(currAnimation).getFrame(delta));
	}

}
