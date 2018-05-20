package com.philsprojects.chart.test;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import com.philsprojects.chart.common.Anchor;
import com.philsprojects.chart.common.Label;
import com.philsprojects.chart.fills.FillSolid;
import com.philsprojects.chart.view.ViewportComponent;

public class TestLabel
{

	public static void main(String[] args)
	{
		final int WIDTH = 256;
		final int HEIGHT = 256;

		final Label label = new Label(true, 0, 0, WIDTH, HEIGHT);
		label.setAnchor(Anchor.Center);
		label.setFontFill(new FillSolid(Color.black));
		label.setLabel("Hello World");
		label.setFont(new Font("Arial", Font.BOLD, 20));
		label.setRotation(0);
		label.setAxisRotation(0);


		final ViewportComponent panel = new ViewportComponent(WIDTH, HEIGHT);
		panel.addCanvas(label);

		final JFrame window = new JFrame("Legend Testing.");
		window.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		window.add(panel);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);

		new Thread("TestLabel Thread")
		{
			@Override
			public void run()
			{
				while (!interrupted())
				{
					label.setRotation(label.getRotation() + 1);
					try
					{
						sleep(10);
					}
					catch (InterruptedException e)
					{
						e.printStackTrace();
					}	
				}
			}
		}.start();
	}

}
