package com.philsprojects.chart.view.input;

import com.philsprojects.chart.view.Viewport;

public abstract class ViewportEvent
{

    protected float scale;
    protected Viewport view;
    

    protected ViewportEvent(Viewport view, float scale)
    {
	this.view = view;
	this.scale = scale;
    }
    
    public abstract void eventTriggered(float strength);


    /**
     * @return the scale
     */
    public float getScale()
    {
        return scale;
    }

    /**
     * @return the view
     */
    public Viewport getView()
    {
        return view;
    }

    /**
     * @param scale the scale to set
     */
    public void setScale(float scale)
    {
        this.scale = scale;
    }

    /**
     * @param view the view to set
     */
    public void setView(Viewport view)
    {
        this.view = view;
    }
    
}
