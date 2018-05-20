package axe.g2f.util;

import axe.DrawMode;
import axe.g2f.Entity2f;
import axe.g2f.Scene2f;
import axe.util.Node;
import axe.util.NodeTraverse;

public class NodeTraverseDraw implements NodeTraverse<Entity2f> 
{
	private static NodeTraverseDraw instance = new NodeTraverseDraw();
	public static NodeTraverseDraw get(DrawMode mode, Scene2f scene) {
		return instance.set(mode, scene);
	}
	
	private DrawMode mode;
	private Scene2f scene;
	
	public NodeTraverseDraw set(DrawMode mode, Scene2f scene) {
		this.mode = mode;
		this.scene = scene;
		return this;
	}
	
	public void onTraverse(Node<Entity2f> node, Entity2f x) {
		x.draw(mode, scene);
	}
	
}