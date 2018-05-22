package net.philsprojects.mario;

import static net.philsprojects.mario.GameConstants.*;
import net.philsprojects.game.GraphicsLibrary;
import net.philsprojects.game.sprites.SpriteTileStatic;
import net.philsprojects.game.util.Color;
import net.philsprojects.game.util.Math;

/**
 * The transition from screen to screen where it uses Mario's head.
 * 
 * @author Philip Diffenderfer
 */
public class ScreenTransition
{

	public static final int SHRINK = 0;
	public static final int GROW = 1;

	private final SpriteTileStatic _head = (SpriteTileStatic)Tiles.get(MARIO_HEAD);

	private float _duration;
	private float _time;
	private int _focusX = 0;
	private int _focusY = 0;
	private int _mode = SHRINK;
	private int _screenWidth = 0;
	private int _screenHeight = 0;
	private float _maxSize = 0;
	private boolean _enabled = true;

	/**
	 * Initializes a screen transition that last duration amount of seconds
	 *    and expands or shrinks on a focus point.
	 */
	public ScreenTransition(float duration, int focusX, int focusY, int mode, int screenWidth, int screenHeight)
	{
		_duration = duration;
		_focusX = focusX;
		_focusY = focusY;
		_mode = mode;
		_screenWidth = screenWidth;
		_screenHeight = screenHeight;

		_maxSize = Math.max(focusX, Math.max(focusY, Math.max(screenWidth - focusX, screenHeight - focusY))) * 3;
	}

	/**
	 * Updates the transitions time.
	 */
	public void update(float deltatime)
	{
		if (!_enabled)
			return;	

		_time += deltatime;
		if (_time >= _duration)
		{
			_enabled = false;
		}
	}

	/**
	 * Draws the transition.
	 */
	public void draw()
	{
		final GraphicsLibrary g = GraphicsLibrary.getInstance();
		float delta = _time / _duration;
		float size = (_mode == SHRINK ? (1 - delta) * _maxSize : delta * _maxSize);
		float half = size / 2f;
		float left = _focusX - half;
		float right = _focusX + half;
		float top = _focusY + half;
		float bottom = _focusY - half;
		final Color black = Color.black();
		//The Head
		g.setTexture(_head.getTexture());
		g.setSource(_head.getSource());
		g.drawSprite(left - 10, bottom - 10, right - left + 20, top - bottom + 20);
		//Everything Around it
		g.fillBounds(0, _screenHeight, _screenWidth, top, black); //Top
		g.fillBounds(0, 0, _screenWidth, bottom, black); //Bottom
		g.fillBounds(0, top, left, bottom, black); //Left
		g.fillBounds(right, top, _screenWidth, bottom, black); //Right
		g.clearSource();
	}

}
