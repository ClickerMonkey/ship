/************************************************\ 
 *                [TileLibrary]                 * 
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

package net.philsprojects.game;

import net.philsprojects.game.sprites.SpriteTileAnimated;
import net.philsprojects.game.sprites.SpriteTileFramed;
import net.philsprojects.game.sprites.SpriteTileStatic;
import net.philsprojects.game.util.Iterator;
import net.philsprojects.game.util.NameHashTable;
import net.philsprojects.game.util.Rectangle;

/**
 * A Library of each ISpriteTile in the game. A Single instance of this ensures that all classes have access to the same tile set.
 * 
 * @author Philip Diffenderfer
 */
public class TileLibrary extends NameHashTable<ISpriteTile>
{

	/**
	 * The only instance of this class.
	 */
	private static TileLibrary _instance = null;

	/**
	 * Returns the only instance of this class.
	 */
	public static TileLibrary getInstance()
	{
		return _instance;
	}

	/**
	 * If this TileLibrary has not been initialized already then it sets the maximum number of SpriteTiles allowed to be added.
	 * 
	 * @param maximumTiles => The maximum number of SpriteTiles this library can hold.
	 */
	public static void initialize(int maximumTiles)
	{
		if (_instance != null)
			return;
		_instance = new TileLibrary(maximumTiles);
	}

	/**
	 * Private constructor to set the maximum size.
	 * 
	 * @param maximumSize => The maximum number of SpriteTiles this library can hold.
	 */
	private TileLibrary(int maximumSize)
	{
		super(maximumSize);
	}

	/**
	 * Adds a SpriteTileStatic to the library.
	 * 
	 * @param name => The reference name of this tile.
	 * @param texture => The texture of this tile.
	 * @param source => The source rectangle of the frame of this tile.
	 */
	public boolean add(String name, String texture, Rectangle source)
	{
		return add(new SpriteTileStatic(name, texture, source));
	}

	/**
	 * Adds a SpriteTileStatic to the library.
	 * 
	 * @param name => The reference name of this tile.
	 * @param texture => The texture of this tile.
	 * @param x => The offset in pixels from the left of the texture to the frame.
	 * @param y => The offset in pixels from the top of the texture to the frame.
	 * @param width => The width in pixels of the frame.
	 * @param height => The height in pixels of the frame.
	 */
	public boolean add(String name, String texture, int x, int y, int width, int height)
	{
		return add(new SpriteTileStatic(name, texture, x, y, width, height));
	}

	/**
	 * Adds a SpriteTileAnimated to the library.
	 * 
	 * @param name => The reference name of this tile.
	 * @param texture => The texture of this tile.
	 * @param type => The animation type of this tile. Either (ONCE_FORWARD, LOOP_FORWARD, ONCE_BACKWARD, LOOP_BACKWARD or PINGPONG)
	 * @param columns => How many columns wide this grid of frames is.
	 * @param totalFrames => How many total frames there are in this grid.
	 * @param frameWidth => The width of the source frame in pixels.
	 * @param frameHeight => The height of the source frame in pixels.
	 * @param animationSpeed => The interval in seconds between each frame change.
	 */
	public boolean add(String name, String texture, int type, int columns, int totalFrames, int frameWidth, int frameHeight, float animationSpeed)
	{
		return add(new SpriteTileAnimated(name, texture, type, columns, totalFrames, frameWidth, frameHeight, animationSpeed));
	}

	/**
	 * Adds a SpriteTileAnimated to the library.
	 * 
	 * @param name => The reference name of this tile.
	 * @param texture => The texture of this tile.
	 * @param type => The animation type of this tile. Either (ONCE_FORWARD, LOOP_FORWARD, ONCE_BACKWARD, LOOP_BACKWARD or PINGPONG)
	 * @param columns => How many columns wide this grid of frames is.
	 * @param totalFrames => How many total frames there are in this grid.
	 * @param frameWidth => The width of the source frame in pixels.
	 * @param frameHeight => The height of the source frame in pixels.
	 * @param offsetX => The offset in pixels from the left of the texture this grid starts at.
	 * @param offsetY => The offset in pixels from the top of the texture this grid starts at.
	 * @param animationSpeed => The interval in seconds between each frame change.
	 */
	public boolean add(String name, String texture, int type, int columns, int totalFrames, int frameWidth, int frameHeight, int offsetX, int offsetY, float animationSpeed)
	{
		return add(new SpriteTileAnimated(name, texture, type, columns, totalFrames, frameWidth, frameHeight, offsetX, offsetY, animationSpeed));
	}

	/**
	 * Adds a SpriteTileFramed to the library.
	 * 
	 * @param name => The reference name of this tile.
	 * @param texture => The texture of this tile.
	 * @param type => The animation type of this tile. Either (ONCE_FORWARD, LOOP_FORWARD, ONCE_BACKWARD, LOOP_BACKWARD or PINGPONG)
	 * @param columns => How many columns wide this grid of frames is.
	 * @param frameWidth => The width of the source frame in pixels.
	 * @param frameHeight => The height of the source frame in pixels.
	 * @param animationSpeed => The interval in seconds between each frame change.
	 * @param frames => The indices of each frame to visit in this tile.
	 * @return
	 */
	public boolean add(String name, String texture, int type, int columns, int frameWidth, int frameHeight, float animationSpeed, int[] frames)
	{
		return add(new SpriteTileFramed(name, texture, type, columns, frameWidth, frameHeight, animationSpeed, frames));
	}

	/**
	 * Adds a SpriteTileFramed to the library.
	 * 
	 * @param name => The reference name of this tile.
	 * @param texture => The texture of this tile.
	 * @param type => The animation type of this tile. Either (ONCE_FORWARD, LOOP_FORWARD, ONCE_BACKWARD, LOOP_BACKWARD or PINGPONG)
	 * @param columns => How many columns wide this grid of frames is.
	 * @param frameWidth => The width of the source frame in pixels.
	 * @param frameHeight => The height of the source frame in pixels.
	 * @param offsetX => The offset in pixels from the left of the texture this grid starts at.
	 * @param offsetY => The offset in pixels from the top of the texture this grid starts at.
	 * @param animationSpeed => The interval in seconds between each frame change.
	 * @param frames => The indices of each frame to visit in this tile.
	 * @return
	 */
	public boolean add(String name, String texture, int type, int columns, int frameWidth, int frameHeight, int offsetX, int offsetY, float animationSpeed, int[] frames)
	{
		return add(new SpriteTileFramed(name, texture, type, columns, frameWidth, frameHeight, offsetX, offsetY, animationSpeed, frames));
	}



	/**
	 * Gets a clone of the ISpriteTile with this name.
	 * 
	 * @param name => The name of the ISpriteTile to get a clone of.
	 */
	public ISpriteTile getClone(String name)
	{
		return super.get(name).getClone();
	}

	/**
	 * Dispose of all ISpriteTile's in this library.
	 */
	public void dispose()
	{
		Iterator<ISpriteTile> iterator = getIterator();
		while (iterator.hasNext())
			iterator.getNext().dispose();
		iterator = null;
	}

}
