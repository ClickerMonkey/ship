package com.philsprojects.chart.view.input;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.philsprojects.chart.common.Axis;
import com.philsprojects.chart.common.AxisX;
import com.philsprojects.chart.common.AxisY;
import com.philsprojects.chart.common.Grid;
import com.philsprojects.chart.common.GridBoth;
import com.philsprojects.chart.outlines.OutlineDelta;
import com.philsprojects.chart.view.Canvas;
import com.philsprojects.chart.view.CanvasListener;
import com.philsprojects.chart.view.Viewport;
import com.philsprojects.chart.view.input.BindingMouse.EventTrigger;
import com.philsprojects.chart.view.input.BindingMouse.Mode;
import com.philsprojects.chart.view.input.EventTranslate.Direction;
import com.philsprojects.chart.view.input.EventZoom.ZoomType;

public class TestBinding
{

    public static void main(String[] args)
    {
	final Grid grid = new GridBoth();
	grid.setEndFrequency(5f);
	grid.setFrequency(80f);
	grid.setStartFrequency(120f);
	grid.setInterval(10);
	grid.setOutline(new OutlineDelta(Color.black, Color.white, 2f, 0.5f));

	final Axis axisX = new AxisX(new BasicStroke(2.5f), Color.blue);
	final Axis axisY = new AxisY(new BasicStroke(2.5f), Color.blue);
	
	final Viewport view = new Viewport(true, 50, 50, 412, 412) {
	    protected void onDraw(Graphics2D gr) {
		grid.draw(gr);
		axisX.draw(gr, this, this);
		axisY.draw(gr, this, this);
	    }
	};
	view.setBounds(206, -206, 206, -206);
	view.setBackground(Color.white);

	grid.setDestination(view);
	grid.setView(view);

	final JPanel panel = new JPanel() {
	    public void paint(Graphics g) {
		super.paint(g);
		view.draw((Graphics2D)g);
	    }
	};
	panel.setPreferredSize(new Dimension(512, 512));
	
	view.setListener(new CanvasListener() {
	    public void requestRedraw(Canvas canvas) {
		canvas.draw((Graphics2D)panel.getGraphics());
	    }
	});

	new BindingMouse(new EventTranslate(view, Direction.Horizontal, 1f), 
		Mode.Dragging, EventTrigger.Horizontal, 1f).listenTo(panel);
	new BindingMouse(new EventTranslate(view, Direction.Vertical, 1f), 
		Mode.Dragging, EventTrigger.Vertical, 1f).listenTo(panel);
	new BindingMouse(new EventZoom(view, ZoomType.InOut, 0.1f),
		Mode.Any, EventTrigger.WheelScroll, 1f).listenTo(panel);
	
	final JFrame window = new JFrame("Testing");
	window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	window.add(panel);
	window.pack();
	window.setLocationRelativeTo(null);
	window.setVisible(true);    
    }

}
