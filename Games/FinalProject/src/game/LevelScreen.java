package game;

import static game.GameConstants.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import net.philsprojects.game.*;
import net.philsprojects.game.sprites.Sprite;
import game.mario.Mario;

/**
 * The Game Screen for loading and playing a level.
 * 
 * @author Philip Diffenderfer
 */
public class LevelScreen extends Screen
{
    private Level _level;
    private Text _printText;
    private Sprite _coin;
    private Sprite _1UP;
    private ScreenTransition _transition = null;
    private boolean _paused = false;
    
    /**
     * Initialize setting this screens name.
     */
    public LevelScreen()
    {
	super(SCREEN_LEVEL);
    }

    /**
     * Load the map, set the world boundaries, setup the text for the screen, 
     *    create the 2 GUI sprites, Play the level's music, clear Mario's data.
     */
    @Override
    public void load()
    {
	Level.openMap("maps/Level1.map", _width, _height);
	_level = Level.getInstance();
	Camera.getInstance().setBounds(_level.getWorldBoundaries());
	_printText = new Text(TILESHEET_TEXTURE, "0123456789xUPC", 0, 244, 32, 37, 4, 24, 28);
	_coin = new Sprite("Coin", 6, _height - 34, 24, 24, Tiles.get(COIN_ANIM), false);
	_1UP = new Sprite("1UP", 200, _height - 42, 42, 42, Tiles.get(MUSHROOM_GREEN), false);
	SoundLibrary.getInstance().loop(MUSIC_LEVEL);
	Mario.clearData();
    }

    /**
     * Updates the game if not paused.
     */
    public void update(float deltatime)
    {
	if (!_paused)
	{
	    _level.update(deltatime);
	}
	_coin.update(deltatime);
	_transition = null;
    }

    /**
     * Draws the level then the GUI text and sprites.
     */
    public void draw()
    {
	final GraphicsLibrary g = GraphicsLibrary.getInstance();
	_level.draw(g);
	_printText.draw("x" + Mario.getTotalCoins(), 30, _height - 36, false);
	_printText.draw("C 000", _width - 120, _height - 36, false);
	_printText.draw(Mario.getLives() + "UP", 240, _height - 36, false);
	_printText.draw(Mario.getPoints() + "", 400, _height - 36, false);
	_coin.draw();
	_1UP.draw();
    }
    
    /**
     * Draw the transition effect.
     */
    @Override
    public void drawEntrance()
    {
	draw();
	if (_transition != null)
	    _transition.draw();
    }

    /**
     * Draw the transition effect.
     */
    @Override
    public void drawExit()
    {
	draw();
	if (_transition != null)
	    _transition.draw();
    }

    /**
     * Updates the transition.
     */
    @Override
    public void updateEntrance(float deltatime)
    {
	if (_transition == null)
	{
	    _transition = new ScreenTransition(getEntranceDuration(), _width / 2, _height / 2, ScreenTransition.GROW, _width, _height);
	}
	_transition.update(deltatime);
    }

    /**
     * Updates the transition.
     */
    @Override
    public void updateExit(float deltatime)
    {
	if (_transition == null)
	{
	    _transition = new ScreenTransition(getExitDuration(), _width / 2, _height / 2, ScreenTransition.SHRINK, _width, _height);
	}
	_transition.update(deltatime);
    }
    
    /**
     * Disposes the Levels data, stops the music, clears the Camera's observers.
     */
    @Override
    public void dispose()
    {
	_level.disposeData();
	_printText = null;
	_coin = null;
	SoundLibrary.getInstance().stop(MUSIC_LEVEL);
	Camera.getInstance().clearObservers();
    }

    /**
     * Key Presses:<br>
     * P     = Pause<br>
     * Right = Move Mario Right<br>
     * Left  = Move Mario Left<br>
     * Up    = Mario Jump<br>
     * Down  = Mario Throw<br>
     * R     = Reset Mario to start<br>
     */
    @Override
    public void keyPressed(KeyEvent e)
    {
	if (e.getKeyCode() == KeyEvent.VK_P)
	{
	    _paused = !_paused;
	    SoundLibrary.getInstance().play(SOUND_PAUSE);
	}
	if (_paused)
	    return;
	switch (e.getKeyCode())
	{
	case KeyEvent.VK_RIGHT:
	    _level.getMario().moveRight();
	    break;
	case KeyEvent.VK_LEFT:
	    _level.getMario().moveLeft();
	    break;
	case KeyEvent.VK_UP:
	    _level.getMario().jump(true);
	    break;
	case KeyEvent.VK_DOWN:
	    _level.getMario().doSpecial();
	    break;
	case KeyEvent.VK_R:
	    _level.resetMario();
	    break;
	}
    }

    /**
     * When the player lets go of any movement keys updates mario's state.
     */
    @Override
    public void keyReleased(KeyEvent e)
    {
	switch (e.getKeyCode())
	{
	case KeyEvent.VK_RIGHT:
	    _level.getMario().stop();
	    break;
	case KeyEvent.VK_LEFT:
	    _level.getMario().stop();
	    break;
	case KeyEvent.VK_UP:
	    _level.getMario().jump(false);
	    _level.getMario().fall(true);
	    break;
	}
    }

    @Override
    public void mouseMoved(MouseEvent e)
    {

    }

    @Override
    public void mousePressed(MouseEvent e)
    {

    }

    @Override
    public void mouseReleased(MouseEvent e)
    {

    }

    @Override
    public float getEntranceDuration()
    {
	return 0.5f;
    }

    @Override
    public float getExitDuration()
    {
	return 0.5f;
    }

}
