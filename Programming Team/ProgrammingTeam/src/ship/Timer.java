package ship;

public class Timer
{

	private long startTime;
	private long endTime;
	
	public Timer()
	{
	}

	public void start()
	{
		startTime = endTime = System.nanoTime();
	}

	public void update()
	{
		endTime = startTime;
		startTime = System.nanoTime();
	}

	public TimeUnit getElapsed()
	{
		return new TimeUnit(startTime - endTime);
	}

}
