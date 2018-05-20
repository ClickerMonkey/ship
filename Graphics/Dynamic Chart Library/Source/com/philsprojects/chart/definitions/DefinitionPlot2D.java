package com.philsprojects.chart.definitions;

import com.philsprojects.chart.fills.Fill;
import com.philsprojects.chart.icons.Icon;
import com.philsprojects.chart.outlines.Outline;

public class DefinitionPlot2D extends Definition
{

    private Outline lineOutline;
    private Icon icon;
    private Fill iconFill;
    private Outline iconOutline;
    
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
     * @return the icon
     */
    public Icon getIcon()
    {
        return icon;
    }
    /**
     * @param icon the icon to set
     */
    public void setIcon(Icon icon)
    {
        this.icon = icon;
    }
    /**
     * @return the iconFill
     */
    public Fill getIconFill()
    {
        return iconFill;
    }
    /**
     * @param iconFill the iconFill to set
     */
    public void setIconFill(Fill iconFill)
    {
        this.iconFill = iconFill;
    }
    /**
     * @return the iconOutline
     */
    public Outline getIconOutline()
    {
        return iconOutline;
    }
    /**
     * @param iconOutline the iconOutline to set
     */
    public void setIconOutline(Outline iconOutline)
    {
        this.iconOutline = iconOutline;
    }
    
}
