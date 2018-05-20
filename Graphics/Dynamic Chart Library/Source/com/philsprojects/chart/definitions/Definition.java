package com.philsprojects.chart.definitions;

import java.awt.Font;

import com.philsprojects.chart.fills.Fill;
import com.philsprojects.chart.outlines.Outline;

public class Definition
{

    private Font font;
    private Fill fontFill;
    private Outline fontOutline;
    
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
    
    
}
