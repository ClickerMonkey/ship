package com.philsprojects.chart.painters;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import com.philsprojects.chart.data.Dataset;
import com.philsprojects.chart.data.Datatable;
import com.philsprojects.chart.definitions.DefinitionBar2D;
import com.philsprojects.chart.fills.Fill;
import com.philsprojects.chart.fills.FillGradient;
import com.philsprojects.chart.fills.FillLinear;
import com.philsprojects.chart.fills.FillSolid;
import com.philsprojects.chart.outlines.Outline;
import com.philsprojects.chart.outlines.OutlineSolid;
import com.philsprojects.chart.outlines.Outline.Cap;
import com.philsprojects.chart.outlines.Outline.Join;
import com.philsprojects.chart.settings.SettingsBar2D;
import com.philsprojects.chart.settings.SettingsBar2D.RenderType;


public class PainterBar2D 
{

    /**
     * Creates a DataListDefinition that has a vertical glare across a 3D Pie Chart.
     * 
     * @param name => The name of the create definition.
     * @param color => The color of the pie slice.
     * @param alpha => The alpha of the pie slice.
     * @param outline => The width of the outline stroke in pixels.
     */
    public static DefinitionBar2D createGlareDefinition(Color color, int alpha, float outline)
    {
	DefinitionBar2D d = new DefinitionBar2D();
	int r = color.getRed();
	int g = color.getGreen();
	int b = color.getBlue();
	int lr = (int)((255 - r) * 0.6 + r);
	int lg = (int)((255 - g) * 0.6 + g);
	int lb = (int)((255 - b) * 0.6 + b);
	int dr = (int)(r * 0.75);
	int dg = (int)(g * 0.75);
	int db = (int)(b * 0.75);

	Color[] colors = {
		new Color(dr, dg, db, alpha),
		new Color(lr, lg, lb, alpha),
		new Color(r, g, b, alpha),
		new Color(dr, dg, db, alpha)
	};
	float[] fractions = {
		0.0f,
		0.2f,
		0.6f,
		1.0f
	};
	d.setBarFill(new FillLinear(colors, fractions, FillLinear.HORIZONTAL, FillLinear.NO_CYCLE, 1.0));
	d.setBarOutline(new OutlineSolid(color, outline, Cap.Round, Join.Round));

	return d;
    }


    /**
     * Creates a DataListDefinition that has a radial glare in the center of the 3D Pie Chart.
     * 
     * @param name => The name of the create definition.
     * @param color => The color of the pie slice.
     * @param alpha => The alpha of the pie slice.
     * @param outline => The width of the outline stroke in pixels.
     */
    public static DefinitionBar2D createGradientDefinition(Color color, int alpha, float outline)
    {
	DefinitionBar2D d = new DefinitionBar2D();
	int r = color.getRed();
	int g = color.getGreen();
	int b = color.getBlue();
	int lr = (int)((255 - r) * 0.2 + r);
	int lg = (int)((255 - g) * 0.2 + g);
	int lb = (int)((255 - b) * 0.2 + b);
	int dr = (int)(r * 0.5);
	int dg = (int)(g * 0.5);
	int db = (int)(b * 0.5);

	d.setBarFill(new FillGradient(new Color(lr, lg, lb, alpha), new Color(dr, dg, db, alpha), FillGradient.VERTICAL));
	d.setBarOutline(new OutlineSolid(color, outline, Cap.Round, Join.Round));

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
    public static DefinitionBar2D createSolidDefinition(Color color, int alpha, float outline)
    {
	DefinitionBar2D d = new DefinitionBar2D();
	int r = color.getRed();
	int g = color.getGreen();
	int b = color.getBlue();

	d.setBarFill(new FillSolid(new Color(r, g, b, alpha)));
	d.setBarOutline(new OutlineSolid(outline, Cap.Round, Join.Round));

	return d;
    }
    
    // The number of lists for the associated plot.
    private final int lists;

    // The data set containing.
    private final Datatable datatable;

    // The physical settings for the 3D pie. 
    private final SettingsBar2D settings;

    // The visual settings for the 3D pie.
    private final DefinitionBar2D[] definitions;


    /**
     * 
     * @param settings
     * @param datatable
     */
    public PainterBar2D(SettingsBar2D settings, DefinitionBar2D[] definitions, Datatable datatable)
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
	
//	/** DEBUG **/
//	gr.setColor(Color.black);
//	gr.drawString(first + ", " + last, 10, 20);
//	/** DEBUG **/
	
	double barWidth = settings.getBarWidth();
	double clusterSpacing = settings.getClusterSpacing();
	double barBase = settings.getOffsetY();
	double datasetWidth = settings.getDatasetWidth(lists);
	double offsetX = settings.getOffsetX() + (datasetWidth * first);
	RenderType type = settings.getRenderType();
	

	Rectangle2D.Double[] bars = new Rectangle2D.Double[lists];
	for (int i = 0; i < lists; i++)
	    bars[i] = new Rectangle2D.Double(0, 0, barWidth, 0);

	Fill[] fills = new Fill[lists];
	for (int i = 0; i < lists; i++)
	    fills[i] = definitions[i].getBarFill();
	
	Outline[] lines = new Outline[lists];
	for (int i = 0; i < lists; i++)
	    lines[i] = definitions[i].getBarOutline();
	
	Dataset set;
	
	for (int i = first; i <= last; i++)
	{
	    set = datatable.get(i);
	    
	    double height;
	    double bottom = barBase;
	    switch(type)
	    {
	    case Stacked:
		for (int j = 0; j < lists; j++)
		{
		    height = settings.getBarHeight(set.getData(j, 0));
		    
		    bars[j].height = height;
		    bars[j].x = offsetX;
		    
		    bottom -= height;
		    
		    bars[j].y = bottom;
		}
		break;
		
	    case Percent:
		double invTotal = 1.0 / set.getTotal(0);
		for (int j = 0; j < lists; j++)
		{
		    height = settings.getBarHeight(set.getData(j, 0) * invTotal * 100);
		    
		    bars[j].height = height;
		    bars[j].x = offsetX;
		    
		    bottom -= height;
		    
		    bars[j].y = bottom;
		}
		break;
		
	    case Clustered:
		double left = offsetX;
		for (int j = 0; j < lists; j++)
		{
		    height = settings.getBarHeight(set.getData(j, 0));
		    bars[j].height = height;
		    bars[j].x = left;
		    bars[j].y = barBase - height;
		    
		    left += barWidth + clusterSpacing;
		}
		break;
	    }
	    
	    
	    for (int j = lists - 1; j >= 0; j--)
	    {
		if (fills[j] != null)
		{
		    fills[j].setShape(bars[j]);
		    fills[j].select(gr);
		    gr.fill(bars[j]);
		}
		
		if (lines[j] != null)
		{
		    lines[j].setShape(bars[j]);
		    lines[j].select(gr);
		    gr.draw(bars[j]);
		}
	    }
	    
	    offsetX += datasetWidth;
	}
    }
    
    /**
     * @return the definitions
     */
    public DefinitionBar2D[] getDefinitions()
    {
	return definitions;
    }
    

}
