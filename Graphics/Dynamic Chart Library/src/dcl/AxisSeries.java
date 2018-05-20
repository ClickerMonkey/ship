package dcl;

import java.awt.geom.GeneralPath;

import com.philsprojects.chart.view.Viewport;

public class AxisSeries extends Series 
{
	
	protected float offset;
	protected float width;
	protected Layout layout;
	
	protected int startIndex;
	protected int endIndex;
	protected float[] heightMap;
	protected GeneralPath heightPath;

	public AxisSeries(Viewport view) {
		super(view);
	}
	
}
