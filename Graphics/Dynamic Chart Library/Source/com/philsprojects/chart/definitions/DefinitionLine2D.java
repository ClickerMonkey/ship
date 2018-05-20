package com.philsprojects.chart.definitions;

import com.philsprojects.chart.fills.Fill;
import com.philsprojects.chart.icons.Icon;
import com.philsprojects.chart.outlines.Outline;

public class DefinitionLine2D extends Definition
{

    private Outline lineOutline;
    private Icon pointIcon;
    private Fill pointIconFill;
    private Outline pointIconOutline;
    
    /**
     * @return the lineOutline
     */
    public Outline getLineOutline()
    {
        return lineOutline;
    }
    /**
     * @param lineOutline the lineOutline to set
     */
    public void setLineOutline(Outline lineOutline)
    {
        this.lineOutline = lineOutline;
    }
    /**
     * @return the pointIcon
     */
    public Icon getPointIcon()
    {
        return pointIcon;
    }
    /**
     * @param pointIcon the pointIcon to set
     */
    public void setPointIcon(Icon pointIcon)
    {
        this.pointIcon = pointIcon;
    }
    /**
     * @return the pointIconFill
     */
    public Fill getPointIconFill()
    {
        return pointIconFill;
    }
    /**
     * @param pointIconFill the pointIconFill to set
     */
    public void setPointIconFill(Fill pointIconFill)
    {
        this.pointIconFill = pointIconFill;
    }
    /**
     * @return the pointIconOutline
     */
    public Outline getPointIconOutline()
    {
        return pointIconOutline;
    }
    /**
     * @param pointIconOutline the pointIconOutline to set
     */
    public void setPointIconOutline(Outline pointIconOutline)
    {
        this.pointIconOutline = pointIconOutline;
    }
    
}
