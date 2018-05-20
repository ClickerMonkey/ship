package axe.g2f;

import axe.DrawMode;
import axe.TimeUnit;
import axe.g2f.util.NodeTraverseDraw;
import axe.g2f.util.NodeTraverseUpdate;
import axe.util.NodeList;

public class Layer2f extends NodeList<Entity2f> implements Entity2f
{
	
	public void update(TimeUnit elapsed, Scene2f scene) 
	{
		traverse(NodeTraverseUpdate.get(elapsed, scene));
	}

	public void draw(DrawMode mode, Scene2f scene) 
	{
		traverse(NodeTraverseDraw.get(mode, scene));
	}
	
}
