package com.philsprojects.chart.view.input;

import java.awt.Component;

public abstract class InputBinding
{

    protected float scale;
    protected ViewportEvent event;

    protected InputBinding(ViewportEvent event, float scale)
    {
	this.event = event;
	this.scale = scale;
    }
    
    public abstract void listenTo(Component component);

    /**
     * @return the scale
     */
    public float getScale()
    {
        return scale;
    }

    /**
     * @return the event
     */
    public ViewportEvent getEvent()
    {
        return event;
    }

    /**
     * @param scale the scale to set
     */
    public void setScale(float scale)
    {
        this.scale = scale;
    }
    
    /**
     * @param event the event to set
     */
    public void setEvent(ViewportEvent event)
    {
        this.event = event;
    }
    
}
