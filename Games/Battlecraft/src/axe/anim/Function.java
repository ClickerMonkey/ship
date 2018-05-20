package axe.anim;

public class Function 
{
	public interface Type {
		public float delta(float d, Method f);
	}
	public interface Method {
		public float motion(float d);
	}

	public static final Type In = new Type() {
		public float delta(float d, Method f) {
			return f.motion(d);
		}
	};
	public static final Type Out = new Type() {
		public float delta(float d, Method f) {
			return (1 - f.motion(1 - d));
		}
	};
	public static final Type InOut = new Type() {
		public float delta(float d, Method f) {
			if (d < 0.5)
				return f.motion(2 * d) * 0.5f;

			return (1 - f.motion(2 - 2 * d) * 0.5f);
		}
	};
	
	

	public static final Method Linear = new Method() {
		public float motion(float d) {
			return d;
		}
	};
	public static Method Quadratic = new Method() {
		public float motion(float d) {
			return d * d;
		}
	};
	public static Method Cubic = new Method() {
		public float motion(float d) {
			return d * d * d;
		}
	};
	public static Method Quartic = new Method() {
		public float motion(float d) {
			float d2 = d * d;
			return d2 * d2;
		}
	};
	public static Method Quintic = new Method() {
		public float motion(float d) {
			float d2 = d * d;
			return d2 * d2 * d;
		}
	};
	public static Method Back = new Method() {
		public float motion(float d) {
			float d2 = d * d;
			float d3 = d2 * d;
			return d3 + d2 - d;
		}
	};
	public static Method Sine = new Method() {
		private double FREQUENCY = Math.PI * 0.5;
		public float motion(float d) {
			return (float)Math.sin(d * FREQUENCY);
		}
	};
	public static Method Elastic = new Method() {
		private double FREQUENCY = Math.PI * 3.5;
		public float motion(float d) {
			float d2 = d * d;
			float d3 = d2 * d;
			float scale = d2 * ((2 * d3) + d2 - (4 * d) + 2);
			float wave = -(float)Math.sin(d * FREQUENCY);
			return scale * wave;
		}
	};
	public static Method Revisit = new Method() {
		private double FREQUENCY = Math.PI;
		public float motion(float d) {
			return (float)Math.abs(-Math.sin(d * FREQUENCY) + d);
		}
	};
	public static Method SlowBounce = new Method() {
		private double FREQUENCY = Math.PI * Math.PI * 1.5;
		public float motion(float d) {
			float d2 = d * d;
			return (float)(1 - Math.abs((1 - d2) * Math.cos(d2 * d * FREQUENCY)));
		}
	};
	public static Method Bounce = new Method() {
		private double FREQUENCY = Math.PI * Math.PI * 1.5;
		public float motion(float d) {
			return (float)(1 - Math.abs((1 - d) * Math.cos(d * d * FREQUENCY)));
		}
	};
	public static Method SmallBounce = new Method() {
		private double FREQUENCY = Math.PI * Math.PI * 1.5;
		public float motion(float d) {
			float inv = 1 - d;
			return (float)(1 - Math.abs(inv * inv * Math.cos(d * d * FREQUENCY)));
		}
	};
	public static Method TinyBounce = new Method() {
		private double FREQUENCY = 7;
		public float motion(float d) {
			float inv = 1 - d;
			return (float)(1 - Math.abs(inv * inv * Math.cos(d * d * FREQUENCY)));
		}
	};
	public static Method Hesitant = new Method() {
		public float motion(float d) {
			return (float)(Math.cos(d * d * 12) * d * (1 - d) + d);
		}
	};
	public static Method Lasso = new Method() {
		public float motion(float d) {
			float d2 = d * d;
			return (float)(1 - Math.cos(d2 * d * 36) * (1 - d));
		}
	};
	public static Method Sqrt = new Method() {
		public float motion(float d) {
			return (float)Math.sqrt(d);
		}
	};
	public static Method Log10 = new Method() {
		public float motion(float d) {
			return (float)((Math.log10(d) + 2) * 0.5);
		}
	};
	public static Method Slingshot = new Method() {
		public float motion(float d) {
			if (d < 0.7f)
				return (d * -0.357f);

			float x = d - 0.7f;
			return ((x * x * 27.5f - 0.5f) * 0.5f);
		}
	};


	private Type type;
	private Method method;
	private float scale;

	public Function(Type type, Method method) {
		this(type, method, 1f);
	}

	public Function(Type type, Method method, float scale) {
		this.type = type;
		this.method = method;
		this.scale = scale;
	}

	public float delta(float delta) {
		float d = type.delta(delta, method);
		if (scale != 1f) {
			d = scale * d + (1 - scale) * delta;
		}
		return d;
	}

	public Type type() {
		return type;
	}

	public void type(Type type) {
		this.type = type;
	}

	public Method method() {
		return method;
	}

	public void method(Method method) {
		this.method = method;
	}

	public float scale() {
		return scale;
	}

	public void scale(float scale) {
		this.scale = scale;
	}


}
