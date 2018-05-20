package net.philsprojects.game;

import static net.philsprojects.game.Constants.HIT_BOTTOM;
import static net.philsprojects.game.Constants.HIT_LEFT;
import static net.philsprojects.game.Constants.HIT_RIGHT;
import static net.philsprojects.game.Constants.HIT_TOP;
import static net.philsprojects.game.util.Math.ceil;
import static net.philsprojects.game.util.Math.cut;
import static net.philsprojects.game.util.Math.floor;

import java.io.DataInputStream;
import java.util.Scanner;

import net.philsprojects.game.sprites.Sprite;
import net.philsprojects.game.util.BoundingBox;
import net.philsprojects.game.util.HashTable;
import net.philsprojects.game.util.Iterator;
import net.philsprojects.game.util.LinkedList;
import net.philsprojects.game.util.Vector;


/**
 * @author Philip Diffenderfer
 */
public class TiledLayer implements ICameraObserver, IUpdate
{

	public static final byte EMPTY_SPACE = 0;

	private int _tileWidth;

	private int _tileHeight;

	private int _tilesWide;

	private int _tilesDown;

	private HashTable<LinkedList<String>> _groups;

	private String _texture = null;

	private int[][] _map;

	private TiledElement[] _elements;

	private LinkedList<ITiledEntity> _entities = null;

	private LinkedList<IName> _objects = null;

	private LinkedList<Landscape> _landscapes = null;

	private boolean _usesTileSheet = false;

	private boolean _enabled = true;

	private boolean _handlesHitsOffScreen = false;

	private int _startX = 0;

	private int _startY = 0;

	private int _offsetX = 0;

	private int _offsetY = 0;

	private int _wide = 0;

	private int _down = 0;

	private float _resolutionPadding = 0.1f;

	private BoundingBox _worldBoundaries = BoundingBox.zero();

	/**
	 * @param tileSheet
	 */
	public TiledLayer(int maxElements, int maxGroups)
	{
		_elements = new TiledElement[maxElements];
		_entities = new LinkedList<ITiledEntity>();
		_objects = new LinkedList<IName>();
		_landscapes = new LinkedList<Landscape>();
		_groups = new HashTable<LinkedList<String>>(maxGroups);
		Camera.getInstance().addObserver(this);
	}

	protected TiledLayer()
	{
		_entities = new LinkedList<ITiledEntity>();
		_objects = new LinkedList<IName>();
		_landscapes = new LinkedList<Landscape>();
	}

	public void setInitialGroups(String... initialGroups)
	{
		for (int i = 0; i < initialGroups.length; i++)
		{
			LinkedList<String> group = new LinkedList<String>();
			group.add(initialGroups[i]);
			_groups.add(initialGroups[i], group);
		}
	}

	public void addGroupAllowance(String group, String added)
	{
		_groups.get(group).add(added);
	}

	/**
	 * @param reader
	 * @throws IOException
	 */
	public void loadFromStream(DataInputStream reader) throws Exception
	{
		_tilesWide = reader.readInt();
		_tilesDown = reader.readInt();
		byte[][] tiles = new byte[_tilesDown][_tilesWide];
		for (int y = 0; y < _tilesDown; y++)
		{
			for (int x = 0; x < _tilesWide; x++)
			{
				tiles[x][y] = reader.readByte();
			}
		}
		setLayerData(tiles);
	}

	public void drawLandscapes(GraphicsLibrary g)
	{
		// Draw each landscape
		Iterator<Landscape> iterLandscape = _landscapes.getIterator();
		while (iterLandscape.hasNext())
			iterLandscape.getNext().draw(g);
	}

	/**
	 * @param gr
	 */
	public void draw(GraphicsLibrary g)
	{
		// Draw the map
		if (_usesTileSheet)
			g.setTexture(_texture);
		TiledElement t = null;
		float s = 0f, sx = 0f, sy = 0f;
		for (int y = 0; y < _down && y + _startY < _tilesDown; y++)
		{
			for (int x = 0; x < _wide && x + _startX < _tilesWide; x++)
			{
				t = _elements[_map[y + _startY][x + _startX]];
				if (t != null)
				{
					if (!_usesTileSheet)
						g.setTexture(t.getTile().getTexture());
					g.setSource(t.getTile().getSource());

					s = t.getScale();
					sx = (_tileWidth - (_tileWidth * s)) / 2f;
					sy = (_tileHeight - (_tileHeight * s)) / 2f;
					g.drawSprite(x * _tileWidth - _offsetX + sx, y * _tileHeight - _offsetY + sy, _tileWidth * s, _tileHeight * s);
				}
			}
		}
	}

	public void drawEntities(GraphicsLibrary g)
	{
		// Draw each entity
		Iterator<ITiledEntity> iterEntity = _entities.getIterator();
		ITiledEntity entity = null;
		while (iterEntity.hasNext())
		{
			entity = iterEntity.getNext();
			if (!entity.isUserDrawn())
				entity.draw();
		}
	}

	public void drawSprites(GraphicsLibrary g)
	{
		// Draw each sprite
		Iterator<IName> iterSprite = _objects.getIterator();
		IName namable;
		while (iterSprite.hasNext())
		{
			namable = iterSprite.getNext();
			if (namable instanceof IDraw)
			{
				IDraw d = (IDraw)namable;
				d.draw();
			}
		}
	}

	/**
	 * 
	 */
	public void cameraChanged(int newX, int newY, int newWidth, int newHeight)
	{
		//
		_offsetX = (newX % _tileWidth);
		_startX = (newX - _offsetX) / _tileWidth;
		if (_startX < 0)
		{
			_offsetX += _startX * _tileWidth;
			_startX = 0;
		}
		_wide = (int)ceil((_offsetX + newWidth) / _tileWidth) + 1;
		//
		_offsetY = (newY % _tileHeight);
		_startY = (newY - _offsetY) / _tileHeight;
		if (_startY < 0)
		{
			_offsetY += _startY * _tileHeight;
			_startY = 0;
		}
		_down = (int)ceil((_offsetY + newHeight) / _tileHeight) + 1;
	}

	/**
	 * @param data
	 */
	public void setLayerData(byte[][] data)
	{
		_tilesDown = data.length;
		_tilesWide = data[0].length;
		_map = new int[_tilesDown][_tilesWide];
		// Set each corresponding map element's clone to a spot on the map
		for (int y = 0; y < _tilesDown; y++)
			for (int x = 0; x < _tilesWide; x++)
			{
				_map[y][x] = data[_tilesDown - 1 - y][x];
				if (_elements[_map[y][x]] != null && _elements[_map[y][x]].tracksInstances())
					_elements[_map[y][x]].addInstance(x, y);
			}
	}

	/**
	 * @param width
	 * @param height
	 */
	public void setTileSize(int width, int height)
	{
		_tileWidth = width;
		_tileHeight = height;
		TiledElement.setWidth(width);
		TiledElement.setHeight(height);
	}

	/**
	 * @param texture
	 */
	public void setTexture(String texture)
	{
		_usesTileSheet = true;
		_texture = texture;
	}

	/**
	 * @param x
	 * @param y
	 */
	public void removeTile(int x, int y)
	{
		_map[y][x] = 0;
	}

	/**
	 * @param x
	 * @param y
	 */
	public void change(int x, int y, int element)
	{
		_map[y][x] = element;
	}

	public void addObject(IName n)
	{
		_objects.add(n);
	}

	public void removeObject(String name)
	{
		LinkedList<IName>.Node first = _objects.getFirstNode();
		LinkedList<IName>.Node next;
		while (first != null)
		{
			next = first._next;
			if (first._element.getName().equals(name))
			{
				_objects.remove(first);
				break;
			}
			first = next;
		}
	}

	public void removeAllSprites(String name)
	{
		LinkedList<IName>.Node first = _objects.getFirstNode();
		LinkedList<IName>.Node next;
		while (first != null)
		{
			next = first._next;
			if (first._element.getName().equals(name))
				_objects.remove(first);
			first = next;
		}
	}

	public void addLandscape(Landscape l)
	{
		_landscapes.add(l);
	}

	/**
	 * @param index
	 * @param tile
	 * @param blocksRight
	 * @param blocksLeft
	 * @param blocksTop
	 * @param blocksBottom
	 */
	public void setElement(int index, ISpriteTile tile, boolean blocksTop, boolean blocksRight, boolean blocksBottom, boolean blocksLeft)
	{
		_elements[index] = new TiledElement(tile, blocksTop, blocksRight, blocksBottom, blocksLeft, true, true, 1f);
	}

	/**
	 * @param index
	 * @param tile
	 * @param blocksTop
	 * @param blocksRight
	 * @param blocksBottom
	 * @param blocksLeft
	 * @param scale
	 */
	public void setElement(int index, ISpriteTile tile, boolean blocksTop, boolean blocksRight, boolean blocksBottom, boolean blocksLeft, float scale)
	{
		_elements[index] = new TiledElement(tile, blocksTop, blocksRight, blocksBottom, blocksLeft, true, true, scale);
	}

	/**
	 * @param index
	 * @param tile
	 * @param blocksTop
	 * @param blocksRight
	 * @param blocksBottom
	 * @param blocksLeft
	 * @param acceptsIntersection
	 * @param correctsIntersection
	 * @param scale
	 */
	public void setElement(int index, ISpriteTile tile, boolean blocksTop, boolean blocksRight, boolean blocksBottom, boolean blocksLeft, boolean acceptsIntersection, boolean correctsIntersection, float scale)
	{
		_elements[index] = new TiledElement(tile, blocksTop, blocksRight, blocksBottom, blocksLeft, acceptsIntersection, correctsIntersection, scale);
	}

	/**
	 * @param index
	 * @param tile
	 * @param scale
	 */
	public void setElement(int index, ISpriteTile tile, float scale)
	{
		_elements[index] = new TiledElement(tile, false, false, false, false, false, false, scale);
	}

	/**
	 * @param index
	 * @param element
	 */
	public void setElement(int index, TiledElement element)
	{
		_elements[index] = element;
	}

	/**
	 * 
	 * @param boundary
	 */
	public void setWorldBoundaries(BoundingBox boundary)
	{
		_worldBoundaries = boundary;
	}

	/**
	 * @param entity
	 */
	public void addEntity(ITiledEntity entity)
	{
		if (entity != null)
			_entities.add(entity);
	}

	public boolean isOnGround(ITiledEntity entity)
	{
		BoundingBox box = entity.getBounds();
		int y = (int)cut(floor(box.getBottom() / _tileHeight) - 1, 0, _tilesDown - 1);
		int startX = (int)cut(floor(box.getLeft() / _tileWidth), 0, _tilesWide - 1);
		int endX = (int)cut(floor(box.getRight() / _tileWidth), 0, _tilesWide - 1);
		// If any of the tiles beneath are a ground element then return true
		for (int x = startX; x <= endX; x++)
			if (_map[y][x] != 0 && _elements[_map[y][x]].blocksTop())
				return true;
		// No ground, return false;
		return false;
	}

	/**
	 * @return
	 */
	public int getTileWidth()
	{
		return _tileWidth;
	}

	/**
	 * @return
	 */
	public int getTileHeight()
	{
		return _tileHeight;
	}

	/**
	 * @return
	 */
	public int[][] getLayerData()
	{
		return _map;
	}

	/**
	 * @return
	 */
	public TiledElement[] getElementData()
	{
		return _elements;
	}

	/**
	 * @return
	 */
	public int getTotalTilesWide()
	{
		return _tilesWide;
	}

	/**
	 * @return
	 */
	public int getTotalTilesDown()
	{
		return _tilesDown;
	}

	/**
	 * @return
	 */
	public int getTilesDrawnAcross()
	{
		return _wide;
	}

	/**
	 * @return
	 */
	public int getTilesDrawnDown()
	{
		return _down;
	}

	/**
	 * @return
	 */
	public int getTotalTilesDrawn()
	{
		return _down * _wide;
	}

	/**
	 * @return
	 */
	public int getLayerUpperBound()
	{
		return _tilesDown * _tileHeight;
	}

	/**
	 * @return
	 */
	public int getLayerRightBound()
	{
		return _tilesWide * _tileWidth;
	}

	public boolean isEnabled()
	{
		return _enabled;
	}

	public void update(float deltatime)
	{
		if (!_enabled)
			return;
		// Update each tile's sprite tile
		for (int i = 0; i < _elements.length; i++)
			if (_elements[i] != null)
				_elements[i].update(deltatime);
		// If any entities exist and are outside the world or they are disabled
		// then remove them
		if (!_worldBoundaries.isEmpty() && _entities.getSize() > 0)
		{
			LinkedList<ITiledEntity>.Node c = _entities.getFirstNode();
			LinkedList<ITiledEntity>.Node n = null;
			while (c != null)
			{
				n = c._next;
				if (!c._element.getBounds().intersects(_worldBoundaries) || !c._element.isEnabled())
				{
					if (c._element.removingEntity())
						_entities.remove(c);
				}
				c = n;
			}
		}
		// If all the entities have been removed due to out of bounds then return.
		if (_entities.getSize() == 0)
			return;
		// Used to iterate through each entity
		Iterator<ITiledEntity> iterCurrent = _entities.getIterator();
		// Current entity
		ITiledEntity entity = null;
		// Loop through each entity
		while (iterCurrent.hasNext())
		{
			// Current entity
			entity = iterCurrent.getNext();
			// Update entity
			entity.update(deltatime);
			// Handle collisions with tiles
			handleEntityTileCollisions(entity);
			// Handle collisions with entities
			handleEntityCollisions(entity);
		}
		iterCurrent = null;
		entity = null;
		// Loop through each sprite.
		LinkedList<IName>.Node current = _objects.getFirstNode();
		LinkedList<IName>.Node next;
		IUpdate updatable;
		while (current != null)
		{
			next = current._next;
			if (current._element instanceof IUpdate)
			{
				updatable = (IUpdate)current._element;
				updatable.update(deltatime);
				if (!updatable.isEnabled())
					_objects.remove(current);
			}
			current = next;
		}
	}

	public void handleEntityTileCollisions(ITiledEntity entity)
	{
		if (!entity.acceptsTileHit())
			return;
		// The current tile being examined
		TiledElement tile = null;
		// The bounding boxes of the entity and current tile
		BoundingBox tileBox = null, entityBox = entity.getBounds(), entityLast = entity.getLastBounds();
		// Compute the indices surrounding the entity
		int startX = (int)cut(floor(entityBox.getLeft() / _tileWidth), 0, _tilesWide - 1);
		int endX = (int)cut(floor(entityBox.getRight() / _tileWidth), 0, _tilesWide - 1);
		int startY = (int)cut(floor(entityBox.getBottom() / _tileHeight) - 1, 0, _tilesDown - 1);
		int endY = (int)cut(floor(entityBox.getTop() / _tileHeight), 0, _tilesDown - 1);
		// Check for collisions with Tiles
		for (int y = startY; y <= endY; y++)
		{
			for (int x = startX; x <= endX; x++)
			{
				if (_map[y][x] != 0)
				{
					tile = _elements[_map[y][x]];
					tileBox = tile.getBounds(x, y);
					if (tile.acceptsIntersection() && entityBox.intersects(tileBox))
					{
						// LOGIC => IF
						// 1. The tile is blocked on the side... AND
						// 2. This side is the most overlapped... AND
						// 3. The bounds of the entity overlap the tile bounds... AND
						// 4. The tile side matches up with the intersection box...
						// THEN
						// IF The tile corrects the intersection... THEN
						// Reset the entities intersected side.
						// Tell the entity that its intersected with the tile at x, y
						// and hit a side
						// Set the hit type for the tile, so the tile know its hit.
						int hitType = 0;
						BoundingBox inter = entityBox.intersection(tileBox);
						// Entity hits top on tile bottom
						if (tile.blocksBottom() && inter.getHeight() < inter.getWidth() && entityBox.getTop() > tileBox.getBottom() && entityLast.getTop() <= entityBox.getTop() && inter.getBottom() == tileBox.getBottom())
						{
							if (tile.correctsIntersection())
								entity.setTop(tileBox.getBottom() - _resolutionPadding);
							entity.hitTile(tile, x, y, HIT_TOP);
							hitType = HIT_BOTTOM;
						}
						// Entity hits bottom on tile top
						else if (tile.blocksTop() && inter.getHeight() < inter.getWidth() && entityBox.getBottom() < tileBox.getTop() && entityLast.getBottom() >= entityBox.getBottom() && inter.getTop() == tileBox.getTop())
						{
							if (tile.correctsIntersection())
								entity.setBottom(tileBox.getTop() + _resolutionPadding);
							entity.hitTile(tile, x, y, HIT_BOTTOM);
							hitType = HIT_TOP;
						}
						// Entity hits right on tile left
						else if (tile.blocksLeft() && inter.getWidth() < inter.getHeight() && entityBox.getRight() > tileBox.getLeft() && entityLast.getRight() <= entityBox.getRight() && inter.getLeft() == tileBox.getLeft())
						{
							if (tile.correctsIntersection())
								entity.setRight(tileBox.getLeft() - _resolutionPadding);
							entity.hitTile(tile, x, y, HIT_RIGHT);
							hitType = HIT_LEFT;
						}
						// Entity hits left on tile right
						else if (tile.blocksRight() && inter.getWidth() < inter.getHeight() && entityBox.getLeft() < tileBox.getRight() && entityLast.getLeft() >= entityBox.getLeft() && inter.getRight() == tileBox.getRight())
						{
							if (tile.correctsIntersection())
								entity.setLeft(tileBox.getRight() + _resolutionPadding);
							entity.hitTile(tile, x, y, HIT_LEFT);
							hitType = HIT_RIGHT;
						}
						tile.hit(entity, x, y, hitType);
					}
				}
			}
		}
	}

	public void handleEntityCollisions(ITiledEntity entity)
	{
		if (!entity.acceptsEntityHit())
			return;
		// Used to iterate through each entity to check for collisions
		// with the current entity
		Iterator<ITiledEntity> iterNext = _entities.getIterator();
		// The current entity and the next entity that its checking for collision
		ITiledEntity next = null;
		// The Bounds of the current tile its looking at
		BoundingBox entityBox = entity.getBounds(), nextBox = null;
		// Check for collisions with entities
		while (iterNext.hasNext())
		{
			next = iterNext.getNext();
			nextBox = next.getBounds();
			// IF (its not the same entity) AND (they are in different collision
			// groups) AND
			// (they both accept hits) AND (handles off screen collisions OR both
			// are on screen)
			// THEN (handle collision)
			if (next != entity && !_groups.get(entity.getGroupID()).contains(next.getGroupID()) && next.acceptsEntityHit() && next.isEnabled() && entity.isEnabled())
			{
				if (_handlesHitsOffScreen || (entityBox.isOnScreen() && nextBox.isOnScreen()))
				{
					BoundingBox inter = entityBox.intersection(nextBox);
					// Entity hits top on other entity bottom
					if (inter.getHeight() < inter.getWidth() && entityBox.getTop() > nextBox.getBottom() && inter.getBottom() == nextBox.getBottom())
					{
						if (next.correctsIntersection())
							entity.setTop(nextBox.getBottom() - _resolutionPadding);
						entity.hitEntity(next, HIT_TOP);
						next.hitEntity(entity, HIT_BOTTOM);
					}
					// Entity hits bottom on other entity top
					else if (inter.getHeight() < inter.getWidth() && entityBox.getBottom() < nextBox.getTop() && inter.getTop() == nextBox.getTop())
					{
						if (next.correctsIntersection())
							entity.setBottom(nextBox.getTop() + _resolutionPadding);
						entity.hitEntity(next, HIT_BOTTOM);
						next.hitEntity(entity, HIT_TOP);
					}
					// Entity hits right on other entity left
					else if (inter.getWidth() < inter.getHeight() && entityBox.getRight() > nextBox.getLeft() && inter.getLeft() == nextBox.getLeft())
					{
						if (next.correctsIntersection())
							entity.setRight(nextBox.getLeft() - _resolutionPadding);
						entity.hitEntity(next, HIT_RIGHT);
						next.hitEntity(entity, HIT_LEFT);
					}
					// Entity hits left on other entity right
					else if (inter.getWidth() < inter.getHeight() && entityBox.getLeft() < nextBox.getRight() && inter.getRight() == nextBox.getRight())
					{
						if (next.correctsIntersection())
							entity.setLeft(nextBox.getRight() + _resolutionPadding);
						entity.hitEntity(next, HIT_LEFT);
						next.hitEntity(entity, HIT_RIGHT);
					}
				}
			}
		}
	}

	public BoundingBox getTileBounds(int x, int y)
	{
		return new BoundingBox(x * _tileWidth, (y + 1) * _tileHeight, (x + 1) * _tileWidth, y * _tileHeight);
	}

	public float getEntityX(int x, float width)
	{
		return (x * _tileWidth + (width - _tileWidth) / 2f);
	}

	public float getEntityY(int y, float offset)
	{
		return (y * _tileHeight - offset);
	}

	public int getEntityCount()
	{
		return _entities.getSize();
	}

	public BoundingBox getWorldBoundaries()
	{
		return _worldBoundaries;
	}

	public TiledElement getTile(int x, int y)
	{
		if (x < 0 || x >= _tilesWide || y < 0 || y >= _tilesDown)
			return null;
		return _elements[_map[y][x]];
	}

	public Vector getIndex(BoundingBox b)
	{
		float cx = (b.getLeft() + b.getRight()) / 2f;
		float cy = (b.getTop() + b.getBottom()) / 2f;
		return new Vector((int)(cx / _tileWidth), (int)(cy / _tileHeight));
	}

	/**
	 * 
	 * @param filepath
	 * @param factory
	 * @throws Exception
	 */
	protected void fromText(String filepath, ITiledLayerFactory factory) throws Exception
	{
		if (factory == null)
			return;
		Scanner in = new Scanner(ClassLoader.getSystemResourceAsStream(filepath));
		String line = "";
		int totalElements, totalEntities, totalGroups;

		// Try to parse all the map data.
		try
		{
			_tileWidth = Integer.parseInt(uncomment(line = in.nextLine()));
			_tileHeight = Integer.parseInt(uncomment(line = in.nextLine()));
			_tilesWide = Integer.parseInt(uncomment(line = in.nextLine()));
			_tilesDown = Integer.parseInt(uncomment(line = in.nextLine()));
			totalElements = Integer.parseInt(uncomment(line = in.nextLine()));
			totalEntities = Integer.parseInt(uncomment(line = in.nextLine()));
			totalGroups = Integer.parseInt(uncomment(line = in.nextLine()));
		}
		catch (Exception e)
		{
			throw new Exception("Error parseing line <" + line + "> as integer. More information: " + e.getMessage());
		}
		// Create layer.
		_elements = new TiledElement[totalElements];
		_entities = new LinkedList<ITiledEntity>();
		_groups = new HashTable<LinkedList<String>>(totalGroups);
		TiledElement.setWidth(_tileWidth);
		TiledElement.setHeight(_tileHeight);

		setTexture(uncomment(line = in.nextLine()));

		int instances = 0;
		// Read each element and element instance data
		TiledElement element;
		TiledElementInstance elementInstance;
		for (int i = 1; i < totalElements; i++)
		{
			element = factory.createTile(uncomment(line = in.nextLine()));
			// If the tiled element returned is null throw an exception.
			if (element == null)
			{
				throw new NullPointerException("The TiledElement returned by the ITiledLayerFactory class is null. Line trying to parse: <" + line + ">. Aborting file parsing");
			}
			else if (element.tracksInstances())
			{
				// Get how many instances this element keeps track of.
				instances = Integer.parseInt(uncomment(line = in.nextLine()));
				// Read in each instance
				for (int j = 0; j < instances; j++)
				{
					elementInstance = factory.createTileInstance(uncomment(line = in.nextLine()));
					// If the element instance returned is null throw an exception.
					if (elementInstance == null) {
						throw new NullPointerException("The TiledElementInstance returned by the ITiledLayerFactory class is null. Line trying to parse: <" + line + ">. Aborting file parsing.");
					}

					element.addInstance(elementInstance);
				}
			}
			_elements[i] = element;
		}
		// Reads each game object
		Object o;
		for (int i = 0; i < totalEntities; i++)
		{
			o = factory.createObject(uncomment(line = in.nextLine()));
			// If the object returned is null then we can't properly load the map,
			// throw an exception
			if (o == null) {
				throw new NullPointerException("The Object returned by the ITiledLayerFactory class is null. Line trying to parse: <" + line + ">. Aborting file parsing");
			}
			
			if (o instanceof ITiledEntity) {
				addEntity((ITiledEntity)o);
			}
			else if (o instanceof Sprite) {
				addObject((IName)o);
			}
			else if (o instanceof Landscape) {
				addLandscape((Landscape)o);
			}
			else {
					throw new Exception("The Object returned by the ITiledLayerFactory class is not of type ITiledEntity, Sprite, or Landscape. Line trying to parse: <" + line + ">. Aborting file parsing.");
			}
		}
		// Read in map data
		int[][] data = new int[_tilesDown][_tilesWide];
		String[] row;
		for (int y = _tilesDown - 1; y >= 0; y--)
		{
			line = uncomment(in.nextLine());
			// Each line of the text file represents a row and each index is
			// separated by a comma
			row = line.split(",");
			// If the length of the row does not match how many tiles wide then
			// throw an exception
			if (row.length != _tilesWide) {
				throw new Exception("The row length of the row <" + line + "> does not match the width of the map defined. Map Width: " + _tilesWide + " Row Width: " + row.length);
			}
			
			for (int x = 0; x < _tilesWide; x++)
				data[y][x] = Integer.parseInt(row[x].trim());
		}
		// Set Layer Data
		_map = data;
		// Read in extra lines
		int extraLines = Integer.parseInt(uncomment(line = in.nextLine()));
		String[] lines = new String[extraLines];
		for (int i = 0; i < extraLines; i++)
			lines[i] = uncomment(line = in.nextLine());
		factory.interpretExtraLines(lines);
		// Finalize loading and tie up any loose ends
		factory.finalizeLoading(this);
		// Add to Camera as Observable
		Camera.getInstance().addObserver(this);

		in.close();
	}

	/**
	 * 
	 * @param line
	 * @return
	 */
	private String uncomment(String line)
	{
		if (line.lastIndexOf("//") == -1)
			return line;
		return line.substring(0, line.lastIndexOf("//")).trim();
	}

	/**
	 * 
	 * @param filepath
	 * @param factory
	 * @return
	 * @throws Exception
	 */
	public static TiledLayer fromTextFile(String filepath, ITiledLayerFactory factory) throws Exception
	{
		TiledLayer layer = new TiledLayer();
		layer.fromText(filepath, factory);
		return layer;
	}

}
