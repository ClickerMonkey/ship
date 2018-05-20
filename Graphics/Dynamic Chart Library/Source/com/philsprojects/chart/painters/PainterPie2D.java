package com.philsprojects.chart.painters;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.awt.geom.Rectangle2D;

import com.philsprojects.chart.data.Datatable;
import com.philsprojects.chart.definitions.DefinitionPie2D;
import com.philsprojects.chart.fills.Fill;
import com.philsprojects.chart.fills.FillCyclic;
import com.philsprojects.chart.fills.FillRadial;
import com.philsprojects.chart.fills.FillSolid;
import com.philsprojects.chart.outlines.Outline;
import com.philsprojects.chart.outlines.OutlineSolid;
import com.philsprojects.chart.outlines.Outline.Cap;
import com.philsprojects.chart.outlines.Outline.Join;
import com.philsprojects.chart.settings.SettingsPie2D;
import com.philsprojects.chart.settings.SettingsPie2D.Order;
import com.philsprojects.chart.settings.SettingsPie2D.RenderType;


public class PainterPie2D 
{

    /**
     * Creates a DataListDefinition that has a vertical glare across a 3D Pie Chart.
     * 
     * @param name => The name of the create definition.
     * @param color => The color of the pie slice.
     * @param alpha => The alpha of the pie slice.
     * @param outline => The width of the outline stroke in pixels.
     */
    public static DefinitionPie2D createGlareDefinition(Color color, int alpha, float outline)
    {
	DefinitionPie2D d = new DefinitionPie2D();
	int r = color.getRed();
	int g = color.getGreen();
	int b = color.getBlue();
	int lr = (int)((255 - r) * 0.6 + r);
	int lg = (int)((255 - g) * 0.6 + g);
	int lb = (int)((255 - b) * 0.6 + b);
	int dr = (int)(r * 0.75);
	int dg = (int)(g * 0.75);
	int db = (int)(b * 0.75);
	int inner = (int)((255 - alpha) * 0.3 + alpha);
	int outer = (int)((255 - alpha) * 0.6 + alpha);

	d.setSliceFill(new FillCyclic(new Color(lr, lg, lb, inner), new Color(dr, dg, db, outer)));
	d.setSliceOutline(new OutlineSolid(outline, Cap.Round, Join.Round));

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
    public static DefinitionPie2D createRadialDefinition(Color color, int alpha, float outline)
    {
	DefinitionPie2D d = new DefinitionPie2D();
	int r = color.getRed();
	int g = color.getGreen();
	int b = color.getBlue();
	int lr = (int)((255 - r) * 0.6 + r);
	int lg = (int)((255 - g) * 0.6 + g);
	int lb = (int)((255 - b) * 0.6 + b);
	int dr = (int)(r * 0.75);
	int dg = (int)(g * 0.75);
	int db = (int)(b * 0.75);
	int inner = (int)((255 - alpha) * 0.3 + alpha);
	int outer = (int)((255 - alpha) * 0.6 + alpha);

	d.setSliceFill(new FillRadial(new Color(lr, lg, lb, inner), new Color(dr, dg, db, outer)));
	d.setSliceOutline(new OutlineSolid(outline, Cap.Round, Join.Round));

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
    public static DefinitionPie2D createSolidDefinition(Color color, int alpha, float outline)
    {
	DefinitionPie2D d = new DefinitionPie2D();
	int r = color.getRed();
	int g = color.getGreen();
	int b = color.getBlue();

	d.setSliceFill(new FillSolid(new Color(r, g, b, alpha)));
	d.setSliceOutline(new OutlineSolid(outline, Cap.Round, Join.Round));

	return d;
    }


    // The current data set in the list.
    private int index = 0;

    // The number of lists for the associated plot.
    private final int lists;

    // The data set containing.
    private final Datatable datatable;

    // The physical settings for the 3D pie. 
    private final SettingsPie2D settings;

    // The visual settings for the 3D pie.
    private final DefinitionPie2D[] definitions;

    // The array of slices.
    private final PieSlice[] slices;
    
    // The values of the current data set.
    private double[] values;

    // The ratio between converting values into pie slice sweep angles.
    private double valueToDegrees;


    /**
     * 
     * @param settings
     * @param datatable
     */
    public PainterPie2D(SettingsPie2D settings, DefinitionPie2D[] definitions, Datatable datatable)
    {
	this.settings = settings;
	this.definitions = definitions;
	this.datatable = datatable;
	this.lists = datatable.getListCount();
	this.slices = new PieSlice[lists];
    }

    /**
     * 
     * @param index
     */
    public void setIndex(int index)
    {
	this.index = index;

	// Grab the floating-point values from the data set
	// based on the index of the set to draw.
	values = getValues();

	// Calculate the valueToDegrees ratio which converts
	// a value to its respective pie slice sweep.
	valueToDegrees = valueToDegrees();

	updateDirection();
    }

    private void updateDirection()
    {
	// Create the pie slices based on the direction of the slices. The
	// first slice will always be the same, set the rest accordingly.
	slices[0] = new PieSlice(values[0] * valueToDegrees, definitions[0]);

	Order o = settings.getOrder();
	int j = 1;
	for (int i = 1; i < lists; i++)
	{
	    j = o.getIndex(i, lists);

	    slices[i] = new PieSlice(values[j] * valueToDegrees, definitions[j]);
	}
    }

    /**
     * 
     * @param gr
     */
    public void draw(Graphics2D gr)
    {
	double cx = settings.getX();
	double cy = settings.getY();
	double width = settings.getWidth();
	double height = settings.getHeight();
	double yaw = settings.getYaw();

	// Set the bounds of the pie based on the center of the pie
	// taking into consideration any stagger offset between slices.
	Rectangle2D.Double bounds = new Rectangle2D.Double(0, 0, width, height);
	bounds.x = cx - (width * 0.5);
	bounds.y = cy - (height * 0.5);

	// Set the slices based on their new orientation
	double currSweep, nextSweep;
	for (int i = 0; i < lists; i++)
	{
	    slices[i].setAngle(yaw);

	    currSweep = slices[i].sweep;
	    nextSweep = slices[(i + 1) % lists].sweep;
	    yaw = wrapAngle(yaw + ((currSweep + nextSweep) * 0.5));
	}
	
	for (PieSlice slice : slices)
	    slice.draw(gr, bounds);

    }

    /**
     * 
     * @param angle
     * @return
     */
    private double wrapAngle(double angle)
    {
	while (angle >= 360) angle -= 360;
	while (angle < 0) angle += 360;
	return angle;
    }

    /**
     * 
     * @return
     */
    private double[] getValues()
    {
	values = new double[lists];

	for (int i = 0; i < lists; i++)
	    values[i] = datatable.get(index).getData(i, 0);

	return values;
    }

    /**
     * 
     * @param values
     * @return
     */
    private double valueToDegrees()
    {
	double total = 0.0;

	for (int i = 0; i < lists; i++)
	    total += values[i];

	return (total == 0.0 ? 0.0 : (1.0 / total * 360));
    }

    /**
     * @return the definitions
     */
    public DefinitionPie2D[] getDefinitions()
    {
	return definitions;
    }
    

    private class PieSlice
    {
	final DefinitionPie2D definition;
	final double sweep;

	double angle;
	double startAngle;
	double endAngle;

	public PieSlice(double sweep, DefinitionPie2D definition)
	{
	    this.sweep = sweep;
	    this.definition = definition;
	}

	public void draw(Graphics2D gr, Rectangle2D bounds)
	{
	    Rectangle2D.Double adjusted = calculateBounds(bounds);

	    Arc2D.Double arc = new Arc2D.Double(Arc2D.PIE);
	    arc.setFrame(adjusted);
	    arc.start = startAngle;
	    arc.extent = (endAngle - startAngle);

	    Fill sliceFill = definition.getSliceFill();
	    if (sliceFill != null)
	    {
		sliceFill.setShape(adjusted);
		sliceFill.select(gr);

		gr.fill(arc);
	    }

	    Outline sliceOutline = definition.getSliceOutline();
	    if (sliceOutline != null)
	    {
		sliceOutline.setShape(adjusted);
		sliceOutline.select(gr);

		gr.draw(arc);
	    }
	}

	public Rectangle2D.Double calculateBounds(Rectangle2D base)
	{
	    double dist = settings.getDistanceFromCenter(sweep);
	    double sepX = settings.getSeparationX(angle, dist); 
	    double sepY = settings.getSeparationY(angle, dist);

	    Rectangle2D.Double adjusted = new Rectangle2D.Double();
	    adjusted.setFrame(base);
	    adjusted.x += sepX;
	    adjusted.y += sepY;

	    // Only if the pie type is CutOff should the adjusted
	    // bounding rectangle be resized.
	    if (settings.getRenderType() == RenderType.CutOff)
	    {
		adjusted.x += dist;
		adjusted.y += dist;
		adjusted.width -= dist * 2.0;
		adjusted.height -= dist * 2.0;
	    }

	    return adjusted;
	}

	public void setAngle(double angle)
	{
	    // Set the new central angle of the pie slice.
	    this.angle = angle;

	    // Update the start angle (startAngle < angle) based on the sweep
	    startAngle = angle - (sweep * 0.5);

	    // Update the end angle (endAngle > angle) based on the sweep.
	    endAngle = angle + (sweep * 0.5);
	}

    }

}
