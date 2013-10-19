package net.tmt.gfx;

import static org.lwjgl.opengl.GL11.*;
import net.tmt.util.Vector2d;

import org.lwjgl.util.Color;
import org.lwjgl.util.ReadableColor;
import org.newdawn.slick.opengl.Texture;

public class Graphics {
	private static final float	LINE_WIDTH	= 1;
	private Texture				whiteTexture;
	private ReadableColor		color		= Color.PURPLE;

	public void drawSprite(final Vector2d pos, final Sprite sprite) {
		sprite.getTexture().bind();

		double widthHALF = sprite.getWidth() / 2;
		double heightHALF = sprite.getHeight() / 2;
		double x = pos.x;
		double y = pos.y;
		if (sprite.isCentered()) {
			x -= widthHALF;
			y -= heightHALF;
		}

		Color c = sprite.getBlendColor();
		glColor4d(c.getRed() / 255., c.getGreen() / 255., c.getBlue() / 255., c.getAlpha() / 255.);

		glPushMatrix();
		{
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

	public void setColor(final ReadableColor cyan) {
		this.color = cyan;
	}


	public void drawRect(final double x, final double y, final double with, final double height) {
		whiteTexture.bind();

		glLineWidth(LINE_WIDTH);
		glColor3d(color.getRed() / 255., color.getGreen() / 255., color.getBlue() / 255.);
		glPushMatrix();
		{
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
		whiteTexture.bind();

		glLineWidth(LINE_WIDTH);
		glColor3d(color.getRed() / 255., color.getGreen() / 255., color.getBlue() / 255.);
		glPushMatrix();
		{
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
		// TODO implement drawCircle
		throw new RuntimeException("Not yet implemented");
	}

	public void fillCircle(final double centerX, final double centerY, final double radius) {
		// TODO implement drawCircle
		throw new RuntimeException("Not yet implemented");
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
