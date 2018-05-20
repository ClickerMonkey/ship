package com.philsprojects.chart;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import com.philsprojects.chart.common.Anchor;
import com.philsprojects.chart.common.Component;
import com.philsprojects.chart.common.Padding;
import com.philsprojects.chart.fills.Fill;
import com.philsprojects.chart.icons.Icon;
import com.philsprojects.chart.outlines.Outline;
import com.philsprojects.chart.view.Viewport;

public class Legend extends Component
{

	/**
	 * The orientation to draw the icons and labels.
	 * 
	 * @author Philip Diffenderfer
	 */
	public enum Orientation
	{
		/**
		 * Renders the icons and labels vertically where each icon and label
		 * pair is on one line and the following pairs on the lines below.
		 */
		Vertical,
		/**
		 * Renders the icons and labels horizontally where each definition
		 * is drawn in the order of icon, label, icon, label... on one line.
		 */
		Horizontal
	}

	/**
	 * The default padding for the legend in pixels.
	 */
	public static final float DEFAULT_PADDING = 10f;

	// The height to draw the labels at to set separation between lines when
	// the orientation of the legend is vertical.
	private float minLabelHeight;

	// The padding between labels in pixels when the orientation of the legend
	// is horizontal.
	private float labelPadding;

	// The padding between the icon and the label in pixels.
	private float iconPadding;

	// The orientation of the legend (which direction to draw the definitions).
	private Orientation orientation;

	// The list of legend definitions, each associated with a data list
	// on a chart.
	private LegendDefinition[] definitions;

	/**
	 * 
	 * @param antialiasing
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param lists => The number of 
	 */
	public Legend(boolean antialiasing, int x, int y, int width, int height,
			int lists)
	{
		super(antialiasing, x, y, width, height);

		definitions = new LegendDefinition[lists];

		anchor = Anchor.Default;
		padding = new Padding(DEFAULT_PADDING);
	}

	@Override
	public boolean listensTo(Viewport view)
	{
		return false;
	}
	
	@Override
	protected void onDraw(Graphics2D gr)
	{
		// Get the padding rectangle based on the clipping.
		Rectangle2D localBounds = new Rectangle2D.Double(0, 0, 
				super.bounds.getWidth(), super.bounds.getHeight());

		Rectangle2D area = padding.getClip(localBounds);

		//	/**=========DEBUG========*/
		//	gr.setColor(Color.red);
		//	gr.draw(area);
		//	/**=========DEBUG========*/

		// ... Do some calculations.
		int lists = definitions.length;
		int maxLabelWidth = getMaxLabelWidth(gr);
		int totalLabelWidth = getTotalLabelWidth(gr);
		int maxLabelHeight = getMaxLabelHeight(gr);
		double maxIconSize = getMaxIconSize();
		double definitionHeight = Math.max(minLabelHeight, Math.max(maxLabelHeight, maxIconSize));
		double halfDefinitionHeight = definitionHeight * 0.5;

		// Determine the bounding width and height of the legend based
		// on its orientation.
		double width = 0.0;
		double height = 0.0;

		switch (orientation)
		{
		case Vertical:
			width = maxLabelWidth + maxIconSize + iconPadding;
			height = definitionHeight * lists;
			break;

		case Horizontal:
			width = (maxIconSize + iconPadding) * lists + totalLabelWidth;
			width += labelPadding * (lists - 1);
			height = definitionHeight;
			break;
		}

		// Get the legend's bounding rectangle based on the anchor.
		anchor.clip(area, width, height);

		//	/**=========DEBUG========*/
		//	gr.setColor(Color.green);
		//	gr.draw(area);
		//	/**=========DEBUG========*/

		// Get each icons center point and each definitions corresponding 
		// bounding rectangle based on it's index and orientation.
		Point2D.Double[] icons = new Point2D.Double[lists];
		Rectangle2D.Double bounds[] = new Rectangle2D.Double[lists];

		switch (orientation)
		{
		case Vertical:
			for (int i = 0; i < lists; i++)
			{
				bounds[i] = new Rectangle2D.Double();
				bounds[i].x = area.getX() + maxIconSize + iconPadding;
				bounds[i].y = area.getY() + (i * definitionHeight);
				bounds[i].height = definitionHeight;
				bounds[i].width = maxLabelWidth;

				icons[i] = new Point2D.Double();
				icons[i].x = area.getX() + halfDefinitionHeight;
				icons[i].y = bounds[i].y + halfDefinitionHeight;


				//		/**=========DEBUG========*/
				//		gr.setColor(Color.gray);
				//		gr.setStroke(new BasicStroke(1f));
				//		gr.draw(bounds[i]);
				//		gr.drawOval((int)icons[i].x - 3, (int)icons[i].y - 3, 6, 6);
				//		/**=========DEBUG========*/
			}
			break;

		case Horizontal:
			double left = area.getX();
			for (int i = 0; i < lists; i++)
			{
				bounds[i] = new Rectangle2D.Double();
				bounds[i].x = left + maxIconSize + iconPadding;
				bounds[i].y = area.getY();
				bounds[i].width = definitions[i].getLabelWidth(gr);
				bounds[i].height = definitionHeight;

				icons[i] = new Point2D.Double();
				icons[i].x = left + halfDefinitionHeight;
				icons[i].y = bounds[i].y + halfDefinitionHeight;

				left = bounds[i].x + bounds[i].width + labelPadding;

				//		/**=========DEBUG========*/
				//		gr.setColor(Color.gray);
				//		gr.setStroke(new BasicStroke(1f));
				//		gr.draw(bounds[i]);
				//		gr.drawOval((int)icons[i].x - 3, (int)icons[i].y - 3, 6, 6);
				//		/**=========DEBUG========*/
			}
			break;
		}

		// Adjust each label bounds based on the anchor
		for (int i = 0; i < lists; i++)
		{
			int w = definitions[i].getLabelWidth(gr);
			int h = definitions[i].getLabelHeight(gr);

			bounds[i].y += (definitionHeight - h) * 0.5;
			anchor.clipX(bounds[i], w, h);
		}

		// Finally draw each icon and label.
		Icon icon;
		Shape iconShape;
		Fill iconFill;
		Outline iconOutline;
		String label;
		Font font;
		Fill fontFill;
		Outline fontOutline;

		for (int i = 0; i < lists; i++)
		{
			icon = definitions[i].getIcon();
			iconShape = icon.getShape(icons[i].x, icons[i].y);
			iconFill = definitions[i].getIconFill();
			iconOutline = definitions[i].getIconOutline();
			label = definitions[i].getLabel();
			font = definitions[i].getFont();
			fontFill = definitions[i].getFontFill();
			fontOutline = definitions[i].getFontOutline();

			if (iconFill != null)
			{
				iconFill.setShape(iconShape);
				iconFill.select(gr);
				gr.fill(iconShape);
			}
			if (iconOutline != null)
			{
				iconOutline.setShape(iconShape);
				iconOutline.select(gr);
				gr.draw(iconShape);
			}

			double bottom = bounds[i].y + bounds[i].height;

			if (font != null)
			{
				gr.setFont(font);
			}

			if (fontFill != null)
			{
				fontFill.setShape(bounds[i]);
				fontFill.select(gr);
				gr.drawString(label, (float)bounds[i].x, (float)bottom);
			}

			if (fontOutline != null)
			{
				TextLayout layout = new TextLayout(label, font, gr.getFontRenderContext());

				AffineTransform transform = new AffineTransform();
				transform.translate(bounds[i].x, bottom);

				Shape outline = layout.getOutline(transform);

				fontOutline.setShape(bounds[i]);
				fontOutline.select(gr);
				gr.draw(outline);
			}

			//	    /**=========DEBUG========*/
			//	    gr.setColor(Color.blue);
			//	    gr.setStroke(new BasicStroke(1f));
			//	    gr.draw(bounds[i]);
			//	    /**=========DEBUG========*/
		}
	}

	public void setDefinition(int list, LegendDefinition definition)
	{
		definitions[list] = definition;
	}

	/**
	 * Returns the maximum width in pixels of any legend definition.
	 */
	private int getTotalLabelWidth(Graphics2D gr)
	{
		int totalWidth = 0;

		for (int i = 0; i < definitions.length; i++)
			totalWidth += definitions[i].getLabelWidth(gr);

		return totalWidth;
	}

	/**
	 * Returns the maximum width in pixels of any legend definition.
	 */
	private int getMaxLabelWidth(Graphics2D gr)
	{
		int maxWidth = Integer.MIN_VALUE;

		for (int i = 0; i < definitions.length; i++)
			maxWidth = Math.max(maxWidth, definitions[i].getLabelWidth(gr));

		return maxWidth;
	}

	/**
	 * Returns the maximum width in pixels of any legend definition.
	 */
	private int getMaxLabelHeight(Graphics2D gr)
	{
		int maxHeight = Integer.MIN_VALUE;

		for (int i = 0; i < definitions.length; i++)
			maxHeight = Math.max(maxHeight, definitions[i].getLabelHeight(gr));

		return maxHeight;
	}

	/**
	 * Returns the largest size of the icons contained in the legend definitions.
	 */
	private double getMaxIconSize()
	{
		double maxSize = -Double.MAX_VALUE;

		for (int i = 0; i < definitions.length; i++)
			maxSize = Math.max(maxSize, definitions[i].getIcon().getSize());

		return maxSize;
	}

	public void setOrientation(Orientation orientation)
	{
		this.orientation = orientation;
	}

	public Orientation getOrientation()
	{
		return orientation;
	}

	/**
	 * @return the minLabelHeight
	 */
	public float getMinLabelHeight()
	{
		return minLabelHeight;
	}

	/**
	 * @param minLabelHeight the minLabelHeight to set
	 */
	public void setMinLabelHeight(float minLabelHeight)
	{
		this.minLabelHeight = minLabelHeight;
	}

	/**
	 * @return the labelPadding
	 */
	public float getLabelPadding()
	{
		return labelPadding;
	}

	/**
	 * @param labelPadding the labelPadding to set
	 */
	public void setLabelPadding(float labelPadding)
	{
		this.labelPadding = labelPadding;
	}

	/**
	 * @return the iconPadding
	 */
	public float getIconPadding()
	{
		return iconPadding;
	}

	/**
	 * @param iconPadding the iconPadding to set
	 */
	public void setIconPadding(float iconPadding)
	{
		this.iconPadding = iconPadding;
	}

	/**
	 * @return the definitions
	 */
	public LegendDefinition[] getDefinitions()
	{
	    return definitions;
	}

}
