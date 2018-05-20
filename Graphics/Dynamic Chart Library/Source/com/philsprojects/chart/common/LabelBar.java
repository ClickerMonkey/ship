package com.philsprojects.chart.common;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

import com.philsprojects.chart.data.Dataset;
import com.philsprojects.chart.data.Datatable;
import com.philsprojects.chart.fills.Fill;
import com.philsprojects.chart.settings.Settings;

public class LabelBar
{

	private Settings settings;
	private Datatable table;

	private double labelAngle = 0.0;

	private int interval = 2;

	private Font font;
	private Fill fontFill;
	// private Outline fontOutline;

	private Padding padding = new Padding(0f);

	public LabelBar()
	{
	}

	public LabelBar(Settings settings, Datatable table)
	{
		this.settings = settings;
		this.table = table;
	}

	public void draw(Graphics2D gr)
	{
		int lists = table.getListCount();
		int sets = table.getSize();

		// The width in pixels of a single data set (spacing included)
		double setWidth = settings.getDatasetWidth(lists);

		// If the setWidth is 0 return to avoid divide-by-zero
		if (setWidth == 0.0)
			return;

		// The spacing in pixels between each data set.
		double setSpacing = settings.getDatasetSpacing();

		// The index of the first data set displayed
		int first = Math.max(0, settings.getFirstVisibleDataset(lists));
		// The index of the last data set displayed
		int last = Math.min(sets - 1, settings.getLastVisibleDataset(lists) + 2);

		// Set the font used to draw the labels and get the metrics of the
		// graphics font to calculate label dimensions.
		if (font != null)
			gr.setFont(font);

		FontMetrics fm = gr.getFontMetrics();

		// Label dimensions.
		int labelWidth, halfLabelWidth;
		int labelHeight = fm.getAscent();
		int halfLabelHeight = labelHeight >> 1;

		// Fix the rotation of the labels and always assume a positive
		// y-coordinate.
		double theta = Math.toRadians(360 - labelAngle);
		double cos = Math.cos(theta);
		double abscos = Math.abs(cos);
		double sin = Math.abs(Math.sin(theta));

		// The maximum space a visible label requires (horizontally) to avoid
		// label intersection.
		double maxSpace = 0.0, space;

		// Before any label drawing determine each labels dimensions
		for (int i = first; i <= last; i++)
		{
			labelWidth = fm.stringWidth(table.get(i).getName());

			space = Math.min(labelWidth, (sin * labelHeight) + (abscos * labelWidth));
			if (space > maxSpace)
				maxSpace = space;
		}

		// How many labels to skip due to long labels.
		// Normalize the skips and scale it based on the interval.
		int skip = (int)Math.max(1, Math.floor(Math.floor(maxSpace / (setWidth * 0.5)) / interval) * interval);

		// Adjust the first so it maintains a constant interval offset of skip.
		first -= first % skip;

		// The starting x-coordinate on the screen to start drawing
		// labels.
		// This accounts for the first label drawn and centering of the label
		// based on the overall width and spacing between sets.
		double offset = settings.getDatasetOffset() + (setWidth * first)
		+ ((setWidth - setSpacing) * 0.5);


		AffineTransform state;
		Dataset set;
		String setName;
		double radius;
		Rectangle2D bounds = new Rectangle2D.Double();

		for (int i = first; i <= last; i += skip)
		{
			set = table.get(i);
			setName = set.getName();

			labelWidth = fm.stringWidth(setName);
			halfLabelWidth = labelWidth >> 1;


		radius = Math.abs(sin * halfLabelWidth) + Math.abs(cos * halfLabelHeight);

		state = gr.getTransform();
		gr.translate(offset, padding.top + radius);
		gr.rotate(theta);

		if (fontFill != null)
		{
			bounds.setFrame(-labelWidth, -halfLabelHeight, labelWidth,
					labelHeight);
			fontFill.setShape(bounds);
			fontFill.select(gr);
			gr.drawString(setName, -halfLabelWidth, halfLabelHeight);
		}

		gr.setTransform(state);

		offset += (setWidth * skip);
		}
	}

	/**
	 * @return the settings
	 */
	public Settings getSettings()
	{
		return settings;
	}

	/**
	 * @param settings
	 *                the settings to set
	 */
	public void setSettings(Settings settings)
	{
		this.settings = settings;
	}

	/**
	 * @return the table
	 */
	public Datatable getTable()
	{
		return table;
	}

	/**
	 * @return the labelAngle
	 */
	public double getLabelAngle()
	{
		return labelAngle;
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
	 * @param table
	 *                the table to set
	 */
	public void setTable(Datatable table)
	{
		this.table = table;
	}

	/**
	 * @param labelAngle
	 *                the labelAngle to set
	 */
	public void setLabelAngle(double labelAngle)
	{
		this.labelAngle = labelAngle;
	}

	/**
	 * @param font
	 *                the font to set
	 */
	public void setFont(Font font)
	{
		this.font = font;
	}

	/**
	 * @param fontFill
	 *                the fontFill to set
	 */
	public void setFontFill(Fill fontFill)
	{
		this.fontFill = fontFill;
	}

	/**
	 * @return the interval
	 */
	public int getInterval()
	{
		return interval;
	}

	/**
	 * @return the padding
	 */
	public Padding getPadding()
	{
		return padding;
	}

	/**
	 * @param interval
	 *                the interval to set
	 */
	public void setInterval(int interval)
	{
		this.interval = interval;
	}

	/**
	 * @param padding
	 *                the padding to set
	 */
	public void setPadding(Padding padding)
	{
		this.padding = padding;
	}

}
