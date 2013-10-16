package net.tmt.gfx;

import static org.lwjgl.opengl.GL11.*;
import net.tmt.util.Vector2d;

public class Graphics {

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

}
