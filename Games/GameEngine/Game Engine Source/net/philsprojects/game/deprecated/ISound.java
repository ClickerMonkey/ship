package net.philsprojects.game.deprecated;

import net.philsprojects.game.IName;

public interface ISound extends IName
{

	public boolean load(String filepath);

	public void play();

	public void play(long offsetInMicro);

	public void loop(int times);

	public void loop(long startInMicro, long endInMicro, int times);

	public void pause();

	public void stop();

	public void dispose();

	public float getDuration();

	public boolean isPlaying();

	public boolean isPaused();

}
