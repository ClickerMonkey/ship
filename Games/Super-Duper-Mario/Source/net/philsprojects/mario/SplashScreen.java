package net.philsprojects.mario;

import static net.philsprojects.mario.GameConstants.*;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import net.philsprojects.game.*;
import net.philsprojects.game.sprites.*;
import net.philsprojects.game.util.*;

/**
 * The first Game Screen with the splash screen.
 * 
 * @author Philip Diffenderfer
 */
public class SplashScreen extends Screen
{

	private Quad _back;
	private Sprite[] _coins;
	private ScreenTransition _transition = null;

	/**
	 * Initialize setting this screens name.
	 */
	public SplashScreen()
	{
		super(SCREEN_SPLASH);
	}

	/**
	 * Loads the splash screen and plays the spash screen music.
	 */
	@Override
	public void load()
	{
		_back = new Quad(SCREEN_SPLASH_TEXTURE, 0, 0, _width, _height, Color.white());
		_coins = new Sprite[4];
		_coins[0] = new Sprite("UL", 5, _height - 45, 40, 40, Tiles.get(COIN_ANIM), false);
		_coins[1] = new Sprite("UR", _width - 45, _height - 45, 40, 40, Tiles.get(COIN_ANIM), false);
		_coins[2] = new Sprite("BR", _width - 45, 5, 40, 40, Tiles.get(COIN_ANIM), false);
		_coins[3] = new Sprite("BL", 5, 5, 40, 40, Tiles.get(COIN_ANIM), false);
		SoundLibrary.getInstance().loop(MUSIC_SPLASH);
		Camera.getInstance().setLocation(0, 0);
	}

	/**
	 * Updates the coins on the splash screen.
	 */
	public void update(float deltatime)
	{
		for (int i = 0; i < 4; i++)
			_coins[i].update(deltatime);
	}

	/**
	 * Draws the background splash and the 4 coins.
	 */
	public void draw()
	{
		_back.draw();
		for (int i = 0; i < 4; i++)
			_coins[i].draw();
	}

	/**
	 * Draws the transition.
	 */
	@Override
	public void drawEntrance()
	{
		draw();
		if (_transition != null)
			_transition.draw();
	}

	/**
	 * Draws the transition.
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
	 * Disposes the splash screens.
	 */
	@Override
	public void dispose()
	{
		_back = null;
		_coins = null;
		SoundLibrary.getInstance().stop(MUSIC_SPLASH);
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		if (e.getKeyCode() == KeyEvent.VK_ENTER)
		{
			ScreenManager.getInstance().setScreen(SCREEN_LEVEL);
		}
	}

	@Override
	public void keyReleased(KeyEvent e)
	{

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
