package axe.core;

import axe.TimeUnit;

public interface Updatable<S extends Scene>
{
	public void update(TimeUnit elapsed, S scene);
}