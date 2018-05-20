package axe;


public class Timer
{

	private long startTime;
	private long endTime;
	private final TimeUnit elapsed;
	
	public Timer() {
		elapsed = new TimeUnit();
	}

	public void start() {
		startTime = endTime = System.nanoTime();
	}

	public void update() {
		endTime = startTime;
		startTime = System.nanoTime();
		elapsed.update(startTime - endTime);
	}

	public TimeUnit elapsed() {
		return elapsed;
	}

}
