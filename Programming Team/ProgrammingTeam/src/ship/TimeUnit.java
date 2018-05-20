package ship;

public class TimeUnit
{

	public final float seconds;
	public final long millis;
	public final long nanos;

	public TimeUnit(long nanosElapsed)
	{
		this.nanos = nanosElapsed;
		this.millis = nanos / 1000000L;
		this.seconds = nanos * 0.000000001f;
	}

}
