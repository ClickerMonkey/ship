package game;

import static game.GameConstants.*;
import net.philsprojects.game.ITiledLayerFactory;
import net.philsprojects.game.Landscape;
import net.philsprojects.game.TiledElement;
import net.philsprojects.game.TiledElementInstance;
import net.philsprojects.game.TiledLayer;
import net.philsprojects.game.util.BoundingBox;
import net.philsprojects.game.util.Color;
import net.philsprojects.game.util.Vector;
import game.objects.*;

/**
 * The class for loading a tiled layer from a text file.
 * 
 * @author Philip Diffenderfer
 */
public final class LevelLoader implements ITiledLayerFactory
{

    private String _source;
    private Vector _marioStart;
    
    /**
     * Default Constructor.
     */
    public LevelLoader()
    {

    }

    /**
     * Creates and returns an entity based on the string
     */
    public Object createObject(String source)
    {
	_source = source.trim();
	String name = cutString();
	if (name.equals("RedMushroom")) // X, Y, Direction (1 or -1)
	    return new RedMushroom(cutInt(), cutInt(), cutInt());
	if (name.equals("GreenMushroom")) // X, Y, Direction (1 or -1)
	    return new GreenMushroom(cutInt(), cutInt(), cutInt());
	if (name.equals("Star")) // X, Y
	    return new Star(cutInt(), cutInt(), cutInt());
	if (name.equals("Plant")) // X, Y
	    return new Plant(cutInt(), cutInt(), cutInt());
	if (name.equals("Goomba")) // X, Y, Direction (1 or -1)
	    return new Goomba(cutInt(), cutInt(), cutInt());
	if (name.equals("Flower")) // X, Y
	    return new Flower(cutInt(), cutInt());
	if (name.equals("IceBeet")) // X, Y
	    return new IceBeet(cutInt(), cutInt());
	if (name.equals("DropPlatform")) // X, Y
	    return new DropPlatform(cutInt(), cutInt());
	if (name.equals("Coin")) // X, Y
	    return new CoinPopup(cutInt(), cutInt());
	if (name.equals("Landscape")) // Tile, X, Y, Width, Height, xDamping, yDamping, spacing, The shade.
	    return new Landscape(Tiles.get(cutString()), cutInt(), cutInt(), cutInt(), cutInt(), cutFloat(), cutFloat(), cutInt(), (Color)createObject(_source));
	if (name.equals("Color")) // Red, Green, Blue, Alpha
	    return new Color(cutFloat(), cutFloat(), cutFloat(), cutFloat());
	return null;
    }

    /**
     * Creates and returns a map tile based on the string
     */
    @SuppressWarnings("unchecked")
    public TiledElement createTile(String source)
    {
	_source = source.trim();
	//Ground element
	if (_source.startsWith("."))
	{
	    _source = _source.substring(1);
	    boolean top, right, left;
	    top = (_source.charAt(0) == 'G');
	    right = (_source.charAt(1) == 'A' && _source.charAt(2) == 'D');
	    left = (_source.charAt(2) == 'D' && _source.charAt(3) == 'A');
	    return new TiledElement(Tiles.get(_source), top, right, false, left, true, true, 1f);
	}
	//Other elements
	else
	{
	    try
	    {
		Class[] newType = {};
		Object[] newData = {};
		Class tiledElement = Class.forName("game.objects." + _source, true, Thread.currentThread().getContextClassLoader());
		return (TiledElement)tiledElement.getConstructor(newType).newInstance(newData);
	    }
	    catch (Exception e)
	    {
		e.printStackTrace();
	    }
	}
	return null;
    }

    /**
     * Creates and returns a map tile's instance from a string.
     */
    public TiledElementInstance createTileInstance(String source)
    {
	_source = source.trim();
	String name = cutString();
	if (name.equals("PrizeBox"))
	{   // X, Y, Object that pops out of the PrizeBox.
	    return new PrizeBox.Instance(cutInt(), cutInt(), createObject(_source)); 
	}
	else if (name.equals("BrickBlock"))
	{   // X, Y, Number of coins in this instance.
	    return new BrickBlock.Instance(cutInt(), cutInt(), cutInt());
	}
	else if (name.equals("MysteryBox"))
	{
	    // X, Y, What this Box turns items into.
	    int[] v = {cutInt(), cutInt(), cutInt()};
	    String[] vectors = _source.split(",");
	    Vector[] boxes = new Vector[vectors.length];
	    // Set the locations of every Tiled Element to change.
	    for (int i = 0; i < vectors.length; i++)
	    {
		_source = vectors[i];
		boxes[i] = new Vector(cutInt(" "), cutInt(" "));
	    }
	    return new MysteryBox.Instance(v[0], v[1], v[2], boxes);
	}
	return null;
    }

    
    /**
     * Cuts off and returns the first integer in the <code>_source</code> string.
     */
    private int cutInt()
    {
	return cutInt(",");
    }

    /**
     * Cuts off and returns the first integer before this split in the <code>_source</code> string.
     */
    private int cutInt(String split)
    {
	int cut = Integer.parseInt((_source.indexOf(split) == -1 ? _source : _source.substring(0, _source.indexOf(split))));
	_source = _source.substring(_source.indexOf(split) + 1);
	return cut;
    }
    
    /**
     * Cuts off and returns the first float in the <code>_source</code> string.
     */
    private float cutFloat()
    {
	return cutFloat(",");
    }

    /**
     * Cuts off and returns the first float before this split in the <code>_source</code> string.
     */
    private float cutFloat(String split)
    {
	float cut = Float.parseFloat((_source.indexOf(split) == -1 ? _source : _source.substring(0, _source.indexOf(split))));
	_source = _source.substring(_source.indexOf(split) + 1);
	return cut;
    }

    /**
     * Cuts off and returns the first String in the <code>_source</code> string.
     */
    private String cutString()
    {
	return cutString(",");
    }

    /**
     * Cuts off and returns the first String before this split in the <code>_source</code> string.
     */
    private String cutString(String split)
    {
	String cut = (_source.indexOf(split) == -1 ? _source : _source.substring(0, _source.indexOf(split)));
	_source = _source.substring(_source.indexOf(split) + 1);
	return cut;
    }

    
    /**
     * This is called once all the data is done loading.
     */
    public void finalizeLoading(TiledLayer layer)
    {
	// Set the world's boundaries and then set the groups collision allowances.
	layer.setWorldBoundaries(new BoundingBox(0f, layer.getTileHeight() * layer.getTotalTilesDown(), layer.getTileWidth() * layer.getTotalTilesWide(), 0f));
	layer.setInitialGroups(GROUP_MARIO, GROUP_MARIO_PROJECTILE, GROUP_ITEM, GROUP_ENEMY, GROUP_PLATFORM);
	layer.addGroupAllowance(GROUP_MARIO, GROUP_MARIO_PROJECTILE);
	layer.addGroupAllowance(GROUP_MARIO_PROJECTILE, GROUP_MARIO);
	layer.addGroupAllowance(GROUP_ITEM, GROUP_MARIO_PROJECTILE);
	layer.addGroupAllowance(GROUP_MARIO_PROJECTILE, GROUP_ITEM);
	layer.addGroupAllowance(GROUP_ITEM, GROUP_ENEMY);
	layer.addGroupAllowance(GROUP_ENEMY, GROUP_ITEM);
    }

    /**
     * The last couple lines defines things like mario's initial position
     */
    public void interpretExtraLines(String[] line)
    {
	_source = line[0];
	_marioStart = new Vector(cutInt(), cutInt());
    }

    //Access Methods
    
    /**
     * Returns the maps starting position for mario.
     */
    public Vector getMarioStart()
    {
	return _marioStart;
    }
    
    
}
