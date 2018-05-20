package pfxEditor;

/**
 * Represents the color of a pixel on the screen of the shade of an object with
 * a red, green, blue, and alpha components, where alpha is the transparency of
 * the color.
 * 
 * @author Philip Diffenderfer
 */
public class Color {

	private float r;
	private float g;
	private float b;
	private float a;

	/**
	 * Default Constructor. Initializes a Color with a default of white.
	 */
	public Color() {
		this(1.0f, 1.0f, 1.0f, 1.0f);
	}

	/**
	 * Initializes a Color with values for each component except alpha ranging
	 * from 0 to 255 where 0 is the absence of the component (black) and 255 is
	 * where the component is full. The default alpha is 255, fully opaque.
	 */
	public Color(int red, int green, int blue) {
		this(red, green, blue, 255);
	}

	/**
	 * Initializes a Color with values for each component except alpha ranging
	 * from 0 to 255 where 0 is the absence of the component (black) and 255 is
	 * where the component is full.
	 */
	public Color(int red, int green, int blue, int alpha) {
		this((red % 256) / 255f, (green % 256) / 255f, (blue % 256) / 255f,
				(alpha % 256) / 255f);
	}

	/**
	 * Initializes a Color with values for each component except alpha ranging
	 * from 0.0 to 1.0 where 0.0 is the absence of the component (black) and 1.0
	 * is where the component is full. The default alpha is 1.0, fully opaque.
	 */
	public Color(float red, float green, float blue) {
		this(red, green, blue, 1.0f);
	}

	/**
	 * Initializes a Color with values for each component except alpha ranging
	 * from 0.0 to 1.0 where 0.0 is the absence of the component (black) and 1.0
	 * is where the component is full.
	 */
	public Color(float red, float green, float blue, float alpha) {
		r = red;
		g = green;
		b = blue;
		a = alpha;
	}

	/**
	 * Returns a java.awt.Color equivalent color.
	 */
	public java.awt.Color getJava2DColor() {
		return new java.awt.Color(r, g, b, a);
	}

	/**
	 * Returns the inverse of this color.
	 * 
	 * @return
	 */
	public Color getInverse() {
		return new Color(1f - r, 1f - g, 1f - b);
	}

	/**
	 * Sets the red component with a range of 0.0 to 1.0.
	 */
	public void setR(float red) {
		r = red;
	}

	/**
	 * Sets the green component with a range of 0.0 to 1.0.
	 */
	public void setG(float green) {
		g = green;
	}

	/**
	 * Sets the blue component with a range of 0.0 to 1.0.
	 */
	public void setB(float blue) {
		b = blue;
	}

	/**
	 * Sets the alpha component with a range of 0.0 to 1.0.
	 */
	public void setA(float alpha) {
		a = alpha;
	}

	/**
	 * Returns the red component.
	 */
	public float getR() {
		return r;
	}

	/**
	 * Returns the green component.
	 */
	public float getG() {
		return g;
	}

	/**
	 * Returns the blue component.
	 */
	public float getB() {
		return b;
	}

	/**
	 * Returns the alpha component.
	 */
	public float getA() {
		return a;
	}

	/**
	 * Returns the red component on a range of 0 to 255.
	 */
	public short getByteR() {
		return (short) (r * 255);
	}

	/**
	 * Returns the green component on a range of 0 to 255.
	 */
	public short getByteG() {
		return (short) (g * 255);
	}

	/**
	 * Returns the blue component on a range of 0 to 255.
	 */
	public short getByteB() {
		return (short) (b * 255);
	}

	/**
	 * Returns the alpha component on a range of 0 to 255.
	 */
	public short getByteA() {
		return (short) (a * 255);
	}

	/**
	 * Returns a string representation of the components in this class.
	 */
	@Override
	public String toString() {
		return String.format("{R<%s> G<%s> B<%s> A<%s>}", r, g, b, a);
	}

	/** Returns a fully opaque white color. * */
	public static Color white() {
		return new Color(255, 255, 255);
	}

	/** Returns a fully opaque black color. * */
	public static Color black() {
		return new Color(0, 0, 0);
	}

	/** Returns a fully opaque red color. * */
	public static Color red() {
		return new Color(255, 0, 0);
	}

	/** Returns a fully opaque green color. * */
	public static Color green() {
		return new Color(0, 255, 0);
	}

	/** Returns a fully opaque blue color. */
	public static Color blue() {
		return new Color(0, 0, 255);
	}

	/** Returns a fully opaque magenta color. */
	public static Color magenta() {
		return new Color(255, 0, 255);
	}

	/** Returns a fully opaque yellow color. */
	public static Color yellow() {
		return new Color(255, 255, 0);
	}

	/** Returns a fully opaque gold color. */
	public static Color gold() {
		return new Color(255, 127, 0);
	}

	/** Returns a fully opaque gray color. */
	public static Color gray() {
		return new Color(127, 127, 127);
	}

	/** Returns a fully opaque light gray color. */
	public static Color lightgray() {
		return new Color(191, 191, 191);
	}

	/** Returns a fully opaque dark gray color. */
	public static Color darkgray() {
		return new Color(63, 63, 63);
	}

	/** Returns a fully opaque orange color. */
	public static Color orange() {
		return new Color(255, 127, 0);
	}

	/** Returns a fully opaque purple color. */
	public static Color purple() {
		return new Color(127, 0, 127);
	}

	/** Returns a fully opaque brown color. */
	public static Color brown() {
		return new Color(127, 63, 0);
	}

}
