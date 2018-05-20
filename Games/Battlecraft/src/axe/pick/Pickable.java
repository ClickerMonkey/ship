package axe.pick;

import axe.core.Drawable;
import axe.core.Scene;

public class Pickable<S extends Scene>
{
	
	protected final Drawable<S> drawable;
	protected int index;
	
	protected Pickable(Drawable<S> drawable, int name) 
	{
		this.drawable = drawable;
		this.index = name;
	}
	
}