/************************************************\ 
 *               [AttachSprite]                 * 
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

package net.philsprojects.game.sprites;

import net.philsprojects.game.IAttach;
import net.philsprojects.game.ISocket;
import net.philsprojects.game.util.Math;
import net.philsprojects.game.util.Vector;

public class AttachSprite extends AnimatedSprite implements IAttach
{

	// The object with a location and angle that can be attached to.
	protected ISocket _socket;
	// If true then this attach sprite's angle move with the socket, if not then it stays the same.
	protected boolean _rotatesWith;

	/**
	 * Initializes an AttachSprite with its name, location, size, maximum number of animations, 
	 *      its angle, and its respect to the camera.
	 * 
	 * @param name => The unique name of this animated sprite.
	 * @param x => The x-coordinate in pixels of this animated sprite.
	 * @param y => The y-coordinate in pixels of this animated sprite.
	 * @param width => The width in pixels of this animated sprite.
	 * @param animations => The maximum numbers of animations this sprite can hold.
	 * @param angle => The angle or rotation of this animated sprite in degrees.
	 * @param respectToCamera => If true then this sprite has world positions and moves when the 
	 *           camera moves, if false then its location are the actual coordinates on the screen.
	 */
	public AttachSprite(String name, float x, float y, float width, float height, int animations, float angle, boolean respectToCamera)
	{
		super(name, x, y, width, height, animations, angle, respectToCamera);
	}

	/**
	 * Initializes an AttachSprite with its name, location, size, maximum number of animations, 
	 *      its angle, its respect to the camera, the socket its attached to, and whether it
	 *      rotates with the socket.
	 * 
	 * @param name => The unique name of this animated sprite.
	 * @param x => The x-coordinate in pixels of this animated sprite.
	 * @param y => The y-coordinate in pixels of this animated sprite.
	 * @param width => The width in pixels of this animated sprite.
	 * @param animations => The maximum numbers of animations this sprite can hold.
	 * @param angle => The angle or rotation of this animated sprite in degrees.
	 * @param respectToCamera => If true then this sprite has world positions and moves when the 
	 *           camera moves, if false then its location are the actual coordinates on the screen.
	 * @param socket => The ISocket this attach sprite is attached to.
	 * @param rotatesWith => If true then this sprite
	 */
	public AttachSprite(String name, float x, float y, float width, float height, int animations, float angle, boolean respectToCamera, ISocket socket, boolean rotatesWith)
	{
		super(name, x, y, width, height, animations, angle, respectToCamera);
		_socket = socket;
		_rotatesWith = rotatesWith;
	}


	public void attach(ISocket socket, float offsetX, float offsetY, float angleOffset, boolean rotatesWith)
	{
		_socket = socket;
		_location.set(offsetX, offsetY);
		_angle = angleOffset;
		_rotatesWith = rotatesWith;
	}    

	@Override
	public float getAngle()
	{
		if (_socket == null) {
			return _angle;
		}
		return (_rotatesWith ? _angle + _socket.getAngle() : _angle);
	}

	public float getAngleOffset()
	{
		return _angle;
	}



	@Override
	public Vector getLocation()
	{
		if (_socket == null) {
			return _location;
		}
		return Vector.add(_socket.getLocation(), (_rotatesWith ? Math.rotateVector(_location, _socket.getAngle()) : _location) );
	}

	public Vector getOffset()
	{
		return _location;
	}

	/**
	 * Gets whether this attach sprite rotates with the socket its attached to.
	 */
	public boolean getRotatesWith()
	{
		return _rotatesWith;
	}

	public ISocket getSocket()
	{
		return _socket;
	}

	/**
	 * 
	 * @param x =>
	 * @param y =>
	 * @see setLocation
	 */
	public void setOffset(float x, float y)
	{
		_location.set(x, y);
	}

	public void setRotatesWith(boolean rotatesWith)
	{
		_rotatesWith = rotatesWith;
	}

	public void setSocket(ISocket socket)
	{
		_socket = socket;
	}

}
