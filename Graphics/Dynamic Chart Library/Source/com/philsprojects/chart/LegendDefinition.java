package com.philsprojects.chart;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

import com.philsprojects.chart.fills.Fill;
import com.philsprojects.chart.icons.Icon;
import com.philsprojects.chart.outlines.Outline;

public class LegendDefinition
{

    private String label;
    
    private Icon icon;
    private Fill iconFill;
    private Outline iconOutline;

    private Font font;
    private Fill fontFill;
    private Outline fontOutline;
    
    public LegendDefinition()
    {
	
    }
    
    public LegendDefinition(String label)
    {
	this.label = label;
    }
    
    public LegendDefinition(String label, LegendDefinition base)
    {
	this.label = label;
	this.icon = base.icon;
	this.iconFill = base.iconFill;
	this.iconOutline = base.iconOutline;
	this.font = base.font;
	this.fontFill = base.fontFill;
	this.fontOutline = base.fontOutline;
    }
    

    /**
     * @param font the font to set
     */
    public void setFont(Font font, Fill fontFill)
    {
        this.font = font;
        this.fontFill = fontFill;
    }
    
    /**
     * @return the fontOutline
     */
    public Outline getFontOutline()
    {
        return fontOutline;
    }

    /**
     * @param fontOutline the fontOutline to set
     */
    public void setFontOutline(Outline fontOutline)
    {
        this.fontOutline = fontOutline;
    }

    /**
     * Returns the bounds that fit this definitions label according
     * 
     * @param gr
     */
    public int getLabelWidth(Graphics2D gr)
    {
	FontMetrics fm = gr.getFontMetrics(font);
	
	return fm.stringWidth(label);
    }
    
    /**
     * Returns the bounds that fit this definitions label according
     * 
     * @param gr
     */
    public int getLabelHeight(Graphics2D gr)
    {
	return font.getSize();
	
//	FontMetrics fm = gr.getFontMetrics(font);
//	return fm.getAscent();
    }
    
    /**
     * @return the label
     */
    public String getLabel()
    {
        return label;
    }

    /**
     * @param label the label to set
     */
    public void setLabel(String label)
    {
        this.label = label;
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
     * @param icon the icon to set
     */
    public void setIcon(Icon icon, Fill iconFill, Outline iconOutline)
    {
        this.icon = icon;
        this.iconFill = iconFill;
        this.iconOutline = iconOutline;
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
    
    /**
     * @return the font
     */
    public Font getFont()
    {
        return font;
    }
    
    /**
     * @param font the font to set
     */
    public void setFont(Font font)
    {
        this.font = font;
    }
    
    /**
     * @return the fontFill
     */
    public Fill getFontFill()
    {
        return fontFill;
    }
    
    /**
     * @param fontFill the fontFill to set
     */
    public void setFontFill(Fill fontFill)
    {
        this.fontFill = fontFill;
    }

}
