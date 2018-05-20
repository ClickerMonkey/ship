package com.philsprojects.chart.view.input;

import com.philsprojects.chart.view.Viewport;

public class EventZoom extends ViewportEvent
{


    public enum ZoomType
    {
	In {
	    protected void zoom(Viewport view, float strength) {
		view.zoom(-Math.abs(strength + 1));
	    }
	},
	Out {
	    protected void zoom(Viewport view, float strength) {
		view.zoom(Math.abs(strength + 1));
	    }
	},
	InOut {
	    protected void zoom(Viewport view, float strength) {
		view.zoom(strength + 1);
	    }
	};
	
	protected abstract void zoom(Viewport view, float strength);
    }

    public EventZoom(Viewport view, ZoomType direction, float scale)
    {
	super(view, scale);
	
	this.direction = direction;
    }
    
    
    protected ZoomType direction;


    @Override
    public void eventTriggered(float strength)
    {
	direction.zoom(view, strength * scale);
	view.requestRedraw();
    }
    
    
    
}
