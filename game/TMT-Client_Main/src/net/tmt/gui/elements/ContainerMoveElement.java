package net.tmt.gui.elements;

import net.tmt.game.interfaces.ClickListener;
import net.tmt.gfx.Sprite;
import net.tmt.util.Vector2d;

public class ContainerMoveElement extends ContainerElement implements ClickListener {
	private Button	moveButton;

	public ContainerMoveElement(final Vector2d pos, final double width, final double height) {
		super(pos, width, height);

		moveButton = new Button(new Vector2d(width - DEFAULT_PADDING * 2 - 32, 0), new Sprite("arrow_left"));
		moveButton.noBackground();
		moveButton.addClickListener(this);

		addGuiElement(moveButton);
	}

	@Override
	public void onClick(final GuiElement caller) {
		// DEBUG: click MoveComposite
		this.setPos(new Vector2d(pos).add(-50, 0));
	}

}