package net.tmt.gui.view;

import java.util.List;

import net.tmt.entity.Entity2D;
import net.tmt.entity.Hero;
import net.tmt.entity.pickups.BackPack;
import net.tmt.game.interfaces.ClickListener;
import net.tmt.game.manager.GameManager;
import net.tmt.gfx.Graphics;
import net.tmt.gfx.Sprite;
import net.tmt.gui.elements.Button;
import net.tmt.gui.elements.ContainerElement;
import net.tmt.gui.elements.GuiElement;
import net.tmt.gui.elements.ImageView;
import net.tmt.util.ColorUtil;
import net.tmt.util.Vector2d;

import org.lwjgl.util.Color;

public class BagView extends ContainerElement implements ClickListener {

	private boolean				isBagOpen	= false;
	private ContainerElement	bag			= new ContainerElement(new Vector2d(0, 500), 350, 220);

	public BagView() {
		super(new Vector2d(0, 460), 32 + 8, 1 * 32 + 1 * 8);
		setBorderColor((Color) ColorUtil.GUI_CYAN_DARK_50);
		setBackgroundColor((Color) ColorUtil.GUI_CYAN_DARK_30);
		addButton(new Button(new Vector2d(0, 0), new Sprite("icon_dummy"), "bag"));
	}

	private void addButton(final Button button) {
		button.noBackground();
		button.addClickListener(this);
		addGuiElement(button);
	}

	@Override
	public void onClick(final GuiElement caller) {
		// toggle
		isBagOpen = !isBagOpen;
	}

	@Override
	public void render(final Graphics g) {
		super.render(g);
		if (isBagOpen)
			bag.render(g);
	}

	@Override
	public void update(final double delta) {
		super.update(delta);
		bag = new ContainerElement(new Vector2d(0, 500), 300, 220);
		List<Entity2D> wearing = ((Hero) (GameManager.getInstance().getActiveGamestate().getPlayer())).getWearing();
		int xPos = 0;
		int yPos = 0;
		Sprite sprite = new Sprite("ak47");

		BackPack bp = null;

		for (Entity2D wear : wearing) {
			if (wear instanceof BackPack) {
				for (Entity2D item : ((BackPack) wear).getContents()) {
					bag.addGuiElement(new ImageView(new Vector2d(xPos, yPos), sprite));
					xPos += sprite.getWidth();
					if (xPos == (10 * sprite.getWidth())) {
						xPos = 0;
						yPos += sprite.getHeight();
					}
				}
			}
		}
	}
}
