package com.philsprojects.chart.settings;

import com.philsprojects.chart.view.Viewport;

public class SettingsPie3D extends SettingsPie2D
{

    // The rotation in degrees of the pie around its x-axis where
    // 90 degrees is directly above the pie (2d) and 0 is at eye level
    // with the top of the pies pieces. The default pitch is 60 degrees.
    private double pitch = 60;

    // The depth of the pie in pixels when the pitch is at eye
    // level. If the pitch is directly above the pie then the
    // depth has no effect. The default depth is 10px.
    private double depth = 10.0;

    // The offset of the slices vertically from each other where the highest
    // slice is at 90 degrees and the lowest slice is at 270 degrees.
    private double staggerOffset;


    /**
     * 
     */
    public SettingsPie3D()
    {
	super();
    }

    /**
     * 
     * @param view
     */
    public SettingsPie3D(Viewport view)
    {
	super(view);
    }


    /**
     * Sets the depth of the pie in pixels when the pitch is at eye level (0).
     * If the pitch is directly above the pie (90) then the depth has no 
     * effect. The default depth is 10px. This causes the pie to redraw.
     * 
     * @param depth => The new depth of the pie in pixels.
     */
    public void setDepth(double depth) 
    {
	this.depth = depth;
	
	if (view != null) view.requestRedraw();
    }

    public void setStaggerOffset(double staggerOffset)
    {
	this.staggerOffset = staggerOffset;
	
	if (view != null) view.requestRedraw();
    }

    /**
     * The rotation in degrees of the pie around its x-axis where 90 degrees
     * is directly above the pie (2d) and 0 is at eye level with the top of 
     * the pies pieces. The default pitch is 60 degrees. If the pitch is not
     * within 0 and 90 degrees it is clamped. This causes the pie to redraw.
     * 
     * @param pitch => The new pitch of the pie in degrees.
     */
    public void setPitch(double pitch)
    {
	this.pitch = Math.max(0, Math.min(pitch, 90));
	
	if (view != null) view.requestRedraw();
    }

    /**
     * 
     * @param yaw
     * @param pitch
     */
    public void addRotation(double addYaw, double addPitch)
    {
	pitch = Math.max(0, Math.min(pitch + addPitch, 90));
	yaw = yaw + addYaw;
	while (yaw >= 360) yaw -= 360;
	while (yaw < 0) yaw += 360;
	
	if (view != null) view.requestRedraw();
    }

    /**
     * Computes the depth of the pie dependent on the pitch of the pie and the
     * associated view port.
     */
    public double getDepth()
    {
	return Math.cos(pitch * DEG_TO_RAD) * depth;
    }

    /**
     * 
     */
    public double getStaggerOffset()
    {
	return Math.cos(pitch * DEG_TO_RAD) * staggerOffset;
    }

    /**
     * Returns the height of the pie in pixels dependent on the pitch of the
     * pie and the associated view port.
     */
    public double getHeight()
    {
	return Math.sin(pitch * DEG_TO_RAD) * radius * 2.0;
    }

    public double getDistanceFromCenter(double sweep)
    {
	// If the type of pie to render is equidistant then just
	// return separation in pixels.
	if (type == RenderType.Equidistant)
	    return separation;

	// Else the distance from center is dependant on the sweep
	// of the pie slice.
	return (separation * 0.5) / Math.sin(sweep * 0.5 * DEG_TO_RAD);
    }

    public double getOffsetY(double distanceFromCenter)
    {
	return Math.sin(pitch * DEG_TO_RAD) * distanceFromCenter;
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
	return Math.sin(pitch * DEG_TO_RAD) * -Math.sin(angle * DEG_TO_RAD) * distanceFromCenter;
    }

    /**
     * Returns the width of the pie in pixels dependent on the associated view
     * port.
     */
    public double getWidth()
    {
	return radius * 2.0;
    }

    public double getPitch() 
    {
	return pitch;
    }

}
