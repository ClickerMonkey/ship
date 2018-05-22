package net.philsprojects.game;

import net.philsprojects.game.util.Vector;

public interface IAttach
{

	public float getAngle();

	public float getAngleOffset();

	public Vector getOffset();

	public Vector getLocation();

	public ISocket getSocket();

	public boolean getRotatesWith();

	public void setAngle(float angle);

	public void setOffset(float x, float y);

	public void setSocket(ISocket socket);

	public void setRotatesWith(boolean rotatesWith);

	public void attach(ISocket socket, float offsetX, float offsetY, float angleOffset, boolean rotatesWith);

}
