package net.philsprojects.game;

import net.philsprojects.game.util.Color;

/**
 * @author Philip Diffenderfer
 */
public class Landscape implements ICameraObserver, IClone<Landscape>
{

	protected int _x = 0;
	protected int _y = 0;
	protected int _width = 0;
	protected int _height = 0;
	protected int _separationX = 0;
	protected float _xDamping = 1.0f;
	protected float _yDamping = 1.0f;
	protected ISpriteTile _tile = null;
	protected Color _shade = Color.white();

	// Drawing Information\\
	private int _offsetX = 0;
	private int _offsetY = 0;
	private int _wide = 0;

	/**
	 * @param texture
	 * @param actualWidth
	 * @param actualHeight
	 * @param landscapeBottom
	 * @param dampingX
	 * @param dampingY
	 */
	public Landscape(ISpriteTile tile, int width, int height, int bottom, float xDamping, float yDamping)
	{
		this(tile, 0, bottom, width, height, xDamping, yDamping, 0, Color.white());
	}

	public Landscape(ISpriteTile tile, int width, int height, int bottom, float xDamping, float yDamping, int separationX)
	{
		this(tile, 0, bottom, width, height, xDamping, yDamping, separationX, Color.white());
	}

	public Landscape(ISpriteTile tile, int x, int y, int width, int height, float xDamping, float yDamping, int separationX, Color shade)
	{
		_tile = tile;
		_x = x;
		_y = y;
		_width = width;
		_height = height;
		_xDamping = xDamping;
		_yDamping = yDamping;
		_separationX = separationX;
		_shade = shade;
		Camera.getInstance().addObserver(this);
	}

	/**
	 * @param gr
	 */
	public void draw(GraphicsLibrary g)
	{
		// if (onScreen()) {
		g.setTexture(_tile.getTexture());
		g.setSource(_tile.getSource());
		for (int i = 0; i < _wide; i++)
		{
			g.drawSprite(i * _width - _offsetX + (i * _separationX), _offsetY, _width, _height, _shade);
		}
		g.clearSource();
		// }
	}

	public void update(float deltatime)
	{
		_tile.update(deltatime);
	}

	/**
	 * @return
	 */
	public boolean onScreen()
	{
		return (_offsetY < Camera.getInstance().getY());
	}

	/**
	 * 
	 */
	public void cameraChanged(int newX, int newY, int newWidth, int newHeight)
	{
		_offsetX = (int)(newX * _xDamping) % (_width + _separationX) + _x;
		if (_offsetX < 0)
			_offsetX += (_width + _separationX);

		_offsetY = (int)(-newY * _yDamping) + _y;

		_wide = (int)Math.ceil((_offsetX + newWidth) / (_width + _separationX)) + 1;
	}

	/**
	 * @return
	 */
	public String getTexture()
	{
		return _tile.getTexture();
	}

	/**
	 * @return
	 */
	public int getY()
	{
		return _y;
	}

	/**
	 * @return
	 */
	public int getX()
	{
		return _x;
	}

	/**
	 * @return
	 */
	public int getWidth()
	{
		return _width;
	}

	/**
	 * @return
	 */
	public int getHeight()
	{
		return _height;
	}

	/**
	 * @return
	 */
	public float getXDamping()
	{
		return _xDamping;
	}

	/**
	 * @return
	 */
	public float getYDamping()
	{
		return _yDamping;
	}

	/**
	 * @return
	 */
	public float getXSeparaion()
	{
		return _separationX;
	}

	/**
	 * @return
	 */
	public int getActualY()
	{
		return _offsetY;
	}

	/**
	 * @return
	 */
	public int getActualXStart()
	{
		return _offsetX;
	}

	/**
	 * @return
	 */
	public int getImagesDrawn()
	{
		return _wide;
	}

	/**
	 * 
	 */
	@Override
	public String toString()
	{
		return ""; //_texture + " " + _offsetX + " " + _offsetY + " " + _wide;
		// return String.format("Landscape<%s> Width<%s> Height<%s> OffsetX<%s> OffsetY<%s>
		// Wide<%s>", texture, width, height, offsetX, offsetY, wide);
	}

	public Landscape getClone()
	{
		return new Landscape(_tile.getClone(), _x, _y, _width, _height, _xDamping, _yDamping, _separationX, _shade.getClone());
	}

}
