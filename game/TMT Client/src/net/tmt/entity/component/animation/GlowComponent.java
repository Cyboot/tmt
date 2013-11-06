package net.tmt.entity.component.animation;

import net.tmt.entity.component.Component;
import net.tmt.entity.component.ComponentDispatcher;
import net.tmt.entity.component.other.RenderComponent;
import net.tmt.util.CountdownTimer;

import org.lwjgl.util.Color;

public class GlowComponent extends Component {
	private CountdownTimer	timerGlow;

	private Color			color;
	private Color			secColor;
	private Color			primColor;

	public GlowComponent(final Color primColor, final Color secColor, final double glowTime) {
		this.primColor = new Color(primColor);
		this.secColor = new Color(secColor);

		color = primColor;

		timerGlow = new CountdownTimer(glowTime);
	}


	@Override
	public void update(final ComponentDispatcher caller, final double delta) {
		if (timerGlow.isTimeleft(delta)) {
			Color tmp = primColor;
			primColor = secColor;
			secColor = tmp;
		}

		double ratio = timerGlow.getTimeleftRatio();
		double red = (primColor.getRed() * ratio + secColor.getRed() * (1 - ratio));
		double green = (primColor.getGreen() * ratio + secColor.getGreen() * (1 - ratio));
		double blue = (primColor.getBlue() * ratio + secColor.getBlue() * (1 - ratio));
		double alpa = (primColor.getAlpha() * ratio + secColor.getAlpha() * (1 - ratio));

		color.set((int) red, (int) green, (int) blue, (int) alpa);

		caller.dispatch(RenderComponent.BLEND_COLOR, color);
	}
}
