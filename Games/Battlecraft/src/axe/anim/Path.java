package axe.anim;

import axe.TimeUnit;
import axe.core.Scene;
import axe.core.Updatable;

public class Path<T> implements Updatable<Scene> 
{
	
	private final Function function = new Function(Function.In, Function.Linear);
	
	private final Delta<T>[] points;
	private final float[] distances;
	
	private final Delta<T> current;
	private float length;
	private float duration;
	private float time;
	
	public Path(Delta<T> current, Delta<T>[] points, float duration) {
		this.current = current;
		this.points = points;
		this.duration = duration;
		this.distances = new float[points.length - 1];
		this.reset();
	}
	
	private void reset() {
		length = 0f;
		for (int i = 0; i < distances.length; i++) {
			distances[i] = points[i].distance(points[i + 1].get());
			length += distances[i];
		}
	}
	
	public void update(TimeUnit elapsed, Scene scene) {
		if (time <= duration) {
			float d = function.delta(time / duration);
			float l = d * length;
			
			if (true) {
				int i = 0;
				float total = distances[i];
				while (total < l) {
					total += distances[++i];
				}
				float before = total - distances[i];
				float q = (l - before) / (total - before);
				current.delta(points[i].get(), points[i + 1].get(), q);	
			}
			
//			float w = Scalarf.clamp(d, 0, 1);
//			int i = (int)(w * (distances.length - 1));
		}
		time += elapsed.seconds;
	}
	
	public Function function() {
		return function;
	}
	
	public Delta<T> current() {
		return current;
	}
	
	public Delta<T>[] points() {
		return points;
	}
	
	public float duration() {
		return duration;
	}
	
	public void duration(float duration) {
		this.duration = duration; 
	}
	
	public float time() {
		return time;
	}
	
	public void time(float time) {
		this.time = time;
	}
	
	public boolean isAlive() {
		return (time <= duration);
	}

}
