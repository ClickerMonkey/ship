package net.philsprojects.game.sprites;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import net.philsprojects.game.ISpriteTile;


@SuppressWarnings("unchecked")
public class SpriteTileReader
{

	static {
		_registeredTypes = new Class[32];
		register(SpriteTileAnimated.class, 0);
		register(SpriteTileFramed.class, 1);
		register(SpriteTileJumping.class, 2);
		register(SpriteTileStatic.class, 3);
	}

	private static Class[] _paramTypes = {DataInputStream.class};
	private static Class[] _registeredTypes;


	public static void register(Class spriteTileClass, int index)
	{
		_registeredTypes[index] = spriteTileClass;
	}

	public static ISpriteTile readTile(DataInputStream reader) throws Exception
	{
		int index = reader.readInt();
		return (ISpriteTile)_registeredTypes[index].getConstructor(_paramTypes).newInstance(new Object[] {reader});
	}

	public static boolean writeTile(DataOutputStream writer, ISpriteTile tile) throws Exception
	{
		int index = getIndex(tile.getClass());
		if (index == -1)
			return false;
		writer.writeInt(index);
		tile.write(writer);
		return true;
	}

	private static int getIndex(Class spriteTileClass)
	{
		for (int i = 0; i < _registeredTypes.length; i++)
			if (_registeredTypes[i].equals(spriteTileClass))
				return i;
		return -1;
	}

}
