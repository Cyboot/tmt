package net.tmt.gui;

import java.util.ArrayList;
import java.util.List;

import net.tmt.gfx.Graphics;
import net.tmt.gfx.Graphics.Fonts;
import net.tmt.gfx.Sprite;
import net.tmt.global.Money;
import net.tmt.global.RPLevel;
import net.tmt.util.ColorUtil;
import net.tmt.util.CountdownTimer;
import net.tmt.util.Vector2d;

import org.newdawn.slick.TrueTypeFont;

public class MoneyLevelOverlay extends Gui {
	private static final int	LEVEL_SIZE			= 96;
	private Sprite				level_bg			= new Sprite("level_bg", LEVEL_SIZE, LEVEL_SIZE).setCentered(false);
	private List<Sprite>		level_part			= new ArrayList<>();

	private CountdownTimer		timerShowMissing	= CountdownTimer.createManualResetTimer(5);
	private CountdownTimer		timerBlink			= new CountdownTimer(0.5);
	private int					lastRP				= 0;
	private String				lastLevel			= "0";
	private boolean				showMissingRP;
	private boolean				blink				= false;

	public MoneyLevelOverlay() {
		for (int i = 0; i < 8; i++) {
			Sprite sprite = new Sprite("level_" + i, LEVEL_SIZE, LEVEL_SIZE).setCentered(false);
			level_part.add(sprite);
			sprite.setAlpha(0);
		}
	}


	@Override
	public void update(final double delta) {
		// only calculate alphas new if level or rp has changed
		if (RPLevel.getRPcurrent() != lastRP || !RPLevel.getLevel().equals(lastLevel)) {
			calcAlpha();
			lastRP = RPLevel.getRPcurrent();
			lastLevel = RPLevel.getLevel();

			timerShowMissing.reset();
			showMissingRP = true;
		}

		if (timerShowMissing.isTimeUp(delta)) {
			showMissingRP = false;
		}
		if (timerBlink.isTimeUp(delta))
			blink = !blink;
	}


	private void calcAlpha() {
		double rp = RPLevel.getRPcurrent();
		double targetRP = RPLevel.getRPtarget();


		double tmp = rp / targetRP * 8;
		int maxFilledIndex = (int) tmp;
		int lastAlpha = (int) ((tmp - maxFilledIndex) * 256);

		for (int i = 0; i < 8; i++) {
			Sprite sprite = level_part.get(i);

			if (i < maxFilledIndex)
				sprite.setAlpha(255);
			else if (i == maxFilledIndex)
				sprite.setAlpha(lastAlpha);
			else
				sprite.setAlpha(0);
		}
	}

	@Override
	public void render(final Graphics g) {
		renderLevel(g);
		renderMoney(g);

		g.setFont(Fonts.font_default);
	}


	private void renderMoney(final Graphics g) {
		TrueTypeFont font = Fonts.font_26_bold;
		g.setFont(font);

		String text = Money.getMoney();
		int x = Math.max(font.getWidth("$"), LEVEL_SIZE / 2 - font.getWidth(text) / 2);

		g.setColor(ColorUtil.GUI_ORANGE);
		g.onGui().drawText(0, LEVEL_SIZE + 12, "$");
		g.onGui().drawText(x, LEVEL_SIZE + 12, text);
	}


	private void renderLevel(final Graphics g) {
		Vector2d pos = Vector2d.tmp1.set(0, 0);

		// sprite for progress
		g.onGui().drawSprite(pos, level_bg);
		for (Sprite s : level_part)
			g.onGui().drawSprite(pos, s);

		// Level Nr
		g.setFont(Fonts.font_26_bold);
		g.setColor(ColorUtil.GUI_ORANGE);
		int offset = 8;
		if (RPLevel.getLevel().length() > 1)
			offset = 0;
		g.onGui().drawText(LEVEL_SIZE / 3 + offset, LEVEL_SIZE / 3, RPLevel.getLevel());

		// missing RP text
		if (showMissingRP) {
			TrueTypeFont font = Fonts.font_12_plain;
			g.setFont(font);

			String text = lastRP + " / " + RPLevel.getRPtarget();
			int x = Math.max(0, LEVEL_SIZE / 2 - font.getWidth(text) / 2);

			g.setColor(ColorUtil.GUI_CYAN);
			g.onGui().drawText(x, LEVEL_SIZE - 3, text);

			if (blink) {
				g.setColor(ColorUtil.GUI_ORANGE);
				g.onGui().drawText(LEVEL_SIZE * 5 / 6, 4, "+" + RPLevel.getLastRPGain());
			}
		}

	}

}
