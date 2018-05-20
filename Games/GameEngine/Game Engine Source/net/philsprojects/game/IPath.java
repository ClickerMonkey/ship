package net.philsprojects.game;

public interface IPath extends IName, IUpdate, ISocket, IClone<IPath>
{

	public void start();

	// Starts at beginning and goes speed the whole way till the end
	public void startSpeed(float speed);

	// Go from beginning to end in duration amount of seconds
	public void startDuration(float duration);

	public void reset();

	public float getSpeed();

	public float getLength();

	public boolean isMoving();

}
