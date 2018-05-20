package com.philsprojects.chart.definitions;

import com.philsprojects.chart.fills.Fill;
import com.philsprojects.chart.outlines.Outline;

public class DefinitionLine3D extends DefinitionLine2D
{

    private Fill downFill;
    private Fill upFill;
    private Outline backOutline;
    
    /**
     * @return the downFill
     */
    public Fill getDownFill()
    {
        return downFill;
    }
    /**
     * @param downFill the downFill to set
     */
    public void setDownFill(Fill downFill)
    {
        this.downFill = downFill;
    }
    /**
     * @return the upFill
     */
    public Fill getUpFill()
    {
        return upFill;
    }
    /**
     * @param upFill the upFill to set
     */
    public void setUpFill(Fill upFill)
    {
        this.upFill = upFill;
    }
    /**
     * @return the backOutline
     */
    public Outline getBackOutline()
    {
        return backOutline;
    }
    /**
     * @param backOutline the backOutline to set
     */
    public void setBackOutline(Outline backOutline)
    {
        this.backOutline = backOutline;
    }
    
}
