package com.philsprojects.chart.common;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import com.philsprojects.chart.fills.Fill;
import com.philsprojects.chart.fills.FillDelta;

public class ValueBar extends ChartVisual
{

	private ValueFormat formatter;

	private Font font;
	private FillDelta fontFill;

	private Anchor anchor;

	public ValueBar(ValueFormat format)
	{
		this.formatter = format;
	}

	@Override
	public void draw(Graphics2D gr)
	{
		if (font != null)
			gr.setFont(font);

		draw(gr, frequency, 10);
	}

	protected void draw(Graphics2D gr, double frequency, int max)
	{
		if (max <= 0) {
			return;
		}
		
		// Determine if this is the base frequency, if it is then do not
		// skip any lines.
		boolean isBaseFrequency = (frequency == this.frequency);

		// Convert the given frequency to screen coordinates (the length of
		// the frequency as pixels on the screen)
		double freqY = view.toScreenSizeY(frequency);

		// Get the size/ascent of the current font...
		Font font = gr.getFont();
		float fontSize = font.getSize2D();

		// If the frequency is less then the visible threshold including the
		// string heights then we can't draw any more lines...
		if (freqY <= endFrequency + fontSize)
			return;

		// Recursively draw the next smallest frequency until the visible
		// threshold is reached.
		draw(gr, frequency / interval, max - 1);

		// Get the font metrics of the current font.
		FontMetrics fm = gr.getFontMetrics(font);

		// Determine the normalized distance this frequency is away from
		// the most visible frequency.
		double deltaY = 1.0 - (freqY / startFrequency);

		// Calculate the additional offset the bottom padding causes
		double bottomPadding =  view.toWorldSizeY(padding.bottom);

		// Calculate the line number of the line closest to the bottom side
		// of the view port. This is used to calculate the y-coordinate to
		// start drawing as well as knowing which lines to skip.
		int lineNumberY = (int)Math.floor((view.getBottom() - offset - bottomPadding) / frequency);

		// Calculate the y-coordinate to start drawing the first line in
		// view port space.
		double startY = lineNumberY * frequency + offset;

		// Convert the y-coordinate to screen space and adjust for string height.
		double actualY = view.toScreenY(startY) + (fontSize * 0.5);

		// Create a copy of the bounds of the destination canvas.
		Rectangle2D area = new Rectangle2D.Double();
		area.setFrame(0, 0, destination.getCanvasWidth(), destination.getCanvasHeight());

		// Clip the bounds based on the padding.
		padding.clip(area);

		// Set and select the outline based on the visibility of this frequency.
		fontFill.setDelta(deltaY);
		fontFill.select(gr);

		String value;
		int valueWidth;
		// Continuously draw lines starting from the bottom towards the top
		// until we've come to the top padding.
		while (actualY >= area.getY() - (fontSize * 0.5))
		{
			// If this is the base frequency (all lines drawn) or the line
			// is not a skipped line...
			if (isBaseFrequency || lineNumberY % interval != 0)
			{
				value = formatter.format(startY);
				valueWidth = fm.stringWidth(value);

				float x = (float)anchor.clipX(area, valueWidth);

				gr.drawString(value, x, (float)actualY);
			}

			actualY -= freqY;
			startY += frequency;
			lineNumberY++;
		}
	}

	/**
	 * @return the font
	 */
	public Font getFont()
	{
		return font;
	}

	/**
	 * @return the fontFill
	 */
	public Fill getFontFill()
	{
		return fontFill;
	}

	/**
	 * @param font the font to set
	 */
	public void setFont(Font font)
	{
		this.font = font;
	}

	/**
	 * @return the formatter
	 */
	public ValueFormat getFormatter()
	{
		return formatter;
	}

	/**
	 * @return the anchor
	 */
	public Anchor getAnchor()
	{
		return anchor;
	}

	/**
	 * @param formatter the formatter to set
	 */
	public void setFormatter(ValueFormat formatter)
	{
		this.formatter = formatter;
	}

	/**
	 * @param fontFill the fontFill to set
	 */
	public void setFontFill(FillDelta fontFill)
	{
		this.fontFill = fontFill;
	}

	/**
	 * @param anchor the anchor to set
	 */
	public void setAnchor(Anchor anchor)
	{
		this.anchor = anchor;
	}


}
