package com.philsprojects.chart.common;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import com.philsprojects.chart.fills.Fill;
import com.philsprojects.chart.outlines.Outline;
import com.philsprojects.chart.view.Viewport;

public class Label extends Component
{

	private String label;

	private Font font;
	private Fill fontFill;
	private Outline fontOutline;

	private double rotation;
	private double axisRotation = Math.PI;


	public Label(boolean antialiasing, Rectangle bounds)
	{
		super(antialiasing, bounds);
	}

	public Label(boolean antialiasing, int offsetX, int offsetY, int width, int height)
	{
		super(antialiasing, offsetX, offsetY, width, height);
	}

	public Label(boolean antialiasing, Shape clipping)
	{
		super(antialiasing, clipping);
	}

	public boolean listensTo(Viewport view)
	{
		return false;
	}
	
	@Override
	protected void onDraw(Graphics2D gr)
	{
		Rectangle2D area = padding.getClip(bounds);

		/** DEBUG **/
		gr.setColor(Color.green);
		gr.draw(area);
		/** DEBUG **/

		// If a font was provided then set it as the current font.
		if (font != null)
			gr.setFont(font);

		// Calculate the center point for drawing the label.
		Point2D center = new Point2D.Double(area.getX(), area.getY());
		anchor.clip(center, area.getWidth(), area.getHeight());

		/** DEBUG **/
		gr.setColor(Color.red);
		gr.drawOval((int)center.getX() - 3, (int)center.getY() - 3, 6, 6);
		/** DEBUG **/

		// Get the metrics of the current graphics font.
		FontMetrics fm = gr.getFontMetrics();

		// Calculate the bounds of the string.
		int labelWidth = fm.stringWidth(label);
		int labelHeight = fm.getAscent();

		double acos = StrictMath.cos(axisRotation);
		double asin = -StrictMath.sin(axisRotation);
		double halfWidth = labelWidth * 0.5;
		double halfHeight = labelHeight * 0.5;
		double wextent = asin * halfHeight + acos * halfWidth;
		double hextent = -asin * halfWidth + acos * halfHeight;
		double cos = StrictMath.cos(rotation);
		double sin = StrictMath.sin(rotation);
		double l = Math.abs(sin * wextent) + Math.abs(cos * hextent);
		double x = asin * l;
		double y = acos * l;

		AffineTransform oldState = gr.getTransform();
		AffineTransform newState = new AffineTransform();

		newState.translate(center.getX(), center.getY());
		newState.translate(x, y);
		newState.rotate(rotation);
		gr.setTransform(newState);

		if (fontFill != null)
		{
			fontFill.setShape(bounds);
			fontFill.select(gr);
			gr.drawString(label, -(int)halfWidth, (int)halfHeight);
		}


		if (fontOutline != null)
		{
			fontOutline.setShape(bounds);
			fontOutline.select(gr);
			//	    gr.drawString(label, (float)offset.getX(), labelHeight + (float)offset.getY());
		}

		gr.setTransform(oldState);
	}


	/**
	 * @return the label
	 */
	public String getLabel()
	{
		return label;
	}

	/**
	 * @param label the label to set
	 */
	public void setLabel(String label)
	{
		this.label = label;
		requestRedraw();
	}

	/**
	 * @return the font
	 */
	public Font getFont()
	{
		return font;
	}

	/**
	 * @param font the font to set
	 */
	public void setFont(Font font)
	{
		this.font = font;
		requestRedraw();
	}

	/**
	 * @return the fontFill
	 */
	public Fill getFontFill()
	{
		return fontFill;
	}

	/**
	 * @param fontFill the fontFill to set
	 */
	public void setFontFill(Fill fontFill)
	{
		this.fontFill = fontFill;
		requestRedraw();
	}

	/**
	 * @return the fontOutline
	 */
	public Outline getFontOutline()
	{
		return fontOutline;
	}

	/**
	 * @param fontOutline the fontOutline to set
	 */
	public void setFontOutline(Outline fontOutline)
	{
		this.fontOutline = fontOutline;
		requestRedraw();
	}

	/**
	 * @return the rotation
	 */
	public double getRotation()
	{
		return Math.toDegrees(rotation);
	}

	/**
	 * @param rotation the rotation to set
	 */
	public void setRotation(double rotation)
	{
		this.rotation = Math.toRadians(rotation);
		requestRedraw();
	}

	/**
	 * @return the axisRotation
	 */
	public double getAxisRotation() {
		return Math.toDegrees(axisRotation);
	}

	/**
	 * @param axisRotation the axisRotation to set
	 */
	public void setAxisRotation(double axisRotation) {
		this.axisRotation = Math.toRadians(axisRotation);
		requestRedraw();
	}

}
