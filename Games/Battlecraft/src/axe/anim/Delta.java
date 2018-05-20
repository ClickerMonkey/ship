package axe.anim;

public interface Delta<T> 
{
	
	public void delta(T start, T end, float delta);
	public float distance(T to);
	public T get();
	public void set(T value);
	
}