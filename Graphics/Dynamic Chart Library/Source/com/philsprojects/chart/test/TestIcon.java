package com.philsprojects.chart.test;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;

import javax.swing.JFrame;

import com.philsprojects.chart.fills.Fill;
import com.philsprojects.chart.fills.FillLinear;
import com.philsprojects.chart.icons.IconCircle;
import com.philsprojects.chart.icons.IconDiamond;
import com.philsprojects.chart.icons.IconNGon;
import com.philsprojects.chart.icons.IconPie;
import com.philsprojects.chart.icons.IconSquare;
import com.philsprojects.chart.icons.IconStar;
import com.philsprojects.chart.icons.IconTriangle;
import com.philsprojects.chart.view.Canvas;
import com.philsprojects.chart.view.Viewport;
import com.philsprojects.chart.view.ViewportComponent;

public class TestIcon 
{

    public static void main(String[] args)
    {
	final double SIZE = 40.0;
	final int ICONS = 7;
	final int WIDTH = (int)(SIZE * 1.25) * ICONS;
	final int HEIGHT = (int)(SIZE * 1.25);


	final IconCircle circle = new IconCircle(SIZE);
	final IconDiamond diamond = new IconDiamond(SIZE);
	final IconSquare square = new IconSquare(SIZE);
	final IconTriangle triangle = new IconTriangle(SIZE);
	final IconStar star = new IconStar(SIZE, 5, 0.4, 90.0);
	final IconPie pie = new IconPie(SIZE, 90.0, 70.0, true);
	final IconNGon ngon = new IconNGon(SIZE, 5, 90.0);


	final Fill area = new FillLinear(
		new Color(0, 0, 255), 
		new Color(0, 0, 127), FillLinear.DIAGONAL_LEFT);

	final Fill outline = new FillLinear(
		new Color(255, 255, 255, 120), 
		new Color(0, 0, 0, 120), FillLinear.DIAGONAL_LEFT);

	final Stroke outlineStroke = new BasicStroke(4f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);


	final Canvas canvas = new Canvas(true, 0, 0, WIDTH, HEIGHT)
	{
	    @Override
	    protected void onDraw(Graphics2D gr)
	    {
		final double y = (SIZE * 0.625);
		final double x = (SIZE * 0.625);
		final double interval = (SIZE * 1.25);

		Shape[] shapes = {
			circle.getShape(x, y),
			diamond.getShape(x + interval, y),
			square.getShape(x + interval * 2, y),
			triangle.getShape(x + interval * 3, y),
			star.getShape(x + interval * 4, y),
			pie.getShape(x + interval * 5, y),
			ngon.getShape(x + interval * 6, y),
		};

		for (Shape s : shapes)
		{
		    area.setShape(s);
		    area.select(gr);
		    gr.fill(s);
		}

		gr.setStroke(outlineStroke);
		for (Shape s : shapes)
		{
		    outline.setShape(s);
		    outline.select(gr);
		    gr.draw(s);
		}
	    }
	    @Override
	    protected boolean listensTo(Viewport view) {
		return false;
	    }
	};

	final ViewportComponent panel = new ViewportComponent(WIDTH, HEIGHT);
	panel.addCanvas(canvas);

	final JFrame window = new JFrame("Icon Testing.");
	window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	window.add(panel);
	window.pack();
	window.setLocationRelativeTo(null);
	window.setVisible(true);
    }

}
