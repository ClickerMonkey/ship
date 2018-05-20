package ship;


public class FrameCounter
{

	private float updateRate;
	private float time;
	private int frames;
	private int frameRate;
	
	public FrameCounter() {
		updateRate = 0.5f;
		time = 0;
	}
	
	public void update(TimeUnit elapsed) {
		time += elapsed.seconds;
		frames++;
		
		if (time >= updateRate) {
			frameRate = (int)(frames / time);
			time -= updateRate;
			frames = 0;
		}
	}
	
	public int getFrameRate() {
		return frameRate;
	}
	
	public void setUpdateRate(int framesPerSecond) {
		updateRate = 1f / framesPerSecond;
	}
	
}
