package axe.anim;

import axe.TimeUnit;
import axe.core.Scene;
import axe.core.Updatable;

//                              loops (3)
//              /=================|=================\
//              |                 |                 |   
// +-------+----------+------+----------+------+----------+
// | delay | duration | rest | duration | rest | duration |
// +-------+----------+------+----------+------+----------+
// 00000000000000000001111111111111111112222222222222222223    <- iteration
//
// total = delay + (duration + rest) * loops - rest;
// remaining = total - time;
// active = iteration < loops
// 
public class Tween<T> implements Updatable<Scene>
{
	private final Function function = new Function(Function.In, Function.Linear);
	
	private Delta<T> start, end, subject;
	private float duration, time/**/, delay, rest;
	private int iteration/**/, loops;
	private boolean active/**/, bounce;
	public Tween(Delta<T> subject, Delta<T> start, Delta<T> end, float duration) {
		this.subject = subject;
		this.start = start;
		this.end = end;
		this.duration = duration;
		this.time = 0f;
		this.active = true;
	}
	public void set(Delta<T> start, Delta<T> end, float duration) {
		this.start = start;
		this.end = end;
		this.duration = duration;
		this.time = 0f;
		this.active = true;
	}
	public void reset() {
		active = true;
		time = 0f;
		iteration = 0;
	}
	public void swap() {
		Delta<T> temp = end;
		end = start;
		start = temp;
	}
	public void reverse() {
		if (iteration > 0) {
			time = duration - time;	
		}
	}
	public void update(TimeUnit elapsed, Scene scene) {
		if (active) {
			if (iteration == 0) {
				if (time > delay) {
					iteration++;
					time -= delay;
				}
			}
			if (iteration > 0) {
				float remain = time - duration;
				if (remain >= 0) {
					subject.set(end.get());
					if (remain >= rest) {
						time -= duration + rest;
						iteration++;
						if (bounce) {
							swap();
						}
						if (iteration > loops) {
							active = false;
						}
					}
				}
				else {
					subject.delta(start.get(), end.get(), time / duration);		
				}
			}
			time += elapsed.seconds;
		}
	}
	public void finish() {
		subject.set(end.get());
		active = false;
	}
	public void stop() {
		active = false;
	}
	
	public Function function() {
		return function;
	}
}