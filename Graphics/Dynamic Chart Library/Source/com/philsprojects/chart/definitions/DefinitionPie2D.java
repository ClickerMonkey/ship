package com.philsprojects.chart.definitions;

import com.philsprojects.chart.fills.Fill;
import com.philsprojects.chart.outlines.Outline;

public class DefinitionPie2D extends Definition
{

    private Fill sliceFill;
    private Outline sliceOutline;
    
    /**
     * @return the sliceFill
     */
    public Fill getSliceFill()
    {
        return sliceFill;
    }
    /**
     * @param sliceFill the sliceFill to set
     */
    public void setSliceFill(Fill sliceFill)
    {
        this.sliceFill = sliceFill;
    }
    /**
     * @return the sliceOutline
     */
    public Outline getSliceOutline()
    {
        return sliceOutline;
    }
    /**
     * @param sliceOutline the sliceOutline to set
     */
    public void setSliceOutline(Outline sliceOutline)
    {
        this.sliceOutline = sliceOutline;
    }
    
}
