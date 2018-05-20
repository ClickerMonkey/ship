package com.philsprojects.chart.settings;

import com.philsprojects.chart.view.Viewport;

public class SettingsBar2D extends Settings
{

    public enum RenderType
    {
	Stacked {
	    public double getDatasetWidth(SettingsBar2D settings, int lists) {
		return settings.barWidth + settings.spacing;
	    }
	},
	Clustered {
	    public double getDatasetWidth(SettingsBar2D settings, int lists) {
		return (settings.barWidth + settings.clusterSpacing) * lists + settings.spacing;
	    }
	},
	Percent {
	    public double getDatasetWidth(SettingsBar2D settings, int lists) {
		return settings.barWidth + settings.spacing;
	    }
	};

	public abstract double getDatasetWidth(SettingsBar2D settings, int lists);

    }

    private double offset;
    private double spacing;
    private double clusterSpacing;
    private double barWidth;
    private RenderType type;

    public SettingsBar2D()
    {
	super();
    }
    
    public SettingsBar2D(Viewport view)
    {
	super(view);
    }

    public double getBarWidth()
    {
	return view.toScreenSizeX(barWidth);
    }

    public double getBarHeight(double value)
    {
	return view.toScreenSizeY(value);
    }

    public double getSpacing()
    {
	return view.toScreenSizeX(spacing);
    }

    public double getClusterSpacing()
    {
	return view.toScreenSizeX(clusterSpacing);
    }

    public double getOffsetX()
    {
	return view.toScreenX(offset);
    }

    public double getOffsetY()
    {
	return view.toScreenY(0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getDatasetWidth(int lists)
    {
	return view.toScreenSizeX(type.getDatasetWidth(this, lists));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getDatasetSpacing()
    {
	return view.toScreenSizeX(spacing);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getDatasetOffset()
    {
	return view.toScreenX(offset);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int getFirstVisibleDataset(int lists)
    {
	double start =  view.getLeft() - offset + spacing;
	double width = type.getDatasetWidth(this, lists);
	return (int)Math.floor(start / width);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getLastVisibleDataset(int lists)
    {
	double end =  view.getRight() - offset;
	double width = type.getDatasetWidth(this, lists);
	return (int)Math.ceil(end / width) - 1;
    }

    public RenderType getRenderType()
    {
	return type;
    }

    public double getOffset()
    {
	return offset;
    }

    /**
     * @return the type
     */
    public RenderType getType()
    {
	return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(RenderType type)
    {
	this.type = type;
	
	if (view != null) view.requestRedraw();
    }

    /**
     * @param offset the offset to set
     */
    public void setOffset(double offset)
    {
	this.offset = offset;
	
	if (view != null) view.requestRedraw();
    }

    /**
     * @param spacing the spacing to set
     */
    public void setSpacing(double spacing)
    {
	this.spacing = spacing;
	
	if (view != null) view.requestRedraw();
    }

    /**
     * @param clusterSpacing the clusterSpacing to set
     */
    public void setClusterSpacing(double clusterSpacing)
    {
	this.clusterSpacing = clusterSpacing;
	
	if (view != null) view.requestRedraw();
    }

    /**
     * @param barWidth the barWidth to set
     */
    public void setBarWidth(double barWidth)
    {
	this.barWidth = barWidth;
	
	if (view != null) view.requestRedraw();
    }

}
