package com.philsprojects.chart.settings;

import com.philsprojects.chart.view.Viewport;

public class SettingsLine2D extends Settings
{

	public enum RenderType
	{
		Stacked,
		Clustered,
		Percent;
	}

	private double offset;
	private double spacing;
	private RenderType type;


	public SettingsLine2D()
	{
	}

	public SettingsLine2D(Viewport view)
	{
		super(view);
	}

	public double getLineOffsetY(double value)
	{
		return view.toScreenSizeY(value);
	}

	public double getSpacing()
	{
		return view.toScreenSizeX(spacing);
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
	 public double getDatasetSpacing()
	 {
		 return view.toScreenSizeX(spacing);
	 }

	 /**
	  * {@inheritDoc}
	  */
	 @Override
	 public int getFirstVisibleDataset(int lists)
	 {
		 double start =  view.getLeft() - offset + spacing;
		 return (int)Math.floor(start / spacing) - 1;
	 }

	 /**
	  * {@inheritDoc}
	  */
	 @Override
	 public int getLastVisibleDataset(int lists)
	 {
		 double end =  view.getRight() - offset;
		 return (int)Math.ceil(end / spacing);
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

}
