package engine;

public class TimeUnit
{

	public float seconds;
	public long millis;
	public long nanos;

	public TimeUnit() {
		
	}
	
	protected void reset(long nanosElapsed) {
		nanos = nanosElapsed;
		millis = nanos / 1000000L;
		seconds = nanos * 0.000000001f;
	}

}
