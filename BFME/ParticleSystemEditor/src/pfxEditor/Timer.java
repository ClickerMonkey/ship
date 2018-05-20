package pfxEditor;
/**
 * Used to calculate an estimated frame rate. <br>
 * <br>
 * Frame rate is calculated 'updates' per second. <br>
 * <br>
 * Also calculates delta-time, the difference in seconds since the last update()
 * method call. <br>
 * 
 * @author Philip Diffenderfer
 */
public class Timer {

	/** Time interval in seconds for how often to update frame rate. */
	private double updateInterval = 0.0f;

	/** The starting or last time the update() method was called (in seconds). */
	private double starting = 0.0f;
	/** The ending or current time the update() method was called (in seconds). */
	private double ending = 0.0f;
	/** The times-per-second this timer is updated. */
	private double frameRate = 0.0f;
	/** Time since last update() method call (in seconds). */
	private double deltatime = 0.0f;
	/** How much time has elapsed since the last frame rate calculation. */
	private double elapsedTime = 0.0f;
	/** How many frames have elapsed since the last frame rate calculation. */
	private int elapsedFrames = 0;

	/**
	 * Constructor <br>
	 * <br>
	 * Initializes a Timer class that calculates the estimated frame rate based
	 * on how often to calculate the frame rate. <br>
	 * 
	 * @param updates
	 *            How many times per second this Timer should update the frame
	 *            rate.
	 */
	public Timer(int updates) {
		updateInterval = 1.0 / updates;
	}

	/**
	 * Starts recording time.
	 */
	public void start() {
		starting = getTime();
	}

	/**
	 * Updates the delta-time as well as the frame rate if its the frame to
	 * re-calculate it.
	 */
	public void update() {
		ending = getTime();
		// Calculate the difference in seconds since last update()
		deltatime = (ending - starting);
		elapsedTime += deltatime;
		elapsedFrames++;
		// If its time to update the frame rate...
		if (elapsedTime >= updateInterval) {
			// Calculate frame rate and clear elapsed variables.
			frameRate = 1.0f / (elapsedTime / elapsedFrames);

			elapsedFrames = 0;
			elapsedTime = 0.0f;
		}
		// Set the last time to now.
		starting = ending;
	}

	/**
	 * Gets the time in seconds on this OS.
	 */
	private double getTime() {
		return (double) System.nanoTime() / 1000000000L;
	}

	/**
	 * Gets the time in seconds between the last update() call.
	 */
	public float getDeltatime() {
		return (float) deltatime;
	}

	/**
	 * Returns the frames-per-second that this timer is updated.
	 */
	public float getFrameRate() {
		return (float) frameRate;
	}

}
