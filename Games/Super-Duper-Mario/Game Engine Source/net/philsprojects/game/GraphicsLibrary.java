package net.philsprojects.game;

import javax.media.opengl.GL;

import net.philsprojects.game.effects.Particle;
import net.philsprojects.game.util.BoundingBox;
import net.philsprojects.game.util.Color;
import net.philsprojects.game.util.Rectangle;

import java.awt.Font;

import static net.philsprojects.game.Constants.*;
import com.sun.opengl.util.j2d.TextRenderer;
import com.sun.opengl.util.texture.Texture;

public final class GraphicsLibrary
{

	private static GraphicsLibrary _instance = new GraphicsLibrary();

	public static GraphicsLibrary getInstance()
	{
		return _instance;
	}

	private static int MODE_SPRITE = 0;

	private TextRenderer _renderer;

	private TextureLibrary _textures;

	private GL _gl;

	private int _currentMode = -1;

	private Texture _currentTexture = null;

	private String _currentTextureName = null;

	private Color _currentForeColor = Color.white();

	private Font _currentFont = new Font("SansSerif", Font.BOLD, 16);

	private float _sourceLeft = 0.0f;

	private float _sourceRight = 1.0f;

	private float _sourceTop = 0.0f;

	private float _sourceBottom = 1.0f;

	private GraphicsLibrary()
	{
	}

	public final void initialize(GL gl, TextureLibrary textureFactory, Color background)
	{
		this._gl = gl;
		// Smooth rendering.
		gl.glShadeModel(GL.GL_SMOOTH);
		gl.glClearColor(background.getR(), background.getG(), background.getB(), 0f);
		gl.glClearDepth(1.0);
		// Disable depth checking.
		gl.glDisable(GL.GL_DEPTH_TEST);
		// Enable alpha and blending components.
		gl.glEnable(GL.GL_ALPHA);
		gl.glEnable(GL.GL_BLEND);
		// Get nicest perspective and point smoothing.
		gl.glHint(GL.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);
		gl.glHint(GL.GL_POINT_SMOOTH_HINT, GL.GL_NICEST);
		// Enable 2D textures.
		gl.glEnable(GL.GL_TEXTURE_2D);
		// Set the reference to the table of textures.
		_textures = textureFactory;
		// Text Renderer
		_renderer = new TextRenderer(_currentFont, true, false);
	}

	public final void setBackgroundColor(Color background)
	{
		_gl.glClearColor(background.getR(), background.getG(), background.getB(), 1f);
		_gl.glClearDepth(1.0);
	}

	public final void clearSource()
	{
		_sourceLeft = 0.0f;
		_sourceRight = 1.0f;
		_sourceTop = 0.0f;
		_sourceBottom = 1.0f;
	}

	public final void setSource(Rectangle source)
	{
		setSource(source.getX(), source.getY(), source.getX() + source.getWidth(), source.getY() + source.getHeight());
	}

	public final void setSource(float left, float top, float right, float bottom)
	{
		_sourceLeft = left / _currentTexture.getWidth();
		_sourceRight = right / _currentTexture.getWidth();
		_sourceTop = top / _currentTexture.getHeight();
		_sourceBottom = bottom / _currentTexture.getHeight();
	}

	public final void flipSprite(int flip)
	{
		if (flip == FLIP_X || flip == FLIP_XY)
		{
			float s = _sourceLeft;
			_sourceLeft = _sourceRight;
			_sourceRight = s;
		}
		if (flip == FLIP_Y || flip == FLIP_XY)
		{
			float s = _sourceTop;
			_sourceTop = _sourceBottom;
			_sourceBottom = s;
		}
	}

	public final void rotateSprite(int rotate)
	{
		if (rotate == ROTATE_90)
		{
		}
	}

	public String getSource()
	{
		return String.format("Left<%s> Top<%s> Right<%s> Bottom<%s>", _sourceLeft, _sourceTop, _sourceRight, _sourceBottom);
	}

	public final void setupSprite()
	{
		if (_currentMode == MODE_SPRITE)
			return;
		// _gl.glTexEnvi(GL.GL_TEXTURE_ENV, GL.GL_TEXTURE_ENV_MODE,
		// GL.GL_REPLACE);
		// _gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
		_gl.glTexEnvi(GL.GL_TEXTURE_ENV, GL.GL_TEXTURE_ENV_MODE, GL.GL_MODULATE);
		_gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
		_currentMode = MODE_SPRITE;
	}

	public final void setupParticle(int type)
	{
		if (_currentMode == type)
		{
			return;
		}
		switch (type)
		{
			case Particle.FULL:
				_gl.glTexEnvi(GL.GL_TEXTURE_ENV, GL.GL_TEXTURE_ENV_MODE, GL.GL_MODULATE);
				_gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
				break;
			case Particle.NEGATIVE:
				_gl.glTexEnvi(GL.GL_TEXTURE_ENV, GL.GL_TEXTURE_ENV_MODE, GL.GL_BLEND);
				_gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_SRC_ALPHA_SATURATE);
				break;
			case Particle.ADDITIVE:
				_gl.glTexEnvi(GL.GL_TEXTURE_ENV, GL.GL_TEXTURE_ENV_MODE, GL.GL_MODULATE);
				_gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE);
				break;
			case Particle.NORMAL:
				_gl.glTexEnvi(GL.GL_TEXTURE_ENV, GL.GL_TEXTURE_ENV_MODE, GL.GL_MODULATE);
				_gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE);
				break;
		}
		_currentMode = type;
	}

	public void setTexture(String name)
	{
		closeTexture();
		_currentTexture = _textures.get(name);
		if (_currentTexture != null)
		{
			_currentTextureName = name;
			_currentTexture.enable();
			_currentTexture.bind();
		}
	}

	public void closeTexture()
	{
		if (_currentTexture != null)
		{
			_currentTexture.disable();
		}
	}

	public String getTexture()
	{
		return _currentTextureName;
	}

	public void setFont(String fontName, int fontStyle, int fontSize)
	{
		_currentFont = new Font(fontName, fontStyle, fontSize);
		_renderer = new TextRenderer(_currentFont, true, false);
		_renderer.setColor(_currentForeColor.getJava2DColor());
	}

	public void setForeColor(Color foreColor)
	{
		_currentForeColor = foreColor;
		_renderer.setColor(foreColor.getJava2DColor());
	}

	public void drawSprite(float x, float y, float width, float height)
	{
		_gl.glColor4f(1f, 1f, 1f, 1f);
		_gl.glBegin(GL.GL_QUADS);
		{
			_gl.glTexCoord2f(_sourceRight, _sourceBottom);
			_gl.glVertex3f(x + width, y, 0.0f);
			_gl.glTexCoord2f(_sourceLeft, _sourceBottom);
			_gl.glVertex3f(x, y, 0.0f);
			_gl.glTexCoord2f(_sourceLeft, _sourceTop);
			_gl.glVertex3f(x, y + height, 0.0f);
			_gl.glTexCoord2f(_sourceRight, _sourceTop);
			_gl.glVertex3f(x + width, y + height, 0.0f);
		}
		_gl.glEnd();
	}

	public void drawGradient(float x, float y, float width, float height, Color top, Color bottom)
	{
		closeTexture();
		_gl.glLoadIdentity();
		_gl.glBegin(GL.GL_QUADS);
		{
			_gl.glColor4f(bottom.getR(), bottom.getG(), bottom.getB(), bottom.getA());
			_gl.glVertex3f(x + width, y, 0.0f);
			// _gl.glColor4f(bottom.getR(), bottom.getG(), bottom.getB(),
			// bottom.getA());
			_gl.glVertex3f(x, y, 0.0f);
			_gl.glColor4f(top.getR(), top.getG(), top.getB(), top.getA());
			_gl.glVertex3f(x, y + height, 0.0f);
			// _gl.glColor4f(top.getR(), top.getG(), top.getB(), top.getA());
			_gl.glVertex3f(x + width, y + height, 0.0f);
		}
		_gl.glEnd();
	}

	public void fillBounds(float left, float top, float right, float bottom, Color shade)
	{
		closeTexture();
		_gl.glLoadIdentity();
		_gl.glColor4f(shade.getR(), shade.getG(), shade.getB(), shade.getA());
		_gl.glBegin(GL.GL_QUADS);
		{
			_gl.glVertex3f(right, bottom, 0.0f);
			_gl.glVertex3f(left, bottom, 0.0f);
			_gl.glVertex3f(left, top, 0.0f);
			_gl.glVertex3f(right, top, 0.0f);
		}
		_gl.glEnd();
	}

	public void drawSprite(float x, float y, float width, float height, Color shade)
	{
		_gl.glColor4f(shade.getR(), shade.getG(), shade.getB(), shade.getA());
		_gl.glBegin(GL.GL_QUADS);
		{
			_gl.glTexCoord2f(_sourceRight, _sourceBottom);
			_gl.glVertex3f(x + width, y, 0.0f);
			_gl.glTexCoord2f(_sourceLeft, _sourceBottom);
			_gl.glVertex3f(x, y, 0.0f);
			_gl.glTexCoord2f(_sourceLeft, _sourceTop);
			_gl.glVertex3f(x, y + height, 0.0f);
			_gl.glTexCoord2f(_sourceRight, _sourceTop);
			_gl.glVertex3f(x + width, y + height, 0.0f);
		}
		_gl.glEnd();
	}

	public void drawSprite(float x, float y, float width, float height, float angle)
	{
		drawSprite(x, y, width, height, angle, width / 2.0f, height / 2.0f, new Color());
	}

	public void drawSprite(float x, float y, float width, float height, float angle, float centerOffsetX, float centerOffsetY)
	{
		drawSprite(x, y, width, height, angle, centerOffsetX, centerOffsetY, new Color());
	}

	public void drawSprite(float x, float y, float width, float height, float angle, Color shade)
	{
		drawSprite(x, y, width, height, angle, width / 2.0f, height / 2.0f, shade);
	}

	public void drawSprite(float x, float y, float width, float height, float angle, float centerOffsetX, float centerOffsetY, Color shade)
	{
		_gl.glPushMatrix();

		_gl.glTranslatef(x, y, 0.0f);
		_gl.glRotatef(angle, 0.0f, 0.0f, 1.0f);
		_gl.glColor4f(shade.getR(), shade.getG(), shade.getB(), shade.getA());

		_gl.glBegin(GL.GL_QUADS);
		{
			_gl.glTexCoord2f(_sourceRight, _sourceBottom);
			_gl.glVertex3f(width - centerOffsetX, -centerOffsetY, 0.0f);
			_gl.glTexCoord2f(_sourceLeft, _sourceBottom);
			_gl.glVertex3f(-centerOffsetX, -centerOffsetY, 0.0f);
			_gl.glTexCoord2f(_sourceLeft, _sourceTop);
			_gl.glVertex3f(-centerOffsetX, height - centerOffsetY, 0.0f);
			_gl.glTexCoord2f(_sourceRight, _sourceTop);
			_gl.glVertex3f(width - centerOffsetX, height - centerOffsetY, 0.0f);
		}
		_gl.glEnd();

		_gl.glPopMatrix();
	}

	public void drawParticle(float x, float y, float size, float angle, float alpha, float red, float green, float blue)
	{
		_gl.glPushMatrix();

		_gl.glTranslatef(x, y, 0.0f);
		_gl.glRotatef(angle, 0.0f, 0.0f, 1.0f);
		_gl.glColor4f(red, green, blue, alpha);
		float s = size / 2f;
		_gl.glBegin(GL.GL_QUADS);
		{
			_gl.glTexCoord2f(_sourceRight, _sourceBottom);
			_gl.glVertex3f(s, -s, 0.0f);
			_gl.glTexCoord2f(_sourceLeft, _sourceBottom);
			_gl.glVertex3f(-s, -s, 0.0f);
			_gl.glTexCoord2f(_sourceLeft, _sourceTop);
			_gl.glVertex3f(-s, s, 0.0f);
			_gl.glTexCoord2f(_sourceRight, _sourceTop);
			_gl.glVertex3f(s, s, 0.0f);
		}
		_gl.glEnd();

		_gl.glPopMatrix();
	}

	public void drawRectangle(BoundingBox box, Color shade, boolean respectToCamera)
	{
		closeTexture();
		_gl.glLoadIdentity();
		if (respectToCamera)
			_gl.glTranslatef(-Camera.getInstance().getX(), -Camera.getInstance().getY(), 0f);
		_gl.glColor4f(shade.getR(), shade.getG(), shade.getB(), shade.getA());
		_gl.glBegin(GL.GL_LINE_LOOP);
		{
			_gl.glVertex3f(box.getLeft(), box.getTop(), 0.0f);
			_gl.glVertex3f(box.getRight(), box.getTop(), 0.0f);
			_gl.glVertex3f(box.getRight(), box.getBottom(), 0.0f);
			_gl.glVertex3f(box.getLeft(), box.getBottom(), 0.0f);
		}
		_gl.glEnd();
	}

	public void beginTransform()
	{
		_gl.glPushMatrix();
	}

	public void translate(float x, float y)
	{
		_gl.glTranslatef(x, y, 0f);
	}

	public void rotate(float angle)
	{
		_gl.glRotatef(angle, 0f, 0f, 1f);
	}

	public void rotate(float angle, float x, float y)
	{
		_gl.glRotatef(angle, x, y, 0f);
	}

	public void scale(float x, float y)
	{
		_gl.glScalef(x, y, 1f);
	}

	public void endTransform()
	{
		_gl.glPopMatrix();
	}

	public void beginText(int drawableWidth, int drawableHeight)
	{
		_renderer.beginRendering(drawableWidth, drawableHeight);
	}

	public void drawString(String text, int x, int y)
	{
		_renderer.draw(text, x, y);
	}

	public void drawString(String text, int x, int y, Color fontColor)
	{
		_renderer.setColor(fontColor.getJava2DColor());
		_renderer.draw(text, x, y);
		_renderer.setColor(_currentForeColor.getJava2DColor());
	}

	public void endText()
	{
		_renderer.endRendering();
	}

	public TextureLibrary getTextures()
	{
		return _textures;
	}

}
