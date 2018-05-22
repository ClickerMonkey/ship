/************************************************\ 
 *                    [Quad]                    * 
 *            ______ ___    __   _    _____     * 
 *           / ____//   |  /  | / |  / ___/     *
 *          / /___ / /| | /   |/  | / __/       *
 *         / /_| // __  |/ /|  /| |/ /__        *
 *        /_____//_/  |_|_/ |_/ |_|\___/        *
 *    _____ __   _  ______ ______ __   _  _____ *
 *   / ___//  | / // ____//_  __//  | / // ___/ *
 *  / __/ / | |/ // /___   / /  / | |/ // __/   *
 * / /__ / /|   // /_| /__/ /_ / /|   // /__    *
 * \___//_/ |__//_____//_____//_/ |__/ \___/    *
 *                                              *
\************************************************/

package net.philsprojects.game.sprites;

import net.philsprojects.game.Camera;
import net.philsprojects.game.GraphicsLibrary;
import net.philsprojects.game.util.Color;

public class Quad
{

	protected int _x = 0;
	protected int _y = 0;
	protected int _width = 0;
	protected int _height = 0;

	protected int _boundTop = 0;
	protected int _boundLeft = 0;
	protected int _boundRight = 0;
	protected int _boundBottom = 0;

	private String _texture;

	private Color _shade;

	public Quad(String texture, int x, int y, int width, int height, Color shade)
	{
		_texture = texture;
		_x = x;
		_y = y;
		_width = width;
		_height = height;
		_shade = shade;
	}

	public void draw()
	{
		final GraphicsLibrary g = GraphicsLibrary.getInstance();
		final Camera c = Camera.getInstance();
		g.setupSprite();
		g.setTexture(_texture);
		g.drawSprite(_x - c.getX(), _y - c.getY(), _width, _height, _shade);
	}

	public final boolean onScreen()
	{
		return Camera.getInstance().intersects(this);
	}

	public final int getX()
	{
		return _x;
	}

	public final int getY()
	{
		return _y;
	}

	public final int getActualWidth()
	{
		return _width;
	}

	public final int getActualHeight()
	{
		return _height;
	}

	public final int getBoundLeft()
	{
		return _x + _boundLeft;
	}

	public final int getBoundRight()
	{
		return _x + _boundRight;
	}

	public final int getBoundTop()
	{
		return _y + _boundTop;
	}

	public final int getBoundBottom()
	{
		return _y + _boundBottom;
	}

	public final int getBoundWidth()
	{
		return _boundRight - _boundLeft;
	}

	public final int getBoundHeight()
	{
		return _boundBottom - _boundTop;
	}

}
