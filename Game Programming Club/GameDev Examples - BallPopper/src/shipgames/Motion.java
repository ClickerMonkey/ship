package shipgames;

/**
 * A class for performing motion of a normalized value. This can be used to
 * provide several types of interpolation (linear, cubic, sinusoidal, etc)
 * between values.
 * 
 * @author Philip Diffenderfer
 *
 */
public class Motion 
{
	/**
	 * A Motion Type is the first layer that attributes to the behavior of the
	 * motion.
	 * 
	 * @author Philip Diffenderfer
	 *
	 */
	public interface Type {
		public float delta(float d, Function f);
	}

	/**
	 * A Function Type is the second layer that attributes to the behavior of
	 * the motion.
	 * 
	 * @author Philip Diffenderfer
	 *
	 */
	public interface Function {
		public float motion(float d);
	}

	/*==================================================================*\
	 * TYPES
	\*==================================================================*/

	// Performs the motion normally.
	public static final Type In = new Type() {
		public float delta(float d, Function f) {
			return f.motion(d);
		}
	};

	// Performs the motion in 'reverse'
	public static final Type Out = new Type() {
		public float delta(float d, Function f) {
			return (1 - f.motion(1 - d));
		}
	};

	// Combination of In/Out, performs the function twice mirrored at 0.5.
	public static final Type InOut = new Type() {
		public float delta(float d, Function f) {
			if (d < 0.5)
				return f.motion(2 * d) * 0.5f;

			return (1 - f.motion(2 - 2 * d) * 0.5f);
		}
	};

	/*==================================================================*\
	 * FUNCTIONS
	\*==================================================================*/

	// Linear Interpolation
	public static final Function Linear = new Function() {
		public float motion(float d) {
			return d;
		}
	};

	// Quadratic Interpolation
	public static Function Quadtratic = new Function() {
		public float motion(float d) {
			return d * d;
		}
	};

	// Cubic Interpolation
	public static Function Cubic = new Function() {
		public float motion(float d) {
			return d * d * d;
		}
	};

	// Quartic Interpolation
	public static Function Quartic = new Function() {
		public float motion(float d) {
			return (d *= d) * d;
		}
	};

	// Quintic Interpolation
	public static Function Quintic = new Function() {
		public float motion(float d) {
			return (d *= d) * d * d;
		}
	};

	// Goes pass the destination and backs up
	public static Function Back = new Function() {
		public float motion(float d) {
			float d2 = d * d;
			float d3 = d2 * d;
			return d3 + d2 - d;
		}
	};

	// Slows down in the middle
	public static Function Sine = new Function() {
		private double FREQUENCY = Math.PI * 0.5;
		public float motion(float d) {
			return (float)Math.sin(d * FREQUENCY);
		}
	};

	// Bounces back and forth and destination
	public static Function Elastic = new Function() {
		private double FREQUENCY = Math.PI * 3.5;
		public float motion(float d) {
			float d2 = d * d;
			float d3 = d2 * d;
			float scale = d2 * ((2 * d3) + d2 - (4 * d) + 2);
			float wave = -(float)Math.sin(d * FREQUENCY);
			return scale * wave;
		}
	};

	// Goes halfway, comes back, goes to destination.
	public static Function Revisit = new Function() {
		private double FREQUENCY = Math.PI;
		public float motion(float d) {
			return (float)Math.abs(-Math.sin(d * FREQUENCY) + d);
		}
	};

	// A slow bounce
	public static Function SlowBounce = new Function() {
		private double FREQUENCY = Math.PI * Math.PI * 1.5;
		public float motion(float d) {
			float d2 = d * d;
			return (float)(1 - Math.abs((1 - d2) * Math.cos(d2 * d * FREQUENCY)));
		}
	};

	// A smaller bounce
	public static Function SmallBounce = new Function() {
		private double FREQUENCY = Math.PI * Math.PI * 1.5;
		public float motion(float d) {
			float inv = 1 - d;
			return (float)(1 - Math.abs(inv * inv * Math.cos(d * d * FREQUENCY)));
		}
	};

	// An even smaller bounce
	public static Function TinyBounce = new Function() {
		private double FREQUENCY = 7;
		public float motion(float d) {
			float inv = 1 - d;
			return (float)(1 - Math.abs(inv * inv * Math.cos(d * d * FREQUENCY)));
		}
	};

	// A normal bounce
	public static Function Bounce = new Function() {
		private double FREQUENCY = Math.PI * Math.PI * 1.5;
		public float motion(float d) {
			return (float)(1 - Math.abs((1 - d) * Math.cos(d * d * FREQUENCY)));
		}
	};

	// Reluctantly moves towards destination...
	public static Function Hesitant = new Function() {
		public float motion(float d) {
			return (float)(Math.cos(d * d * 12) * d * (1 - d) + d);
		}
	};

	// Similar to elastic
	public static Function Lasso = new Function() {
		public float motion(float d) {
			float d2 = d * d;
			return (float)(1 - Math.cos(d2 * d * 36) * (1 - d));
		}
	};

	// Square root
	public static Function Sqrt = new Function() {
		public float motion(float d) {
			return (float)Math.sqrt(d);
		}
	};

	// Log base 10
	public static Function Log10 = new Function() {
		public float motion(float d) {
			return (float)((Math.log10(d) + 2) * 0.5);
		}
	};

	// Backs up and slingshots toward destination
	public static Function Slingshot = new Function() {
		public float motion(float d) {
			if (d < 0.7f)
				return (d * -0.357f);

			float x = d - 0.7f;
			return ((x * x * 27.5f - 0.5f) * 0.5f);
		}
	};


	// The type of motion.
	private Type type;

	// The function of the motion.
	private Function function;

	// The scale of the motion.
	private float scale;

	/**
	 * Creates a new motion given its type and function
	 * 
	 * @param type => The type of motion.
	 * @param function => The function of the motion.
	 */
	public Motion(Type type, Function function)
	{
		this(type, function, 1f);
	}

	/**
	 * Creates a new motion given its type, function, and scale.
	 * 
	 * @param type => The type of motion.
	 * @param function => The function of the motion.
	 * @param scale => The scale of the motion.
	 */
	public Motion(Type type, Function function, float scale)
	{
		this.type = type;
		this.function = function;
		this.scale = scale;
	}

	/**
	 * Applies the type and function to the given delta. If a scale was provided
	 * this magnifies the results of the type and function.
	 * 
	 * @param delta => The delta to apply this motion to.
	 * @return => The modified delta.
	 */
	public float getDelta(float delta)
	{
		float d = type.delta(delta, function);

		if (scale != 1f) {
			d = scale * d + (1 - scale) * delta;
		}

		return d;
	}

	/**
	 * Returns the type of thie motion.
	 */
	public Type getType() 
	{
		return type;
	}

	/**
	 * Returns the function of this motion.
	 */
	public Function getFunction() {
		return function;
	}

	/**
	 * Returns the scale of this motion.
	 */
	public float getScale() 
	{
		return scale;
	}

	/**
	 * Sets the type of this motion.
	 * 
	 * @param type => The new type of this motion.
	 */
	public void setType(Type type) 
	{
		this.type = type;
	}

	/**
	 * Sets the function of this motion.
	 * 
	 * @param function => The new function of this motion.
	 */
	public void setFunction(Function function)
	{
		this.function = function;
	}

	/**
	 * Sets the scale of this motion.
	 * 
	 * @param scale => The new scale of this motion.
	 */
	public void setScale(float scale) 
	{
		this.scale = scale;
	}


}
