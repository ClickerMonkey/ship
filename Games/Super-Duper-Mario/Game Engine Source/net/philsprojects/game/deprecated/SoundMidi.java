package net.philsprojects.game.deprecated;

import javax.sound.midi.*;


public class SoundMidi implements ISound, MetaEventListener
{

	private Sequencer _sequencer = null;
	private Synthesizer _synthesizer = null;
	private Sequence _sequence = null;
	private String _name = null;
	private boolean _isPaused = false;
	private boolean _isDisposed = false;

	public void dispose()
	{
		if (_isDisposed || _sequencer == null)
			return;

		if (_sequencer.isRunning())
			_sequencer.stop();

		_sequencer.removeMetaEventListener(this);
		_sequencer.close();

		if (_synthesizer != null)
			_synthesizer.close();

		_sequencer = null;
		_synthesizer = null;
		_sequence = null;
	}

	public float getDuration()
	{
		return _sequencer.getMicrosecondLength() / 1000000.0f;
	}

	public boolean isPlaying()
	{
		return _sequencer.isRunning();
	}

	public boolean load(String filepath)
	{
		try
		{
			_sequencer = MidiSystem.getSequencer();

			if (_sequencer == null)
				return false; 

			_sequencer.open();
			_sequencer.addMetaEventListener(this);

			if (!(_sequencer instanceof Synthesizer))
			{
				_synthesizer = MidiSystem.getSynthesizer();
				_synthesizer.open();
				_sequencer.getTransmitter().setReceiver(_synthesizer.getReceiver());
			}
			else
				_synthesizer = (Synthesizer)_sequencer;

			_sequence = MidiSystem.getSequence(getClass().getResource(filepath));
			_sequencer.setSequence(_sequence);
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}

	public void loop(int times)
	{
		_isPaused = false;
		_sequencer.stop();
		_sequencer.setMicrosecondPosition(0);
		_sequencer.setLoopCount(times);
		_sequencer.start();
	}

	public void loop(long startInMicro, long endInMicro, int times)
	{
		_isPaused = false;
		_sequencer.stop();
		_sequencer.setMicrosecondPosition(0);
		_sequencer.setLoopStartPoint((startInMicro / _sequencer.getMicrosecondLength() * _sequencer.getTickLength()));
		_sequencer.setLoopEndPoint((endInMicro / _sequencer.getMicrosecondLength() * _sequencer.getTickLength()));
		_sequencer.setLoopCount(times);
		_sequencer.start();
	}

	public void pause()
	{
		_isPaused = true;
		_sequencer.stop();
	}

	public void play()
	{
		_isPaused = false;
		_sequencer.setLoopCount(0);
		_sequencer.start();
	}

	public void play(long offsetInMicro)
	{
		_isPaused = false;
		_sequencer.setMicrosecondPosition(offsetInMicro);
		_sequencer.start();
	}

	public void stop()
	{
		_isPaused = false;
		_sequencer.stop();
		_sequencer.setMicrosecondPosition(0);
	}

	public String getName()
	{
		return _name;
	}

	public void meta(MetaMessage meta)
	{
		final int END_OF_TRACK = 47;
		if (meta.getType() == END_OF_TRACK && !_isPaused)
		{
			_sequencer.stop();
			_sequencer.setMicrosecondPosition(0);
		}
	}

	public boolean isPaused()
	{
		return _isPaused;
	}

}
