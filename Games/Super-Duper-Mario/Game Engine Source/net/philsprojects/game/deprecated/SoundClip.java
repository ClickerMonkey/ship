package net.philsprojects.game.deprecated;

import javax.sound.sampled.*;


public class SoundClip implements ISound, LineListener
{

	public static final int INFINITE = -1;

	private Clip _clip = null;
	private String _name = null;
	private boolean _isPaused = false;
	private boolean _isDisposed = false;

	public void dispose()
	{
		if (_isDisposed || _clip == null)
			return;

		if (_clip.isRunning())
			_clip.stop();

		_clip.removeLineListener(this);
		_clip.close();
		_clip = null;

		_isDisposed = true;
	}

	public boolean load(String filepath)
	{
		try
		{
			AudioInputStream stream = AudioSystem.getAudioInputStream(ClassLoader.getSystemResource(filepath));
			AudioFormat format = stream.getFormat();

			if ((format.getEncoding() == AudioFormat.Encoding.ULAW) || (format.getEncoding() == AudioFormat.Encoding.ALAW))
			{
				AudioFormat newFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, format.getSampleRate(), format.getSampleSizeInBits() * 2, format.getChannels(), format.getFrameSize() * 2,
						format.getFrameRate(), true);
				stream = AudioSystem.getAudioInputStream(newFormat, stream);
				format = newFormat;
			}

			DataLine.Info info = new DataLine.Info(Clip.class, format);

			if (!AudioSystem.isLineSupported(info)) { return false; }

			_clip = (Clip)AudioSystem.getLine(info);

			_clip.open(stream);
			stream.close();

			_clip.addLineListener(this);

			_clip.setFramePosition(0);
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}

	public void pause()
	{
		_isPaused = true;
		_clip.stop();
	}

	public void stop()
	{
		_isPaused = false;
		_clip.stop();
		_clip.setMicrosecondPosition(0);
	}

	public String getName()
	{
		return _name;
	}

	public float getDuration()
	{
		return _clip.getMicrosecondLength() / 10000000.0f;
	}

	public void update(LineEvent event)
	{
		if (event.getType() == LineEvent.Type.STOP && !_isPaused)
		{
			_clip.stop();
			_clip.setMicrosecondPosition(0);
		}
	}

	public void loop(int times)
	{
		_isPaused = false;
		_clip.stop();
		_clip.setFramePosition(0);
		_clip.loop(times);
	}

	public void loop(long startInMicro, long endInMicro, int times)
	{
		_isPaused = false;
		int startFrame = (int)(startInMicro / _clip.getMicrosecondLength() * _clip.getFrameLength());
		int endFrame = (int)(endInMicro / _clip.getMicrosecondLength() * _clip.getFrameLength());
		_clip.stop();
		_clip.setFramePosition(0);
		_clip.setLoopPoints(startFrame, endFrame);
		_clip.loop(times);
	}

	public void play()
	{
		_isPaused = false;
		_clip.start();
	}

	public void play(long offsetInMicro)
	{
		_isPaused = false;
		_clip.setMicrosecondPosition(offsetInMicro);
		_clip.start();
	}

	public boolean isPlaying()
	{
		return _clip.isRunning();
	}

	public boolean isPaused()
	{
		return _isPaused;
	}

}
