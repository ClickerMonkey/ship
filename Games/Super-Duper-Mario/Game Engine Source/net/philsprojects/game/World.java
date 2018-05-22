package net.philsprojects.game;

import net.philsprojects.game.util.NameHashTable;

/**
 * A 2-dimensional world made up of layers, and on each layer there are drawable
 * and updateable objects.
 * 
 * @author Philip Diffenderfer
 */
public class World
{


	private NameHashTable<Layer> _layers;


	public World(int maximumLayers)
	{
		_layers = new NameHashTable<Layer>(maximumLayers);
	}

	public boolean addLayer(String layername)
	{
		return _layers.add(new Layer(layername));
	}

	public boolean add(String layername, Object object)
	{
		Layer layer = _layers.get(layername);
		if (layer != null)
		{
			if (object instanceof IDraw)
				layer.add((IDraw)object);
			if (object instanceof IUpdate)
				layer.add((IUpdate)object);
			return true;
		}
		return false;
	}

	public Layer removeLayer(String layername)
	{
		return _layers.remove(layername);
	}

	public Layer getLayer(String layername)
	{
		return _layers.get(layername);
	}


}
