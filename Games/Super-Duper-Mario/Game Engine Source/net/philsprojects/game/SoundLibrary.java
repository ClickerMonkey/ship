package net.philsprojects.game;

import java.applet.AudioClip;
import java.applet.Applet;

import net.philsprojects.game.util.Iterator;
import net.philsprojects.game.util.NameHashTable;


public final class SoundLibrary
{

	private static SoundLibrary _instance = null;

	public static SoundLibrary getInstance() 
	{
		return _instance;
	}

	public static void initialize(int maxSounds)
	{
		if (_instance != null)
			return;
		_instance = new SoundLibrary(maxSounds);
	}


	private String _directory = "";
	private NameHashTable<Sound> _sounds = null;

	private SoundLibrary(int maxSounds)
	{
		_sounds = new NameHashTable<Sound>(maxSounds);
	}

	public void setDirectory(String directory)
	{
		if (directory == null)
			_directory = "";
		else
			_directory = directory;
	}

	public boolean add(String referenceName, String filepath)
	{
		Sound s = new Sound(referenceName, _directory + filepath);
		if (s.loaded())
		{
			return _sounds.add(s);
		}
		return false;
	}

	public void play(String referenceName)
	{
		play(referenceName, true);
	}

	public void play(String referenceName, boolean interrupt)
	{
		Sound s = _sounds.get(referenceName);
		if (interrupt)
			s._clip.stop();
		s._clip.play();
	}

	public void loop(String referenceName)
	{
		play(referenceName, false);
	}

	public void loop(String referenceName, boolean interrupt)
	{
		Sound s = _sounds.get(referenceName);
		if (interrupt)
			s._clip.stop();
		s._clip.loop();
	}

	public void stop(String referenceName)
	{
		Sound s = _sounds.get(referenceName);
		if (s != null)
			s._clip.stop();
	}

	public void stopAll()
	{
		Iterator<Sound> i = _sounds.getIterator();
		while (i.hasNext())
			i.getNext()._clip.stop();
	}

	private class Sound implements IName
	{
		private AudioClip _clip = null;
		private String _name = null;

		public Sound(String name, String filepath)
		{
			_name = name;
			_clip = Applet.newAudioClip(ClassLoader.getSystemResource(filepath));
		}

		public boolean loaded()
		{
			return (_clip != null);
		}

		public String getName()
		{
			return _name;
		}


	}

}
