package net.philsprojects.game.animation;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import net.philsprojects.game.*;
import net.philsprojects.game.util.Vector;

import static net.philsprojects.game.util.Math.*;

public class Joint implements IName, IBinary
{

	protected String _name = null;
	protected String _parentName = null;
	protected Joint _parent = null;
	protected float _distance = 0f;
	protected float _angle = 0f;
	protected int _level = 0;
	protected Vector _location = Vector.zero();

	protected Joint()
	{
	}

	public Joint(String name, float distance, float angle)
	{
		_name = name;
		_distance = distance;
		_angle = angle;
	}

	public void update()
	{
		_location.x = cos(_angle) * _distance;
		_location.y = sin(_angle) * _distance;
	}



	public void setParent(Joint parent)
	{
		_parent = parent;
		_parentName = parent.getName();
	}

	public void setDistance(float distance)
	{
		_distance = distance;
	}

	public void setAngle(float angle)
	{
		_angle = angle;
	}

	public void setLevel(int level)
	{
		_level = level;
	}


	public String getName()
	{
		return _name;
	}

	public String getParentName()
	{
		return _parentName;
	}

	public Joint getParent()
	{
		return _parent;
	}

	public Vector getLocation()
	{
		return Vector.add(_parent.getLocation(), _location);
	}

	public float getAngle()
	{
		return _angle;
	}

	public float getDistance()
	{
		return _distance;
	}    

	public int getLevel()
	{
		return _level;
	}


	public boolean isEnabled()
	{
		return true;
	}


	// <==========================================> //
	// <=== IBinary Interface ====================> //
	// <==========================================> //

	public Joint(DataInputStream reader) throws Exception
	{
		read(reader);
	}

	public void read(DataInputStream reader) throws Exception
	{
		_name = reader.readUTF();
		_parentName = reader.readUTF();
		_distance = reader.readFloat();
		_angle = reader.readFloat();
	}

	public void write(DataOutputStream writer) throws Exception
	{
		writer.writeUTF(_name);
		writer.writeUTF(_parentName);
		writer.writeFloat(_distance);
		writer.writeFloat(_angle);
	}

}
