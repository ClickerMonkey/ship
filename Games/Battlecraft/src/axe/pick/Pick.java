package axe.pick;

import axe.core.Drawable;
import axe.core.Scene;

public class Pick<S extends Scene> 
{
	
	public int hits;
	public int minZ;
	public int maxZ;
	public Drawable<S> obj;
	public Pickable<S> pickable;
	
	public Pick(Pickable<S> pickable) 
	{
		this.pickable = pickable;
		this.obj = pickable.drawable;
	}
	
}
