package com.philsprojects.chart.definitions;

import com.philsprojects.chart.fills.Fill;
import com.philsprojects.chart.outlines.Outline;

public class DefinitionBar2D extends Definition
{

    private Fill barFill;
    private Outline barOutline;
    
    /**
     * @return the barFill
     */
    public Fill getBarFill()
    {
        return barFill;
    }
    /**
     * @param barFill the barFill to set
     */
    public void setBarFill(Fill barFill)
    {
        this.barFill = barFill;
    }
    /**
     * @return the barOutline
     */
    public Outline getBarOutline()
    {
        return barOutline;
    }
    /**
     * @param barOutline the barOutline to set
     */
    public void setBarOutline(Outline barOutline)
    {
        this.barOutline = barOutline;
    }
   
}
