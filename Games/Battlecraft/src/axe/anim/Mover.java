package axe.anim;

import axe.TimeUnit;
import axe.core.Scene;
import axe.core.Updatable;

public class Mover<T> implements Updatable<Scene> 
{
	
	private Motion<T> subject, max, vel, acc;
	
	public Mover(Motion<T> subject, Motion<T> max, Motion<T> vel, Motion<T> acc) 
	{
		this.subject = subject;
		this.max = max;
		this.vel = vel;
		this.acc = acc;
	}
	
	public void update(TimeUnit elapsed, Scene scene) 
	{
		vel.add(acc.get(), elapsed.seconds);
		vel.max(max.get());
		subject.add(vel.get(), elapsed.seconds);
	}
	
}