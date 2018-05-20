package com.philsprojects.chart.definitions;

import com.philsprojects.chart.fills.Fill;

public class DefinitionPie3D extends DefinitionPie2D
{

    private Fill sideFill;
    private Fill arcFill;
    
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
    /**
     * @return the arcFill
     */
    public Fill getArcFill()
    {
        return arcFill;
    }
    /**
     * @param arcFill the arcFill to set
     */
    public void setArcFill(Fill arcFill)
    {
        this.arcFill = arcFill;
    }
    
}
