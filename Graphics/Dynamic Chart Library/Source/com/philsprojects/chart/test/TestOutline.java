package com.philsprojects.chart.test;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JFrame;

import com.philsprojects.chart.fills.FillGradient;
import com.philsprojects.chart.outlines.Outline;
import com.philsprojects.chart.outlines.OutlineDashed;
import com.philsprojects.chart.outlines.OutlineFill;
import com.philsprojects.chart.outlines.OutlineSolid;
import com.philsprojects.chart.outlines.Outline.Cap;
import com.philsprojects.chart.outlines.Outline.Join;
import com.philsprojects.chart.view.Canvas;
import com.philsprojects.chart.view.Viewport;
import com.philsprojects.chart.view.ViewportComponent;

public class TestOutline 
{

    public static void main(String[] args)
    {
	final double SIZE = 60.0;

	final int OUTLINES = 3;
	final int WIDTH = (int)(SIZE * 1.25) * OUTLINES;
	final int HEIGHT = (int)(SIZE * 1.25) * 2;

	final Outline color = new OutlineSolid(Color.blue, 6f, Cap.Butt, Join.Miter);
	final Outline fill = new OutlineFill(new FillGradient(Color.lightGray, Color.black, FillGradient.VERTICAL), 6f);
	final Outline stroke = new OutlineDashed(
		new FillGradient(Color.black, Color.lightGray, FillGradient.HORIZONTAL), 
		6f, Cap.Square, Join.Miter, 1f, 0f, 20f, 10f);

	final Canvas canvas = new Canvas(true, 0, 0, WIDTH, HEIGHT)
	{
	    @Override
	    protected void onDraw(Graphics2D gr)
	    {
		final Rectangle2D.Double rect = new Rectangle2D.Double();
		final Ellipse2D.Double oval = new Ellipse2D.Double();


		final double y = (SIZE * 0.125);
		final double x = (SIZE * 0.125);
		final double interval = (SIZE * 1.25);

		rect.width = SIZE;
		rect.height = SIZE;
		rect.y = y;
		rect.x = x;
		oval.width = SIZE;
		oval.height = SIZE;
		oval.y = y + interval;
		oval.x = x;

		Outline[] outlines = { color, fill, stroke };

		for (int i = 0; i < OUTLINES; i++)
		{
		    outlines[i].setShape(rect);
		    outlines[i].select(gr);
		    gr.draw(rect);

		    outlines[i].setShape(oval);
		    outlines[i].select(gr);
		    gr.draw(oval);

		    rect.x += interval;
		    oval.x += interval;
		}
	    }
	    @Override
	    protected boolean listensTo(Viewport view)
	    {
		return false;
	    }
	};


	final ViewportComponent panel = new ViewportComponent(WIDTH, HEIGHT);
	panel.addCanvas(canvas);

	final JFrame window = new JFrame("Fill Testing.");
	window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	window.add(panel);
	window.pack();
	window.setLocationRelativeTo(null);
	window.setVisible(true);
    }

}
