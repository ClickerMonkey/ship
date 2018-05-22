package net.philsprojects.game;

/**
 * @author Philip Diffenderfer
 */
public interface ICameraObserver
{

	/**
	 * @param newX
	 * @param newY
	 * @param newWidth
	 * @param newHeight
	 */
	public void cameraChanged(int newX, int newY, int newWidth, int newHeight);

}
