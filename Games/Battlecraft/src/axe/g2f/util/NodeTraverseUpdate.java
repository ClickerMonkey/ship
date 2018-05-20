package axe.g2f.util;

import axe.TimeUnit;
import axe.g2f.Entity2f;
import axe.g2f.Scene2f;
import axe.util.Node;
import axe.util.NodeTraverse;

public class NodeTraverseUpdate implements NodeTraverse<Entity2f> 
{
	private static NodeTraverseUpdate instance = new NodeTraverseUpdate();
	
	public static NodeTraverseUpdate get(TimeUnit elapsed, Scene2f scene) {
		return instance.set(elapsed, scene);
	}
	
	private TimeUnit elapsed;
	private Scene2f scene;
	
	public NodeTraverseUpdate set(TimeUnit elapsed, Scene2f scene) {
		this.elapsed = elapsed;
		this.scene = scene;
		return this;
	}
	
	public void onTraverse(Node<Entity2f> node, Entity2f x) {
		x.update(elapsed, scene);
	}
	
}