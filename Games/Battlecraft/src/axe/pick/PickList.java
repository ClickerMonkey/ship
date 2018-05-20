package axe.pick;

import axe.DrawMode;
import axe.core.Drawable;
import axe.core.Scene;

import static org.lwjgl.opengl.GL11.*;

public class PickList<S extends Scene>
{
	private int size;
	private Pickable<S>[] pickables;
	
	@SuppressWarnings("unchecked")
	public PickList(int capacity) {
		pickables = new Pickable[capacity];
		size = 0;
	}
	public Pickable<S> add(Drawable<S> drawable) {
		if (size == pickables.length) {
			return null;
		}
		return (pickables[size++] = new Pickable<S>(drawable, size));
	}
	public void remove(Pickable<S> pickable) {
		int index = pickable.index;
		pickables[index] = pickables[--size];
		pickables[index].index = index;
	}
	public Pickable<S> get(int index) {
		return pickables[index];
	}
	public void draw(DrawMode mode, S scene) {
		for (int i = 0; i < size; i++) {
			glLoadName(i);
			pickables[i].drawable.draw(mode, scene);
		}
	}
	public int size() {
		return size;
	}
}