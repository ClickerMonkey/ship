package net.philsprojects.game.effects;

import net.philsprojects.game.Camera;
import net.philsprojects.game.GraphicsLibrary;
import net.philsprojects.game.util.Iterator;
import net.philsprojects.game.util.NameLinkedList;
import net.philsprojects.game.util.Vector;

public class SystemNodeList implements ISystemNode
{

	private boolean _enabled = true;
	private boolean _visible = true;
	private float _angle = 0f;
	private Vector _scale = Vector.one();
	private Vector _offset = Vector.zero();
	private Vector _location = Vector.zero();
	private String _name = null;
	private NameLinkedList<ISystemNode> _nodes = null; 


	public SystemNodeList(String name)
	{
		_name = name;
		_nodes = new NameLinkedList<ISystemNode>();
	}

	public void initialize()
	{
		Iterator<ISystemNode> i = _nodes.getIterator();
		while (i.hasNext())
			i.getNext().initialize();
	}

	public void update(float deltatime)
	{
		if (!_enabled)
			return;
		Iterator<ISystemNode> i = _nodes.getIterator();
		while (i.hasNext())
			i.getNext().update(deltatime);
	}

	public void draw()
	{
		if (!_visible)
			return;
		final GraphicsLibrary g = GraphicsLibrary.getInstance();
		final Camera c = Camera.getInstance();

		g.beginTransform();
		g.translate(_location.x - c.getX(), _location.y - c.getY());
		g.rotate(_angle);
		g.scale(_scale.x, _scale.y);

		Iterator<ISystemNode> i = _nodes.getIterator();
		while (i.hasNext())
			i.getNext().draw();

		g.endTransform();
	}


	public void addNode(ISystemNode node)
	{
		_nodes.add(node);
	}

	public ISystemNode removeNode(String name)
	{
		return _nodes.remove(name);
	}

	public ISystemNode getNode(String name)
	{
		return _nodes.remove(name);
	}



	public void setEmitterAngle(float angle)
	{
		_angle = angle;
	}

	public void setEmitterOffset(float x, float y)
	{
		_offset.set(x, y);
	}

	public void setEmitterOffset(Vector offset)
	{
		_offset = offset;
	}

	public void setEmitterScale(float x, float y)
	{
		_scale.set(x, y);
	}

	public void setEmitterScale(Vector scale)
	{
		_scale = scale;
	}

	public void setLocation(float x, float y)
	{
		_location.set(x, y);
	}

	public void setLocation(Vector location)
	{
		_location = location;
	}

	public void setVisible(boolean visible)
	{
		_visible = visible;
	}



	public void addEmitterAngle(float angle)
	{
		_angle = angle;
	}

	public void addEmitterOffset(float x, float y)
	{
		_offset.add(x, y);
	}

	public void addEmitterScale(float x, float y)
	{
		_scale.add(x, y);
	}



	public float getEmitterAngle()
	{
		return _angle;
	}

	public Vector getEmitterOffset()
	{
		return _offset;
	}

	public Vector getEmitterScale()
	{
		return _scale;
	}

	public Vector getLocation()
	{
		return _location;
	}

	public String getName()
	{
		return _name;
	}

	public NameLinkedList<ISystemNode> getNodeList()
	{
		return _nodes;
	}

	public boolean isVisible()
	{
		return _visible;
	}

	public boolean isEnabled()
	{
		return _enabled;
	}

	public SystemNodeList getClone()
	{
		SystemNodeList clone = new SystemNodeList(_name + "#");
		clone._angle = _angle;
		clone._enabled = _enabled;
		clone._location = _location.getClone();
		clone._nodes = _nodes.getClone();
		clone._offset = _offset.getClone();
		clone._scale = _scale.getClone();
		clone._visible = _visible;
		return clone;
	}

}
