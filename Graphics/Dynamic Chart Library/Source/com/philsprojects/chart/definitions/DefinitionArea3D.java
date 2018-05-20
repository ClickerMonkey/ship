package com.philsprojects.chart.definitions;

import com.philsprojects.chart.fills.Fill;

public class DefinitionArea3D extends DefinitionArea2D
{

    private Fill downFill;
    private Fill upFill;
    
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
    
}
