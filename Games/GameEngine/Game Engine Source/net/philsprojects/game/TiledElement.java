package net.philsprojects.game;

import net.philsprojects.game.util.BoundingBox;
import net.philsprojects.game.util.Iterator;
import net.philsprojects.game.util.LinkedList;

public class TiledElement implements IUpdate
{
	private static int WIDTH = 0;
	private static int HEIGHT = 0;

	public static int getWidth()
	{
		return WIDTH;
	}

	public static int getHeight()
	{
		return HEIGHT;
	}

	public static void setWidth(int width)
	{
		WIDTH = width;
	}

	public static void setHeight(int height)
	{
		HEIGHT = height;
	}

	private boolean _blocksRight = false;
	private boolean _blocksLeft = false;
	private boolean _blocksTop = false;
	private boolean _blocksBottom = false;
	private boolean _acceptsIntersection = true;
	private boolean _correctsIntersection = true;
	private boolean _enabled = true;
	private boolean _tracksInstances = false;
	private ISpriteTile _tile = null;

	protected LinkedList<TiledElementInstance> _instances = new LinkedList<TiledElementInstance>();

	private float _scale = 1f;

	public TiledElement(ISpriteTile tile, boolean blocksTop, boolean blocksRight, boolean blocksBottom, boolean blocksLeft, boolean accepts, boolean corrects, boolean tracks, float scale)
	{
		_tile = tile;
		_blocksRight = blocksRight;
		_blocksLeft = blocksLeft;
		_blocksTop = blocksTop;
		_blocksBottom = blocksBottom;
		_acceptsIntersection = accepts;
		_correctsIntersection = corrects;
		_tracksInstances = tracks;
		_scale = scale;
	}

	public TiledElement(ISpriteTile tile, boolean blocksTop, boolean blocksRight, boolean blocksBottom, boolean blocksLeft, boolean accepts, boolean corrects, float scale)
	{
		this(tile, blocksTop, blocksRight, blocksBottom, blocksLeft, accepts, corrects, false, scale);
	}

	public boolean isEnabled()
	{
		return _enabled;
	}

	public void update(float deltatime)
	{
		_tile.update(deltatime);
	}

	public boolean blocksTop()
	{
		return _blocksTop;
	}

	public boolean blocksBottom()
	{
		return _blocksBottom;
	}

	public boolean blocksLeft()
	{
		return _blocksLeft;
	}

	public boolean blocksRight()
	{
		return _blocksRight;
	}

	public boolean acceptsIntersection()
	{
		return _acceptsIntersection;
	}

	public boolean correctsIntersection()
	{
		return _correctsIntersection;
	}

	public boolean tracksInstances()
	{
		return _tracksInstances;
	}

	public void hit(ITiledEntity entity, int x, int y, int hitType)
	{

	}

	public boolean remove()
	{
		return false;
	}

	public float getScale()
	{
		return _scale;
	}

	public ISpriteTile getTile()
	{
		return _tile;
	}

	/**
	 * Returns the bounds of this box based on its scale and size;
	 * 
	 * @param x The index on the horizontal plane
	 * @param y The index on the vertical plane
	 */
	public final BoundingBox getBounds(int x, int y)
	{
		//Center Location
		float cX = x * WIDTH + (WIDTH / 2f);
		float cY = y * HEIGHT + (HEIGHT / 2f);
		//Half Size
		float hW = (WIDTH / 2f) * _scale;
		float hH = (HEIGHT / 2f) * _scale;
		return new BoundingBox(cX - hW, cY + hH, cX + hW, cY - hH);
	}

	public void addInstance(int x, int y)
	{
	}

	public void addInstance(TiledElementInstance instance)
	{
		_instances.add(instance);
	}

	public TiledElementInstance getInstance(int x, int y)
	{
		Iterator<TiledElementInstance> iter = _instances.getIterator();
		TiledElementInstance i = null;
		while (iter.hasNext())
		{
			i = iter.getNext();
			if (i.equals(x, y))
				return i;
		}
		return null;
	}

	public String getTexture()
	{
		return _tile.getTexture();
	}

	public TiledElement getClone()
	{
		return new TiledElement(_tile, _blocksTop, _blocksRight, _blocksBottom, _blocksLeft, _acceptsIntersection, _correctsIntersection, _scale);
	}

}
