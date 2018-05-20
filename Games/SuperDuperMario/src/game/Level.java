package game;

import static game.GameConstants.MUSIC_LEVEL;
import net.philsprojects.game.Camera;
import net.philsprojects.game.GraphicsLibrary;
import net.philsprojects.game.ITiledEntity;
import net.philsprojects.game.SoundLibrary;
import net.philsprojects.game.TiledLayer;
import net.philsprojects.game.util.BoundingBox;
import net.philsprojects.game.util.Color;
import net.philsprojects.game.util.Iterator;
import net.philsprojects.game.util.LinkedList;
import net.philsprojects.game.util.Vector;
import game.objects.GameEffect;
import game.objects.Plant;
import game.mario.*;

/**
 * The current level the player is playing.
 * 
 * @author Philip Diffenderfer
 */
public class Level extends TiledLayer
{
    
    //The single instance of this Level
    private static Level _instance;
    
    /**
     * Returns the single instance of this Level.
     */
    public static Level getInstance()
    {
	return _instance;
    }  
    
    /**
     * Opens the map at a certain file location.
     */
    public static void openMap(String filepath, int viewportWidth, int viewportHeight)
    {
	try
	{
	    //Initializes the Level, plants, and the Level Loader.
	    _instance = new Level();
	    _instance._plants = new LinkedList<Plant>();
	    _instance._loader = new LevelLoader();
	    // Reads a Level from the filepath specified using the LevelLoader
	    _instance.fromText(filepath, _instance._loader);
	    // Set the Levels view size and then load the map data.
	    _instance.VIEW_WIDTH = viewportWidth;
	    _instance.VIEW_HEIGHT = viewportHeight;
	    _instance.loadMapData();
	}
	catch (Exception e)
	{
	    e.printStackTrace();
	}
    }
    
    
    private int VIEW_WIDTH = 0, VIEW_HEIGHT = 0;
    // Loads and interprets data from a text file into a Level
    private LevelLoader _loader;
    // The list of currently living Game Effects.
    private LinkedList<GameEffect> _effects;
    // The list of Plants used for drawing them separately.
    private LinkedList<Plant> _plants;
    // The padding around the screen that determines when to scroll based on Mario's position.
    private BoundingBox _scrollPadding;
    // Itsa Me Mario!
    private Mario _mario = null;
    
    /**
     * Initializes the super classes' data items.
     */
    public Level()
    {
	super();
    }
    
    /**
     * Initializes the Game Effects, Scroll padding, then Mario at the start of the level.
     */
    private void loadMapData()
    {
	// Initialize Game Effects
	_effects = new LinkedList<GameEffect>();
	// Initialize Scrolling padding
	_scrollPadding = new BoundingBox(383f, VIEW_HEIGHT - 200, 385f, 200f);
	// Initialize Mario
	_mario = new Mario((int)_loader.getMarioStart().x, (int)_loader.getMarioStart().y);
	addEntity(_mario);
    }
    
    /**
     * Removes any current game effects then resets Mario's position to start.
     */
    public void resetMario()
    {
	_effects.clear();
	_mario.reset((int)_loader.getMarioStart().x, (int)_loader.getMarioStart().y);
	SoundLibrary.getInstance().loop(MUSIC_LEVEL);
    }
  
    /**
     * Adds this special effect to the game.
     */
    public void addEffect(GameEffect system)
    {
	_effects.add(system);
    }
    
    /**
     * Draws every part of this Level. Renders in this order:<br>
     * #1. Draw Sky<br>
     * #2. Draw Clouds and Towers<br>
     * #3. Draw Plants<br>
     * #4. Draw TiledLayer<br>
     * #5. Draw Entities<br>
     * #6. Draw Objects<br>
     * #7. Draw Game Effects<br>
     * #8. Draw Mario<br>
     */
    @Override
    public void draw(GraphicsLibrary g)
    {
	// Draw Background Gradient
	g.drawGradient(0, 0, VIEW_WIDTH, VIEW_HEIGHT, new Color(0, 100, 254), new Color(192, 243, 246));
	// Draw landscapes
	super.drawLandscapes(g);
	// Draw Plants
	LinkedList<Plant>.Node first = _plants.getFirstNode();
	LinkedList<Plant>.Node next = null;
	while (first != null)
	{
	    next = first._next;
	    // If the plant is enabled draw it, if not then remove it.
	    if (first._element.isEnabled())
		first._element.draw();
	    else
		_plants.remove(first);
	    first = next;
	}
	// Draw TiledLayer, Entities, and Sprites
	super.draw(g);
	super.drawEntities(g);
	super.drawSprites(g);
	// Draw GameEffects
	Iterator<GameEffect> iter = _effects.getIterator();
	for (GameEffect e = iter.getNext(); e != null; e = iter.getNext())
	    if (!e.isUserDrawn())
		e.draw();
	// Draw Mario
	_mario.draw();
	g.clearSource();
    }
    
    /**
     * Updates every part of this Level, The GameEffects, the tiled layer, then the scrolling.
     */
    @Override
    public void update(float deltatime)
    {
	// Update GameEffects
	updateEffects(deltatime);
	// Update TiledLayer
	super.update(deltatime);
	// Update camera based on scrolling bounds and camera bounds
	updateScroll();
    }
    
    /**
     * Updates the GameEffects currently in this Level.
     */
    public void updateEffects(float deltatime)
    {
	LinkedList<GameEffect>.Node effect = _effects.getFirstNode();
	LinkedList<GameEffect>.Node next = null;
	while (effect != null)
	{
	    next = effect._next;
	    effect._element.update(deltatime);
	    // If the effects is dead and has no more particles remove it.
	    if (!effect._element.isEnabled())
		_effects.remove(effect);
	    effect = next;
	}
    }
    
    /**
     * Updates the Camera's location based on Mario's location and the Scrollpadding bounds.
     */
    public void updateScroll()
    {
	Camera c = Camera.getInstance();
	Vector actual = Vector.subtract(_mario.getLocation(), c.getLocation());
	if (actual.x > _scrollPadding.getRight())
	    c.setLocation(c.getX() + (int)(actual.x - _scrollPadding.getRight()), c.getY());
	if (actual.x < _scrollPadding.getLeft())
	    c.setLocation(c.getX() - (int)(_scrollPadding.getLeft() - actual.x), c.getY());
	if (actual.y > _scrollPadding.getTop())
	    c.setLocation(c.getX(), c.getY() + (int)(actual.y - _scrollPadding.getTop()));
	if (actual.y < _scrollPadding.getBottom())
	    c.setLocation(c.getX(), c.getY() - (int)(_scrollPadding.getBottom() - actual.y));
    }
    
    /**
     * Overrides the TiledLayer addEntity so that every time a plant is added we add it to
     *    our plant list.
     */
    @Override
    public void addEntity(ITiledEntity entity)
    {
	if (entity instanceof Plant)
	    _plants.add((Plant)entity);
	super.addEntity(entity);
    }
    
    /**
     * Disposes of this Levels data.
     */
    public void disposeData()
    {
	_effects.clear();
	_plants.clear();
	_effects = null;
	_plants = null;
	_loader = null;
    }
    
    /**
     * Returns the Level's Mario.
     */
    public Mario getMario()
    {
	return _mario;
    }

}
