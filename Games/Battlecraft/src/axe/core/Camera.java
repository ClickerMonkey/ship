package axe.core;

public interface Camera<S extends Scene> extends Updatable<S>
{
	public void bind();
}
