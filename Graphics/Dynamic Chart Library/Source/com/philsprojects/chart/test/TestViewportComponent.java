package com.philsprojects.chart.test;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Stroke;

import javax.swing.JFrame;

import com.philsprojects.chart.common.Anchor;
import com.philsprojects.chart.common.Axis;
import com.philsprojects.chart.common.AxisX;
import com.philsprojects.chart.common.AxisY;
import com.philsprojects.chart.common.Grid;
import com.philsprojects.chart.common.GridBoth;
import com.philsprojects.chart.common.Padding;
import com.philsprojects.chart.common.ValueBar;
import com.philsprojects.chart.common.ValueFormat;
import com.philsprojects.chart.fills.FillDelta;
import com.philsprojects.chart.outlines.OutlineDelta;
import com.philsprojects.chart.view.Canvas;
import com.philsprojects.chart.view.Viewport;
import com.philsprojects.chart.view.ViewportComponent;

public class TestViewportComponent
{

    public static void main(String[] args)
    {
	final Color startColor = new Color(64, 64, 120, 255);
	final Color endColor = new Color(64, 64, 120, 0);

	final Grid grid = new GridBoth();
	grid.setOutline(new OutlineDelta(startColor, endColor, 2f, 0.5f));
	grid.setStartFrequency(100);
	grid.setFrequency(100);
	grid.setEndFrequency(8);
	grid.setInterval(10);
	grid.setOffset(0);

	final ValueBar leftBar = new ValueBar(ValueFormat.Decimal2);
	leftBar.setFont(new Font("Arial", Font.PLAIN, 12));
	leftBar.setFontFill(new FillDelta(startColor, endColor));
	leftBar.setAnchor(Anchor.CenterRight);
	leftBar.setPadding(new Padding(5));
	leftBar.setStartFrequency(100);
	leftBar.setFrequency(100);
	leftBar.setEndFrequency(0);
	leftBar.setInterval(10);
	leftBar.setOffset(0);

	final ValueBar rightBar = new ValueBar(ValueFormat.Decimal2);
	rightBar.setFont(new Font("Arial", Font.PLAIN, 12));
	rightBar.setFontFill(new FillDelta(startColor, endColor));
	rightBar.setAnchor(Anchor.CenterLeft);
	rightBar.setPadding(new Padding(5));
	rightBar.setStartFrequency(100);
	rightBar.setFrequency(100);
	rightBar.setEndFrequency(0);
	rightBar.setInterval(10);
	rightBar.setOffset(0);

	final Stroke axisStroke = new BasicStroke(2f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL);

	final Axis axisX = new AxisX();
	axisX.setColor(Color.red);
	axisX.setStroke(axisStroke);

	final Axis axisY = new AxisY();
	axisY.setColor(Color.red);
	axisY.setStroke(axisStroke);

	final Viewport viewPort = new Viewport(true, 100, 50, 300, 300) {
	    protected void onDraw(Graphics2D gr) {
		grid.draw(gr);
		axisX.draw(gr, this, this);
		axisY.draw(gr, this, this);
	    }
	};
	viewPort.setBounds(225, -25, 225, -25);
	viewPort.setMinWidth(0.2);
	viewPort.setMinHeight(0.2);

	grid.setDestination(viewPort);
	grid.setView(viewPort);

	final Canvas leftCanvas = new Canvas(true, 0, 50, 100, 300) {
	    protected void onDraw(Graphics2D gr) {
		leftBar.draw(gr);
	    }
	    protected boolean listensTo(Viewport view) {
		return (view == viewPort);
	    }
	};
	leftBar.setDestination(leftCanvas);
	leftBar.setView(viewPort);

	final Canvas rightCanvas = new Canvas(true, 400, 50, 100, 300) {
	    protected void onDraw(Graphics2D gr) {
		rightBar.draw(gr);
	    }
	    protected boolean listensTo(Viewport view) {
		return (view == viewPort);
	    }
	};
	rightBar.setDestination(rightCanvas);
	rightBar.setView(viewPort);

	final ViewportComponent component = new ViewportComponent(500, 400);
	component.addCanvas(viewPort);
	component.addCanvas(leftCanvas);
	component.addCanvas(rightCanvas);

	leftCanvas.setBackground(component.getBackground());
	rightCanvas.setBackground(component.getBackground());

	final JFrame window = new JFrame("Viewport Component Testing.");
	window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	window.add(component);
	window.pack();
	window.setLocationRelativeTo(null);
	window.setVisible(true);
    }

}
