package com.philsprojects.chart.definitions;

import com.philsprojects.chart.fills.Fill;

public class DefinitionBar3D extends DefinitionBar2D
{

    private Fill topFill;
    private Fill sideFill;
    
    /**
     * @return the topFill
     */
    public Fill getTopFill()
    {
        return topFill;
    }
    /**
     * @param topFill the topFill to set
     */
    public void setTopFill(Fill topFill)
    {
        this.topFill = topFill;
    }
    /**
     * @return the sideFill
     */
    public Fill getSideFill()
    {
        return sideFill;
    }
    /**
     * @param sideFill the sideFill to set
     */
    public void setSideFill(Fill sideFill)
    {
        this.sideFill = sideFill;
    }
    
}
