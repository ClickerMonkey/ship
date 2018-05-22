package net.philsprojects.game.effects;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import net.philsprojects.game.IBinary;
import net.philsprojects.game.IClone;


// 8 bytes
public class Range implements IBinary, IClone<Range>
{

	private float _min = 0.0f;
	private float _max = 0.0f;

	public Range(float value)
	{
		_min = _max = value;
	}

	public Range(float maximum, float minimum)
	{
		_max = maximum;
		_min = minimum;
	}

	public void set(float minimum, float maximum)
	{
		_min = minimum;
		_max = maximum;
	}

	public void set(Range r)
	{
		_min = r._min;
		_max = r._max;
	}

	public float randomFloat()
	{
		return random(_min, _max);
	}

	public int randomInteger()
	{
		return random((int)_min, (int)_max);
	}

	public float getMin()
	{
		return _min;
	}

	public float getMax()
	{
		return _max;
	}

	public String toString()
	{
		return String.format("{Min<%s> Max<%s>}", _min, _max);
	}


	public Range getClone()
	{
		return new Range(_min, _max);
	}

	public static int random(int min, int max)
	{
		if (min == max)
			return max;
		return (int)(Math.random() * (max - min + 1) + min);
	}

	public static float random(float min, float max)
	{
		if (min == max)
			return max;
		return (float)Math.random() * (max - min) + min;
	}


	public Range(DataInputStream reader) throws Exception
	{
		read(reader);
	}

	public void read(DataInputStream reader) throws Exception
	{
		_min = reader.readFloat();
		_max = reader.readFloat();
	}

	public void write(DataOutputStream writer) throws Exception
	{
		writer.writeFloat(_min);
		writer.writeFloat(_max);
	}


}
