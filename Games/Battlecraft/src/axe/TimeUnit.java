package axe;

public class TimeUnit
{

	public float seconds;
	public long millis;
	public long nanos;

	public TimeUnit() 
	{
	}
	
	protected void update(long nanosElapsed) 
	{
		this.nanos = nanosElapsed;
		this.millis = nanos / 1000000L;
		this.seconds = nanos * 0.000000001f;
	}

}
