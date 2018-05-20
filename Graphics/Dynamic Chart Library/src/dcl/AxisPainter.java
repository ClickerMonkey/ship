package dcl;

import java.awt.Graphics2D;

public class AxisPainter extends Painter 
{

	private AxisSeries[] series;
	private AxisSettings settings;
	
	@Override
	public void draw(Graphics2D gr) 
	{

	}

	/**
	 * @param index the index
	 * @return the series
	 */
	public AxisSeries getSeries(int index)
	{
	    return series[index];
	}


	/**
	 * @param index the index
	 * @param axisSeries the series to set
	 */
	public void setSeries(int index, AxisSeries axisSeries)
	{
	    series[index] = axisSeries;
	}

	/**
	 * @return the settings
	 */
	public AxisSettings getSettings()
	{
	    return settings;
	}

	/**
	 * @param settings the settings to set
	 */
	public void setSettings(AxisSettings settings)
	{
	    this.settings = settings;
	}

}
