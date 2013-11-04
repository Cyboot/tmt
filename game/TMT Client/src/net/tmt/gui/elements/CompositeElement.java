package net.tmt.gui.elements;

import java.util.ArrayList;
import java.util.List;

import net.tmt.gfx.Graphics;
import net.tmt.util.Vector2d;

public class CompositeElement extends GuiElement {
	public static final double	DEFAULT_PADDING	= 4;

	private double				paddingLeft		= DEFAULT_PADDING;
	private double				paddingTop		= DEFAULT_PADDING;

	protected List<GuiElement>	elementList		= new ArrayList<>();

	public CompositeElement(final Vector2d pos, final double width, final double height) {
		super(pos, height, width);
	}

	@Override
	public void update(final double delta) {
		for (GuiElement guiElement : elementList) {
			guiElement.update(delta);
		}
	}

	@Override
	public void render(final Graphics g) {
		super.render(g);
		for (GuiElement guiElement : elementList) {
			guiElement.render(g);
		}
	}

	public void addGuiElement(final GuiElement guiElement) {
		// add composite offset + padding
		Vector2d pos = guiElement.getPos();
		pos.add(this.pos).add(paddingLeft, paddingTop);
		guiElement.setPos(pos);

		elementList.add(guiElement);
	}
}
