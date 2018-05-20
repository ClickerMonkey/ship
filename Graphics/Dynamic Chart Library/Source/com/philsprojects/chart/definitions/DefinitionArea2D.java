package com.philsprojects.chart.definitions;

import com.philsprojects.chart.fills.Fill;
import com.philsprojects.chart.outlines.Outline;

public class DefinitionArea2D extends DefinitionLine2D
{

    private Outline areaOutline;
    private Fill areaFill;
    
    /**
     * @return the areaOutline
     */
    public Outline getAreaOutline()
    {
        return areaOutline;
    }
    
    /**
     * @param areaOutline the areaOutline to set
     */
    public void setAreaOutline(Outline areaOutline)
    {
        this.areaOutline = areaOutline;
    }
    
    /**
     * @return the areaFill
     */
    public Fill getAreaFill()
    {
        return areaFill;
    }
    
    /**
     * @param areaFill the areaFill to set
     */
    public void setAreaFill(Fill areaFill)
    {
        this.areaFill = areaFill;
    }
    
}
