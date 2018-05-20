package net.philsprojects.game.animation;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import net.philsprojects.game.*;
import net.philsprojects.game.sprites.SpriteTileReader;
import net.philsprojects.game.util.Color;
import net.philsprojects.game.util.Vector;

import static net.philsprojects.game.util.Math.*;

public class Limb implements IName, IBinary
{

	protected float _width = 0f;
	protected float _height = 0f;
	protected String _name = null;
	protected Joint _parent = null;
	protected String _parentName = null;
	protected Joint _child = null;
	protected String _childName = null;
	protected Vector _parentOffset = Vector.zero();
	protected Vector _childOffset = Vector.zero();
	protected ISpriteTile _tile = null;
	protected Color _shade = Color.white();
	protected Vector _center = null;

	public Limb(String name, Joint parent, Joint child)
	{
		_name = name;
		_parent = parent;
		_parentName = parent.getName();
		_child = child;
		_childName = child.getName();
	}

	public void update()
	{
		Vector parent = rotateVector(Vector.add(_parent.getLocation(), _parentOffset), _child.getAngle());
		Vector child = rotateVector(Vector.add(_child.getLocation(), _childOffset), 360 - _child.getAngle());
		_center = midVector(parent, child);
		_height = distance(parent, child);
	}

	public void draw()
	{
		final GraphicsLibrary g = GraphicsLibrary.getInstance();
		g.drawSprite(_center.x, _center.y, _width, _height, _child.getAngle(), _width / 2f, _height / 2f, _shade);
	}


	public void setWidth(float width)
	{
		_width = width;
	}

	public void setParent(Joint parent)
	{
		_parent = parent;
		_parentName = parent.getName();
	}

	public void setChild(Joint child)
	{
		_child = child;
		_childName = child.getName();
	}

	public void setParentOffset(Vector offset)
	{
		_parentOffset = offset;
	}

	public void setChildOffset(Vector offset)
	{
		_childOffset = offset;
	}

	public void setTile(ISpriteTile tile)
	{
		_tile = tile;
	}

	public void setShade(Color shade)
	{
		_shade = shade;
	}

	public String getName()
	{
		return _name;
	}

	public float getWidth()
	{
		return _width;
	}

	public float getHeight()
	{
		return _height;
	}

	public Joint getParent()
	{
		return _parent;
	}

	public String getParentName()
	{
		return _parentName;
	}

	public Joint getChild()
	{
		return _child;
	}

	public String getChildName()
	{
		return _childName;
	}

	public Vector getParentOffset()
	{
		return _parentOffset;
	}

	public Vector getChildOffset()
	{
		return _childOffset;
	}

	public ISpriteTile getTile()
	{
		return _tile;
	}

	public Color getShade()
	{
		return _shade;
	}

	public Limb(DataInputStream reader) throws Exception
	{
		read(reader);
	}

	public void read(DataInputStream reader) throws Exception
	{
		_name = reader.readUTF();
		_parentName = reader.readUTF();
		_childName = reader.readUTF();
		_parentOffset.read(reader);
		_childOffset.read(reader);
		_width = reader.readFloat();
		_tile = SpriteTileReader.readTile(reader);
	}

	public void write(DataOutputStream writer) throws Exception
	{
		writer.writeUTF(_name);
		writer.writeUTF(_parentName);
		writer.writeUTF(_childName);
		_parentOffset.write(writer);
		_childOffset.write(writer);
		writer.writeFloat(_width);
		SpriteTileReader.writeTile(writer, _tile);
	}



}
