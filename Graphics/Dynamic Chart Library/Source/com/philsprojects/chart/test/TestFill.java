package com.philsprojects.chart.test;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import com.philsprojects.chart.fills.Fill;
import com.philsprojects.chart.fills.FillCyclic;
import com.philsprojects.chart.fills.FillGradient;
import com.philsprojects.chart.fills.FillImage;
import com.philsprojects.chart.fills.FillLinear;
import com.philsprojects.chart.fills.FillRadial;
import com.philsprojects.chart.fills.FillSolid;
import com.philsprojects.chart.view.Canvas;
import com.philsprojects.chart.view.Viewport;
import com.philsprojects.chart.view.ViewportComponent;

public class TestFill 
{

	public static void main(String[] args)
	{
		final double SIZE = 60.0;

		final int FILLS = 6;
		final int WIDTH = (int)(SIZE * 1.25) * FILLS;
		final int HEIGHT = (int)(SIZE * 1.25) * 2;

		final FillCyclic cyclic = new FillCyclic(Color.blue, Color.red, 70.0);
		final FillGradient gradient = new FillGradient(Color.blue, Color.red, FillGradient.VERTICAL);
		final FillImage image = new FillImage(createPattern(20, 20), new Rectangle2D.Double(0, 0, 20, 20));
		final FillRadial radial = new FillRadial(Color.blue, Color.red, FillGradient.VERTICAL);
		final FillSolid solid = new FillSolid(Color.blue);
		final FillLinear linear = new FillLinear(
				new Color[] {Color.blue, Color.red, Color.green}, 
				new float[] {0, 0.3f, 1}, FillLinear.HORIZONTAL, FillLinear.REFLECT, 2);


		final Canvas canvas = new Canvas(true, 0, 0, WIDTH, HEIGHT)
		{
			public boolean listensTo(Viewport view)
			{
				return false;
			}
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

				Fill[] fills = { cyclic, gradient, image, radial, solid, linear };

				for (int i = 0; i < FILLS; i++)
				{
					fills[i].setShape(rect);
					fills[i].select(gr);
					gr.fill(rect);

					fills[i].setShape(oval);
					fills[i].select(gr);
					gr.fill(oval);

					rect.x += interval;
					oval.x += interval;
				}
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

	private static BufferedImage createPattern(int w, int h)
	{
		BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

		Graphics2D gr = (Graphics2D)image.getGraphics();

		gr.setColor(Color.black);
		gr.fillRect(0, 0, w, h);

		gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		int qw = w >> 2;
				int qh = h >> 2;
				gr.setColor(Color.blue);
				gr.fillRect(0, 0, qw, qh);
				gr.fillRect(qw * 3, 0, qw, qh);
				gr.fillRect(qw * 3, qh * 3, qw, qh);
				gr.fillRect(0, qh * 3, qw, qh);
				gr.fillOval(qw, qh, qw << 1, qh << 1);

				gr.dispose();

				return image;
	}

}
