package axe.anim;


public interface Motion<T> 
{
	
	public void add(T value, float scale);
	public void max(T max);
	public T get();
	public void set(T value);
	
}