package net.philsprojects.game;

import net.philsprojects.game.util.HashTable;

import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureIO;


/**
 * A HashTable of Textures based on a reference name.
 * 
 * @author Philip Diffenderfer
 */
public class TextureLibrary
{

	private static TextureLibrary _instance;

	public static TextureLibrary getInstance()
	{
		return _instance;
	}

	public static void initialize(int initialSize)
	{
		if (_instance != null)
			return;
		_instance = new TextureLibrary(initialSize);
	}

	private HashTable<Texture> _textures = null;
	private String _directory = "";

	private TextureLibrary(int initialSize)
	{
		_textures = new HashTable<Texture>(initialSize);
	}

	public void setDirectory(String directory)
	{
		if (directory != null && directory.length() > 0)
		{
			_directory = directory;
			if (!_directory.endsWith("/"))
				_directory = _directory + "/";
		}
		else
		{
			_directory = "";
		}
	}

	public boolean add(String filepath, String referenceName)
	{
		if (!_textures.exists(referenceName))
		{
			Texture texture = null;
			try
			{
				texture = TextureIO.newTexture(getClass().getClassLoader().getResource(_directory + filepath), false, null);
			}
			catch (Exception e)
			{
				e.printStackTrace();
				return false;
			}
			_textures.add(referenceName, texture);
			return true;
		}
		// This name already exists!
		return false;
	}

	public Texture get(String referenceName)
	{
		return _textures.get(referenceName);
	}

	public boolean remove(String referenceName)
	{
		return (_textures.remove(referenceName) != null ? true : false);
	}

	public void clear()
	{
		_textures.clear();
	}

	public String getDirectory()
	{
		return _directory;
	}

	public boolean exists(String referenceName)
	{
		return _textures.exists(referenceName);
	}

}
