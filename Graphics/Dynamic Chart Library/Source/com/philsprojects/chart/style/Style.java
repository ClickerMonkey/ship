package com.philsprojects.chart.style;

import com.philsprojects.chart.LegendDefinition;
import com.philsprojects.chart.common.GridBoth;
import com.philsprojects.chart.common.GridHorizontal;
import com.philsprojects.chart.common.GridVertical;
import com.philsprojects.chart.common.LabelBar;
import com.philsprojects.chart.common.ValueBar;
import com.philsprojects.chart.definitions.DefinitionArea2D;
import com.philsprojects.chart.definitions.DefinitionArea3D;
import com.philsprojects.chart.definitions.DefinitionBar2D;
import com.philsprojects.chart.definitions.DefinitionBar3D;
import com.philsprojects.chart.definitions.DefinitionLine2D;
import com.philsprojects.chart.definitions.DefinitionLine3D;
import com.philsprojects.chart.definitions.DefinitionPie2D;
import com.philsprojects.chart.definitions.DefinitionPie3D;
import com.philsprojects.chart.view.ViewportComponent;

public abstract class Style 
{

	protected final int lists;
	
	public Style(int lists)
	{
		this.lists = lists;
	}
	
	public abstract LegendDefinition[] createLegends();
	public abstract DefinitionArea2D createArea2D();
	public abstract DefinitionArea3D createArea3D();
	public abstract DefinitionBar2D createBar2D();
	public abstract DefinitionBar3D createBar3D();
	public abstract DefinitionLine2D createLine2D();
	public abstract DefinitionLine3D createLine3D();
	public abstract DefinitionPie2D createPie2D();
	public abstract DefinitionPie3D createPie3D();
	public abstract ViewportComponent createComponent(int width, int height);
	public abstract ValueBar createValueBar(boolean antialiasing, int x, int y, int width, int height);
	public abstract LabelBar createLabelBar(boolean antialiasing, int x, int y, int width, int height);
	public abstract GridBoth createGridBoth();
	public abstract GridVertical createGridVertical();
	public abstract GridHorizontal createGridHorizontal();
	
	
}
