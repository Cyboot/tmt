package net.tmt.gui.elements;

import java.util.ArrayList;
import java.util.List;

import net.tmt.gfx.Graphics;
import net.tmt.util.Vector2d;

/**
 * An GuiElement that contains other elements. use
 * {@link ContainerElement#addGuiElement(GuiElement)} to add other elements
 * relative to the container
 * 
 * @author Tim Schmiedl (Cyboot)
 */
public class ContainerElement extends GuiElement {
	private double				paddingLeft		= GuiElement.DEFAULT_PADDING;
	private double				paddingTop		= GuiElement.DEFAULT_PADDING;

	protected List<GuiElement>	guiElementList	= new ArrayList<>();

	public ContainerElement(final Vector2d pos, final double width, final double height) {
		super(pos, width, height);
	}

	@Override
	public void update(final double delta) {
		for (GuiElement guiElement : guiElementList) {
			guiElement.update(delta);
		}
	}

	@Override
	public void render(final Graphics g) {
		super.render(g);
		for (GuiElement guiElement : guiElementList) {
			guiElement.render(g);
		}
	}

	/**
	 * adds the given GuiElement to the container. Position of the guiElement is
	 * relative to the container
	 * 
	 * @param guiElement
	 */
	public void addGuiElement(final GuiElement guiElement) {
		guiElementList.add(guiElement);

		// add composite offset + padding
		Vector2d pos = guiElement.getPos();
		pos.add(this.pos).add(paddingLeft, paddingTop);
		guiElement.setPos(pos);
	}

	@Override
	public void setPos(final Vector2d pos) {
		Vector2d diff = null;
		if (this.pos != null)
			diff = new Vector2d(this.pos).sub(pos);

		super.setPos(pos);

		if (guiElementList != null) {
			for (GuiElement g : guiElementList) {
				g.setPos(g.getPos().sub(diff));;
			}
		}
	}
}
