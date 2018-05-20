package com.philsprojects.chart.painters;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.PathIterator;

import com.philsprojects.chart.data.Dataset;
import com.philsprojects.chart.data.Datatable;
import com.philsprojects.chart.definitions.DefinitionLine2D;
import com.philsprojects.chart.fills.Fill;
import com.philsprojects.chart.fills.FillSolid;
import com.philsprojects.chart.icons.Icon;
import com.philsprojects.chart.outlines.Outline;
import com.philsprojects.chart.outlines.OutlineSolid;
import com.philsprojects.chart.settings.SettingsLine2D;
import com.philsprojects.chart.settings.SettingsLine2D.RenderType;
import com.philsprojects.chart.outlines.OutlineDashed;
import com.philsprojects.chart.outlines.Outline.Cap;
import com.philsprojects.chart.outlines.Outline.Join;


public class PainterLine2D 
{

	/**
	 * Creates a DataListDefinition that has a solid color on a 3D Pie Chart.
	 * 
	 * @param name => The name of the create definition.
	 * @param color => The color of the pie slice.
	 * @param alpha => The alpha of the pie slice.
	 * @param outline => The width of the outline stroke in pixels.
	 */
	public static DefinitionLine2D createDashedDefinition(Color color, int alpha, float outline, float dashLength, float dashSpacing)
	{
		DefinitionLine2D d = new DefinitionLine2D();
		int r = color.getRed();
		int g = color.getGreen();
		int b = color.getBlue();

		Fill fill = new FillSolid(new Color(r, g, b, alpha));
		d.setLineOutline(new OutlineDashed(fill, outline, Cap.Square, Join.Bevel, 1f, 0f, dashLength, dashSpacing));

		return d;
	}

	/**
	 * Creates a DataListDefinition that has a solid color on a 3D Pie Chart.
	 * 
	 * @param name => The name of the create definition.
	 * @param color => The color of the pie slice.
	 * @param alpha => The alpha of the pie slice.
	 * @param outline => The width of the outline stroke in pixels.
	 */
	public static DefinitionLine2D createSolidDefinition(Color color, int alpha, float outline)
	{
		DefinitionLine2D d = new DefinitionLine2D();
		int r = color.getRed();
		int g = color.getGreen();
		int b = color.getBlue();

		d.setLineOutline(new OutlineSolid(new Color(r, g, b, alpha), outline, Cap.Round, Join.Round));

		return d;
	}

	// The number of lists for the associated plot.
	private final int lists;

	// The data set containing.
	private final Datatable datatable;

	// The physical settings for the 3D pie. 
	private final SettingsLine2D settings;

	// The visual settings for the 3D pie.
	private final DefinitionLine2D[] definitions;


	/**
	 * 
	 * @param settings
	 * @param datatable
	 */
	public PainterLine2D(SettingsLine2D settings, DefinitionLine2D[] definitions, Datatable datatable)
	{
		this.settings = settings;
		this.definitions = definitions;
		this.datatable = datatable;
		this.lists = datatable.getListCount();
	}

	/**
	 * 
	 * @param gr
	 */
	public void draw(Graphics2D gr)
	{
		int totalSets = datatable.getSize();
		int first = Math.max(0, settings.getFirstVisibleDataset(lists));
		int last = Math.min(totalSets - 1, settings.getLastVisibleDataset(lists));

		double spacing = settings.getSpacing();
		double lineBase = settings.getOffsetY();
		double offsetX = settings.getOffsetX() + (spacing * first);
		RenderType type = settings.getRenderType();

		GeneralPath[] lines = new GeneralPath[lists];
		for (int i = 0; i < lists; i++)
			lines[i] = new GeneralPath();


		double height;
		double bottom = lineBase;
		Dataset set;

		set = datatable.get(first);
		switch(type)
		{
		case Stacked:
			for (int j = 0; j < lists; j++)
			{
				height = settings.getLineOffsetY(set.getData(j, 0));
				lines[j].moveTo(offsetX, bottom - height);
				bottom -= height;
			}
			break;
		case Clustered:
			for (int j = 0; j < lists; j++)
			{
				height = settings.getLineOffsetY(set.getData(j, 0));
				lines[j].moveTo(offsetX, lineBase - height);
			}
			break;
		case Percent:
			double invTotal = 1.0 / set.getTotal(0);
			for (int j = 0; j < lists; j++)
			{
				height = settings.getLineOffsetY((set.getData(j, 0) * invTotal) * 100);
				lines[j].moveTo(offsetX, bottom - height);
				bottom -= height;
			}
			break;
		}
		offsetX += spacing;


		for (int i = first + 1; i <= last; i++)
		{
			set = datatable.get(i);

			bottom = lineBase;
			switch(type)
			{
			case Stacked:
				for (int j = 0; j < lists; j++)
				{
					height = settings.getLineOffsetY(set.getData(j, 0));
					lines[j].lineTo(offsetX, bottom - height);
					bottom -= height;
				}
				break;

			case Percent:
				double invTotal = 1.0 / set.getTotal(0);
				for (int j = 0; j < lists; j++)
				{
					height = settings.getLineOffsetY((set.getData(j, 0) * invTotal) * 100);
					lines[j].lineTo(offsetX, bottom - height);
					bottom -= height;
				}
				break;

			case Clustered:
				for (int j = 0; j < lists; j++)
				{
					height = settings.getLineOffsetY(set.getData(j, 0));
					lines[j].lineTo(offsetX, lineBase - height);
				}
				break;
			}

			offsetX += spacing;
		}

		for (int i = 0; i < lists; i++)
		{
			Outline outline = definitions[i].getLineOutline();

			if (outline != null)
			{
				outline.setShape(lines[i]);
				outline.select(gr);
				gr.draw(lines[i]);
			}

			Icon icon = definitions[i].getPointIcon();
			Fill iconFill = definitions[i].getPointIconFill();
			Outline iconOutline = definitions[i].getPointIconOutline();
			if (icon != null && (iconFill != null || iconOutline != null))
			{
				Shape shape;
				PathIterator iter = lines[i].getPathIterator(new AffineTransform());

				double[] coords = new double[6];

				while (!iter.isDone())
				{
					iter.currentSegment(coords);

					shape = icon.getShape(coords[0], coords[1]);

					if (iconFill != null)
					{
						iconFill.setShape(shape);
						iconFill.select(gr);
						gr.fill(shape);
					}

					if (iconOutline != null)
					{
						iconOutline.setShape(shape);
						iconOutline.select(gr);
						gr.draw(shape);
					}

					iter.next();
				}

			}
		}


	}

	/**
	 * @return the definitions
	 */
	public DefinitionLine2D[] getDefinitions()
	{
		return definitions;
	}


}
