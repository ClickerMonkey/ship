package shipgames;

public class Motion 
{
    public interface Type {
	public float delta(float d, Function f);
    }
    public interface Function {
	public float motion(float d);
    }

    public static final Type In = new Type() {
	public float delta(float d, Function f) {
	    return f.motion(d);
	}
    };
    public static final Type Out = new Type() {
	public float delta(float d, Function f) {
	    return (1 - f.motion(1 - d));
	}
    };
    public static final Type InOut = new Type() {
	public float delta(float d, Function f) {
	    if (d < 0.5)
		return f.motion(2 * d) * 0.5f;

	    return (1 - f.motion(2 - 2 * d) * 0.5f);
	}
    };

    public static final Function Linear = new Function() {
	public float motion(float d) {
	    return d;
	}
    };
    public static Function Quadtratic = new Function() {
	public float motion(float d) {
	    return d * d;
	}
    };
    public static Function Cubic = new Function() {
	public float motion(float d) {
	    return d * d * d;
	}
    };
    public static Function Quartic = new Function() {
	public float motion(float d) {
	    return (d *= d) * d;
	}
    };
    public static Function Quintic = new Function() {
	public float motion(float d) {
	    return (d *= d) * d * d;
	}
    };
    public static Function Back = new Function() {
	public float motion(float d) {
	    float d2 = d * d;
	    float d3 = d2 * d;
	    return d3 + d2 - d;
	}
    };
    public static Function Sine = new Function() {
	private double FREQUENCY = Math.PI * 0.5;
	public float motion(float d) {
	    return (float)Math.sin(d * FREQUENCY);
	}
    };
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
    public static Function Revisit = new Function() {
	private double FREQUENCY = Math.PI;
	public float motion(float d) {
	    return (float)Math.abs(-Math.sin(d * FREQUENCY) + d);
	}
    };
    public static Function SlowBounce = new Function() {
	private double FREQUENCY = Math.PI * Math.PI * 1.5;
	public float motion(float d) {
	    float d2 = d * d;
	    return (float)(1 - Math.abs((1 - d2) * Math.cos(d2 * d * FREQUENCY)));
	}
    };
    public static Function SmallBounce = new Function() {
	private double FREQUENCY = Math.PI * Math.PI * 1.5;
	public float motion(float d) {
	    float inv = 1 - d;
	    return (float)(1 - Math.abs(inv * inv * Math.cos(d * d * FREQUENCY)));
	}
    };
    public static Function TinyBounce = new Function() {
	private double FREQUENCY = 7;
	public float motion(float d) {
	    float inv = 1 - d;
	    return (float)(1 - Math.abs(inv * inv * Math.cos(d * d * FREQUENCY)));
	}
    };
    public static Function Bounce = new Function() {
	private double FREQUENCY = Math.PI * Math.PI * 1.5;
	public float motion(float d) {
	    return (float)(1 - Math.abs((1 - d) * Math.cos(d * d * FREQUENCY)));
	}
    };
    public static Function Hesitant = new Function() {
	public float motion(float d) {
	    return (float)(Math.cos(d * d * 12) * d * (1 - d) + d);
	}
    };
    public static Function Lasso = new Function() {
	public float motion(float d) {
	    float d2 = d * d;
	    return (float)(1 - Math.cos(d2 * d * 36) * (1 - d));
	}
    };
    public static Function Sqrt = new Function() {
	public float motion(float d) {
	    return (float)Math.sqrt(d);
	}
    };
    public static Function Log10 = new Function() {
	public float motion(float d) {
	    return (float)((Math.log10(d) + 2) * 0.5);
	}
    };
    public static Function Slingshot = new Function() {
	public float motion(float d) {
	    if (d < 0.7f)
		return (d * -0.357f);
	    
	    float x = d - 0.7f;
	    return ((x * x * 27.5f - 0.5f) * 0.5f);
	}
    };
    

    private Type type;
    private Function function;
    private float scale;

    public Motion(Type type, Function function)
    {
	this(type, function, 1f);
    }

    public Motion(Type type, Function function, float scale)
    {
	this.type = type;
	this.function = function;
	this.scale = scale;
    }

    public float getDelta(float delta)
    {
	float d = type.delta(delta, function);

	if (scale != 1f)
	{
	    d = scale * d + (1 - scale) * delta;
	}

	return d;
    }

    /**
     * @return the type
     */
    public Type getType() {
	return type;
    }

    /**
     * @return the function
     */
    public Function getFunction() {
	return function;
    }

    /**
     * @return the scale
     */
    public float getScale() {
	return scale;
    }

    /**
     * @param type the type to set
     */
    public void setType(Type type) {
	this.type = type;
    }

    /**
     * @param function the function to set
     */
    public void setFunction(Function function) {
	this.function = function;
    }

    /**
     * @param scale the scale to set
     */
    public void setScale(float scale) {
	this.scale = scale;
    }


}
