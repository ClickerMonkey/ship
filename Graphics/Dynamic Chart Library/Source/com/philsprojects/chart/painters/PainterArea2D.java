package com.philsprojects.chart.painters;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.util.ArrayDeque;

import com.philsprojects.chart.data.Dataset;
import com.philsprojects.chart.data.Datatable;
import com.philsprojects.chart.definitions.DefinitionArea2D;
import com.philsprojects.chart.fills.Fill;
import com.philsprojects.chart.fills.FillGradient;
import com.philsprojects.chart.fills.FillSolid;
import com.philsprojects.chart.icons.Icon;
import com.philsprojects.chart.outlines.Outline;
import com.philsprojects.chart.outlines.OutlineSolid;
import com.philsprojects.chart.settings.SettingsArea2D;
import com.philsprojects.chart.settings.SettingsLine2D.RenderType;
import com.philsprojects.chart.outlines.Outline.Cap;
import com.philsprojects.chart.outlines.Outline.Join;


public class PainterArea2D 
{

    /**
     * Creates a DataListDefinition that has a solid color on a 3D Pie Chart.
     * 
     * @param name => The name of the create definition.
     * @param color => The color of the pie slice.
     * @param alpha => The alpha of the pie slice.
     * @param outline => The width of the outline stroke in pixels.
     */
    public static DefinitionArea2D createGradientDefinition(Color color, int alpha, float outline)
    {
	DefinitionArea2D d = new DefinitionArea2D();
	int r = color.getRed();
	int g = color.getGreen();
	int b = color.getBlue();
	int lr = (int)((255 - r) * 0.5 + r);
	int lg = (int)((255 - g) * 0.5 + g);
	int lb = (int)((255 - b) * 0.5 + b);
	int dr = (int)(r * 0.5);
	int dg = (int)(g * 0.5);
	int db = (int)(b * 0.5);
	
	d.setAreaFill(new FillGradient(new Color(lr, lg, lb, alpha), new Color(dr, dg, db, alpha), FillGradient.VERTICAL));
	d.setAreaOutline(new OutlineSolid(color, outline * 0.5f, Cap.Round, Join.Round));
	d.setLineOutline(new OutlineSolid(color, outline, Cap.Round, Join.Round));

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
    public static DefinitionArea2D createSolidDefinition(Color color, int alpha, float outline)
    {
	DefinitionArea2D d = new DefinitionArea2D();
	int r = color.getRed();
	int g = color.getGreen();
	int b = color.getBlue();

	Color shade = new Color(r, g, b, alpha);
	
	d.setAreaFill(new FillSolid(shade));
	d.setAreaOutline(new OutlineSolid(shade, outline * 0.5f));
	d.setLineOutline(new OutlineSolid(shade, outline, Cap.Round, Join.Round));

	return d;
    }

    // The number of lists for the associated plot.
    private final int lists;

    // The data set containing.
    private final Datatable datatable;

    // The physical settings for the 3D pie. 
    private final SettingsArea2D settings;

    // The visual settings for the 3D pie.
    private final DefinitionArea2D[] definitions;


    /**
     * 
     * @param settings
     * @param datatable
     */
    public PainterArea2D(SettingsArea2D settings, DefinitionArea2D[] definitions, Datatable datatable)
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
	
	double currentX = offsetX + spacing;

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
		    lines[j].lineTo(currentX, bottom - height);
		    bottom -= height;
		}
		break;

	    case Percent:
		double invTotal = 1.0 / set.getTotal(0);
		for (int j = 0; j < lists; j++)
		{
		    height = settings.getLineOffsetY((set.getData(j, 0) * invTotal) * 100);
		    lines[j].lineTo(currentX, bottom - height);
		    bottom -= height;
		}
		break;

	    case Clustered:
		for (int j = 0; j < lists; j++)
		{
		    height = settings.getLineOffsetY(set.getData(j, 0));
		    lines[j].lineTo(currentX, lineBase - height);
		}
		break;
	    }

	    currentX += spacing;
	}
	
	// Move currentX back to its correct X position (at the last data set).
	currentX -= spacing;
	
	// Drawing order for 2D Area
	// 1. Area
	// 2. Area outline
	// 3. Line
	// 4. Line icons

	for (int i = lists - 1; i >= 0; i--)
	{
	    GeneralPath areaPath = new GeneralPath();
	    areaPath.append(lines[i], true);
	    
	    if (type == RenderType.Clustered || (i == 0))
	    {
		areaPath.lineTo(currentX, lineBase);
		areaPath.lineTo(offsetX, lineBase);
		areaPath.closePath();
	    }
	    else
	    {
		addReversedPath(lines[i - 1], areaPath);
		areaPath.closePath();
	    }
	    
	    Shape area = areaPath.getBounds2D();
	    
	    Fill areaFill = definitions[i].getAreaFill();
	    if (areaFill != null)
	    {
		areaFill.setShape(area);
		areaFill.select(gr);
		gr.fill(areaPath);
	    }
	    
	    Outline areaOutline = definitions[i].getAreaOutline();
	    if (areaOutline != null)
	    {
		areaOutline.setShape(area);
		areaOutline.select(gr);
		gr.draw(areaPath);
	    }
	    
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
    
    private void addReversedPath(GeneralPath source, GeneralPath destination)
    {
	PathIterator iterator = source.getPathIterator(new AffineTransform());
	ArrayDeque<Point2D> points = new ArrayDeque<Point2D>();

	double[] coords = new double[6];
	while (!iterator.isDone())
	{
	    iterator.currentSegment(coords);
	    iterator.next();
	    points.push(new Point2D.Double(coords[0], coords[1]));
	}

	Point2D current;
	while (!points.isEmpty())
	{
	    current = points.pop();
	    destination.lineTo(current.getX(), current.getY());
	}
    }

    /**
     * @return the definitions
     */
    public DefinitionArea2D[] getDefinitions()
    {
	return definitions;
    }


}
