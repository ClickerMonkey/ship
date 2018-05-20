package axe.core;

import axe.DrawMode;

public interface Drawable<S extends Scene> 
{
	public void draw(DrawMode mode, S scene);
}