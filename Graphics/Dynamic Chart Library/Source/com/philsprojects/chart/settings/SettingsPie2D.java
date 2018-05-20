package com.philsprojects.chart.settings;

import com.philsprojects.chart.view.Viewport;

public class SettingsPie2D extends Settings
{

    protected static final double DEG_TO_RAD = Math.PI / 180;

    /**
     * The order to draw the slices by the way the data set is presented.
     * 
     * @author Philip Diffenderfer
     */
    public enum Order
    {
	// The first slice is drawn at the yaw specified in the settings and
	// all following slices are drawn clockwise.
	Clockwise {
	    public int getIndex(int i, int slices) {
		return slices - i;
	    }
	},

	// The first slice is drawn at the yaw specified in the settings and
	// all following slices are drawn counter-clockwise.
	CounterClockwise {
	    public int getIndex(int i, int slices) {
		return i;
	    }
	},

	// The first slice is drawn at the yaw specified in the settings and
	// all following slices are drawn switching between each side of the
	// first slice.
	RoundRobin {
	    public int getIndex(int i, int slices) {
		return ((i & 1) == 1 ? ((i + 1) >> 1) : ((slices - i) + (i >> 1)));
	    }
	};

	// Each Order type returns the actual index given the
	// absolute index and the number of slices.
	public abstract int getIndex(int i, int slices);

    }

    /**
     * The type of adjustment to perform based on the separation. If the
     * separation is 0.0 then the render type has no effect.
     * 
     * @author Philip Diffenderfer
     */
    public enum RenderType
    {
	// Preserves the size of the piece and provides equal separation 
	// between all slices, even if it results in pieces sticking out.
	StickOut,
	// Provides equal separation between all slices and resizes the 
	// pieces so all stay within the pie.
	CutOff,
	// Provides unequal separation between all slices but all slices
	// are equidistant from the center of the pie.
	Equidistant
    }


    // The center x-coordinate of the pie.
    protected double x;

    // The center y-coordinate of the pie.
    protected double y;

    // The rotation in degrees of the pie around its center axis. Also the
    // central angle of the first piece of the pie. The default yaw is 90 degrees.
    protected double yaw = 90.0;

    // The radius of the pie in pixels. The default radius is 50px.
    protected double radius = 50.0;

    // The distance the point of each pie slice is from the center
    // of the pie. A separation of 0.0 has all pie slices touching.
    // The default separation is 0.0.
    protected double separation = 0.0;

    // The order of rendering the pie slices.
    protected Order order = Order.Clockwise;

    // The type of adjustment to perform based on the separation.
    protected RenderType type = RenderType.CutOff;

    /**
     * 
     */
    public SettingsPie2D()
    {
	super();
    }
    
    /**
     * 
     * @param view
     */
    public SettingsPie2D(Viewport view)
    {
	super(view);
    }

    /**
     * Sets the radius of the pie in pixels. The default radius is 50px. This
     * causes the pie to redraw.
     * 
     * @param radius => The new radius of the pie in pixels.
     */
    public final void setRadius(double radius) 
    {
	this.radius = radius;
	
	if (view != null) view.requestRedraw();
    }

    /**
     * 
     * @param separation => The new separation of the pie slices in pixels.
     */
    public final void setSeparation(double separation)
    {
	this.separation = separation;

	if (view != null) view.requestRedraw();
    }

    /**
     * The rotation in degrees of the pie around its center axis. Also the
     * central angle of the first piece of the pie. The default yaw is 90 
     * degrees. This causes the pie to redraw.
     * 
     * @param yaw => The new yaw of the pie in degrees.
     */
    public void setYaw(double yaw)
    {
	while (yaw >= 360) yaw -= 360;
	while (yaw < 0) yaw += 360;
	this.yaw = yaw;
	
	if (view != null) view.requestRedraw();
    }

    /**
     * 
     * @param yaw
     * @param pitch
     */
    public void addRotation(double addYaw)
    {
	yaw = yaw + addYaw;
	while (yaw >= 360) yaw -= 360;
	while (yaw < 0) yaw += 360;
	
	if (view != null) view.requestRedraw();
    }


    public double getDistanceFromCenter(double sweep)
    {
	// If the type of pie to render is equidistant then just
	// return separation in pixels.
	if (type == RenderType.Equidistant)
	    return separation;

	// Else the distance from center is dependent on the sweep
	// of the pie slice.
	return (separation * 0.5) / Math.sin(sweep * 0.5 * DEG_TO_RAD);
    }

    /**
     */
    public double getSeparationX(double angle, double distanceFromCenter)
    {
	return Math.cos(angle * DEG_TO_RAD) * distanceFromCenter;
    }

    /**
     */
    public double getSeparationY(double angle, double distanceFromCenter)
    {
	return -Math.sin(angle * DEG_TO_RAD) * distanceFromCenter;
    }

    /**
     * The new 
     * @param direction => The new order of the pie slices.
     */
    public final void setRenderType(RenderType type) 
    {
	this.type = type;
	
	if (view != null) view.requestRedraw();
    }

    /**
     * The new 
     * @param direction => The new order of the pie slices.
     */
    public final void setOrder(Order order) 
    {
	this.order = order;
	
	if (view != null) view.requestRedraw();
    }

    /**
     * 
     */
    public final void setX(double x) 
    {
	this.x = x;
	
	if (view != null) view.requestRedraw();
    }

    /**
     * 
     */
    public final void setY(double y) 
    {
	this.y = y;
	
	if (view != null) view.requestRedraw();
    }

    /**
     * 
     */
    public final void setCenter(double x, double y) 
    {
	this.x = x;
	this.y = y;
	
	if (view != null) view.requestRedraw();
    }

    /**
     * Returns the height of the pie in pixels dependent on the associated 
     * view port.
     */
    public double getHeight()
    {
	return view.toScreenSizeY(radius * 2.0);
    }

    /**
     * Returns the width of the pie in pixels dependent on the associated view
     * port.
     */
    public double getWidth()
    {
	return view.toScreenSizeX(radius * 2.0);
    }

    /**
     * Returns the center x-coordinate of the pie in pixels dependent on the 
     * associated view port.
     */
    public double getX()
    {
	return view.toScreenX(x);
    }

    /**
     * Returns the center x-coordinate of the pie in pixels dependent on the 
     * associated view port.
     */
    public double getY()
    {
	return view.toScreenY(y);
    }

    /**
     * Returns the separation of the pieces on the x-axis dependent on the
     * associated view port.
     */
    public final double getSeparationX()
    {
	return view.toScreenX(separation);
    }

    /**
     * Returns the separation of the pieces on the y-axis dependent on the
     * associated view port.
     */
    public final double getSeparationY()
    {
	return view.toScreenY(separation);
    }

    /**
     * Returns the radius of the pie in pixels without rotation.
     */
    public final double getRadius()
    {
	return radius;
    }

    /**
     * Returns the radius of the pie in pixels without rotation.
     */
    public final double getSeparation()
    {
	return radius;
    }

    public final Order getOrder()
    {
	return order;
    }

    public final RenderType getRenderType()
    {
	return type;
    }

    public final double getYaw() 
    {
	return yaw;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getDatasetOffset()
    {
	return 0;
    }

    /**
     * {@inheritDoc}
     */
    public double getDatasetSpacing()
    {
	return 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getDatasetWidth(int lists)
    {
	return 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getFirstVisibleDataset(int lists)
    {
	return 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getLastVisibleDataset(int lists)
    {
	return -1;
    }

}
