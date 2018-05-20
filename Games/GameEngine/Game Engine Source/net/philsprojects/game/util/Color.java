/**************************************************\ 
 *            ______ ___    __   _    _____       * 
 *           / ____//   |  /  | / |  / ___/       *
 *          / /___ / /| | /   |/  | / __/         *
 *         / /_| // __  |/ /|  /| |/ /__          *
 *        /_____//_/  |_|_/ |_/ |_|\___/          *
 *     _____ __   _  ______ ______ __   _  _____  *
 *    / ___//  | / // ____//_  __//  | / // ___/  *
 *   / __/ / | |/ // /___   / /  / | |/ // __/    *
 *  / /__ / /|   // /_| /__/ /_ / /|   // /__     *
 *  \___//_/ |__//_____//_____//_/ |__/ \___/     *
 *                                                *
 * Author: Philip Diffenderfer                    *
 *  Class: engine.util.Color                      *
 *                                                *
 \**************************************************/

package net.philsprojects.game.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import net.philsprojects.game.IBinary;
import net.philsprojects.game.IClone;


/**
 * Represents the color of a pixel on the screen of the shade of an object with
 * a red, green, blue, and alpha components, where alpha is the transparency of
 * the color.
 * 
 * @author Philip Diffenderfer
 */
public class Color implements IBinary, IClone<Color>
{

	// The red value of a color ranging from 0 to 1.
	private float _r;

	// The green value of a color ranging from 0 to 1.
	private float _g;

	// The blue value of a color ranging from 0 to 1.
	private float _b;

	// The alpha value of a color ranging from 0 to 1.
	private float _a;

	/**
	 * Initializes a Color with a default shade of white.
	 */
	public Color()
	{
		this(1f, 1f, 1f, 1f);
	}

	/**
	 * Initializes an opaque Color with the Red, Green, and Blue values in the
	 * range of 0 to 255.
	 * 
	 * @param red =>
	 *           The red value in this color between 0 and 255, where 0 is the
	 *           complete absence of red and 255 is a full red value.
	 * @param green =>
	 *           The green value in this color between 0 and 255, where 0 is the
	 *           complete absence of green and 255 is a full green value.
	 * @param blue =>
	 *           The blue value in this color between 0 and 255, where 0 is the
	 *           complete absence of blue and 255 is a full blue value.
	 */
	public Color(int red, int green, int blue)
	{
		this(red, green, blue, 255);
	}

	/**
	 * Initializes a Color with the Red, Green, Blue, and Alpha values in the
	 * range of 0 to 255.
	 * 
	 * @param red =>
	 *           The red value in this color between 0 and 255, where 0 is the
	 *           complete absence of red and 255 is a full red value.
	 * @param green =>
	 *           The green value in this color between 0 and 255, where 0 is the
	 *           complete absence of green and 255 is a full green value.
	 * @param blue =>
	 *           The blue value in this color between 0 and 255, where 0 is the
	 *           complete absence of blue and 255 is a full blue value.
	 * @param alpha =>
	 *           The alpha value in this color between 0 and 255, where 0 is a
	 *           completely transparent color and 255 is fully opaque.
	 */
	public Color(int red, int green, int blue, int alpha)
	{
		this((red % 256) / 255f, (green % 256) / 255f, (blue % 256) / 255f, (alpha % 256) / 255f);
	}

	/**
	 * Initializes an opaque Color with the Red, Green, and Blue values in the
	 * range of 0 to 1.
	 * 
	 * @param red =>
	 *           The red value in this color between 0 and 1, where 0 is the
	 *           complete absence of red and 1 is a full red value.
	 * @param green =>
	 *           The green value in this color between 0 and 1, where 0 is the
	 *           complete absence of green and 1 is a full green value.
	 * @param blue =>
	 *           The blue value in this color between 0 and 1, where 0 is the
	 *           complete absence of blue and 1 is a full blue value.
	 */
	public Color(float red, float green, float blue)
	{
		this(red, green, blue, 1f);
	}

	/**
	 * Initializes a Color with the Red, Green, Blue, and Alpha values in the
	 * range of 0 to 1.
	 * 
	 * @param red =>
	 *           The red value in this color between 0 and 1, where 0 is the
	 *           complete absence of red and 1 is a full red value.
	 * @param green =>
	 *           The green value in this color between 0 and 1, where 0 is the
	 *           complete absence of green and 1 is a full green value.
	 * @param blue =>
	 *           The blue value in this color between 0 and 1, where 0 is the
	 *           complete absence of blue and 1 is a full blue value.
	 * @param alpha =>
	 *           The alpha value in this color between 0 and 1, where 0 is a
	 *           completely transparent color and 1 is fully opaque.
	 */
	public Color(float red, float green, float blue, float alpha)
	{
		_r = red;
		_g = green;
		_b = blue;
		_a = alpha;
	}

	/**
	 * Returns a java.awt.Color equivalent color.
	 */
	public java.awt.Color getJava2DColor()
	{
		return new java.awt.Color(_r, _g, _b, _a);
	}

	/**
	 * Returns the inverse of this color.
	 */
	public Color getInverse()
	{
		return new Color(1f - _r, 1f - _g, 1f - _b);
	}

	/**
	 * Returns a new Color with the same red, green, and blue values but a
	 * different alpha value.
	 * 
	 * @param alpha =>
	 *           The alpha value in this color between 0 and 1, where 0 is a
	 *           completely transparent color and 1 is fully opaque.
	 */
	public Color modAlpha(float alpha)
	{
		return new Color(_r, _g, _b, alpha);
	}

	/**
	 * Returns a new Color with the same green, blue, and alpha values but a
	 * different red value.
	 * 
	 * @param red =>
	 *           The red value in this color between 0 and 1, where 0 is the
	 *           complete absence of red and 1 is a full red value.
	 */
	public Color modRed(float red)
	{
		return new Color(red, _g, _b, _a);
	}

	/**
	 * Returns a new Color with the same red, blue, and alpha values but a
	 * different green value.
	 * 
	 * @param green =>
	 *           The green value in this color between 0 and 1, where 0 is the
	 *           complete absence of green and 1 is a full green value.
	 */
	public Color modGreen(float green)
	{
		return new Color(_r, green, _b, _a);
	}

	/**
	 * Returns a new Color with the same red, green, and alpha values but a
	 * different blue value.
	 * 
	 * @param blue =>
	 *           The blue value in this color between 0 and 1, where 0 is the
	 *           complete absence of blue and 1 is a full blue value.
	 */
	public Color modBlue(float blue)
	{
		return new Color(_r, _g, blue, _a);
	}

	/**
	 * Sets the red value of this color.
	 * 
	 * @param red =>
	 *           The red value in this color between 0 and 1, where 0 is the
	 *           complete absence of red and 1 is a full red value.
	 */
	public void setR(float red)
	{
		_r = red;
	}

	/**
	 * Sets the green value of this color.
	 * 
	 * @param green =>
	 *           The green value in this color between 0 and 1, where 0 is the
	 *           complete absence of green and 1 is a full green value.
	 */
	public void setG(float green)
	{
		_g = green;
	}

	/**
	 * Sets the blue value of this color.
	 * 
	 * @param blue =>
	 *           The blue value in this color between 0 and 1, where 0 is the
	 *           complete absence of blue and 1 is a full blue value.
	 */
	public void setB(float blue)
	{
		_b = blue;
	}

	/**
	 * Sets the alpha value of this color.
	 * 
	 * @param alpha =>
	 *           The alpha value in this color between 0 and 1, where 0 is a
	 *           completely transparent color and 1 is fully opaque.
	 */
	public void setA(float alpha)
	{
		_a = alpha;
	}

	/**
	 * Returns the red value in this color between 0 and 1, where 0 is the
	 * complete absence of red and 1 is a full red value.
	 */
	public float getR()
	{
		return _r;
	}

	/**
	 * Returns the green value in this color between 0 and 1, where 0 is the
	 * complete absence of green and 1 is a full green value.
	 */
	public float getG()
	{
		return _g;
	}

	/**
	 * Returns the blue value in this color between 0 and 1, where 0 is the
	 * complete absence of blue and 1 is a full blue value.
	 */
	public float getB()
	{
		return _b;
	}

	/**
	 * Returns the alpha value in this color between 0 and 1, where 0 is a
	 * completely transparent color and 1 is fully opaque.
	 */
	public float getA()
	{
		return _a;
	}

	/**
	 * Returns the red value in this color between 0 and 255, where 0 is the
	 * complete absence of red and 255 is a full red value.
	 */
	public short getByteR()
	{
		return (short)(_r * 255);
	}

	/**
	 * Returns the green value in this color between 0 and 255, where 0 is the
	 * complete absence of green and 255 is a full green value.
	 */
	public short getByteG()
	{
		return (short)(_g * 255);
	}

	/**
	 * Returns the blue value in this color between 0 and 255, where 0 is the
	 * complete absence of blue and 255 is a full blue value.
	 */
	public short getByteB()
	{
		return (short)(_b * 255);
	}

	/**
	 * Returns the alpha value in this color between 0 and 255, where 0 is a
	 * completely transparent color and 255 is fully opaque.
	 */
	public short getByteA()
	{
		return (short)(_a * 255);
	}

	/**
	 * Returns an exact clone of this color.
	 */
	public Color getClone()
	{
		return new Color(_r, _g, _b, _a);
	}

	public int toInteger()
	{
		int r = (int)(_r * 255) % 256;
		int g = (int)(_g * 255) % 256;
		int b = (int)(_b * 255) % 256;
		int a = (int)(_a * 255) % 256;
		int v = b | (g << 8) | (r << 16) | (a << 24);
		return v;
	}

	public void fromInteger(int color)
	{
		_a = ((color >>> 24) & 0xFF) / 255f;
		_r = ((color >>> 16) & 0xFF) / 255f;
		_g = ((color >>>  8) & 0xFF) / 255f;
		_b = (color & 0xFF) / 255f;
	}

	/**
	 * Returns a string representation of the values in this class.
	 */
	@Override
	public String toString()
	{
		return String.format("{R<%.3f> G<%.3f> B<%.3f> A<%.3f>}", _r, _g, _b, _a);
	}

	/**
	 * Returns a color <code>(delta*100)%</code> between start and end.
	 * 
	 * @param start =>
	 *           The Color at the start, which is at 0.
	 * @param end =>
	 *           The Color at the end, which is 1.
	 * @param delta =>
	 *           A value between 0 and 1 which represents the color between the
	 *           start and the end.
	 */
	public static Color delta(Color start, Color end, float delta)
	{
		if (delta >= 1f)
			return end;
		if (delta <= 0f)
			return start;
		float r = (end._r - start._r) * delta + start._r;
		float g = (end._g - start._g) * delta + start._g;
		float b = (end._b - start._b) * delta + start._b;
		float a = (end._a - start._a) * delta + start._a;
		return new Color(r, g, b, a);
	}

	/**
	 * Returns a fully opaque white color.
	 */
	public static Color white()
	{
		return new Color(1f, 1f, 1f);
	}

	/**
	 * Returns a fully opaque black color.
	 */
	public static Color black()
	{
		return new Color(0f, 0f, 0f);
	}

	/**
	 * Returns a fully opaque red color.
	 */
	public static Color red()
	{
		return new Color(1f, 0f, 0f);
	}

	/**
	 * Returns a fully opaque green color.
	 */
	public static Color green()
	{
		return new Color(0f, 1f, 0f);
	}

	/**
	 * Returns a fully opaque blue color.
	 */
	public static Color blue()
	{
		return new Color(0f, 0f, 1f);
	}

	/**
	 * Returns a fully opaque magenta color.
	 */
	public static Color magenta()
	{
		return new Color(1f, 0f, 1f);
	}

	/**
	 * Returns a fully opaque yellow color.
	 */
	public static Color yellow()
	{
		return new Color(1f, 1f, 0f);
	}

	/**
	 * Returns a fully opaque gold color.
	 */
	public static Color gold()
	{
		return new Color(1f, 0.5f, 0f);
	}

	/**
	 * Returns a fully opaque gray color.
	 */
	public static Color gray()
	{
		return new Color(0.5f, 0.5f, 0.5f);
	}

	/**
	 * Returns a fully opaque light gray color.
	 */
	public static Color lightgray()
	{
		return new Color(0.75f, 0.75f, 0.75f);
	}

	/**
	 * Returns a fully opaque dark gray color.
	 */
	public static Color darkgray()
	{
		return new Color(0.25f, 0.25f, 0.25f);
	}

	/**
	 * Returns a fully opaque orange color.
	 */
	public static Color orange()
	{
		return new Color(1f, 0.5f, 0f);
	}

	/**
	 * Returns a fully opaque purple color.
	 */
	public static Color purple()
	{
		return new Color(0.5f, 0f, 0.5f);
	}

	/**
	 * Returns a fully opaque brown color.
	 */
	public static Color brown()
	{
		return new Color(0.5f, 0.25f, 0f);
	}


	public Color(DataInputStream reader) throws Exception
	{
		read(reader);
	}

	public void read(DataInputStream reader) throws Exception
	{
		_r = reader.readFloat();
		_g = reader.readFloat();
		_b = reader.readFloat();
		_a = reader.readFloat();
	}

	public void write(DataOutputStream writer) throws Exception
	{
		writer.writeFloat(_r);
		writer.writeFloat(_g);
		writer.writeFloat(_b);
		writer.writeFloat(_a);
	}


}
