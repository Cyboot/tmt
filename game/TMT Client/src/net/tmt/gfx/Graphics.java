package net.tmt.gfx;

import static org.lwjgl.opengl.GL11.*;
import net.tmt.map.World;
import net.tmt.util.Vector2d;

import org.lwjgl.util.Color;
import org.lwjgl.util.ReadableColor;
import org.newdawn.slick.opengl.Texture;

public class Graphics {
	private static final int	DEFAULT_LINE_WIDTH	= 1;

	private float				lineWidth			= DEFAULT_LINE_WIDTH;
	private Texture				whiteTexture;
	private ReadableColor		color				= Color.PURPLE;
	private boolean				onGui;

	public void drawSprite(final Vector2d pos, final Sprite sprite) {
		sprite.getTexture().bind();

		double widthHALF = sprite.getWidth() / 2;
		double heightHALF = sprite.getHeight() / 2;
		double x = pos.x;
		double y = pos.y;
		if (!sprite.isCentered()) {
			x += widthHALF;
			y += heightHALF;
		}

		Color c = sprite.getBlendColor();
		glColor4d(c.getRed() / 255., c.getGreen() / 255., c.getBlue() / 255., c.getAlpha() / 255.);

		glPushMatrix();
		{
			applyOffset();
			glTranslated(x, y, 0);
			glRotated(sprite.getRotation(), 0, 0, 1);
			{
				glBegin(GL_QUADS);
				{
					glVertex2d(-widthHALF, heightHALF);
					glTexCoord2d(0, 0);

					glVertex2d(-widthHALF, -heightHALF);
					glTexCoord2d(1, 0);

					glVertex2d(widthHALF, -heightHALF);
					glTexCoord2d(1, 1);

					glVertex2d(widthHALF, heightHALF);
					glTexCoord2d(0, 1);
				}
				glEnd();
			}
		}
		glPopMatrix();
	}

	private void applyOffset() {
		// ignore offset if onGui == true
		if (!onGui)
			glTranslated(-World.getInstance().getOffset().x, -World.getInstance().getOffset().y, 0);
		onGui = false;

		// reset lineWidth to default width
		// lineWidth = DEFAULT_LINE_WIDTH;
	}

	public void setColor(final ReadableColor cyan) {
		this.color = cyan;
	}

	public void setLineWidth(final int width) {
		lineWidth = width;
	}

	public void drawRect(final double x, final double y, final double with, final double height) {
		applyColor();

		glLineWidth(lineWidth);
		glPushMatrix();
		{
			applyOffset();
			glTranslated(x, y, 0);
			{
				glBegin(GL_LINE_STRIP);
				{
					glVertex2d(0, height);
					glVertex2d(0, 0);
					glVertex2d(with, 0);
					glVertex2d(with, height);
					glVertex2d(0, height);
				}
				glEnd();
			}
		}
		glPopMatrix();
	}

	public void fillRect(final double x, final double y, final double with, final double height) {
		applyColor();

		glLineWidth(lineWidth);
		glPushMatrix();
		{
			applyOffset();
			glTranslated(x, y, 0);
			{
				glBegin(GL_QUADS);
				{
					glVertex2d(0, height);
					glVertex2d(0, 0);
					glVertex2d(with, 0);
					glVertex2d(with, height);
				}
				glEnd();
			}
		}
		glPopMatrix();
	}

	public void drawCircle(final double centerX, final double centerY, final double radius) {
		// TODO better performance for cicles
		applyColor();
		int sides = 32;

		glPushMatrix();
		{
			applyOffset();
			glTranslated(centerX, centerY, 0);
			glBegin(GL_LINE_LOOP);
			for (int a = 0; a < 360; a += 360 / sides) {
				double heading = Math.toRadians(a);

				glVertex2d(Math.cos(heading) * radius, Math.sin(heading) * radius);
			}
			glEnd();
		}
		glPopMatrix();
	}

	public void drawLine(final double x1, final double y1, final double x2, final double y2) {
		applyColor();

		glLineWidth(lineWidth);
		glPushMatrix();
		{
			applyOffset();
			{
				glBegin(GL_LINES);
				{
					glVertex2d(x1, y1);
					glVertex2d(x2, y2);
				}
				glEnd();
			}
		}
		glPopMatrix();
	}

	public void fillCircle(final double centerX, final double centerY, final float radius) {
		Vector2d offset = World.getInstance().getOffset();

		// FIXME: better solution to check if on display
		if (offset.distanceTo(centerX, centerY) > 2000)
			return;

		applyColor();

		glPointSize(radius);

		glPushMatrix();
		{
			applyOffset();
			glBegin(GL_POINTS);
			{
				glVertex2d(centerX, centerY);
			}
			glEnd();
		}
		glPopMatrix();
	}

	private void applyColor() {
		whiteTexture.bind();
		glColor3d(color.getRed() / 255., color.getGreen() / 255., color.getBlue() / 255.);
	}

	/**
	 * Render absolut to screen dimension, not relative to World (ignore
	 * World-offset) An Example would be the Gui.
	 * 
	 * @return
	 */
	public Graphics gui() {
		onGui = true;
		return this;
	}


	private static Graphics	instance;

	public static void init() {
		instance = new Graphics();
		instance.whiteTexture = new Sprite("white").getTexture();
	}

	public static Graphics getInstance() {
		return instance;
	}
}
