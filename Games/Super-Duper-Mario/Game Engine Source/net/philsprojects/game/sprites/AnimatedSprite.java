/**************************************************\ 
 *            ______ ___    __   _    _____       * 
 *           / ____//   |  /  | / |  / ___/       *
 *          / /___ / /| | /   |/  | / __/         *
 *         / /_| // __  |/ /|  /| |/ /__          *
 *        /_____//_/  |_|_/ |_/ |_|\___/          *
 *     _____ __   _  ______ ______ __   _  _____  *
 *    / ___//  | / // ____//_  __//  | / // ___/  *
 *   / __/ / | |/ // /___   / /  / | |/ // __/    *
 *  / /__ / /|   // /_| /__/ /_ / /|   // /__     *
 *  \___//_/ |__//_____//_____//_/ |__/ \___/     *
 *                                                *
 * Author: Philip Diffenderfer                    *
 *  Class: engine.sprites.AnimatedSprite          *
 *                                                *
\**************************************************/

package net.philsprojects.game.sprites;

import net.philsprojects.game.ISpriteTile;
import net.philsprojects.game.TextureLibrary;
import net.philsprojects.game.util.Iterator;
import net.philsprojects.game.util.NameHashTable;

/**
 * A Sprite with several animations. It can add, play, and remove animations.
 * 
 * @author Philip Diffenderfer
 */
public class AnimatedSprite extends Sprite
{

	// The HashTable of animations as ISpriteTile.
	protected NameHashTable<ISpriteTile> _animations = null;

	/**
	 * Initializes an AnimatedSprite with its name, location, size, and maximum number of animations.
	 * 
	 * @param name => The unique name of this animated sprite.
	 * @param x => The x-coordinate in pixels of this animated sprite.
	 * @param y => The y-coordinate in pixels of this animated sprite.
	 * @param width => The width in pixels of this animated sprite.
	 * @param animations => The maximum numbers of animations this sprite can hold.
	 */
	public AnimatedSprite(String name, float x, float y, float width, float height, int animations)
	{
		this(name, x, y, width, height, animations, 0f, true);
	}

	/**
	 * Initializes an AnimatedSprite with its name, location, size, maximum number of animations,
	 * and its respect to the camera.
	 * 
	 * @param name => The unique name of this animated sprite.
	 * @param x => The x-coordinate in pixels of this animated sprite.
	 * @param y => The y-coordinate in pixels of this animated sprite.
	 * @param width => The width in pixels of this animated sprite.
	 * @param animations => The maximum numbers of animations this sprite can hold.
	 * @param respectToCamera => If true then this sprite has world positions and moves when the 
	 *           camera moves, if false then its location are the actual coordinates on the screen.
	 */
	public AnimatedSprite(String name, float x, float y, float width, float height, int animations, boolean respectToCamera)
	{
		this(name, x, y, width, height, animations, 0f, respectToCamera);
	}   

	/**
	 * Initializes an AnimatedSprite with its name, location, size, maximum number of animations, 
	 * and its angle.
	 * 
	 * @param name => The unique name of this animated sprite.
	 * @param x => The x-coordinate in pixels of this animated sprite.
	 * @param y => The y-coordinate in pixels of this animated sprite.
	 * @param width => The width in pixels of this animated sprite.
	 * @param animations => The maximum numbers of animations this sprite can hold.
	 * @param angle => The angle or rotation of this animated sprite in degrees.
	 */
	public AnimatedSprite(String name, float x, float y, float width, float height, int animations, float angle)
	{
		this(name, x, y, width, height, animations, angle, true);
	}

	/**
	 * Initializes an AnimatedSprite with its name, location, size, maximum number of animations, 
	 * its angle, and its respect to the camera.
	 * 
	 * @param name => The unique name of this animated sprite.
	 * @param x => The x-coordinate in pixels of this animated sprite.
	 * @param y => The y-coordinate in pixels of this animated sprite.
	 * @param width => The width in pixels of this animated sprite.
	 * @param animations => The maximum numbers of animations this sprite can hold.
	 * @param angle => The angle or rotation of this animated sprite in degrees.
	 * @param respectToCamera => If true then this sprite has world positions and moves when the 
	 *           camera moves, if false then its location are the actual coordinates
	 *           on the screen.
	 */
	public AnimatedSprite(String name, float x, float y, float width, float height, int animations, float angle, boolean respectToCamera)
	{
		super(name, x, y, width, height, null, angle, respectToCamera);
		_animations = new NameHashTable<ISpriteTile>(animations);
	}

	/**
	 * Plays an animation based on the animation name. It does not restart the animation if it's 
	 * already playing.
	 * 
	 * @param name => The name of the Animation to play, case sensitive.
	 * @return True if this animation was played successful, False if it doesn't exist or is 
	 *           already playing.
	 */
	public boolean playAnimation(String name)
	{
		return playAnimation(name, false);
	}

	/**
	 * Plays an animation based on the animation name. It can restart the animation if its already
	 * playing or just ignore it.

	 * @param name => The name of the Animation to play, case sensitive.
	 * @param interruptIfPlaying => If true then if this sprite is already playing the animation it
	 *           will restart, if false it will not restart.
	 * @return True if this animation was played successful, False if it doesn't exist or is already 
	 *           playing.
	 */
	public boolean playAnimation(String name, boolean interruptIfPlaying)
	{
		//Don't play an already playing animation
		if (_tile.getName().equals(name) && !interruptIfPlaying)
			return false;
		//Get the animation if any
		ISpriteTile animation = _animations.get(name);
		//If it doesn't exist then animation can't be played
		if (animation == null)
			return false;
		//Set the current animation and reset its values
		_tile = animation;
		_tile.reset();
		_enabled = true;
		return true;
	}

	/**
	 * Adds an animation to a HashTable of animations.
	 * 
	 * @param animation => The animation to add.
	 * @return True if an animation with this name has not been added already, otherwise false if 
	 *           it exists or the animation trying to add is null.
	 */
	public boolean addAnimation(ISpriteTile animation)
	{
		if (animation == null)
			return false;
		_tile = animation;
		return _animations.add(animation);
	}

	/**
	 * Removes an animation from the HashTable of animations.
	 * 
	 * @param name => The name of the animation to remove, case sensitive.
	 * @return True if the animation was removed, false if not.
	 */
	public boolean removeAnimation(String name)
	{
		return (_animations.remove(name) != null);
	}

	/**
	 * Clears all animations from the HashTable and sets the current animation to null.
	 */
	public void clearAnimations()
	{
		_tile = null;
		_animations.clear();
	}

	/**
	 * Gets whether the current animation playing has this name.
	 * 
	 * @param name => The name of the animation to check.
	 * @return True if the current animation has this name, false if it doesn't.
	 */
	public boolean isPlaying(String name)
	{
		if (_tile == null || name == null)
			return false;
		return (_tile.getName().equals(name));
	}

	/**
	 * Gets an animation from the HashTable based on a name.
	 * 
	 * @param name => The name of the animation to check.
	 * @return A reference to the Animation with this name if any, if not then it returns null.
	 */
	public ISpriteTile getAnimation(String name)
	{
		return _animations.get(name);
	}

	/**
	 * Changes each animations texture to a new one. Used mainly for animated sprites with 
	 * different looks but the same animation sequences.
	 * 
	 * @param texture => The name of the new texture to set it to.
	 * @return True if they all were successfully changed, false if the texture doesn't even 
	 *      exist in the TextureLibrary.
	 */
	public boolean changeTexture(String texture)
	{
		if (!TextureLibrary.getInstance().exists(texture))
			return false;
		Iterator<ISpriteTile> i = _animations.getIterator();
		while (i.hasNext())
			i.getNext().setTexture(texture);
		return true;
	}

}
