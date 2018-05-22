package net.philsprojects.game;

import net.philsprojects.game.util.Color;
import net.philsprojects.game.util.Rectangle;

/**
 * A Class used to display characters on the screen based on a character string and a 
 * 	string of characters for the texture that represents their order.
 * 
 * @author Philip Diffenderfer
 */
public class Text implements ITexture, IClone<Text>
{

	private String _texture = null;
	private String _characters = null;
	private int[] _locations = new int[256];
	private int _offsetX = 0;
	private int _spacing = 0;
	private int _charWidth = 0;
	private int _charHeight = 0;
	private Rectangle _source = Rectangle.zero();

	/**
	 * Creates a Text instance where the character size drawn is the actual size of each frame.
	 *  
	 * @param texture => The texture that the text frames are pulled from.
	 * @param characters => The characters on the texture in that order.
	 * @param offsetX => The offset from the left in pixels where the text starts.
	 * @param offsetY => The offset from the top in pixels where the text starts.
	 * @param frameWidth => The width in pixels of the frame for each letter.
	 * @param frameHeight => The height in pixels of the frame for each letter.
	 * @param spacing => The offset in spacing between each character rendered where a positive value brings the letters closer together.
	 */
	public Text(String texture, String characters, int offsetX, int offsetY, int frameWidth, int frameHeight, int spacing)
	{
		this(texture, characters, offsetX, offsetY, frameWidth, frameHeight, spacing, frameWidth, frameHeight);
	}

	/**
	 * Creates a Text instance where the character size drawn is different size from the frame.
	 * 
	 * @param texture => The texture that the text frames are pulled from.
	 * @param characters => The characters on the texture in that order.
	 * @param offsetX => The offset from the left in pixels where the text starts.
	 * @param offsetY => The offset from the top in pixels where the text starts.
	 * @param frameWidth => The width in pixels of the frame for each letter.
	 * @param frameHeight => The height in pixels of the frame for each letter.
	 * @param spacing => The offset in spacing between each character rendered where a positive value brings the letters closer together.
	 * @param charWidth => The width of the character drawn on the screen in pixels.
	 * @param charHeight => The height of the character draw on the screen in pixels.
	 */
	public Text(String texture, String characters, int offsetX, int offsetY, int frameWidth, int frameHeight, int spacing, int charWidth, int charHeight)
	{
		_texture = texture;
		_characters = characters;
		for (int i = 0; i < _characters.length(); i++)
			_locations[_characters.charAt(i)] = i + 1;
		_offsetX = offsetX;
		_spacing = spacing;
		_charWidth = charWidth;
		_charHeight = charHeight;
		_source.setSize(frameWidth, frameHeight);
		_source.setY(offsetY);
	}

	/**
	 * Draws the text on the screen at x and y with white shading (normal).
	 * 
	 * @param text => The string to draw. If a character in the string is not on the texture a blank space is drawn.
	 * @param x => Pixels from the left the of screen to draw the string.
	 * @param y => Pixels from the bottom of the screen to the base of the string.
	 * @param respectsCamera => If true then the text's location is affected by the camera's location. If false then the location is actual.
	 */
	public void draw(String text, int x, int y, boolean respectsCamera)
	{
		draw(text, x, y, respectsCamera, Color.white());
	}

	/**
	 * Draws the text on the screen at x and y with custom shading.
	 * 
	 * @param text => The string to draw. If a character in the string is not on the texture a blank space is drawn.
	 * @param x => Pixels from the left the of screen to draw the string.
	 * @param y => Pixels from the bottom of the screen to the base of the string.
	 * @param respectsCamera => If true then the text's location is affected by the camera's location. If false then the location is actual.
	 * @param foreColor => The color or shade to change the text.
	 */
	public void draw(String text, int x, int y, boolean respectsCamera, Color foreColor)
	{
		final GraphicsLibrary g = GraphicsLibrary.getInstance();
		final Camera c = Camera.getInstance();
		int xOff = (respectsCamera ? c.getX() : 0);
		int yOff = (respectsCamera ? c.getY() : 0);
		g.setTexture(_texture);
		int index = 0;
		for (int i = 0; i < text.length(); i++)
		{
			index = _locations[text.charAt(i)];
			if (index >= 1)
			{
				setSource(index - 1);
				g.setSource(_source);
				g.drawSprite(x - xOff, y - yOff, _charWidth, _charHeight, foreColor);
			}
			x += (_charWidth - _spacing);
		}
	}

	/**
	 * Sets the source texture of the text.
	 */
	public void setTexture(String texture)
	{
		_texture = texture;
	}

	/**
	 * Gets the source rectangle from the texture based on the character index, offset, and frame size.
	 * @param index => The index between 0 and characters.length() of the character to get the source from.
	 */
	private void setSource(int index)
	{
		_source.setX(_offsetX + index * _source.getWidth());
	}

	/**
	 * Returns the texture associated with this Text.
	 */
	public String getTexture()
	{
		return _texture;
	}

	/**
	 * Returns the characters on the texture in that order.
	 */
	public String getCharacters()
	{
		return _characters;
	}

	/**
	 * Returns the offset in pixels from the left the of screen to draw the string.
	 */
	public int getOffsetX()
	{
		return _offsetX;
	}

	/**
	 * Returns the offset in pixels from the bottom of the screen to the base of the string.
	 */
	public int getOffsetY()
	{
		return (int)_source.getY();
	}

	/**
	 * Returns the width of the character drawn on the screen in pixels.
	 */
	public int getCharWidth()
	{
		return _charWidth;
	}

	/**
	 * Returns the height of the character draw on the screen in pixels.
	 */
	public int getCharHeight()
	{
		return _charHeight;
	}

	/**
	 * Returns the offset in spacing between each character rendered where a positive value brings the letters closer together.
	 */
	public int getSpacing()
	{
		return _spacing;
	}

	/**
	 * Returns the width in pixels of the frame for each letter.
	 */
	public int getFrameWidth()
	{
		return (int)_source.getWidth();
	}

	/**
	 * Returns the height in pixels of the frame for each letter.
	 */
	public int getFrameHeight()
	{
		return (int)_source.getHeight();
	}

	public Text getClone()
	{
		return new Text(_texture, _characters, _offsetX, (int)_source.getY(), (int)_source.getWidth(), (int)_source.getHeight(), _spacing, _charWidth, _charHeight);
	}

}
