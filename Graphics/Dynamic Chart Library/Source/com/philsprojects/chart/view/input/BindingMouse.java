package com.philsprojects.chart.view.input;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.event.MouseInputListener;

public class BindingMouse extends InputBinding implements MouseWheelListener, MouseInputListener
{
    
    public enum EventTrigger
    {
	Horizontal {
	    protected int getStrength(MouseEvent current, MouseEvent previous) {
		return (previous.getX() - current.getX());
	    }
	},
	Vertical {
	    protected int getStrength(MouseEvent current, MouseEvent previous) {
		return (current.getY() - previous.getY());
	    }
	},
	WheelScroll {
	    protected int getStrength(MouseEvent current, MouseEvent previous) {
		return ((MouseWheelEvent)current).getWheelRotation();
	    }
	};
	
	protected abstract int getStrength(MouseEvent current, MouseEvent previous);
    }

    public enum Mode
    {
	Moving,
	Dragging,
	Any
    }
    
    protected int button;
    protected int buttonDown;
    protected Mode mode;
    protected EventTrigger trigger;
    
    protected MouseEvent current;
    protected MouseEvent previous;

    
    public BindingMouse(ViewportEvent event, Mode mode, EventTrigger trigger, float scale)
    {
	this(event, mode, trigger, MouseEvent.BUTTON1, scale);
    }
    
    public BindingMouse(ViewportEvent event, Mode mode, EventTrigger trigger, int button, float scale)
    {
	super(event, scale);
	
	this.mode = mode;
	this.trigger = trigger;
	this.button = button;
    }
    
    public void listenTo(Component component)
    {
	component.addMouseListener(this);
	component.addMouseMotionListener(this);
	component.addMouseWheelListener(this);
    }


    public void mousePressed(MouseEvent e)
    {
	previous = current = e;
	buttonDown = e.getButton();
    }

    public void mouseDragged(MouseEvent e)
    {
	if (button != buttonDown || mode != Mode.Dragging || trigger == EventTrigger.WheelScroll)
	    return;
	
	previous = current;
	current = e;
	
	float strength = trigger.getStrength(current, previous);
	
	event.eventTriggered(strength * scale);
    }
 
    public void mouseMoved(MouseEvent e)
    {
	if (button != buttonDown || mode != Mode.Moving || trigger == EventTrigger.WheelScroll)
	    return;
	
	previous = current;
	current = e;
	
	float strength = trigger.getStrength(current, previous);
	
	event.eventTriggered(strength * scale);
    }

    public void mouseWheelMoved(MouseWheelEvent e)
    {
	if (trigger != EventTrigger.WheelScroll)
	    return;
	
	float strength = trigger.getStrength(e, null);
	
	event.eventTriggered(strength * scale);
    }

    public void mouseClicked(MouseEvent e)
    {
	
    }

    public void mouseEntered(MouseEvent e)
    {
	
    }

    public void mouseExited(MouseEvent e)
    {
	
    }

    public void mouseReleased(MouseEvent e)
    {
	
    }

}
