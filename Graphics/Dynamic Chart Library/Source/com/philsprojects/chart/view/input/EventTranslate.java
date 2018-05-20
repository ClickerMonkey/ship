package com.philsprojects.chart.view.input;

import com.philsprojects.chart.view.Viewport;

public class EventTranslate extends ViewportEvent
{


    public enum Direction
    {
	Vertical {
	    protected void translate(Viewport view, float strength) {
		view.translateY(view.toWorldSizeY(strength));
	    }
	},
	Horizontal {
	    protected void translate(Viewport view, float strength) {
		view.translateX(view.toWorldSizeX(strength));
	    }
	};
	
	protected abstract void translate(Viewport view, float strength);
    }

    public EventTranslate(Viewport view, Direction direction, float scale)
    {
	super(view, scale);
	
	this.direction = direction;
    }
    
    
    protected Direction direction;


    @Override
    public void eventTriggered(float strength)
    {
	direction.translate(view, strength * scale);
	view.requestRedraw();
    }
    
    
    
}
